package wooteco.subway.maps.map.domain;

import java.util.List;

import wooteco.subway.maps.line.domain.Line;

public class WoowaSubwaySubwayFare extends SubwayFare {

    private final int distance;
    private final List<Line> lines;

    public WoowaSubwaySubwayFare(int distance, List<Line> lines) {
        this.distance = distance;
        this.lines = lines;
    }

    @Override
    protected int stationExtraCharge() {
        return 0;
    }

    @Override
    protected int distanceExtraCharge() {
        int weight = 0;
        if (distance >= 10 && distance < 50) {
            weight = (distance - 10) / 5 + 1;
        }
        if (distance >= 50) {
            weight = 8 + ((distance - 50) / 8);
        }
        return weight;
    }
}
