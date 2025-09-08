package org.kosa.myproject.repository;

import org.kosa.myproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
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

    List<Product> findAllByOrderByPriceAsc();
}







