package dao;

public interface UserDAO {

		//사용자 관련 (회원가입,로그인,정보 조회)
	//유저테이블 룸 테이블
	
	
		//회원가입시 무슨정보를 입력할것인지?
		//ex)User id / 패스워드 /  전화번호 는 테이블안에 추가하면 된다
		
		//빌딩 동번호/ 방번호는 입력이 아니라 이미 있는 room테이블속의 것이랑 대비하는것이다
		
		
		//총3번 업데이트 1번 인서트 1번 업데이트 1번
		int register(UserDTO user);
		
		//ex)User id / 패스워드
		UserDTO login(String id,String pass);
		
		//뭔 업뎃할지 여기서 오버라이딩 매개변수
		void updateUserInfo();
}
