package dao;

public interface SensorDAO {
	
	//센서데이터의 정보만 관리
	//warning + sensor테이블과 연결
	
	void insertSensorData();
	void getLatestSensorDate();
	void detectIntrusion();
	void detectFire();
	
	
	
}
