package wooteco.subway.maps.line.exception;

public class InvalidFareException extends RuntimeException {
    public InvalidFareException(int amount) {
        super("잘못된 추가요금을 입력했습니다." + amount);
    }
}
