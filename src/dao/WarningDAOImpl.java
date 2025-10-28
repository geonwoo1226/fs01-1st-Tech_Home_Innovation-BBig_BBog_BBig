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
	public List<WarningDTO> getAllWarning(WarningDTO warning) {
		String sql = "select w.room_id, user_id, w.sensor, w.warning_type, w.message, w.date, u.phone_number "
				+ "from warning w join user u on w.room_id = u.room_id";
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		
		List<WarningDTO> warningList = new ArrayList<WarningDTO>();
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				warning = new WarningDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				warningList.add(warning);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, ptmt, con);
		}
		
		
		return warningList;
	}


}
