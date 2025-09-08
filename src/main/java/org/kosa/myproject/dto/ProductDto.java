package org.kosa.myproject.dto;

import lombok.*;
import org.kosa.myproject.entity.Product;

/*
    Dto : Data Transfer Object - 계층간 데이터 전송용 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {
        private Long id;
        private String name;
        private Integer price;
        private Integer stockQuantity;
        private String description;
    /**
     *  Entity -> Dto 변환
     *  조회할 때 사용
     */
    public static ProductDto from(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .description(product.getDescription())
                .build();
    }
    /**
     *      Dto -> Entity 변환
     *      생성 / 수정 할때 사용
     */
    public Product toEntity(){
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .description(this.description)
                .build();
    }
}

















