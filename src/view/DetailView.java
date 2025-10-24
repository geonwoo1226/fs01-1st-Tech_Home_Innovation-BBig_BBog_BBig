package view;

import java.util.Scanner;

import javax.swing.JOptionPane;

import dto.UserDTO;

public class DetailView {
	private static final Scanner scanner = new Scanner(System.in);

	// 1번 센서 관리
	public void selectSensorType(UserDTO user) {
			
		System.out.println("\n==================================================");
		System.out.println("      센서 제어 시스템");
		System.out.println("==================================================");
		System.out.printf("%s님, 환영합니다!         현재 상태: %s		building: %d, roomNum: %s%n", user.getUserId(), user.getState(), user.getBuilding(), user.getRoomNum());
		System.out.println("  [1] LED 관리 💡");
		System.out.println("  [2] 커튼 Open/Close 📊");
		System.out.println("  [3] 화분 ⚙️");
		System.out.println("  [4] 이전 메뉴로 돌아가기");
		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");
		
		String select = scanner.nextLine();
		
		selectLocation(select, user);
	}
	
	// 1-1 LED -> 방 선택
	public void selectLocation(String select, UserDTO user) {
		System.out.println("\n==================================================");
		System.out.println("      센서를 제어할 방을 선택하세요");
		System.out.println("==================================================");
		System.out.printf("%s님, 환영합니다!         현재 상태: %s		building: %d, roomNum: %s%n", user.getUserId(), user.getState(), user.getBuilding(), user.getRoomNum());
		System.out.println("  [1] 방1 💡");
		System.out.println("  [2] 방2 📊");
		System.out.println("  [3] 거실 ⚙️");
		System.out.println("  [4] 부엌 ⚙️");
		System.out.println("  [5] 이전 메뉴로 돌아가기");
		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");
		
		switch(select) {
		case "1":
			System.out.println("방1  ----> on/ off");
		}
		
	}
	

	// 6번 상태 변환 시스템
	public String stateUpdate(UserDTO user) {
		System.out.println("\n==================================================");
		System.out.println("      외출 상태 변환 시스템");
		System.out.println("==================================================");
		System.out.printf("%s님, 현재 외출/귀가 상태를 선택해주세요         현재 상태: %s		building: %d, roomNum: %s%n", user.getUserId(), user.getState(), user.getBuilding(), user.getRoomNum());
		System.out.println("  [1] 외출");
		System.out.println("  [2] 재택");

		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");
		
		return scanner.nextLine();
	}
	
	

	
	// 2번 앱 로그인 후 정보 조회
	public void showUserInfo(UserDTO user) {

		 String message = "=== 내 정보 조회 ===\n"
                 + "아이디: " + user.getUserId() + "\n"
                 + "전화번호: " + user.getPhoneNumber() + "\n"
                 + "상태: " + user.getState() + "\n"
                 + "건물: " + user.getBuilding() + "\n"
                 + "호실: " + user.getRoomNum();

		 // 팝업으로 출력
		 JOptionPane.showMessageDialog(null, message, "내 정보", JOptionPane.INFORMATION_MESSAGE);
	}

}
