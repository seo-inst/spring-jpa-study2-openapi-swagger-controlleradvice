package org.kosa.myproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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

    private Long id;
}








