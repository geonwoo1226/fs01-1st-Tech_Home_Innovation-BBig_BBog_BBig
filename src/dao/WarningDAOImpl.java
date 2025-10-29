package dao;

import dto.WarningDTO;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarningDAOImpl implements WarningDAO {


//	@Override
//	public void insertWarning(WarningDTO warning) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public List<WarningDTO> getAllWarning(WarningDTO dummy) { // 매개변수 이름 dummy로 바꿈
	    String sql = "select w.room_id, user_id, w.sensor, w.warning_type, w.message, w.date, u.phone_number "
	               + "from warning w join user u on w.room_id = u.room_id";
	    Connection con = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;

	    List<WarningDTO> warningList = new ArrayList<>();
	    try {
	        con = DBUtil.getConnect();
	        ptmt = con.prepareStatement(sql);
	        rs = ptmt.executeQuery();

	        while (rs.next()) {
	            WarningDTO warning = new WarningDTO(
	                rs.getInt("room_id"),
	                rs.getString("user_id"),
	                rs.getString("sensor"),
	                rs.getString("warning_type"),
	                rs.getString("message"),
	                rs.getString("date"),
	                rs.getString("phone_number")
	            );
	            warningList.add(warning);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(rs, ptmt, con);
	    }

	    return warningList;
	}


}
