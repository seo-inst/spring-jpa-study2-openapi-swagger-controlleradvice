package org.kosa.myproject.config;

// =================================
// 테스트용 샘플 데이터 자동 생성
// =================================

import lombok.RequiredArgsConstructor;
import org.kosa.myproject.dto.ProductDto;
import org.kosa.myproject.entity.Product;
import org.kosa.myproject.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataInitializer 역할 - 애플리케이션 시작 시 샘플 데이터 자동 생성
 * CommandLineRunner: Spring Boot 애플리케이션이 완전히 시작된 후 실행되는 인터페이스
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        if (productService.findAllProducts().size() == 0)
            createSampleProducts();
    }

    private void createSampleProducts() {

        // 샘플 상품들 생성
        Product[] sampleProducts = {
                Product.builder()
                        .name("MacBook Pro M3")
                        .price(2500000)
                        .stockQuantity(15)
                        .description("Apple M3 칩 탑재, 14인치 디스플레이")
                        .build(),

                Product.builder()
                        .name("Galaxy S24 Ultra")
                        .price(1350000)
                        .stockQuantity(25)
                        .description("200MP 카메라, S펜 기본 제공")
                        .build(),

                Product.builder()
                        .name("iPad Pro 12.9")
                        .price(1200000)
                        .stockQuantity(12)
                        .description("M2 칩, Liquid Retina XDR 디스플레이")
                        .build(),

                Product.builder()
                        .name("AirPods Pro 2세대")
                        .price(350000)
                        .stockQuantity(30)
                        .description("능동형 소음 차단, MagSafe 충전 케이스")
                        .build()
        };

        // 각 상품을 데이터베이스에 저장
        for (Product product : sampleProducts) {
            productService.createProduct(ProductDto.from(product));
            System.out.println("상품 등록: " + product.getName());
        }
    }
}