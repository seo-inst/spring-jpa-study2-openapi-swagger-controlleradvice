package org.kosa.myproject.service;

import lombok.RequiredArgsConstructor;
import org.kosa.myproject.dto.ProductDto;
import org.kosa.myproject.dto.ProductStatsDto;
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
    @Transactional
    public ProductDto createProduct(ProductDto productDto){
       // Dto -> Entity 변환
        Product product = productDto.toEntity();
        // 데이터베이스에 저장
        Product savedProduct = productRepository.save(product);
        // Entity -> Dto 로 변환하여 반환
        return ProductDto.from(savedProduct);
    }

    /**
     *   상품아이디로 상품 조회
     * @param id
     * @return 조회된 상품 Dto
     */
    public ProductDto findProduct(Long id){
        // 상품을 검색해서 존재하면 상품객체를 리턴하고 존재하지 않으면 RuntimeException 을 발생시킨다
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("상품을 찾을 수 없습니다 ID:"+id));
        return  ProductDto.from(product);// Entity 를 Dto 로 변환해 리턴한다
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

    /**
     * 상품 수정하는 메서드
     * @param id   수정할 상품 아이디
     * @param updateDto  수정할 상품 정보
     * @return ProductDto 수정된 상품 Dto
     */
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto updateDto) {
        // db 에 있는 상품 조회
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다 ID:"+id));
        // 상품 정보 업데이트 ( Entity 의 비즈니스 메서드 활용 )
        existingProduct.updateProduct(updateDto.getName(),updateDto.getPrice(),updateDto.getStockQuantity(),updateDto.getDescription());
        // commit 시점에  JPA 의 더티체킹(변경감지)으로 자동 업데이트 - update SQL 실행  , save() 불필요
        // 상품 정보 업데이트 된 Entity 를  Dto 로 변환해 반환
        return ProductDto.from(existingProduct);
    }
    @Transactional
    public void deleteProduct(Long productId) {
        // 존재 여부 확인 : 검증 절차
        if(!productRepository.existsById(productId)){
            throw new IllegalArgumentException("상품을 찾을 수 없습니다:"+productId);
        }
        productRepository.deleteById(productId);
    }

    /**
     * 상품 존재 유무 확인
     * @param productId
     * @return 존재 여부 (true/false)
     */
    public boolean existsProduct(Long productId) {
        return  productRepository.existsById(productId);
    }

    /**
     *  상품명으로 조회
     * @param name 검색할 상품명
     * @return 조회된 상품 DTO
     */
    public ProductDto findProductByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(()->new IllegalArgumentException("상품을 찾을 수 없습니다. 상품명:"+name));
        return  ProductDto.from(product);// entity 를 dto 로 반환한다
    }

    public List<ProductDto> findProductsOrderByPrice() {
        List<Product> products = productRepository.findAllByOrderByPriceAsc();
        // entity 요소로 구성된 리스트를 stream() 과 map() 을 이용해 dto 요소로 구성된 리스트로 만들어 리턴
        return  products.stream().map(ProductDto::from).collect(Collectors.toUnmodifiableList());
    }

    public List<ProductDto> searchProductsByName(String  keyword) {
        // repository : findByNameContaining(keyword) 를 ProductService 에서 호출해 구현하세요
        List<Product> products = productRepository.findByNameContaining(keyword);
        return products.stream().map(ProductDto::from).collect(Collectors.toUnmodifiableList());
    }
    @Transactional
    public ProductDto increaseStock(Long id,int quantity){
        // 상품 존재 여부 확인
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("상품을 찾을 수 없습니다 ID:"+id));
        // Entity 의 비즈니스 메서드 호출 : 트랜잭션 커밋 시점에 변경 감지(Dirty Checking) 되어 update sql 실행
        product.increaseStock(quantity);
        //  Entity 를 Dto 로 변환
        return ProductDto.from(product);
    }
    @Transactional
    public ProductDto decreaseStock(Long id, int quantity){
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("상품을 찾을 수 없습니다"));
        product.decreaseStock(quantity);
        return ProductDto.from(product);
    }
    /**
     *      전체 상품 통계 서비스 ( Repository 의 JPQL 적용 메서드 테스트를 위해 )
     */
    public ProductStatsDto findProductStatistics(){
        return productRepository.findProductStatistics();
    }
}

















