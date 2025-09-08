package org.kosa.myproject.service.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kosa.myproject.dto.ProductDto;
import org.kosa.myproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // 테스트 후 자동 롤백
@Slf4j
public class ProductServiceTest {
    @Autowired
    ProductService productService;
    // 기본 CRUD 테스트
    @Test
    void testCreateProduct(){
        //Given  : 정보 생성용 테스트 데이타 준비
        ProductDto productDto = ProductDto.builder()
                .price(1500000)
                .stockQuantity(10)
                .name("테스트 노트북")
                .description("테스트용 고성능 노트북")
                .build();
        // When : 상품 생성
        ProductDto createdProduct =   productService.createProduct(productDto);
        // Then : 생성된 상품 검증
        assertThat(createdProduct).isNotNull();
    }
    @Test
    void testFindAllProducts(){
        // Given: 여러 상품 생성
        ProductDto product1 = ProductDto.builder()
                .name("상품1").price(10000).stockQuantity(50).build();
        ProductDto product2 = ProductDto.builder()
                .name("상품2").price(20000).stockQuantity(30).build();
        ProductDto product3 = ProductDto.builder()
                .name("상품3").price(30000).stockQuantity(20).build();

        productService.createProduct(product1);
        productService.createProduct(product2);
        productService.createProduct(product3);
        // When : 전체 상품 조회
        List<ProductDto> allProducts = productService.findAllProducts();
        // Then : 생성한 상품들의 리스트가 조회되는 지 확인
        assertThat(allProducts).hasSize(3);
    }
}


















