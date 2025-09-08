package org.kosa.myproject.service;

import lombok.RequiredArgsConstructor;
import org.kosa.myproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 트랜잭션 처리 : 읽기 전용
@RequiredArgsConstructor // 롬복 :  final field 를 매개변수로 하는 생성자를 자동 생성
public class ProductService {
    private final ProductRepository productRepository;
}
