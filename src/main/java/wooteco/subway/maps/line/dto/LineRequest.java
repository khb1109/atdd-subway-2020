package wooteco.subway.maps.line.dto;

import java.time.LocalTime;

import wooteco.subway.maps.line.domain.Line;

public class LineRequest {
    private String name;
    private String color;
    private int lineFare;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer intervalTime;

    public LineRequest() {
    }

    public LineRequest(String name, String color, LocalTime startTime, LocalTime endTime,
        Integer intervalTime) {
        this.name = name;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public LineRequest(String name, String color, int lineFare, LocalTime startTime, LocalTime endTime,
        Integer intervalTime) {
        this.name = name;
        this.color = color;
        this.lineFare = lineFare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getLineFare() {
        return lineFare;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public Line toLine() {
        return new Line(name, color, lineFare, startTime, endTime, intervalTime);
    }
}
