package Controller;

import mqtt.MqttManager;

public class SensorControl {
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
		
		
	}

}
