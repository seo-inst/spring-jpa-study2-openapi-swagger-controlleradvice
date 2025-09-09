package org.kosa.myproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosa.myproject.dto.ProductDto;
import org.kosa.myproject.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody(JSON 자동 응답 변환)
@RequestMapping("/api/products")  // 기본 경로 설정
@Slf4j
@RequiredArgsConstructor
public class ProductController {
        private final ProductService productService;
        @GetMapping("/{id}")
        public ResponseEntity<ProductDto> findProduct(@PathVariable  Long id){
            log.info("상품 조회 요청: id={}",id);
            ProductDto productDto = productService.findProduct(id);
            return ResponseEntity.ok(productDto); // 예외처리는 ControllerAdvice 에서 전담한다
        }
}
