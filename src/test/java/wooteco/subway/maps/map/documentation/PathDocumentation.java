package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.dto.StationResponse;

@WebMvcTest(controllers = MapController.class)
public class PathDocumentation extends Documentation {

    protected TokenResponse tokenResponse;
    @Autowired
    private MapController mapController;
    @MockBean
    private MapService mapService;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @DisplayName("지하철의 경로를 조회한다.")
    @Test
    void findPath() {
        PathResponse pathResponse = new PathResponse(Arrays.asList(
            new StationResponse(1L, "2호선", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(2L, "2호선", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(3L, "2호선", LocalDateTime.now(), LocalDateTime.now()))
            , 10, 10, 1250);

        BDDMockito.given(mapService.findPath(anyLong(), anyLong(), any(PathType.class), null))
            .willReturn(pathResponse);

        given().log().all().
            header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            get("/paths?source=1&target=2&type=DURATION").
            then().
            log().all().
            apply(document("map/find-path",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                requestParameters(
                    parameterWithName("source").description("출발역 아이디"),
                    parameterWithName("target").description("도착역 아이디"),
                    parameterWithName("type").description("패스 비용 기준")
                ),
                responseFields(
                    fieldWithPath("stations").type(JsonFieldType.ARRAY).description("최단경로드"),
                    fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("스테이션 ID"),
                    fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("스테이션 이름"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("예상 시간"),
                    fieldWithPath("distance").type(JsonFieldType.NUMBER).description("예상 거리"),
                    fieldWithPath("fare").type(JsonFieldType.NUMBER).description("경로 비용")))).
            extract();
    }
}
