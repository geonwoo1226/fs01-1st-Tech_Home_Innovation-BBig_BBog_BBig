package Controller;

import dto.UserDTO;
import dto.UserSessionDTO;
import mqtt.MqttManager;
import service.SensorService;
import service.WarningService;
import service.SensorServiceImpl;
import service.WarningServiceImpl;

public class SensorControl {
//	public void ledSensor(2) {
//	if 1:
//	mqttManager = new MqttManager("");
//	mqttManager.publish("home/test", "led_off");
//	
//	2:
//		led 
	
	//모스키토주소
	private final String broker = "tcp://192.168.14.168:1883";
	//서브토픽
	private final String subTopic = "/home/#";
//}
  	private UserDTO currentUser = new UserDTO();
	
  	//sub만 하기
	private WarningService warningService = new WarningServiceImpl(currentUser);

	//pub만 하기
	private SensorService sensorService = new SensorServiceImpl();
	
//    public void onUserClickToggleLed(UserSessionDTO session, SensorDTO sensor) {
//        if (!sensorService.hasControlPermission(session, sensor.getSensorId())) {
//            System.out.println("❌ 권한 없음: " + session.getUserId());
//            return;
//        }
//
//        sensorService.controlDevice(session, sensor, "ON");
//    }
	
//	워닝서비스안의 함수2개
//    //sub받아서 화면에 출력
//	void subscribeAndDisplaySensorData(String brokerUrl, String topic);
//
//	//sub받아서 db에저장
//	void subscribeAndSaveSensorData(String brokerUrl, String topic);
    

    public void handleSensorControl(String sensorId, String message, UserDTO session) {
        String userId = session.getUserId();
        
     // 토픽 구성 (예: "sensor/livingroom/led1")
        String subTopic = "sensor/" + sensorId;

//        // 제어 권한 확인
//        if (!sensorService.hasControlPermission(userId, sensorId)) {
//            System.out.println("❌ 권한이 없습니다: " + userId);
//            return;
//        }

       

        //라즈베리로 제어 명령 전송
        //sensorService.controlDevice(subTopic, message)
    }
    
	private MqttManager mqttManager;

    public SensorControl() {
        mqttManager = new MqttManager("");
        System.out.println("test");
    }
  

    // 로그인 한 유저의 건물 동/호수/센서/on또는 off
	public void control(int building, String roomNum, String selectedSensor, String action, String selecteRoom) {
		MqttManager mqtt = new MqttManager();
		System.out.println("control2");
		
		//  ex) topic = building/1/room/101/led
		String address = building+"/"+roomNum+"/";
//		String topic = mqtt.getPubTopic()+address;
		String message =  selecteRoom+"/"+selectedSensor.toLowerCase()+"_"+action.toLowerCase();
		
		System.out.println("MQTT 전송 -> Topic: "+"home/"+address + ", Message: "+message);
		
		// 실제 MQTT publish
		// 센서 동작 제어 메시지
		mqttManager.publish("home/"+address, message);
		
		// lcd 출력 메시지 전송
		mqttManager.publish("home/lcd" , message + "/기능을 수행합니다.");
		
		
		 //라즈베리 센서 데이터받는코드
        warningService.subscribeAndDisplaySensorData(broker, subTopic);
        
        warningService.subscribeAndSaveSensorData(broker, subTopic);
		
	}

}
