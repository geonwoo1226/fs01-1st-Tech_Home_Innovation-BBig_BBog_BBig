package view;



import java.util.Scanner;

import javax.swing.JOptionPane;

import dto.LoginUserDTO;
import dto.UserDTO;

public class MainView {
	private static final Scanner scanner = new Scanner(System.in);

	// 앱 실행 첫 화면
	public String showInitialMenu() {
		System.out.println("\n==================================================");
		System.out.println("     🖥 테크 홈 이노베이션 - 삑뽁삑(BBig BBog BBig) 🖥");
		System.out.println("==================================================");
		System.out.println("\n환영합니다! 원하시는 메뉴를 선택해주세요.\n");
		System.out.println("  [1] 회원가입");
		System.out.println("  [2] 로그인");
		System.out.println("  [9] 프로그램 종료");
		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");

		return scanner.nextLine();
	}
	
	// 회원가입 정보를 입력받아 DTO 객체로 반환하는 메소드
	public UserDTO showRegistrationForm() {
		System.out.println("\n=========================");
		System.out.println("              회원가입           ");
		System.out.println("---------------------------");
		UserDTO newUser = new UserDTO();
		
		System.out.println("아이디: ");
		newUser.setUserId(scanner.next());
		
		while (true) {
			System.out.println("비밀번호: ");
			String password = scanner.next();
			System.out.println("비밀번호 확인");
			String passwordConfirm = scanner.next();
			
			if(password.equals(passwordConfirm)) {
				newUser.setPass(passwordConfirm);
				break;
			} else {
				System.out.println("\n 비밀번호가 일치하지 않습니다.");
			}
		}
		

		System.out.println("핸드폰 번호: ");
		newUser.setPhoneNumber(scanner.next());
		
		System.out.println("(아파트) 동: ");
		newUser.setBuilding(scanner.nextInt());
		System.out.println("(아파트) 호실: ");
		newUser.setRoomNum(scanner.next());
		
		System.out.println("-----------------");
		
		return newUser;
	}
	
	// 로그인 뷰
	public LoginUserDTO handleLogin() {
		System.out.println("\n=========================");
		System.out.println("              로그인           ");
		System.out.println("---------------------------");
		System.out.println("아이디: ");
		String userId = scanner.nextLine();
		System.out.println("비밀번호: ");
		String pass = scanner.nextLine();
		return new LoginUserDTO(userId, pass);
	}
	
	
	// 로그인 성공 시 화면
	public String showMainMenu(UserDTO userDTO) {
		
		System.out.println("\n==================================================");
		System.out.println("      🌿 라즈베리파이 스마트홈 제어 시스템 🌿");
		System.out.println("==================================================");
		System.out.printf("%s님, 환영합니다!         현재 상태: %s 중입니다. \n", userDTO.getUserId(), userDTO.getState());
		System.out.println("  [1] 센서 관리 💡");
		System.out.println("  [2] 정보 조회 📊");
		System.out.println("  [3] 사용자 정보 수정 ⚙️");
		System.out.println("  [4] 단지 마트");
		System.out.println("  [5] 아파트 게시판");
		System.out.println("  [6] 외출 상태 변환");
		System.out.println("  [7] 로그아웃");
		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");
		return scanner.nextLine();
	}
	
	
	
	
	public void showMessage(String string) {
		
	}
	
	
	// 프로그램 종료
	public static void exitProgram() {
		System.out.println("프로그램을 종료합니다.");
		System.exit(0);
	}

}
