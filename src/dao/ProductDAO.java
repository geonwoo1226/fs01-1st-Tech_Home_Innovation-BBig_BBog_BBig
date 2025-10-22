package dao;

public interface ProductDAO {
	
	//주문가능한 상품목록 
	//product 테이블에서 가져오기
	
	//새상품 종류 정보 추가
	//가격+ 재고
	
	//상품정보 데이터 리스트 가져오기
	void getProductList();
	
	//상품정보 데이터 리스트 가져오기
	void getProductById();
	
	//상품정보 수정
	void updateProduct();
	
	//상품정보 삭제
	void deleteProduct();
	
	

}
