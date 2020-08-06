package wooteco.subway.maps.map.domain;

public abstract class SubwayFare {

    private static final int SUBWAY_PRICE = 1250;

    private static final int DISTANCE_EXTRA_PRICE = 100;

    public int calculate() {
        return SUBWAY_PRICE + (distanceExtraCharge() * DISTANCE_EXTRA_PRICE) + stationExtraCharge();
    }

    protected abstract int distanceExtraCharge();

    protected abstract int stationExtraCharge();
}
