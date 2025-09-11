package org.kosa.myproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 *  Entity : 데이터베이스 테이블과 1:1 매핑되는 자바 객체
 *  JPA가 관리(EntityManager가 관리주체)하는 영속성 컨텍스트 (Persistence Context) 에 저장되는 객체   *
 */
@Entity // JPA 엔티티 선언
@Table(name = "products")  // 테이블 명 지정 - 생략시 클래스명을 snake case 로 변환
@Getter
@NoArgsConstructor // JPA 요구사항 - 기본 생성자 필수
@AllArgsConstructor // 모든 필드를 받는 생성자
@Builder // 롬복을 이용한 빌더 패턴 적용 ,  객체 생성을 효율적으로
@ToString
public class Product {
    @Id // Primary Key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// AUTO_INCREMENT 설정
    @Column(name="product_id") // 컬럼명 명시적 지정
    private Long id;
    @Column(name="product_name",unique = true,nullable = false,length = 100)
    private String name;
    @Column(name="price",nullable = false)
    private Integer price;
    /*
        재고 수량
     */
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;
    /*
            상품 설명
            @Lob :  Large Object - 긴 텍스트나 바이너리 데이터
     */
    @Lob
    @Column(name = "description")
    private String description;
    /**
     *  상품 정보 생성일시
     *  @CreationTimestamp : JPA (Hibernate) 가 엔티티 생성시 자동으로 현재 시간 설정
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //==================================
    // 비즈니스 메서드들 ( 도메인 로직 )
    //==================================
    public void updateProduct(String name,Integer price,Integer stockQuantity,String description){
        if(name !=null && !name.isBlank())
            this.name=name;
        if(price != null && price > 0)
            this.price=price;
        if(stockQuantity!=null && stockQuantity >=0)
            this.stockQuantity = stockQuantity;
        if(description !=null)
            this.description = description;
    }
    /*
            재고 증가
     */
     public void increaseStock(int quantity){
         if(quantity <=0 )
             throw new IllegalArgumentException("재고 증가 수량은 양수여야 합니다");
         this.stockQuantity += quantity;
     }
     /*
            재고 감소
      */
    public void decreaseStock(int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("재고 감소 수량은 양수여야 합니다");
        }
        if(this.stockQuantity < quantity){
            throw new IllegalStateException("재고가 부족합니다");
        }
        this.stockQuantity -= quantity;
    }
}










