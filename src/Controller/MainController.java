package Controller;

import javax.swing.JOptionPane;

import dto.LoginUserDTO;
import dto.UserDTO;
import dto.UserSessionDTO;
import mqtt.MqttManager;
import service.UserService;
import service.UserServiceImpl;
import util.ConsoleUtils;
import view.MainView;

public class MainController {
	// 현재 로그인한 사용자 정보
	private UserSessionDTO currentUser = null;
	// 화면을 담당하는 View 객체
	private final MainView view = new MainView();
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
		case "3":
			mqttManager = new MqttManager("");
			mqttManager.publish("home/test", "mqtt test");
			break;
		case "4":
			mqttManager = new MqttManager("");
			mqttManager.publish("home/test", "led_on");
			break;
		case "5":
			mqttManager = new MqttManager("");
			mqttManager.publish("home/test", "led_off");
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
//			mqttManager = new MqttManager(currentUser.getLoginUser().getUserId());
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
			SensorControl sensorContorl = new SensorControl();
			view.showMessage("센서 제어 메뉴입니다.");
			break;
		case "2":
			// myPage();
			view.showMessage("정보 조회 메뉴입니다.");
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
			// stateUpdate();
			view.showMessage("외출 상태 변환 메뉴입니다");
			break;
		case "7":
			view.showMessage("프로그램을 종료합니다");  // 일단 프로그램 종료로 하고, 이후 이전 페이지로 변동 예정
			exitProgram();
			break;

			
		}
		
	}
	
	
	
	// 로그아웃
	private void exitProgram() {
		ConsoleUtils.clearConsole();
		
		MainView.exitProgram();
	}
	
	
}	
