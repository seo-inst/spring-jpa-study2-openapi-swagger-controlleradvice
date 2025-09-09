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
    @Test
    void testUpdateProduct(){
        // Given: 수정할 상품 생성
        ProductDto originalProduct = ProductDto.builder()
                .name("원본상품")
                .price(100000)
                .stockQuantity(50)
                .description("원본 설명")
                .build();

        ProductDto createdProduct = productService.createProduct(originalProduct);
        // When : 상품 정보 수정
        ProductDto updateDto = ProductDto.builder()
                .name("수정된상품")
                .price(150000)
                .stockQuantity(30)
                .description("수정된설명")
                .build();
        // 수정
         ProductDto updatedProduct = productService.updateProduct(createdProduct.getId(),updateDto);
         // Then : 수정된 내용 검증
        assertThat(updatedProduct.getName()).isEqualTo("수정된상품");
        log.info("수정된 상품 정보: {}",updatedProduct.toString());
    }
    @Test
    void testDeleteProduct(){
        ProductDto productDto = ProductDto.builder()
                .name("삭제될상품")
                .price(50000)
                .stockQuantity(10)
                .build();

        ProductDto createdProduct = productService.createProduct(productDto);
        Long productId = createdProduct.getId();
        //When : 상품 삭제
        productService.deleteProduct(productId);
        //Then: 삭제되었는 지 검증
        assertThat(productService.existsProduct(productId)).isFalse();
    }
    // =================================
    // 2단계: 쿼리 메서드 기반 서비스 테스트
    // =================================
    @Test
    void testFindProductByName() {
        // Given: 특정 이름의 상품 생성
        ProductDto productDto = ProductDto.builder()
                .name("특별한상품")
                .price(75000)
                .stockQuantity(25)
                .build();

        productService.createProduct(productDto);

        // When: 상품명으로 조회
        ProductDto foundProduct =  productService.findProductByName("특별한상품");

        // Then: 올바른 상품이 조회되는지 검증
        assertThat(foundProduct.getName()).isEqualTo("특별한상품");
    }
}





















