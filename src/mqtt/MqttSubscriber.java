package mqtt;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import dto.UserDTO;

public class MqttSubscriber implements MqttCallback {

    private UserDTO userDTO;
    
    private String id;
    
    private MqttClient client;
    
    private final String broker = "tcp://192.168.14.168:1883";
    
    private final String subTopic = "/home/#"; // 전체 하위 토픽 구독

    // 생성자: UserDTO 기반
    public MqttSubscriber(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        this.userDTO = userDTO;
        this.id = userDTO.getUserId(); // ID 추출

        try {
            // 고유 클라이언트 ID 생성
            String clientId = "subscriber_" + UUID.randomUUID().toString();
            client = new MqttClient(broker, clientId);

            // 연결 옵션
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // 콜백 등록
            client.setCallback(this);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected.");

            // 구독 시작
            subscribe();

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // subTopic 기반 구독
    private void subscribe() {
        try {
            //String fullTopic = id + subTopic; // 예: user123/home/rasp/#
        	
        	String fullTopic = subTopic;
            client.subscribe(fullTopic);
            System.out.println("Subscribed to topic: " + fullTopic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived! Topic: " + topic + ", Message: " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken token) {
        // 구독 전용이라 필요 없음
    }

    // 연결 종료
    public void close() {
        try {
            client.disconnect();
            client.close();
            System.out.println("Disconnected.");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
