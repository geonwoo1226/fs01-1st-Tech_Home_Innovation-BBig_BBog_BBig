package service;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import dto.SensorDTO;
import dto.UserSessionDTO;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


//SensorServiceImpl — 로그인 유저 기반으로 유저가 mqtt pub로만 센서 제어
public class SensorServiceImpl implements SensorService {

    private static final String BROKER_URL = "tcp://localhost:1883"; // MQTT 브로커 주소
    
    
    //의문4 로그인을 한 userdto를 내가 신경안썼네? 로그인여부에 따라 센서보내지는 정도가 왔다갔다하는데?
    //현재 로그인한 사용자 정보
  	private UserSessionDTO currentUser;
    
    private static final String CLIENT_ID = "JavaAppController"; // 자바앱 MQTT 클라이언트 ID

    @Override
    public void controlDevice(UserSessionDTO userSession, SensorDTO sensorDTO, String message) {
        if (userSession == null || !userSession.isLoggedIn()) {
            System.out.println("❌ 로그인 필요");
            return;
        }

        String topic = buildTopic(sensorDTO);
        String clientId = CLIENT_PREFIX + userSession.getUserId();

        try (MqttClient client = new MqttClient(BROKER_URL, clientId, new MemoryPersistence())) {
            client.connect();

            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);

            client.publish(topic, mqttMessage);
            System.out.println("✅ MQTT 발행 완료: " + topic + " → " + message);

            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasControlPermission(UserSessionDTO userSession, String sensorId) {
        // TODO: 실제로는 DB에서 sensor.owner_id == userSession.userId 확인
        // 임시로 true 반환
        return userSession != null && userSession.isLoggedIn();
    }

    @Override
    public String buildTopic(SensorDTO sensorDTO) {
        return "home/" + sensorDTO.getSensorLocation() + "/" + 
               sensorDTO.getSensorType() + "/" + sensorDTO.getSensorId();
    }

    @Override
    public void controlDevice(String topic, String message) {
        try {
            // MQTT 클라이언트 생성
            MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
            client.connect();

            // 메시지 생성 및 발행
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1); // QoS 1: 최소 한 번 전달
            client.publish(topic, mqttMessage);

            System.out.println("✅ MQTT 전송 완료: topic=" + topic + ", message=" + message);

            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import view.DetailView;

public class SensorServiceImpl implements SensorService {
	@Override
	public void saveSensorData(String topic, String message) {
		// TODO Auto-generated method stub
		
	}
	DetailView view = new DetailView();

	public List<String> getSensorByRoom(String roomChoice) {
		List<String> sensors = new ArrayList<>();
		
	    
		while(true) {
			switch(roomChoice) {
			case "방1":
				sensors = Arrays.asList("LED", "커튼");
				break;
			case "방2":
				sensors = Arrays.asList("LED", "커튼");
				break;
			case "거실":
				sensors = Arrays.asList("LED", "화분센서");
				break;
			case "부엌":
				sensors = Arrays.asList("LED");
				break;
			}
			return sensors;
		}
		
	}	

    @Override
    public boolean hasControlPermission(String userId, String sensorId) {
        // 예시: DB 조회 또는 UserSession 확인 로직
        // 예를 들어 Sensor 테이블에서 userId == ownerId인지 확인
        // 지금은 임시로 true 리턴
        return true;
    }
}