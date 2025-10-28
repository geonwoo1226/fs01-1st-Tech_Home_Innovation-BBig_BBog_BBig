package view;

import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import dto.NoticeDTO;
import dto.UserDTO;
import dto.WarningDTO;

public class AdminView {
	private static final Scanner scanner = new Scanner(System.in);
	
	
	public void showResidentList(List<UserDTO> residentsresidents) {
	    System.out.println("================ 입주민 목록 ====================");
	    System.out.printf("%-10s | %-10s | %-15s | %-5s | %-8s | %-6s\n",
	            "UserID", "Name", "PhoneNumber", "State", "Building", "Room");
	    System.out.println("=============================================");
	    // 데이터 출력
	    for (UserDTO user : residentsresidents) {
	        System.out.printf("%-10s | %-10s | %-15s | %-5s | %-8d | %-6s\n",
	                user.getUserId(),
	                user.getName(),
	                user.getPhoneNumber(),
	                user.getState(),
	                user.getBuilding(),
	                user.getRoomNum());
	        System.out.println("------------------------------------------------------------");
	    }
	    System.out.println("===============================================================");
		System.out.print("목록 나가기 >>>>");
		scanner.nextLine();
	}
	
	public void viewWarning(List<WarningDTO> warningList) {
		System.out.println("\n==================================================");
		System.out.println("      경고 수신함");
		System.out.println("===============================================================");
		System.out.printf("%-5s | %-10s | %-8s | %-8s | %-20s | %-20s\n",
		        "Room", "입주민ID", "감지센서", "경고타입", "내용", "발생날짜");
		System.out.println("--------------------------------------------------------------------------");

		for (WarningDTO notice : warningList) {
		    System.out.printf("%-5d | %-10s | %-8s | %-8s | %-20s | %-20s\n",
		            notice.getRoom_id(),
		            "[" + notice.getUser_id() + "]",
		            notice.getSensor(),
		            notice.getWarningType(),
		            notice.getDate() != null ? notice.getDate().toString() : "",
		            notice.getMessage() != null ? notice.getMessage() : ""

		    );
		    System.out.println("--------------------------------------------------------------------------");
		}

		System.out.println("===============================================================");
		System.out.print("게시판 나가기 >>>>");
		scanner.nextLine();

	}
	
	
	public void viewPostAdmin(List<NoticeDTO> noticeList) {
		System.out.println("\n==================================================");
		System.out.println("      아파트 소통 게시판");
		System.out.println("==================================================");
		System.out.println("번호\t | 카테고리 | 제목 \t \t| 내용 \t \t| 작성날짜 \t |");
		System.out.println("-------------------------------------------------------------------------------");
		int size = noticeList.size();

		for (int i = 0; i < size; i++) {
			NoticeDTO notice = noticeList.get(i);
			System.out.print(notice.getNoticeId() + "\t | ");
			System.out.print("[" + notice.getTitle() + "]" + "\t | ");
			System.out.print(notice.getType() + "\t | ");

			System.out.print(notice.getPost() + "\t | ");
			System.out.print(notice.getPostDate() + "\t | ");
			System.out.println("\n------------------------------------------------------------------------");
		}
		System.out.println("==============================================================================");
		System.out.print("게시판 나가기 >>>>");
		scanner.nextLine();

	}
}
