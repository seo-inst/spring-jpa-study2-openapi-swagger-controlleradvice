package org.kosa.myproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.ProductDto;
import org.kosa.myproject.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @Controller + @ResponseBody(JSON 자동 응답 변환)
@RequestMapping("/api/products")  // 기본 경로 설정
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Product" , description = "상품 관리 API") // Swagger 문서화
public class ProductController {
        private final ProductService productService;
        @GetMapping("/{id}")
        @Operation(summary = "상품조회", description = "ID로 상품을 조회합니다") // Swagger 문서에 반영
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "조회 성공"),
                @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
        })
        public ResponseEntity<ProductDto> findProduct(@PathVariable  Long id){
            log.info("상품 조회 요청: id={}",id);
            ProductDto productDto = productService.findProduct(id);
            return ResponseEntity.ok(productDto); // 예외처리는 ControllerAdvice 에서 전담한다
        }
    /**
     *  상품 생성 API
     *  POST     기본 URL PATTERN  /api/products
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        log.info("상품 생성 요청:{}",productDto);
        ProductDto createdProduct = productService.createProduct(productDto);
        // 201 Created 상태 코드로 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    /**
     *  전체 상품 조회 API
     *  GET  /api/products
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts(){
        log.info("전체 상품 조회 요청");
        List<ProductDto> productDtos = productService.findAllProducts();
        return ResponseEntity.ok(productDtos);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable  Long id){
        log.info("상품 삭제 요청: id={}",id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    /**
     *  상품 수정 API
     *  PUT  /api/products/{id}
     *
     *  return ResponseEntity.ok(updatedProduct);
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDto productDto){
        log.info("상품 정보 수정 요청 id = {}, data = {}",id,productDto);
        ProductDto updatedProductDto = productService.updateProduct(id,productDto);
        return ResponseEntity.ok(updatedProductDto);
    }
}
