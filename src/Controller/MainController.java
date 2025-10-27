package Controller;

import java.util.List;

import javax.swing.JOptionPane;

import dao.UserDAO;
import dao.UserDAOImpl;
import dto.LoginUserDTO;
import dto.NoticeDTO;
import dto.UserDTO;
import dto.UserSessionDTO;
import mqtt.MqttManager;
import service.AdminService;
import service.NoticeService;
import service.NoticeServiceImpl;
import service.SensorService;
import service.SensorServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import util.ConsoleUtils;
import view.AdminView;
import view.DetailView;
import view.MainView;

public class MainController {
    // 현재 로그인한 사용자 정보
    private UserSessionDTO currentUser = null;
    // 메인 화면
    private final MainView view = new MainView();
    // 공지 관련
    private NoticeService noticeService;
    private DetailView detailView;
    // 관리자 화면
    private final AdminView adminView = new AdminView();
    private final UserService service = new UserServiceImpl();
    private AdminService adminService;
    private MqttManager mqttManager;

    // 기본 생성자
    public MainController() {
        this.noticeService = new NoticeServiceImpl();        // ① 먼저 서비스 생성
        this.detailView = new DetailView(this.noticeService); // ② 그 서비스로 뷰 연결
    }

    // 다른 생성자 (필요하다면 유지)
    public MainController(AdminService adminService) {
        this(); // 기본 생성자 호출해서 위 두 개 먼저 초기화
        this.adminService = adminService;
    }

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
		if (loginSuccessUser != null) {
			// 현재 사용자 정보 저장

			currentUser = new UserSessionDTO(loginSuccessUser);
			System.out.println("\n MQTT 서비스에 연결을 시작합니다.");
			// mqttManager = new MqttManager(currentUser.getLoginUser().getUserId());
			
			
			// 1️ UserDTO 생성
	        UserDTO user = new UserDTO();
	        user.setUserId("user123"); // 예시 ID

	        // 2️ MqttManager 생성 (구독 시작)
	        MqttManager mqttManager = new MqttManager(user);

	        // 3️ 발행 테스트
	        String publishTopic = "/home/pc/livingroom/light";
	        String message = "Light ON";

	        mqttManager.publish(publishTopic, message);
			
			// 만약 로그인한 user_id가 admin일 경우
			if ("admin".equals(loginSuccessUser.getUserId())) {
				JOptionPane.showMessageDialog(null, "관리자로 로그인했습니다.");
				adminMainMenu();
			} else {
				JOptionPane.showMessageDialog(null, "로그인에 성공했습니다.");
				handleMainMenu();
			}
		} else {
			JOptionPane.showMessageDialog(null, "로그인 실패");
			login();
		}

	}

	// 관리자 메뉴
	private void adminMainMenu() {
		while (true) {
			ConsoleUtils.clearConsole();
			String choice = view.adminMainMenu(currentUser.getLoginUser());
			switch (choice) {
			case "1": // 빌딩 사용자 정보 조회
				handleListResidents();
				break;
			case "2": // 유저 정보 조회
				showInfo();
				break;
			case "3":
				userInfoUpdate();
				view.showMessage("사용자 정보 수정 메뉴입니다.");
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
				currentUser = null;

		        // 5️ 연결 종료
		        mqttManager.close();
				JOptionPane.showMessageDialog(null, "로그아웃 됐습니다.");
				run();
				break;
			}
		}
	}

	// 관리자가 입주민 정보를 확인
	private void handleListResidents() {
		ConsoleUtils.clearConsole();

		List<UserDTO> residents = adminService.getResidentList(currentUser.getLoginUser());

		adminView.showResidentList(residents);
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
			userInfoUpdate();
			view.showMessage("사용자 정보 수정 메뉴입니다.");
			break;
		case "4":
			// martUse();
			view.showMessage("단지 마트 메뉴입니다.");
			break;
		case "5":
			noticeBoard();
			view.showMessage("아파트 게시판입니다.");

			break;
		case "6":
			handleStateUpdate();
			view.showMessage("외출 상태 변환 메뉴입니다");
			break;
		case "7":
			view.showMessage("로그아웃");
			currentUser = null;
			JOptionPane.showMessageDialog(null, "로그아웃 됐습니다.");
			run();
			break;
		}
	}

	// 아파트 커뮤니티 게시판
	public void noticeBoard() {
		ConsoleUtils.clearConsole();
		UserDAO dao = new UserDAOImpl();
		UserDTO user = currentUser.getLoginUser();

		NoticeService noticeService = new NoticeServiceImpl();
		List<NoticeDTO> noticeList = noticeService.getAllPosts();
		
		int choice = detailView.noticeMenu(user);
		
		switch(choice) {
		case 1:
			detailView.writePost(user);   // 게시글 작성
			JOptionPane.showMessageDialog(null, "게시글이 작성됐습니다.");
			noticeBoard();
			break;
		case 2:
			detailView.viewPost(noticeList);    // 게시글 조회
			noticeBoard();
			break;
		case 3:
	//		detailView.viewPostDetail(user);    // 자신이 작성한 게시글 조회
			noticeBoard();
			break;
		case 4:
			handleMainMenu();
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

	private void userInfoUpdate() {
		UserDAO dao = new UserDAOImpl();
		UserDTO user = currentUser.getLoginUser();

		UserDTO updatedUser = detailView.userInfoUpdate(user);

		if (updatedUser != null) {
			service.updateUserInfo(user, updatedUser); // Service에 전달 → DB 반영
			JOptionPane.showMessageDialog(null, "사용자 정보가 수정됐습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않아 수정이 취소됐습니다.");
		}

	}

	private void handleSensorMenu() {
		// 로그인된 유저 데이터 넘기기
		UserDTO user = currentUser.getLoginUser();

		while (true) {
			// 장소 선택 -> DetailView에서 입력받음
			String selecteRoom = detailView.selectLocation(user);
			if ("PREV_MENU".equals(selecteRoom)) {
				return;
			}
			// 센서 목록 가져오기
			SensorService sensorService = new SensorServiceImpl();

			List<String> sensorList = sensorService.getSensorByRoom(selecteRoom);
			if (sensorList == null || sensorList.isEmpty()) {
				System.out.println("해당 방에는 센서가 없습니다.");
				continue; // 다시 방 선택
			}

			while (true) {
				// 센서 선택
				String selectedSensor = detailView.selectSensorType(selecteRoom, sensorList);
				if ("PREV_MENU".equals(selectedSensor)) {
					break;
				}

				// 센서 제어 (on/off 결과 받음)
				String action = detailView.onOffMenu(selectedSensor);
				if ("PREV_MENU".equals(action))
					continue; // 이전 메뉴 선택시 센서 선택으로 돌아감

				System.out.println("test");
				// 센서 컨트롤 (유저 동/호수, 제어 센서, on/off)
				SensorControl sensor = new SensorControl();

				// 센서 종류 선택
				sensor.control(user.getBuilding(), user.getRoomNum(), selectedSensor, action, selecteRoom);

				break;
			}
		}
	}

	private void showInfo() {
		if (currentUser == null) {
			System.out.println("로그인된 사용자가 없습니다.");
			return;
		}

		UserDTO user = currentUser.getLoginUser();

		detailView.showUserInfo(user);
	}

	// 로그아웃
	private void exitProgram() {
		ConsoleUtils.clearConsole();
		JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
		MainView.exitProgram();
	}

}
