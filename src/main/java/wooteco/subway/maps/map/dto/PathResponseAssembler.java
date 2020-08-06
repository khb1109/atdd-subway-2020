package wooteco.subway.maps.map.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.domain.WoowaSubwaySubwayFare;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

public class PathResponseAssembler {
    public static PathResponse assemble(SubwayPath subwayPath, Map<Long, Station> stations,
        WoowaSubwaySubwayFare woowaSubwayFare) {
        List<StationResponse> stationResponses = subwayPath.extractStationId().stream()
            .map(it -> StationResponse.of(stations.get(it)))
            .collect(Collectors.toList());

        int distance = subwayPath.calculateDistance();
        int price = woowaSubwayFare.calculate();

        return new PathResponse(stationResponses, subwayPath.calculateDuration(), distance, price);
    }
}
