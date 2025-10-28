package dao;

import dto.UserSessionDTO;
import dto.WarningDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public interface WarningDAO {
	
	// 경고 목록 조회
	List<WarningDTO> getAllWarning(WarningDTO warning);


}
