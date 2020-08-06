package wooteco.subway.maps.line.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import wooteco.subway.maps.line.exception.InvalidFareException;

@Embeddable
public class LineFare {

    @Column(nullable = true)
    private int amount;

    public LineFare() {
    }

    public LineFare(int amount) {
        if (amount < 0) {
            throw new InvalidFareException(amount);
        }
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
