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
        return getWeight(10, 5) + getWeight(50, 8);
    }

    private int getWeight(int condition, int criteria) {
        if (distance - condition >= 0) {
            return (distance - condition) / criteria + 1;
        }
        return 0;
    }
}
