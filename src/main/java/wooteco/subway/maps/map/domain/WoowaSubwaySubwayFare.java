package wooteco.subway.maps.map.domain;

import java.util.Map;

import wooteco.subway.maps.station.domain.Station;

public class WoowaSubwaySubwayFare extends SubwayFare {

    private final int distance;
    private final Map<Long, Station> stations;

    public WoowaSubwaySubwayFare(int distance, Map<Long, Station> stations) {
        this.distance = distance;
        this.stations = stations;
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
