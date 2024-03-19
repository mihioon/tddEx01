package io.hhplus.tdd.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.catalina.User;

// record
// 선언된 필드에 대한 생성자 자동으로 생성
// 필드를 읽기 위한 getter 메서드 자동으로 생성
@Builder
public record UserPoint(
        Long id,
        Long point,
        Long updateMillis
) {
}
