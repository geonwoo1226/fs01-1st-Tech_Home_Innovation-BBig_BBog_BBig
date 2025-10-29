package service;

import java.sql.*;
import java.util.*;

import dao.UserDAO;
import dao.UserDAOImpl;
import dto.LoginUserDTO;
import dto.UserDTO;
import dto.UserSessionDTO;
import dto.WarningDTO;
import service.WarningService;
import util.DBUtil;

import dao.WarningDAO;
import dao.WarningDAOImpl;

import dto.UserDTO;
import dto.WarningDTO;

public class WarningServiceImpl implements WarningService {

    private UserDTO userDTO;
    private String id;
    
    private WarningDAO dao = new WarningDAOImpl();
    
    public WarningServiceImpl() {
    	
    }
	
    @Override
    public List<WarningDTO> viewWarning(int roomId, int userId, String sensor, String warningType, String message) {
        WarningDTO warning = new WarningDTO(
            roomId,       // 방 아이디
            userId,       // 사용자 아이디
            sensor,       // 센서 이름
            warningType,  // 경고 타입
            message,      // 경고 메시지
            null,         // date 자동 입력
            null          // phoneNumber
        );

        return dao.getAllWarning(warning);
    }
    
    
    //1차로 데이터베이스 저장함수
    @Override
    public void saveWarning(String topic, String payload) {
        // 예: payload = "1,dht,화재,화재 발생"
    	
        String[] data = payload.split(",");

        if (data.length == 4) {
            Connection conn = DBUtil.getConnect();
            String sql = "INSERT INTO warning (room_id, sensor, warning_type, message) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(data[0]));
                pstmt.setString(2, data[1]);
                pstmt.setString(3, data[2]);
                pstmt.setString(4, data[3]);
                pstmt.executeUpdate();
                System.out.println("✅ 경고 메시지 저장 완료 (" + payload + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.err.println("⭕ 데이터 형식 크기가 4입니다. 정확히 맞는 데이터 형식입니다.");
            return;
        }else if(data.length == 3) {
        	System.err.println("❌ 데이터 형식 크기가 3입니다. 데이터베이스의 스키마수와 부적합합니다.");
            return;
        }else if(data.length == 2) {
        	System.err.println("❌ 데이터 형식 크기가 2입니다. 데이터베이스의 스키마수와 부적합합니다.");
            return;
        }else if (data.length == 1) {
        	
        	
//            String sql = "INSERT INTO warning (message) VALUES (?)";
//            try (Connection conn = DBUtil.getConnect();
//                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, data[0]);
//                pstmt.executeUpdate();
//                System.out.println("✅ 경고 메시지 저장 완료 (" + data[0] + ")");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        	
        	// 1️ 로그인한 유저의 roomId 가져오기
        

            // 2️ DB 연결 및 저장

            System.err.println("⭕ 데이터 형식 크기가 1입니다. 화재발생 메시지만 저장합니다");
            return;
            
            
        }else if(data.length == 0) {
        	System.err.println("❌ 데이터 형식 크기가 0입니다. 데이터베이스의 스키마수와 부적합합니다.");
            return;
        }


    }
    
    
    
    //현재 로그인한 유저의 정보중 룸아이디를 데이터베이스에 저장함수
    @Override
    public void saveWarning(int roomId, String topic, String payload) {
        // 메시지 예: payload = "1,dht,화재,화재 발생"
    	
        String[] data = payload.split(",");

        if (data.length == 4) {
            System.err.println("⭕ 데이터 형식 크기가 4입니다. 정확히 맞는 데이터 형식입니다.");
            return;
        }else if(data.length == 3) {
        	System.err.println("❌ 데이터 형식 크기가 3입니다. 데이터베이스의 스키마수와 부적합합니다.");
            return;
        }else if(data.length == 2) {
        	System.err.println("❌ 데이터 형식 크기가 2입니다. 데이터베이스의 스키마수와 부적합합니다.");
            return;
        }else if (data.length == 1) {
 	
        	// 1️ 로그인한 유저의 roomId 가져오기
    		int userRoomId = roomId;
    		
            String warning_type;

//            int roomId = UserDTO.getRoomId();
//            
//            if(roomId == 0) {
//            	roomId = 2;
//            }

            // 2️ DB 연결 및 저장
            String sql = "INSERT INTO warning (room_id, warning_type , message) VALUES (?, ?, ?)";

            try (Connection conn = DBUtil.getConnect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

            	//룸아이디 정보가 없으면 룸아이디 정보를 1로 만들기
            	if(userRoomId==0) {
            		userRoomId=1;
            	}
            	
            	if(payload=="fire") {
            		warning_type="화재";
            	}else {
            		warning_type="침입자 감지";
            	}
            	
                pstmt.setInt(1, userRoomId);
                pstmt.setString(2, warning_type);
                pstmt.setString(3, payload);
                pstmt.executeUpdate();

                //System.out.println("✅  경고 메시지 저장 완료 (userRoomId=" + userRoomId + " warning_type = " +warning_type+",  message=" + payload + ")");

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("❌  경고 메시지 저장 실패 (userRoomId=" + userRoomId + "message=" + payload + ")");
            }
            //System.err.println("⭕ 데이터 형식 크기가 1입니다. 화재발생 메시지만 저장합니다");
            return;
            
            
        }else if(data.length == 0) {
        	System.err.println("❌ 데이터 형식 크기가 0입니다. 데이터베이스의 스키마수와 부적합합니다.");
            return;
        }


    }


	@Override
	public void saveWarning(int roomId, int userId, String sensor, String warningType, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWarning(String topic, byte[] bs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWarning(UserDTO currentuser, String topic, String payload) {
		// TODO Auto-generated method stub
		
	}
	
	
}
