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

public class WarningServiceImpl implements WarningService {

    private UserDTO userDTO;
    private String id;
	
	// roomId 기준 경고 조회
    @Override
    public List<WarningDTO> viewWarning(int roomId, int userId, String sensor, String warningType, String message) {
        List<WarningDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM warning WHERE room_id = ?";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnect();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, roomId);
            rs = pstmt.executeQuery();

//            while (rs.next()) {
//                WarningDTO dto = new WarningDTO(
//                    rs.getInt("warning_id"),
//                    rs.getInt("room_id"),
//                    rs.getString("sensor"),
//                    rs.getString("warning_type"),
//                    rs.getString("message"),
//                    rs.getTimestamp("date")
//                );
//                list.add(dto);
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, con);
        }

        return list;
    }

    // 경고 조회 (roomId 기준 sql은 직접짜는 형식)
//    @Override
//    public List<WarningDTO> viewWarning(int roomId, int userId, String sensor, String warningType, String message) {
//        List<WarningDTO> list = new ArrayList<>();
//        String sql = "SELECT * FROM warning WHERE room_id = ?";
//
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, roomId);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                WarningDTO dto = new WarningDTO(
//                    rs.getInt("warning_id"),
//                    rs.getInt("room_id"),
//                    rs.getString("sensor"),
//                    rs.getString("warning_type"),
//                    rs.getString("message"),
//                    rs.getTimestamp("date")
//                );
//                list.add(dto);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    
    
//    // MQTT로부터 SQL문을 직접 받아 실행하는 방식
//    @Override
//    public void saveWarning(String topic, String payload) {
//        System.out.println("📥 수신된 SQL: " + payload);
//        try (Statement stmt = conn.createStatement()) {
//            int result = stmt.executeUpdate(payload);
//            System.out.println("✅ SQL 실행 완료 (" + result + " rows affected)");
//        } catch (SQLException e) {
//            System.err.println("❌ SQL 실행 오류: " + e.getMessage());
//        }
//    }
    
    
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
	

    // SQL 기반 저장 (payload가 SQL문)
//    @Override
//    public void saveWarning(String topic, String payload) {
//        System.out.println("📩 수신 토픽: " + topic);
//        System.out.println("📥 수신된 SQL 쿼리: " + payload);
//
//        Connection con = null;
//        Statement stmt = null;
//
//        try {
//            con = DBUtil.getConnect();
//            stmt = con.createStatement();
//
//            int result = stmt.executeUpdate(payload);
//            System.out.println("✅ SQL 실행 성공 (" + result + "행 처리됨)");
//
//        } catch (SQLException e) {
//            System.err.println("❌ SQL 실행 실패: " + e.getMessage());
//        } finally {
//            DBUtil.close(null, stmt, con);
//        }
//    }
	
	
}
