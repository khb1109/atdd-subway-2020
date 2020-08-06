package wooteco.subway.maps.line.domain;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import wooteco.subway.common.domain.BaseEntity;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;

    @Embedded
    private LineFare lineFare;

    private LocalTime startTime;

    private LocalTime endTime;

    private int intervalTime;
    @Embedded
    private LineStations lineStations = new LineStations();

    public Line() {
    }

    public Line(String name, String color, LocalTime startTime, LocalTime endTime,
        int intervalTime) {
        this.name = name;
        this.color = color;
        this.lineFare = new LineFare(0);
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public Line(String name, String color, int lineFare, LocalTime startTime, LocalTime endTime,
        int intervalTime) {
        this.name = name;
        this.color = color;
        this.lineFare = new LineFare(lineFare);
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public void update(Line line) {
        this.name = line.getName();
        this.startTime = line.getStartTime();
        this.lineFare = lineFare;
        this.endTime = line.getEndTime();
        this.intervalTime = line.getIntervalTime();
        this.color = line.getColor();
    }

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public void removeLineStationById(Long stationId) {
        lineStations.removeByStationId(stationId);
    }

    public List<LineStation> getStationInOrder() {
        return lineStations.getStationsInOrder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public LineFare getLineFare() {
        return lineFare;
    }

    public LineStations getLineStations() {
        return lineStations;
    }
}
