package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.model.requestlog.CreationRequestLog;
import dev.ias.springtextboard.repository.LoggedRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestLoggingServiceCreationRequestLogUnitTest {
    @Mock
    private LoggedRequestRepository<CreationRequestLog> loggedRequestRepository;
    @InjectMocks
    private RequestLoggingService requestLoggingService;

    @Mock
    private HttpServletRequest httpServletRequest;
    private HttpMethod postMethod;
    private String userAgent;
    private String path;
    private String localhost;

    private CreationRequestLog creationRequestLog;

    @BeforeEach
    public void setUp() {
        postMethod = HttpMethod.POST;
        path = "/path";
        userAgent = "Mozilla/5.0";
        localhost = "127.0.0.1";

        creationRequestLog = CreationRequestLog.builder()
                .remoteAddress(localhost)
                .userAgent(userAgent)
                .method(postMethod.name())
                .path(path)
                .accessCount(1L)
                .entry(Entry.builder().id(1001L).build())
                .build();
    }

    @Test
    @Order(1)
    void shouldSaveCreationRequest() {
        doReturn(localhost).when(httpServletRequest).getRemoteAddr();
        doReturn(userAgent).when(httpServletRequest).getHeader(HttpHeaders.USER_AGENT);
        doReturn(creationRequestLog).when(loggedRequestRepository).save(any(CreationRequestLog.class));

        CreationRequestLog createdCreationRequestLog = requestLoggingService.save(
                postMethod, path, httpServletRequest, creationRequestLog.getEntry()
        );

        assertThat(createdCreationRequestLog.getRemoteAddress()).isEqualTo(localhost);
        assertThat(createdCreationRequestLog.getUserAgent()).isEqualTo(userAgent);
        assertThat(createdCreationRequestLog.getMethod()).isEqualTo(postMethod.name());
        assertThat(createdCreationRequestLog.getPath()).isEqualTo(path);
        assertThat(createdCreationRequestLog.getEntry().getId()).isEqualTo(1001L);
    }
}
