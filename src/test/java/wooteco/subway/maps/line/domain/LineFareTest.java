package wooteco.subway.maps.line.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.maps.line.exception.InvalidFareException;

class LineFareTest {

    @DisplayName("잘못된 추가요금 생성시 예외 발생")
    @Test
    void name() {
        assertThatThrownBy(() -> new LineFare(-1))
            .isInstanceOf(InvalidFareException.class)
            .hasMessageContaining("잘못된 추가요금을 입력했습니다.");
    }
}