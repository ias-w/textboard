package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.requestlog.RequestLog;
import dev.ias.springtextboard.repository.LoggedRequestRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestLoggingServiceRequestLogUnitTest {
    @Mock
    private LoggedRequestRepository<RequestLog> loggedRequestRepository;
    @InjectMocks
    private RequestLoggingService requestLoggingService;

    @Mock
    private HttpServletRequest httpServletRequest;
    private HttpMethod getMethod;
    private String userAgent;
    private String path;
    private String localhost;

    private RequestLog requestLog;

    @BeforeEach
    public void setUp() {
        getMethod = HttpMethod.GET;
        path = "/path";
        userAgent = "Mozilla/5.0";
        localhost = "127.0.0.1";

        requestLog = RequestLog.builder()
                .remoteAddress(localhost)
                .userAgent(userAgent)
                .method(getMethod.name())
                .path(path)
                .accessCount(1L)
                .build();
    }

    @Test
    @Order(1)
    void shouldSaveRequestLog_whenTheRequestParametersReturnNoResult() {
        doReturn(localhost).when(httpServletRequest).getRemoteAddr();
        doReturn(userAgent).when(httpServletRequest).getHeader(HttpHeaders.USER_AGENT);
        doReturn(Optional.empty()).when(loggedRequestRepository)
                .findByRemoteAddressAndUserAgentAndMethodAndPath(
                        localhost, userAgent, getMethod.name(), path
                );
        doReturn(requestLog).when(loggedRequestRepository).save(any(RequestLog.class));

        RequestLog createdRequestLog = requestLoggingService.save(
                getMethod, path, httpServletRequest
        );

        assertThat(createdRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdRequestLog.getMethod()).isEqualTo(getMethod.name());
        assertThat(createdRequestLog.getPath()).isEqualTo(path);
        assertThat(createdRequestLog.getAccessCount()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    void shouldSaveRequestLog_whenTheRequestParametersReturnResult() {
        doReturn(localhost).when(httpServletRequest).getRemoteAddr();
        doReturn(userAgent).when(httpServletRequest).getHeader(HttpHeaders.USER_AGENT);
        doReturn(Optional.of(requestLog)).when(loggedRequestRepository)
                .findByRemoteAddressAndUserAgentAndMethodAndPath(
                        localhost, userAgent, getMethod.name(), path
                );
        doReturn(requestLog).when(loggedRequestRepository).save(any(RequestLog.class));

        RequestLog createdRequestLog = requestLoggingService.save(
                getMethod, path, httpServletRequest
        );

        then(loggedRequestRepository).should().save(requestLog);
        assertThat(createdRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdRequestLog.getMethod()).isEqualTo(getMethod.name());
        assertThat(createdRequestLog.getPath()).isEqualTo(path);
        assertThat(createdRequestLog.getAccessCount()).isEqualTo(2L);
    }
}
