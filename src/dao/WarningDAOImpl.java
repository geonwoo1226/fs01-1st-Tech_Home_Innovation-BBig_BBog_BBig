package dao;

import dto.WarningDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarningDAOImpl implements WarningDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/smart_home_system";
    private static final String USER = "sample";
    private static final String PASSWORD = "1234";

    // ✅ Warning 데이터 저장
    @Override
    public void insertWarning(WarningDTO warningDTO) {
        String sql = "INSERT INTO warning (sensor_id, message, warning_date, user_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, warningDTO.getSensorId());
            pstmt.setString(2, warningDTO.getMessage());
            pstmt.setTimestamp(3, warningDTO.getWarningDate());
            pstmt.setString(4, warningDTO.getUserId()); 
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ sensor_id(혹은 user_id) 기준으로 Warning 조회
    public List<WarningDTO> selectWarningsBySensor(String sensorId) {
        List<WarningDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM warning WHERE sensor_id = ? ORDER BY warning_date DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sensorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WarningDTO dto = new WarningDTO(
                    rs.getInt("warning_id"),
                    rs.getString("sensor_id"),
                    rs.getString("message"),
                    rs.getTimestamp("warning_date"),
                    rs.getString("user_id")
                );
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

	@Override
	public List<WarningDTO> selectWarningsByTopic(String topic) {
		// TODO Auto-generated method stub
		return null;
	}
}
