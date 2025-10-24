package Controller;

import mqtt.MqttManager;

public class SensorControl {
	private MqttManager mqttManager;
	
	public void ledSensor() {
		mqttManager = new MqttManager("");
		mqttManager.publish("home/test", "led_off");
	}
}
