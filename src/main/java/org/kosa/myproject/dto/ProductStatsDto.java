package org.kosa.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
        상품 통계 서비스를 위한 Dto
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStatsDto {
    private Double avgPrice;
    private Integer minPrice;
    private Integer maxPrice;
    private Long totalCount;
}








