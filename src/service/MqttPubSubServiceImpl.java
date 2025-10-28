package service;

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

public class MqttPubSubServiceImpl implements MqttCallback {

    private MqttClient client;
    private final String broker = "tcp://192.168.14.168:1883"; // 라즈베리파이 MQTT 브로커
    private final String subTopic = "/home/#"; // 전체 구독

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

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String payload = new String(message.getPayload());

            // Service 인터페이스 사용
            WarningService warningService = new WarningServiceImpl();
            warningService.saveWarning(topic, payload);

            // 팝업 표시
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, payload, "센서 경고", JOptionPane.INFORMATION_MESSAGE);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("⚠️ Connection lost: " + cause.getMessage());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // 발행 기능 없음 → 빈 구현
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
}
