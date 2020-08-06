package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.members.member.domain.LoginMember;

class WoowaSubwaySubwayFareTest {

    @DisplayName("거리별(10~50km) 요금이 추가된다.")
    @CsvSource({"9,1250", "10,1350", "15,1450", "20,1550"})
    @ParameterizedTest
    void fare(int distance, int expect) {
        List<Line> lines = Arrays.asList(
            new Line("1호선", "red", 0, LocalTime.now(), LocalTime.now(), 10),
            new Line("2호선", "red", 0, LocalTime.now(), LocalTime.now(), 10),
            new Line("3호선", "red", 0, LocalTime.now(), LocalTime.now(), 10));

        WoowaSubwaySubwayFare woowaSubwaySubwayFare = new WoowaSubwaySubwayFare(distance, lines, null);

        assertThat(woowaSubwaySubwayFare.calculate()).isEqualTo(expect);
    }

    @DisplayName("거리별(50km 이상) 요금이 추가된다.")
    @CsvSource({"50,2050", "57,2050", "58,2150"})
    @ParameterizedTest
    void fare2(int distance, int expect) {
        List<Line> lines = Arrays.asList(
            new Line("1호선", "red", 0, LocalTime.now(), LocalTime.now(), 10),
            new Line("2호선", "red", 0, LocalTime.now(), LocalTime.now(), 10),
            new Line("3호선", "red", 0, LocalTime.now(), LocalTime.now(), 10));

        WoowaSubwaySubwayFare woowaSubwaySubwayFare = new WoowaSubwaySubwayFare(distance, lines, null);

        assertThat(woowaSubwaySubwayFare.calculate()).isEqualTo(expect);
    }

    @DisplayName("가장 비싼 역의 요금이 적용된다.")
    @Test
    void fare3() {
        int distance = 5;
        List<Line> lines = Arrays.asList(
            new Line("1호선", "red", 100, LocalTime.now(), LocalTime.now(), 10),
            new Line("2호선", "red", 200, LocalTime.now(), LocalTime.now(), 10),
            new Line("3호선", "red", 300, LocalTime.now(), LocalTime.now(), 10));

        WoowaSubwaySubwayFare woowaSubwaySubwayFare = new WoowaSubwaySubwayFare(distance, lines, null);

        assertThat(woowaSubwaySubwayFare.calculate()).isEqualTo(1550);
    }

    @DisplayName("연령별 요금 할인이 적용된다.")
    @CsvSource({"20,1250", "18,720", "12,450"})
    @ParameterizedTest
    void name(int age, int expect) {
        int distance = 5;
        List<Line> lines = Arrays.asList(
            new Line("1호선", "red", 0, LocalTime.now(), LocalTime.now(), 10),
            new Line("2호선", "red", 0, LocalTime.now(), LocalTime.now(), 10),
            new Line("3호선", "red", 0, LocalTime.now(), LocalTime.now(), 10));

        LoginMember loginMember = new LoginMember(1L, "test@test.com", "123", age);

        WoowaSubwaySubwayFare woowaSubwaySubwayFare = new WoowaSubwaySubwayFare(distance, lines, loginMember);

        assertThat(woowaSubwaySubwayFare.calculate()).isEqualTo(expect);
    }
}