package Controller;

import javax.swing.JOptionPane;

import dao.UserDAO;
import dao.UserDAOImpl;
import dto.LoginUserDTO;
import dto.UserDTO;
import dto.UserSessionDTO;
import mqtt.MqttManager;
import service.UserService;
import service.UserServiceImpl;
import util.ConsoleUtils;
import view.DetailView;
import view.MainView;

public class MainController {
	// 현재 로그인한 사용자 정보
	private UserSessionDTO currentUser = null;
	// 메인 화면을 담당하는 View 객체
	private final MainView view = new MainView();
	// 상세 화면을 담당하는 View 객체
	private final DetailView detailView = new DetailView();
	private final UserService service = new UserServiceImpl();
	private MqttManager mqttManager;

	public void run() {
		while (true) {
			if (currentUser == null) {
				// 로그인되지 않았을 때의 로직 처리
				handleInitialMenu();
			} else {
				// 로그인된 후의 로직 처리
				handleMainMenu();
				System.out.println("로그인");
			}
		}
	}

	// 로그인 되지 않았을 때 로직
	// 1번 로그인 2번 회원가입 9번 로그아웃 출력
	private void handleInitialMenu() {
		ConsoleUtils.clearConsole();
		// view는 메뉴를 보여주고 입력만 받아서 전달
		String choice = view.showInitialMenu();
		switch (choice) {
		case "1":
			register();
			break;
		case "2":
			login();
			break;

		case "9":
			exitProgram();
			break;
		default:
//			view.showMessage("(!) 잘못된 입력입니다.");

		}

	}

	// 회원가입
	private void register() {
		// 콘솔창 클리어
		ConsoleUtils.clearConsole();

		// View에 현재 사용자 이름을 넘겨주어 메뉴를 보여줌
		UserDTO user = view.showRegistrationForm();
		int result = service.register(user);

		new Thread(() -> {
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "회원가입이 성공했습니다.");
			} else if (result == 0) {
				JOptionPane.showMessageDialog(null, "회원가입이 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			} else if (result == -1) {
				JOptionPane.showMessageDialog(null, "존재하지 않는 동/호실 입니다.");
			}
		}).start(); // 스레드 시작
	}

	
	
	// 로그인
	private void login() {
		ConsoleUtils.clearConsole();
		
		// 사용자 입력값 가져오기
		LoginUserDTO loginUser = view.handleLogin();
		
		// (id, pass)로 사용자 확인 성공 시 UserDTO 반환
		UserDTO loginSuccessUser = service.login(loginUser.getUserId(), loginUser.getPass());
		
		// 로그인 성공하면 세션에 로그인 사용자 정보를 담고, 
		// Mqtt Subscriber를 실행함
		if(loginSuccessUser != null) {
			// 현재 사용자 정보 저장
			currentUser = new UserSessionDTO(loginSuccessUser);
			System.out.println("\n MQTT 서비스에 연결을 시작합니다.");
			//mqttManager = new MqttManager(currentUser.getLoginUser().getUserId());
			handleMainMenu();
		}else {
			JOptionPane.showMessageDialog(null, "로그인 실패");
			login();
		}
	
	}
	
	// 로그인 성공 시 실행
	private void handleMainMenu() {
		ConsoleUtils.clearConsole();
		// View에 현재 사용자 이름을 넘겨주어 메뉴를 보여주게 함
		String choice = view.showMainMenu(currentUser.getLoginUser());
		switch (choice) {
		case "1":
			handleSensorMenu();
			System.out.println("센서");
			break;
		case "2":
			showInfo();
			break;
		case "3":
			// infoUpdate();
			view.showMessage("사용자 정보 수정 메뉴입니다.");
			break;
		case "4":
			// martUse();
			view.showMessage("단지 마트 메뉴입니다.");
			break;
		case "5":
			// noticeBoard();
			view.showMessage("아파트 게시판입니다.");
			break;
		case "6":
			handleStateUpdate();
			view.showMessage("외출 상태 변환 메뉴입니다");
			break;
		case "7":
			view.showMessage("로그아웃");  
			login();
			break;
		}
	}
	
	private void handleStateUpdate() {
	    UserDAO dao = new UserDAOImpl();
	    UserDTO user = currentUser.getLoginUser();

	    String choice = detailView.stateUpdate(user);
	    String newState = null;

	    if ("1".equals(choice)) {
	        newState = "외출";
	    } else if ("2".equals(choice)) {
	        newState = "재택";
	    } else {
	        System.out.println("잘못된 값을 입력했습니다.");
	        return;
	    }

	    // 1) DB 반영
	    dao.stateUpdate(user, newState);

	    // 2) DTO에도 반영
	    user.setState(newState);

	    System.out.println("현재 상태: " + user.getState());
	}
	
	
	
	private void handleSensorMenu() {
		// 로그인된 유저 데이터 넘기기
		UserDTO user = currentUser.getLoginUser();
		SensorControl sensor = new SensorControl();
		// 센서 종류 선택
		detailView.selectSensorType(user);

//		switch(sensorType) {
//		case "1":
//			
//            System.out.println("LED관리");
//            sensor.ledSensor();
//            // 서비스 호출 → 센서 제어
//            break;
//        case "2":
//            System.out.println("커튼 관리");
//            break;
//        case "3":
//            System.out.println("화분 관리");
//            break;
//        case "4":
//        	handleMainMenu();
//        	break;
//        default:
//            view.showMessage("잘못된 선택입니다.");
//            break;
//            
//		}
		
		
		
		
	}
	
	private void showInfo() {
		if(currentUser == null) {
	        System.out.println("로그인된 사용자가 없습니다.");
	        return;
	    }

	    UserDTO user = currentUser.getLoginUser();

	    detailView.showUserInfo(user);
	}
	
	// 로그아웃
	private void exitProgram() {
		ConsoleUtils.clearConsole();
		
		MainView.exitProgram();
	}
	
	
}	
