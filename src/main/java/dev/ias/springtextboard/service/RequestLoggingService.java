package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.model.requestlog.CreationRequestLog;
import dev.ias.springtextboard.model.requestlog.RequestLog;
import dev.ias.springtextboard.model.requestlog.SearchingRequestLog;
import dev.ias.springtextboard.repository.LoggedRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RequestLoggingService {
    private Logger logger = Logger.getLogger(RequestLoggingService.class.getName());
    private final LoggedRequestRepository<RequestLog> loggedRequestRepository;

    public CreationRequestLog save(HttpMethod httpMethod, String path, HttpServletRequest request, Entry createdEntry) {
        CreationRequestLog savedRequest = loggedRequestRepository.save(
                CreationRequestLog.builder()
                        .remoteAddress(request.getRemoteAddr())
                        .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                        .method(httpMethod.name())
                        .path(path)
                        .entry(createdEntry)
                        .build()
        );
        logger.info("Saved request: " + savedRequest);
        return savedRequest;
    }

    public RequestLog save(HttpMethod httpMethod, String path, HttpServletRequest request) {
        RequestLog savedRequest = loggedRequestRepository.save(
                RequestLog.builder()
                        .remoteAddress(request.getRemoteAddr())
                        .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                        .method(httpMethod.name())
                        .path(path)
                        .build()
        );
        logger.info("Saved request: " + savedRequest);
        return savedRequest;
    }

    public SearchingRequestLog save(HttpMethod httpMethod, String path, HttpServletRequest request, String query) {
        SearchingRequestLog savedRequest = loggedRequestRepository.save(
                SearchingRequestLog.builder()
                        .remoteAddress(request.getRemoteAddr())
                        .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                        .method(httpMethod.name())
                        .path(path)
                        .query(query)
                        .build()
        );
        logger.info("Saved request: " + savedRequest);
        return savedRequest;
    }
}
