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
		
		
//		String sql = "select w.room_id, user_id, w.sensor, w.warning_type, w.message, w.date, u.phone_number "
//				+ "from warning w join user u on w.room_id = u.room_id";
		
		String sql = """
			    SELECT 
			        t.room_id,
			        t.user_id,
			        t.sensor,
			        t.warning_type,
			        t.message,
			        t.warning_date,  -- 별칭으로 명확하게 변경
			        t.phone_number
			    FROM (
			        SELECT 
			            w.room_id,
			            u.user_id,
			            w.sensor,
			            w.warning_type,
			            w.message,
			            w.`date` AS warning_date,  -- 별칭 지정
			            u.phone_number,
			            ROW_NUMBER() OVER (
			                PARTITION BY w.message 
			                ORDER BY u.user_id ASC
			            ) AS rn
			        FROM warning AS w
			        INNER JOIN user AS u 
			            ON w.room_id = u.room_id
			    ) AS t
			    WHERE t.rn <= 2
			    ORDER BY t.user_id ASC;
			    """;
		
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		
		List<WarningDTO> warningList = new ArrayList<WarningDTO>();
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				//warning = new WarningDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				
				warning = new WarningDTO(
       // warningId
					    rs.getInt("room_id"),           // room_id
					    rs.getString("user_id"),        // user_id
					    rs.getString("sensor"),         // sensor
					    rs.getString("warning_type"),   // warningType
					    rs.getString("message"),  // date -> SQL에서 별칭 지정
					    rs.getString("warning_date")
					    
					    
					    //rs.getString("phone_number")    // phoneNumber
					);
				
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
