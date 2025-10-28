package service;

import java.util.List;

import dto.UserDTO;
import dto.WarningDTO;

public interface WarningService {
	
	// 방/유저 기반 경고 조회
	List<WarningDTO> viewWarning(int roomId, int userId, String sensor, String warningType, String message);

    // 방/유저 기반 경고 수정
	void saveWarning(int roomId, int userId, String sensor, String warningType, String message);

    // 토픽/페이로드 기반 경고 payload(메시지) String타입 저장
    void saveWarning(String topic, String payload);
    
    // 토픽/페이로드 기반 경고 바이트로 저장
    void saveWarning(String topic, byte[] bs);

	void saveWarning(UserDTO currentuser, String topic, String payload);

	void saveWarning(int roomId, String topic, String payload);
}
