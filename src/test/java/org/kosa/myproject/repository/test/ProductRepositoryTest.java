package org.kosa.myproject.repository.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kosa.myproject.entity.Product;
import org.kosa.myproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}



