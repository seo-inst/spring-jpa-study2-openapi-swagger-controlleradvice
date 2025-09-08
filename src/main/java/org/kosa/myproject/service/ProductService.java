package org.kosa.myproject.service;

import lombok.RequiredArgsConstructor;
import org.kosa.myproject.dto.ProductDto;
import org.kosa.myproject.entity.Product;
import org.kosa.myproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // 트랜잭션 처리 : 읽기 전용
@RequiredArgsConstructor // 롬복 :  final field 를 매개변수로 하는 생성자를 자동 생성
public class ProductService {
    /**
     *  의존성 주입
     *  - final 키워드로 불변성 보장
     *  - Lombok 의 @RequiredArgsConstructor 로 생성자 주입
      */
    private final ProductRepository productRepository;
    public ProductDto createProduct(ProductDto productDto){
       // Dto -> Entity 변환
        Product product = productDto.toEntity();
        // 데이터베이스에 저장
        Product savedProduct = productRepository.save(product);
        // Entity -> Dto 로 변환하여 반환
        return ProductDto.from(savedProduct);
    }

    /**
     * 모든 상품 조회
     * @return 전체 상품 목록 Dto
     */
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        // Stream API 를 활용해 Entity List -> Dto List 로 변환
        return  products.stream().map(ProductDto::from).collect(Collectors.toUnmodifiableList());
    }
}












