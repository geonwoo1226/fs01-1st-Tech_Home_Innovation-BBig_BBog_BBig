package dao;

import dto.UserDTO;

public interface UserDAO {
	
	// 회원가입
	int register(UserDTO user);
	
	// 로그인
	UserDTO login(String id, String pass);
	
	// 정보조회
//	int showInfo();
}
