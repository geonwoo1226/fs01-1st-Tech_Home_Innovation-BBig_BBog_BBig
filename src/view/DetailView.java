package view;

import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import dao.NoticeDAO;
import dto.NoticeDTO;
import dto.UserDTO;
import service.NoticeService;

public class DetailView {
	private static final Scanner scanner = new Scanner(System.in);
	private NoticeService noticeService;
	
	public DetailView() {
		
	}

	public DetailView(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}

	// 1번 제어할 방 먼저 선택
	public String selectLocation(UserDTO user) {
		while (true) {
			System.out.println("\n==================================================");
			System.out.printf("   %s님,   센서를 제어할 방을 선택하세요 \n", user.getUserId());
			System.out.println("==================================================");
			System.out.printf(" 현재 상태: %s		building: %d, roomNum: %s \n", user.getState(), user.getBuilding(),
					user.getRoomNum());
			System.out.println("  [1] 방1 💡");
			System.out.println("  [2] 방2 📊");
			System.out.println("  [3] 거실 ⚙️");
			System.out.println("  [4] 부엌 ⚙️");
			System.out.println("  [5] 이전 메뉴로 돌아가기");
			System.out.println("\n--------------------------------------------------");
			System.out.println("> 입력: ");

			String choice = scanner.next();

			switch (choice) {
			case "1":
				return "방1";
			case "2":
				return "방2";
			case "3":
				return "거실";
			case "4":
				return "부엌";
			case "5":
				return "PREV_MENU"; // 이전 메뉴
			default:
				System.out.println("잘못된 선택입니다. 다시 선택하세요");

			}
		}
	}

	// 어떤 센서를 제어할 것인지 (방마다 가지고 있는 센서가 다름)
	public String selectSensorType(String selecteRoom, List<String> sensors) {
		while (true) {
			System.out.println("\n==================================================");
			System.out.printf("   %s 센서 제어 시스템 \n", selecteRoom);
			System.out.println("==================================================");

			for (int i = 0; i < sensors.size(); i++) {
				System.out.printf("  [%d] %s%n", i + 1, sensors.get(i));
			}

			System.out.printf("  [%d] 이전 메뉴로 돌아기기 %n", sensors.size() + 1);
			System.out.println("> 입력: ");

			try {
				int choice = Integer.parseInt(scanner.next());
				if (choice >= 1 && choice <= sensors.size()) {
					return sensors.get(choice - 1);
				} else if (choice == sensors.size() + 1) {
					return "PREV_MENU"; // 이전메뉴로 돌아가기
				} else {
					// 팝업으로 출력
					JOptionPane.showMessageDialog(null, "잘못된 선택입니다. 다시 입력해주세요", "알림", JOptionPane.INFORMATION_MESSAGE);

				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요");
			}
		}
	}

	// 선택한 센서의 on/off 제어
	public String onOffMenu(String selectedSensor) {
		while (true) {
			System.out.println("\n==================================================");
			System.out.printf("   %s   센서 제어 시스템 \n", selectedSensor);
			System.out.println("==================================================");
			System.out.println("  [1] ON");
			System.out.println("  [2] OFF");
			System.out.println("  [3] 센서 선택으로 돌아가기");
			System.out.println("> 입력: ");

			String onOff = scanner.next();

			switch (onOff) {
			case "1":
				return "ON";
			case "2":
				return "OFF";
			case "3":
				return "PREV_MENU";
			default:
				System.out.println("잘못입력했습니다.");

			}

		}
	}

	// 2번 앱 로그인 후 정보 조회
	public void showUserInfo(UserDTO user) {

		String message = "=== 내 정보 조회 ===\n" + "아이디: " + user.getUserId() + "\n" + "이름: " + user.getName() + "\n"
				+ "전화번호: " + user.getPhoneNumber() + "\n" + "상태: " + user.getState() + "\n" + "건물: "
				+ user.getBuilding() + "\n" + "호실: " + user.getRoomNum();

		// 팝업으로 출력
		JOptionPane.showMessageDialog(null, message, "내 정보", JOptionPane.INFORMATION_MESSAGE);
	}

	// 3번 사용자 정보 수정
	public UserDTO userInfoUpdate(UserDTO user) {
		System.out.println("\n==================================================");
		System.out.println("      사용자 정보 수정");
		System.out.println("==================================================");
		System.out.printf("%s님의 정보를 수정합니다. \n", user.getUserId());
		System.out.println("현재 비밀번호를 입력하세요: ");
		String password = scanner.next();

		while (true) {
			if (password.equals(user.getPass())) {
				System.out.println("새로운 비밀번호를 입력하세요: ");
				String newPass = scanner.next();
				System.out.println("새 비밀번호 확인: ");
				String setPass = scanner.next();

				if (newPass.equals(setPass)) {
					System.out.printf("새로운 이름을 입력하세요 (현재 이름-%s): \n", user.getName());
					String newName = scanner.next();
					System.out.printf("새로운 전화번호를 입력하세요 (현재 번호-%s): \n", user.getPhoneNumber());
					String newPhone = scanner.next();
					// 숫자만 남기기 (혹시 - 넣은 경우 대비)
					newPhone = newPhone.replaceAll("[^0-9]", "");
					// 길이에 따라 하이픈 자동 삽입
					if (newPhone.length() == 11) {
						newPhone = newPhone.substring(0, 3) + "-" + newPhone.substring(3, 7) + "-"
								+ newPhone.substring(7);
					}
					user.setName(newName);
					user.setPhoneNumber(newPhone);
					System.out.println("정보가 수정되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
					continue;
				}
				user.setPass(newPass);
				return user;
				
			}
			
			return null;
		}
		
	}
	
	
	// 5번 아파트 게시판
	public int noticeMenu(UserDTO user) {
		System.out.println("\n==================================================");
		System.out.println("      아파트 자유 게시판입니다.");
		System.out.println("==================================================");
		System.out.println("  [1] 게시글 작성");
		System.out.println("  [2] 게시글 목록 보기");
		System.out.println("  [3] 작성한 게시글 보기");
		System.out.println("  [4] 메뉴로 돌아가기");

		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");

		return scanner.nextInt();
	}
	
	
	public int writePost(UserDTO user) {
		if(scanner.hasNextLine()) {
		    scanner.nextLine(); 
		}
		
		System.out.println("\n==================================================");
		System.out.println("      게시글을 작성합니다");
		System.out.println("==================================================");
		System.out.println("  [민원/소통] 카테고리를 선택해주세요. ");
		System.out.println("  [1] 민원");
		System.out.println("  [2] 소통");
		System.out.print("> 입력: ");
		String type = scanner.nextLine();
		
		switch(type) {
		case "1": type = "민원"; break;
		case "2": type = "소통"; break;
		}
		
		System.out.print("  [제목]: ");
		String title = scanner.nextLine();
		System.out.print("  [내용]: ");
		String post = scanner.nextLine();

		System.out.println("\n--------------------------------------------------");

	    // NoticeDTO 객체 생성
	    NoticeDTO notice = new NoticeDTO();
	    notice.setType(type);
	    notice.setTitle(title);
	    notice.setPost(post);
	    
		return noticeService.writePost(user, notice);
	}

	public void viewPost(List<NoticeDTO> noticeList) {
		scanner.nextLine(); 
		System.out.println("\n==================================================");
		System.out.println("      아파트 소통 게시판");
		System.out.println("==================================================");
		System.out.println("번호\t | 카테고리 | 제목 \t \t| 내용 \t \t| 작성날짜 \t |");
		System.out.println("-------------------------------------------------------------------------------");
		int size = noticeList.size();
		
		for(int i=0; i < size; i++) {
			NoticeDTO notice = noticeList.get(i);
			System.out.print(notice.getNoticeId()+ "\t | ");
			System.out.print("["+notice.getTitle()+"]"+ "\t | ");
			System.out.print(notice.getType()+ "\t | ");

			System.out.print(notice.getPost()+ "\t | ");
			System.out.print(notice.getPostDate()+ "\t | ");
			System.out.println("\n------------------------------------------------------------------------");
		}
		System.out.println("==============================================================================");
		System.out.print("게시판 나가기 >>>>");
		scanner.nextLine();
		
	}
	
//	public void viewPostDetail(UserDTO user) {
//		scanner.nextLine(); 
//		System.out.println("\n==================================================");
//		System.out.printf("      %s님이 작성한 목록 \n", user.getName());
//		System.out.println("==================================================");
//		System.out.println("번호\t | 카테고리 | 제목 \t \t| 내용 \t \t| 작성날짜 \t |");
//		System.out.println("-------------------------------------------------------------------------------");
//		int size = myPostlist.size();
//		
//		for(int i=0; i < size; i++) {
//			NoticeDTO notice = noticeList.get(i);
//			System.out.print(notice.getNoticeId()+ "\t | ");
//			System.out.print("["+notice.getTitle()+"]"+ "\t | ");
//			System.out.print(notice.getType()+ "\t | ");
//
//			System.out.print(notice.getPost()+ "\t | ");
//			System.out.print(notice.getPostDate()+ "\t | ");
//			System.out.println("\n------------------------------------------------------------------------");
//		}
//		System.out.println("==============================================================================");
//		System.out.print("게시판 나가기 >>>>");
//		scanner.nextLine();
//		
//	}

	// 6번 상태 변환 시스템
	public String stateUpdate(UserDTO user) {
		System.out.println("\n==================================================");
		System.out.println("      외출 상태 변환 시스템");
		System.out.println("==================================================");
		System.out.printf("%s님, 현재 외출/귀가 상태를 선택해주세요         현재 상태: %s		building: %d, roomNum: %s%n",
				user.getUserId(), user.getState(), user.getBuilding(), user.getRoomNum());
		System.out.println("  [1] 외출");
		System.out.println("  [2] 재택");
		System.out.println("  [3] 메뉴로 돌아가기");

		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");

		return scanner.next();
	}




}
