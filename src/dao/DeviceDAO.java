package dao;

public interface DeviceDAO {

	//라즈베리파이 부저 모터 led 기기제어 상태 DB관리
	//sensor db warning 테이블
	
	//라즈베리 출력상태 정보 조작
	void updateDeviceState();
	
	//라즈베리의 지금 켜진건지 꺼진건지 속도등 현재상태정보 가져오기
	void getDeviceState();
	
	//새로운 기기를 선치그리고 등록하기
	void addDevice();
	
	//방별로 어떤 기기와 센서가 설치됬는지 정보출력
	void getDeviceByRoom();
	
}
