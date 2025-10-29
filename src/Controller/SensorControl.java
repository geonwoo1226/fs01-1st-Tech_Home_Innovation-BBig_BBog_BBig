package Controller;

import dto.UserDTO;
import mqtt.MqttManager;

public class SensorControl {
    private MqttManager mqttManager;

    // 로그인된 사용자 전달
    public SensorControl(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보가 null입니다.");
        }
        mqttManager = new MqttManager(user); // 내부에서 client 생성 및 connect
        System.out.println("SensorControl 초기화 완료, MQTT 연결됨");
    }

    public void control(int building, String roomNum, String selectedSensor, String action, String selecteRoom) {
		MqttManager mqtt = new MqttManager();
    	String address = building + "/" + roomNum + "/";
        String message = selecteRoom + "/" + selectedSensor.toLowerCase() + "_" + action.toLowerCase();

        System.out.println("MQTT 전송 -> Topic: home/" + address + ", Message: " + message);

        // 센서 제어 메시지
        mqttManager.publish("home/" + address, message);

        // lcd 메시지
        System.out.println("home/lcd"+"message: "+ message + "/기능을 수행합니다.");
        mqttManager.publish("home/lcd", message + "/기능을 수행합니다.");

        System.out.println("끝");
    }
}
