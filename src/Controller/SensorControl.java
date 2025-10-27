package Controller;

import dto.UserDTO;
import dto.UserSessionDTO;



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
	// 현재 로그인한 사용자 정보
	private UserDTO currentUser = null;
	
	//로그인해야함
	// 로그인된 사용자 정보
	UserDTO session = new UserDTO();
	
	//모스키토주소
	private final String broker = "tcp://192.168.14.168:1883";
	//서브토픽 모음
	
	//토픽1
	private final String subTopic = "/home/#";
	//토픽2
	private final String topic = "raspberry/sensor/temp/1";
//}
	
  	//sub만 하기
	private WarningService warningService = new WarningServiceImpl(currentUser);
	
	WarningServiceImpl service = new WarningServiceImpl(session);



	//pub만 하기
	private SensorService sensorService = new SensorServiceImpl();
	
	
	
	
    // 콘솔 출력만
    // service.subscribeAndDisplaySensorData(broker, topic);

    // DB 저장용
    //service.subscribeAndSaveSensorData(broker, topic);
	
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

        //라즈베리 센서테이버 받기
        warningService.subscribeAndDisplaySensorData(broker, subTopic);
        
        warningService.subscribeAndSaveSensorData(broker, subTopic);

        //라즈베리로 제어 명령 전송
        sensorService.controlDevice(subTopic, message);
    

}
