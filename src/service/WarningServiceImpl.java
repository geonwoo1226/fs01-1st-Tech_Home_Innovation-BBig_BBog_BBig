package service;

import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import dao.WarningDAO;
import dao.WarningDAOImpl;
import dto.UserDTO;
import dto.UserSessionDTO;
import dto.WarningDTO;

public class WarningServiceImpl implements WarningService {

    private WarningDAO warningDAO = new WarningDAOImpl();
    private IMqttClient mqttClient;
    private UserDTO currentUser;
    
    public WarningServiceImpl() {
    	
    }

    public WarningServiceImpl(UserDTO currentUser) {
        this.currentUser = currentUser;
    }

    // ✅ 구현 함수 1: 로그인 유저 기반 MQTT 구독 → 콘솔 출력
    @Override
    public void subscribeAndDisplaySensorData(String brokerUrl, String topic) {
        try {
            // 로그인된 사용자 기준으로 클라이언트 ID 생성
            String clientId = currentUser.getUserId();

            mqttClient = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            mqttClient.connect(options);

            mqttClient.subscribe(topic, (t, msg) -> {
                String payload = new String(msg.getPayload());
                System.out.println("📡 [MQTT 수신 - " + clientId + "] 토픽: " + t + " | 메시지: " + payload);
            });

            System.out.println("✅ MQTT 구독 성공 - 사용자: " + clientId + " / 토픽: " + topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // ✅ 구현 함수 2: 로그인 유저 기반 MQTT 구독 → DB 저장
    @Override
    public void subscribeAndSaveSensorData(String brokerUrl, String topic) {
        try {
            String clientId = currentUser.getUserId();

            mqttClient = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            mqttClient.connect(options);

            mqttClient.subscribe(topic, (t, msg) -> {
                String payload = new String(msg.getPayload());

                // topic → sensor_id 추출
                String[] parts = t.split("/");
                String sensorId = parts[parts.length - 1];

                WarningDTO warning = new WarningDTO();
                warning.setUserId(clientId);
                warning.setSensorId(sensorId);
                warning.setMessage(payload);
                warning.setWarningDate(new java.sql.Timestamp(System.currentTimeMillis()));

                warningDAO.insertWarning(warning);

                System.out.println("💾 DB 저장 완료 [" + clientId + "] 센서: " + sensorId + " / 값: " + payload);
            });

            System.out.println("✅ MQTT 구독(DB저장) 시작 - 사용자: " + clientId + " / 토픽: " + topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 기존 저장 메서드
    @Override
    public void saveWarningData(String topic, String message) {
        try {
            String[] parts = topic.split("/");
            String sensorId = parts[parts.length - 1];

            WarningDTO warning = new WarningDTO();
            warning.setSensorId(sensorId);
            warning.setMessage(message);
            warning.setWarningDate(new java.sql.Timestamp(System.currentTimeMillis()));
            warning.setUserId(currentUser != null ? currentUser.getUserId() : "system");

            warningDAO.insertWarning(warning);
            System.out.println("✅ Warning 데이터 저장 완료: " + warning.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WarningDTO> loadWarningData(UserDTO userDTO, WarningDTO warningDTO) {
        List<WarningDTO> list = null;
        try {
            String userId = userDTO.getUserId();
            String sensorId = warningDTO.getSensorId();
            list = warningDAO.selectWarningsBySensor(sensorId);
            System.out.println("✅ 사용자 [" + userId + "] 의 센서 [" + sensorId + "] 경고 로그 " + list.size() + "건 조회");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

