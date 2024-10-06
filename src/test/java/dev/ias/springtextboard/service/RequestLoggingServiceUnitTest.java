package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.model.requestlog.CreationRequestLog;
import dev.ias.springtextboard.model.requestlog.RequestLog;
import dev.ias.springtextboard.model.requestlog.SearchingRequestLog;
import dev.ias.springtextboard.repository.LoggedRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestLoggingServiceUnitTest {
    @Mock
    private LoggedRequestRepository<RequestLog> loggedRequestRepository;
    @InjectMocks
    private RequestLoggingService requestLoggingService;

    @Mock
    private HttpServletRequest httpServletRequest;
    private HttpMethod getMethod;
    private HttpMethod postMethod;
    private String userAgent;
    private String path;
    private String localhost;
    private String query;

    private CreationRequestLog creationRequestLog;
    private RequestLog requestLog;
    private SearchingRequestLog searchingRequest;

private MockedStatic<Clock> clockMockedStatic;
    @BeforeEach
    public void setUp() {
        getMethod = HttpMethod.GET;
        postMethod = HttpMethod.POST;
        path = "/path";
        userAgent = "Mozilla/5.0";
        localhost = "127.0.0.1";
        query = "query=query";

        creationRequestLog = CreationRequestLog.builder()
                .remoteAddress(localhost)
                .userAgent(userAgent)
                .method(postMethod.name())
                .path(path)
                .entry(Entry.builder().id(1001L).build())
                .build();

        requestLog = RequestLog.builder()
                .remoteAddress(localhost)
                .userAgent(userAgent)
                .method(getMethod.name())
                .path(path)
                .build();

        searchingRequest = SearchingRequestLog.builder()
                .remoteAddress(localhost)
                .userAgent(userAgent)
                .method(getMethod.name())
                .path(path)
                .query(query)
                .build();
    }

    @Test
    @Order(1)
    void shouldSaveCreationRequest() {
        doReturn(creationRequestLog).when(loggedRequestRepository).save(any(RequestLog.class));

        CreationRequestLog createdCreationRequestLog = requestLoggingService.save(
                postMethod, path, httpServletRequest, creationRequestLog.getEntry()
        );

        assertThat(createdCreationRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdCreationRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdCreationRequestLog.getMethod()).isEqualTo(postMethod.name());
        assertThat(createdCreationRequestLog.getPath()).isEqualTo(path);
        assertThat(createdCreationRequestLog.getEntry().getId()).isEqualTo(1001L);
    }

    @Test
    @Order(2)
    void shouldSaveSearchingRequest() {
        doReturn(searchingRequest).when(loggedRequestRepository).save(any(RequestLog.class));

        SearchingRequestLog createdSearchingRequestLog = requestLoggingService.save(
                getMethod, path, httpServletRequest, query
        );

        assertThat(createdSearchingRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdSearchingRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdSearchingRequestLog.getMethod()).isEqualTo(getMethod.name());
        assertThat(createdSearchingRequestLog.getPath()).isEqualTo(path);
        assertThat(createdSearchingRequestLog.getQuery()).isEqualTo(query);
    }

    @Test
    @Order(3)
    void shouldSaveRequestLog() {
        doReturn(requestLog).when(loggedRequestRepository).save(any(RequestLog.class));

        RequestLog createdRequestLog = requestLoggingService.save(
                getMethod, path, httpServletRequest
        );

        assertThat(createdRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdRequestLog.getMethod()).isEqualTo(getMethod.name());
        assertThat(createdRequestLog.getPath()).isEqualTo(path);
    }
}
