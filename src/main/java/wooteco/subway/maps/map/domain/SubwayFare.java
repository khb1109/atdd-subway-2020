package wooteco.subway.maps.map.domain;

import java.util.Objects;

import wooteco.subway.members.member.domain.LoginMember;

public abstract class SubwayFare {

    private static final int SUBWAY_PRICE = 1250;

    private LoginMember loginMember;

    public SubwayFare(LoginMember loginMember) {
        this.loginMember = loginMember;
    }

    public int calculate() {
        int extraPrice = distanceExtraCharge() + stationExtraCharge();

        if (Objects.isNull(loginMember)) {
            return SUBWAY_PRICE + extraPrice;
        }
        return loginMember.discountFare(SUBWAY_PRICE + extraPrice);
    }

    protected abstract int distanceExtraCharge();

    protected abstract int stationExtraCharge();
}
