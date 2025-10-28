package mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import dto.UserDTO;
import dto.UserSessionDTO;
import service.SensorService;
import service.SensorServiceImpl;
import service.WarningService;
import service.WarningServiceImpl;

public class MqttManager implements MqttCallback {

	private UserDTO logindUser;
	private String id;
	private MqttClient client;
	private final String broker = "tcp://192.168.14.39:1883";

	private final String pubTopic = "/home/";

	// 서브토픽
	private final String subTopic = "/home/#"; // home 하위의 모든 토픽을 구독

	// private SensorService service = new SensorServiceImpl();

	private SensorService sensorService = new SensorServiceImpl();

	private WarningService warningService = new WarningServiceImpl();

	String clientId;

	public MqttManager() {

	}

	// UserDTO 기반 생성자
	public MqttManager(UserDTO userDTO) {
		if (userDTO == null) {
			throw new IllegalArgumentException("UserDTO cannot be null");
		}

		this.logindUser = userDTO;
		this.id = logindUser.getUserId(); // userDTO에서 ID 가져오기

		try {
			// 고유한 클라이언트 ID 생성 (브로커안 통신 충돌 방지)
			clientId = this.id.toString() + UUID.randomUUID().toString();
			client = new MqttClient(broker, clientId);

			// 연결 옵션 설정
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			// MqttCallback 인터페이스를 현재 클래스가 구현했으므로 this로 설정
			client.setCallback(this);

			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);
			System.out.println("Connected.");

			// 연결 후 바로 구독 시작
			this.subscribe();

		} catch (MqttException me) {
			me.printStackTrace();
		}
	}

	public MqttManager(String string) {
		// TODO Auto-generated constructor stub
	}

	private void subscribe() {
		try {
			this.client.subscribe(clientId + subTopic);
			System.out.println("Subscribed to topic: " + clientId + subTopic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void publish(String topic, String content) {
		try {
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(0); // QoS Level 0
			this.client.publish(topic, message);
			System.out.println("Message published.");
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}

	public void close() {
		try {
			this.client.disconnect();
			this.client.close();
			System.out.println("Disconnected.");
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	// ---- MqttCallback 인터페이스의 메소드들 ---- //

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost: " + cause.getMessage());
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String payload = new String(message.getPayload());

		System.out.println("\n=============== MESSAGE ARRIVED ===============");
		System.out.println(" Topic: " + topic);
		System.out.println(" Message: " + payload);
		System.out.println("==============================================");

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// 발행 완료 시 호출
	}

}
