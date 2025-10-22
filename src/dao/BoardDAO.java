package dao;

public interface BoardDAO {
	//공지사항 ,입주민 게시판, 민원관리
	
	//notice 테이블
	
	
	//로그인된 사용자가 커뮤니티게시판에 글 올리기
	void insertPost();
	
	//로그인된 사용자가 커뮤니티게시판에 글을 요청시 다 보여주기
	void getAllPost();
	
	//글을 분류에 따라 보여주기
	void getPostByType();
	
	//로그인된 사용자가 로그인되있으면 글을 삭제하기
	void deletsPost();

}
