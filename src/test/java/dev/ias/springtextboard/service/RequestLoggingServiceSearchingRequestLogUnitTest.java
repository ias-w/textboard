package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.requestlog.SearchingRequestLog;
import dev.ias.springtextboard.repository.SearchingRequestLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestLoggingServiceSearchingRequestLogUnitTest {
    @Mock
    private SearchingRequestLogRepository searchingRequestLogRepository;
    @InjectMocks
    private RequestLoggingService requestLoggingService;

    @Mock
    private HttpServletRequest httpServletRequest;
    private HttpMethod getMethod;
    private String userAgent;
    private String path;
    private String localhost;
    private String query;

    private SearchingRequestLog searchingRequestLog;

    @BeforeEach
    public void setUp() {
        getMethod = HttpMethod.GET;
        path = "/path";
        userAgent = "Mozilla/5.0";
        localhost = "127.0.0.1";
        query = "query=query";

        searchingRequestLog = SearchingRequestLog.builder()
                .remoteAddress(localhost)
                .userAgent(userAgent)
                .method(getMethod.name())
                .path(path)
                .accessCount(1L)
                .query(query)
                .build();
    }

    @Test
    @Order(1)
    void shouldSaveSearchingRequest_whenTheRequestParametersReturnNoResult() {
        doReturn(localhost).when(httpServletRequest).getRemoteAddr();
        doReturn(userAgent).when(httpServletRequest).getHeader(HttpHeaders.USER_AGENT);
        doReturn(Optional.empty()).when(searchingRequestLogRepository)
                .findByRemoteAddressAndUserAgentAndMethodAndPathAndQuery(
                        localhost, userAgent, getMethod.name(), path, query
                );
        doReturn(searchingRequestLog).when(searchingRequestLogRepository).save(any(SearchingRequestLog.class));

        SearchingRequestLog createdSearchingRequestLog = requestLoggingService.save(
                getMethod, path, httpServletRequest, query
        );

        assertThat(createdSearchingRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdSearchingRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdSearchingRequestLog.getMethod()).isEqualTo(getMethod.name());
        assertThat(createdSearchingRequestLog.getPath()).isEqualTo(path);
        assertThat(createdSearchingRequestLog.getQuery()).isEqualTo(query);
        assertThat(createdSearchingRequestLog.getAccessCount()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    void shouldSaveSearchingRequest_whenTheRequestParametersReturnResult() {
        doReturn(localhost).when(httpServletRequest).getRemoteAddr();
        doReturn(userAgent).when(httpServletRequest).getHeader(HttpHeaders.USER_AGENT);
        doReturn(Optional.of(searchingRequestLog)).when(searchingRequestLogRepository)
                .findByRemoteAddressAndUserAgentAndMethodAndPathAndQuery(
                        localhost, userAgent, getMethod.name(), path, query
                );
        doReturn(searchingRequestLog).when(searchingRequestLogRepository).save(any(SearchingRequestLog.class));

        SearchingRequestLog createdSearchingRequestLog = requestLoggingService.save(
                getMethod, path, httpServletRequest, query
        );

        then(searchingRequestLogRepository).should().save(searchingRequestLog);
        assertThat(createdSearchingRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdSearchingRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdSearchingRequestLog.getMethod()).isEqualTo(getMethod.name());
        assertThat(createdSearchingRequestLog.getPath()).isEqualTo(path);
        assertThat(createdSearchingRequestLog.getQuery()).isEqualTo(query);
        assertThat(createdSearchingRequestLog.getAccessCount()).isEqualTo(2L);

    }
}
