package view;

import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import dto.UserDTO;

public class AdminView {
	private static final Scanner scanner = new Scanner(System.in);
	
	
	public void showResidentList(List<UserDTO> residentsresidents) {

	    StringBuilder sb = new StringBuilder();
	    sb.append("=== 입주민 목록 ===\n");
	    for(UserDTO user : residentsresidents) {
	        sb.append(String.format("%s | %s | %s | %s | %d | %s \n ---------------------------------------------\n", user.getUserId(), user.getName(), user.getPhoneNumber(),user.getState(), user.getBuilding(), user.getRoomNum()));
	    }

	    // 팝업으로 표시
	    JOptionPane.showMessageDialog(null, sb.toString(), "입주민 목록", JOptionPane.INFORMATION_MESSAGE);
	}
}
