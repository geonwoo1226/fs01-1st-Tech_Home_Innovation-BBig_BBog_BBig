package service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import dto.UserDTO;

public class MqttPubSubServiceImpl implements MqttPubSubService, MqttCallback {

	
	//로그인된 유저정보 담을 UserDTO 이렇게만 선언해도 로그인유저의 정보가 담아지나?
    private UserDTO userDTO;
	
    private MqttClient client;
    private final String broker = "tcp://192.168.14.39:1883"; // 라즈베리파이 MQTT 브로커
    
    
    private final String subTopic = "/home/#"; // 전체 구독
    //private final String pubTopic = "/home/#"; // 전체 구독

	private int roomId;

	private String id;
    
    //로그인된 아이디로 mqtt접속
    public MqttPubSubServiceImpl() {
        //this.id = userDTO.getUserId();
    	
    	try {
    		this.roomId = userDTO.getRoomId();
    	}catch (NullPointerException e) {
			System.out.println(e);
		}
        

        try {
            // 고유한 클라이언트 ID 생성
            String clientId = "pubsub_" + UUID.randomUUID();
            client = new MqttClient(broker, clientId);

            // 연결 옵션 설정
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            client.setCallback(this);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected as " + clientId);

            subscribe(); // 자동 구독 시작

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    // 🔹 생성자: 사용자 기준 MQTT 클라이언트 생성 + 구독
    public MqttPubSubServiceImpl(UserDTO userDTO) {
        if (userDTO == null) throw new IllegalArgumentException("UserDTO cannot be null");

        try {
            // 🔹 clientId는 고유하게 생성
            String clientId = "mqtt_controller_" + UUID.randomUUID();
            client = new MqttClient(broker, clientId);

            // 🔹 연결 옵션 설정: cleanSession=true
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // 🔹 콜백 설정: 메시지 수신, 연결 끊김 등 처리
            client.setCallback(this);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected as " + clientId);

            // 🔹 전체 토픽 구독
            client.subscribe(subTopic);
            System.out.println("Subscribed to topic: " + subTopic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

 // ===== MqttCallback =====
    
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        System.out.println("📩 Message arrived!");
//        System.out.println("   Topic: " + topic);
//        System.out.println("   Message: " + new String(message.getPayload()));
//    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) {	
        try {
            String payload = new String(message.getPayload());
            
            // Service 인터페이스 사용해 데이터베이스에 메시지 저장할 객체
            WarningService warningService = new WarningServiceImpl();
            
            // 토픽별 분기 처리
            switch (topic) {
                case "/sensor/temp":
                    //handleTemperature(message);
                    break;
                    
                case "/sensor/humidity":
                    //handleHumidity(message);
                    break;
                    
                case "/home/warning":
                    // Service 인터페이스 사용해 데이터베이스에 메시지 저장
                    warningService.saveWarning(topic, payload);
                    break;
                case "/home/abc":
                    // Service 인터페이스 사용해 데이터베이스에 메시지 저장
                    warningService.saveWarning(roomId, topic, payload);
                    break;
                default:
                    //System.out.println("처리되지 않은 토픽과 메시지: " + topic + payload);
            }
 
            // 메시지가 오면 팝업창 표시
            SwingUtilities.invokeLater
        (() -> {
            JOptionPane.showMessageDialog(null, payload, "센서 경고", JOptionPane.INFORMATION_MESSAGE);
        });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ===== MqttCallback =====
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("⚠️ Connection lost: " + cause.getMessage());
    }

 // ===== MqttCallback =====
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("✅ Message delivery complete.");
    }
    
    

    // 🔹 연결 종료
    public void close() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                client.close();
                System.out.println("🔌 Disconnected.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMessageListener(MessageListener listener) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public void subscribe() {
        try {
        	//라즈베리파이에서는 userid를 확인할 방법이 없어 아래코드는 참고용으로만
            // 사용자별 구독 (예: user123/home/#)
            //String fullTopic = id + subTopic;
            
            // 서브 토픽으로만 구독 (예: /home/led)
            String fullTopic = subTopic;
            
            client.subscribe(fullTopic);
            System.out.println("Subscribed to topic: " + fullTopic);
            
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(String topic, String message) {
        try {
        	
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(0);
            client.publish(topic, mqttMessage);
            
            System.out.println("📤 Published to [" + topic + "]: " + message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    

    
}
