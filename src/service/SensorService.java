package service;

import java.util.ArrayList;
import java.util.List;

import view.DetailView;

public interface SensorService {
	
	//라즈베리파이에서 가져온 센서데이터를 데이터베이스에 저장하기
	public void saveSensorData(String topic,String message);
	
	// 방 번호를 받으면 센서 목록 반환
	List<String> getSensorByRoom(String roomName);

}
