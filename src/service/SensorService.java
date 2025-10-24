package service;

public interface SensorService {
	
	//라즈베리파이에서 가져온 센서데이터를 데이터베이스에 저장하기
	
	
	//자바앱(클라2)의 요청을 라즈베리파이 센서(클라1)로 보내기 
	
	//센서 종류 / 번호를 구분해서 
	//센서종류와 번호를 어떻게 구분하지?
	
	
	//자바앱(클라2)컨트롤러계층의 요청을 라즈베리파이 센서(클라1)로 MQTT통신으로 보는 함수
	
	//viewlayer -> controllerlayer -> SensorService -> 라즈베리 os -> 센서
	public void saveSensorData(String topic,String message);
	
	
	
	

}
