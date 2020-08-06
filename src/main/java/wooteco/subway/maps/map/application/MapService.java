package wooteco.subway.maps.map.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.maps.line.application.LineService;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.line.dto.LineStationResponse;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.domain.WoowaSubwaySubwayFare;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.dto.PathResponseAssembler;
import wooteco.subway.maps.station.application.StationService;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.domain.LoginMember;

@Service
@Transactional
public class MapService {
    private LineService lineService;
    private StationService stationService;
    private PathService pathService;

    public MapService(LineService lineService, StationService stationService, PathService pathService) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.pathService = pathService;
    }

    public MapResponse findMap() {
        List<Line> lines = lineService.findLines();
        Map<Long, Station> stations = findStations(lines);

        List<LineResponse> lineResponses = lines.stream()
            .map(it -> LineResponse.of(it, extractLineStationResponses(it, stations)))
            .collect(Collectors.toList());

        return new MapResponse(lineResponses);
    }

    @Transactional
    public PathResponse findPath(
        Long source, Long target, PathType type,
        LoginMember loginMember) {
        List<Line> lines = lineService.findLines();
        SubwayPath subwayPath = pathService.findPath(lines, source, target, type);
        Map<Long, Station> stations = stationService.findStationsByIds(subwayPath.extractStationId());

        List<Line> containLines = findLinesByPath(lines, subwayPath.getLineStationEdges());

        WoowaSubwaySubwayFare woowaSubwayFare = new WoowaSubwaySubwayFare(subwayPath.calculateDistance(), containLines,
            loginMember);

        return PathResponseAssembler.assemble(subwayPath, stations, woowaSubwayFare);
    }

    private List<Line> findLinesByPath(List<Line> lines, List<LineStationEdge> subwayPath) {
        Map<Long, Line> lineMap = lines.stream()
            .collect(Collectors.toMap(line -> line.getId(), line -> line));

        return subwayPath.stream()
            .filter(it -> lineMap.containsKey(it.getLineId()))
            .map(it -> lineMap.get(it.getLineId()))
            .collect(Collectors.toList());
    }

    private Map<Long, Station> findStations(List<Line> lines) {
        List<Long> stationIds = lines.stream()
            .flatMap(it -> it.getStationInOrder().stream())
            .map(it -> it.getStationId())
            .collect(Collectors.toList());

        return stationService.findStationsByIds(stationIds);
    }

    private List<LineStationResponse> extractLineStationResponses(Line line, Map<Long, Station> stations) {
        return line.getStationInOrder().stream()
            .map(it -> LineStationResponse.of(line.getId(), it, StationResponse.of(stations.get(it.getStationId()))))
            .collect(Collectors.toList());
    }
}
