package service;

import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttClient;

import dao.WarningDAO;
import dao.WarningDAOImpl;
import dto.UserDTO;
import dto.WarningDTO;

public class WarningServiceImpl implements WarningService {

    private WarningDAO dao = new WarningDAOImpl();
    
    public WarningServiceImpl() {
    	
    }


    public WarningServiceImpl(UserDTO logindUser) {
		// TODO Auto-generated constructor stub
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


	@Override
	public void saveWarning(String topic, String payload) {
		// TODO Auto-generated method stub
		
	}
}

