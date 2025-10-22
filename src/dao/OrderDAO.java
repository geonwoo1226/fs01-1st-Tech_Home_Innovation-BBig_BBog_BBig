package dao;

public interface OrderDAO {
	
	//사용자 구매내역 및취소내역정보 영수증 관리
	//미니 쇼핑몰의 장바구니정보?
	
	//order_info + order_product 테이블
	
	//사용자가 장바구니속의 물건 주문하기
	void createOrder();
	
	
	//방별 주문내역 정보출력
	void getOrderByRoom();
	
	//주문 취소 요청
	void cancleOrder();
	

}
