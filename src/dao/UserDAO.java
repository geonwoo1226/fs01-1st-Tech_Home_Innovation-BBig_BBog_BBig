package dao;

import dto.UserDTO;

public interface UserDAO {
	
	// 회원가입
	int register(UserDTO user);
	
	// 로그인
	UserDTO login(String id, String pass);
	
	
	// 재택/외출 상태 변환
	int stateUpdate(UserDTO user, String state);
}
