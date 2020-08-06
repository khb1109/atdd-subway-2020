package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import wooteco.subway.maps.station.domain.Station;

class WoowaSubwaySubwayFareTest {

    @DisplayName("거리별 요금이 추가된다.")
    @CsvSource({"9,1250", "10,1350", "15,1450", "20,1550"})
    @ParameterizedTest
    void name(int distance, int expect) {
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, new Station("2호선"));
        stations.put(2L, new Station("3호선"));
        stations.put(3L, new Station("4호선"));
        stations.put(4L, new Station("5호선"));

        WoowaSubwaySubwayFare woowaSubwaySubwayFare = new WoowaSubwaySubwayFare(distance, stations);

        assertThat(woowaSubwaySubwayFare.calculate()).isEqualTo(expect);
    }
}