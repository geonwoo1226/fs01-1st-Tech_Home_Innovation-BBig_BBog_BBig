package service;



import dto.SensorDTO;
import dto.UserSessionDTO;
import java.util.ArrayList;
import java.util.List;

import view.DetailView;

public interface SensorService {
	
	//자바앱으로 라즈베리파이 조작하기
	
	//자바앱(클라2)의 요청을 mqtt를 통해 라즈베리파이 센서(클라1)로 보내기 
	
	//센서 종류 / 번호를 구분해서 
	//센서종류와 번호를 어떻게 구분하지?
	
	
	//자바앱(클라2)컨트롤러계층의 센서제어 요청을 라즈베리파이(클라1)로 MQTT통신으로 보는 함수
	//장소는 토픽으로 구분하고 센서도 토픽으로 구분해야되나?
	
	//아 내가 로그인여부를 여기서도 깜빡했네
	//당연히 로그인 된 상테에서만 센서를 조작가능해야겠지?
	
	//viewlayer -> controllerlayer -> SensorService(mqtt클라1) -> 라즈베리 os(mqtt클라2) -> 센서조작
	
	
	
	
	/*
	 * ✅ 핵심 설계 포인트 설명
	메서드명	역할	설명
	controlDevice()	실제 MQTT 메시지 전송	로그인 검증 + 토픽 생성 + MQTT publish 수행
	hasControlPermission()	사용자 제어 권한 확인	UserSessionDTO.userId와 센서 소유자 확인
	buildTopic()	MQTT 토픽 자동 생성	sensor_location + sensor_type + sensor_id 기반 문자열 생성
	 * 
	 * 
	 * */
	
	public void controlDevice(String topic,String message);
	
	 /**
     * 로그인된 사용자가 특정 센서를 제어하는 함수.
     * 
     * @param userSession 현재 로그인된 사용자 세션 정보
     * @param sensorDTO   제어할 센서 정보 (sensorId, sensorType, sensorLocation 포함)
     * @param message     제어 명령 메시지 (예: "ON", "OFF", "TOGGLE")
     */
    void controlDevice(UserSessionDTO userSession, SensorDTO sensorDTO, String message);

    
    /**
     * 센서 제어 권한이 있는지 확인하는 함수.
     * 
     * @param userSession 로그인된 사용자 정보
     * @param sensorId    제어하려는 센서의 ID
     * @return 제어 권한이 있으면 true, 없으면 false
     */
    boolean hasControlPermission(UserSessionDTO userSession, String sensorId);


    /**
     * MQTT 토픽 생성 함수.
     * 
     * DB의 센서정보(SensorDTO) 기준으로 토픽을 자동 생성한다.
     * 예: "home/livingroom/led/1"
     * 
     * @param sensorDTO 센서 정보 객체
     * @return MQTT 토픽 문자열
     */
    String buildTopic(SensorDTO sensorDTO);
	
	
	// 방 번호를 받으면 센서 목록 반환
	List<String> getSensorByRoom(String roomName);

}
