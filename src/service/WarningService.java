package service;

public interface WarningService {

	
	//라즈베리파이에서 가져온 경고 메시지를 데이터베이스에 추가해서 저장하기
	
	//이거는 필요없겠지?
	//아니네? db의 메시지 내용을 저장하고 메시지를 뷰로 보내야하네?
	
	//1.라즈베리파이(클라1) 센서들의 정보를 자바앱(클라2)로 가져오기
	//2.자바앱서비스단에서 db안 워닝테이블로 경고메시지를 추가하기
	//3.워닝테이블로 경고메시지를 자바앱으로 가져와서 뷰로 보내기
	
	//센서 -> 라즈베리 os -> SensorService -> controllerlayer -> viewlayer
	void saveWarningData(String topic, String message);
	
	//센서 번호를 기준으로

}
