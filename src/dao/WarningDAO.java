package dao;

import dto.UserSessionDTO;
import dto.WarningDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public interface WarningDAO {
	
	public static final String URL = "";
    public static final String USER = "";
    public static final String PASSWORD = "";
    
    //의문4 로그인을 한 userdto를 내가 신경안썼네? 로그인여부에 따라 센서보내지는 정도가 왔다갔다하는데?
    //현재 로그인한 사용자 정보
	
	public void insertWarning(WarningDTO dto);
	
	public List<WarningDTO> selectWarningsByTopic(String topic);

	List<WarningDTO> selectWarningsBySensor(String sensorId);

}
