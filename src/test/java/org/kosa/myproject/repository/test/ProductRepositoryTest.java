package org.kosa.myproject.repository.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kosa.myproject.entity.Product;
import org.kosa.myproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // 테스트에서 이 애너테이션을 명시하면 테스트 후 자동 롤백
@Slf4j
public class ProductRepositoryTest {
        @Autowired
        ProductRepository productRepository;
        @Test
        void testFindByName(){
            //Given
            productRepository.save(Product.builder()
                    .name("노트북")
                    .price(150000)
                    .stockQuantity(50)
                    .build()
            );
            //When  상품명으로 조회
            Optional<Product> product = productRepository.findByName("노트북");
            //Then
            assertThat(product).isPresent();
        }
        @Test
        void testFindAllByOrderByPriceAsc(){
            // Given: 가격이 다른 상품들 저장
            productRepository.save(Product.builder()
                    .name("비싼상품")
                    .price(100000)
                    .stockQuantity(10)
                    .build());

            productRepository.save(Product.builder()
                    .name("저렴한상품")
                    .price(10000)
                    .stockQuantity(100)
                    .build());

            productRepository.save(Product.builder()
                    .name("중간상품")
                    .price(50000)
                    .stockQuantity(50)
                    .build());
            // When : 가격 기준 오름차순 정렬 조회
            List<Product> products = productRepository.findAllByOrderByPriceAsc();
            // Then
            assertThat(products).hasSize(3);
            products.forEach(product -> log.info(product.toString()));
        }
}



