package org.kosa.myproject.repository;

import org.kosa.myproject.dto.ProductStatsDto;
import org.kosa.myproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// <Product,Long> : Product 는 관리 엔티티, Long 은 Primary key에 대응하는 필드의 타입
@Repository // Spring 데이터 접근 Data Access Layer(영속성 계층) 컴포넌트 임을 명시
public interface ProductRepository  extends JpaRepository<Product,Long> {
    // 기본 CRUD 는 JpaRepository 가 제공 : save() , saveAll() , count() , findAll(), findById() , delete() 등
    /*
            상품명 name 으로 조회
            메서드 명명 규칙 : find + By + 엔티티필드명
            SQL : SELECT * FROM products WHERE product_name = ?
     */
    Optional<Product> findByName(String 노트북);
    // 정렬
    List<Product> findAllByOrderByPriceAsc();
    // like  : 특정 정보가 포함된 상품명의 상품들을 조회
    List<Product> findByNameContaining(String keyword);
    /*
            전체 상품의 가격 평균,최소,최대,총합,개수 등을 조회
            -> JPQL 이 필요
            JPQL 은 주로 집계함수, 성능 최적화 (Fetch Join..), 쿼리 메서드로 표현하기 복잡한 조건 일 때 사용
     */
    @Query("SELECT new org.kosa.myproject.dto.ProductStatsDto(AVG(p.price),MIN(p.price),MAX(p.price),COUNT(p)) FROM  Product p")
    ProductStatsDto findProductStatistics();
}














