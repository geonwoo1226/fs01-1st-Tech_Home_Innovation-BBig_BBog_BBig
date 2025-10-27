package service;

import java.util.List;
import dto.UserDTO;
import dto.WarningDTO;

/**
 * WarningService
 * 
 * - 센서에서 발생한 경고 메시지를 DB에 저장하고
 * - 로그인된 사용자의 관련 경고 로그를 조회하는 서비스 계층
 * 
 * 
 * 
 * 
 * ✅ 구조 요약 (계층 간 흐름)
[라즈베리파이 센서]
   ↓ MQTT Publish
[자바 서버 (MQTT Subscriber)]
   ↓
[WarningService.saveWarningData()]
   ↓
[WarningDAOImpl.insertWarning()]
   ↓
[warning 테이블 저장]

[자바 클라이언트 (로그인)]
   ↓
[WarningService.loadWarningData()]
   ↓
[WarningDAOImpl.selectWarningsBySensor()]
   ↓
[경고 목록 UI 표시]

💡 DTO에서 uesrId → userId로 오타를 수정하는 걸 추천드립니다.
이게 유지보수 시 매우 중요합니다.

✅ 관계 예시 (데이터 흐름)
Sensor	Warning
sensor_id: temp_living_01	sensor_id: temp_living_01
sensor_type: temperature	message: Temperature exceeds 30°C
sensor_location: livingroom	warning_date: 2025-10-26 22:00:00
✅ 실제 작동 시 시나리오 예시
📡 MQTT 통신 흐름

라즈베리파이 센서(temp_living_01) 가 온도 이상 감지

MQTT 메시지 발행 →

topic: sensor/temp_living_01
payload: {"message":"Temperature exceeds 30°C"}


자바 서버(MQTT Subscriber) 가 해당 메시지를 받아 WarningDAOImpl.insertWarning() 호출

warning 테이블에 데이터 저장

자바앱(클라이언트) 에서 selectWarningsBySensor("temp_living_01") 호출
→ 최근 경고 내역 UI로 표시

🧠 다음 단계 (추천)

지금까지 DTO 구조가 명확히 정리되었으니, 다음으로 아래 중 하나를 진행하면 전체 시스템 완성도가 올라갑니다:

SensorDAO / SensorDAOImpl — 센서 정보 등록·조회·수정 (ON/OFF 제어 포함)

SensorServiceImpl — 로그인 유저 기반으로 센서 제어

WarningServiceImpl — MQTT 메시지 수신 시 DB 저장

원하신다면 다음 단계로
👉 SensorDAO + SensorDAOImpl (CRUD 포함)
까지 바로 만들어드릴까요?
그럼 Warning과 Sensor가 완전히 연결된 구조를 볼 수 있습니다.

| 구성 요소               | 역할                  |
| ------------------- | ------------------- |
| `SensorService`     | 자바앱의 센서 제어 로직 인터페이스 |
| `SensorServiceImpl` | MQTT 통신 구현          |
| `UserSessionDTO`    | 로그인 사용자 세션 정보       |
| `SensorController`  | UI/요청 → 서비스 계층 연결   |
| `라즈베리파이 MQTT 클라이언트` | 실제 하드웨어 제어 수행       |


 */



public interface WarningService {

    /**
     * MQTT 브로커로부터 전달받은 센서 경고 메시지를 DB의 warning 테이블에 저장한다.
     *
     * @param topic   MQTT 토픽 (예: home/livingroom/gas/1)
     * @param message 센서 상태 메시지 (예: "gas_detected", "led_on", "fire_alert")
     */
    void saveWarningData(String topic, String message);


	//UserDTO를 가져와야하나? 아니면 UserSessionDTO를 매개변수로 가져와야하나?
    /**
     * 로그인된 사용자의 센서별 최신 경고 데이터를 조회하여 반환한다.
     * 
     * 사용자는 UserDTO로 식별하며, 
     * WarningDTO의 sensorId 또는 warningId 기준으로 DB에서 로그를 검색한다.
     *
     * @param userDTO 로그인된 사용자 정보 (userId를 포함)
     * @param warningDTO 조회할 센서 또는 경고 정보 (sensorId를 포함)
     * @return 해당 사용자의 센서 경고 내역 리스트 (최신순)
     */
    List<WarningDTO> loadWarningData(UserDTO userDTO, WarningDTO warningDTO);


    //sub받아서 화면에 출력
	void subscribeAndDisplaySensorData(String brokerUrl, String topic);

	//sub받아서 db에저장
	void subscribeAndSaveSensorData(String brokerUrl, String topic);


	void subscribeAndDisplaySensorData(String brokerUrl, String topic, UserDTO user);


	void subscribeAndSaveSensorData(String brokerUrl, String topic, UserDTO user);
}
