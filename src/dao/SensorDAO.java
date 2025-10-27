package dao;

import dto.UserSessionDTO;

public interface SensorDAO {
	
    //의문4 로그인을 한 userdto를 내가 신경안썼네? 로그인여부에 따라 센서보내지는 정도가 왔다갔다하는데?
    //현재 로그인한 사용자 정보
  	private UserSessionDTO currentUser;
	
	//센서데이터의 정보만 관리
	//warning + sensor테이블과 연결
	
	void insertSensorData();
	void getLatestSensorDate();
	void detectIntrusion();
	void detectFire();
	
	
	
}
