package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.model.requestlog.CreationRequestLog;
import dev.ias.springtextboard.model.requestlog.RequestLog;
import dev.ias.springtextboard.model.requestlog.SearchingRequestLog;
import dev.ias.springtextboard.repository.LoggedRequestRepository;
import dev.ias.springtextboard.repository.SearchingRequestLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RequestLoggingService {
    private Logger logger = Logger.getLogger(RequestLoggingService.class.getName());
    private final LoggedRequestRepository<RequestLog> loggedRequestRepository;
    private final SearchingRequestLogRepository searchingRequestLogRepository;

    public CreationRequestLog save(HttpMethod httpMethod, String path, HttpServletRequest request, Entry createdEntry) {
        CreationRequestLog savedRequest = loggedRequestRepository.save(
                CreationRequestLog.builder()
                        .remoteAddress(request.getRemoteAddr())
                        .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                        .method(httpMethod.name())
                        .path(path)
                        .entry(createdEntry)
                        .accessCount(1L)
                        .build()
        );
        logger.info("Saved request: " + savedRequest);
        return savedRequest;
    }

    @Transactional
    public RequestLog save(HttpMethod httpMethod, String path, HttpServletRequest request) {
        RequestLog savedRequest;
        Optional<RequestLog> optional = loggedRequestRepository
                .findByRemoteAddressAndUserAgentAndMethodAndPath(
                        request.getRemoteAddr(), request.getHeader(HttpHeaders.USER_AGENT), httpMethod.name(), path
                );
        if (optional.isPresent()) {
            RequestLog requestLog = optional.get();
            requestLog.setAccessCount(requestLog.getAccessCount() + 1L);
            savedRequest = loggedRequestRepository.save(requestLog);
        } else {
            savedRequest = loggedRequestRepository.save(
                    RequestLog.builder()
                            .remoteAddress(request.getRemoteAddr())
                            .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                            .method(httpMethod.name())
                            .path(path)
                            .accessCount(1L)
                            .build()
            );
        }
        logger.info("Saved request: " + savedRequest);
        return savedRequest;
    }

    @Transactional
    public SearchingRequestLog save(HttpMethod httpMethod, String path, HttpServletRequest request, String query) {
        SearchingRequestLog savedRequest;
        Optional<SearchingRequestLog> optional = searchingRequestLogRepository
                .findByRemoteAddressAndUserAgentAndMethodAndPathAndQuery(
                        request.getRemoteAddr(), request.getHeader(HttpHeaders.USER_AGENT), httpMethod.name(), path, query
                );
        if (optional.isPresent()) {
            SearchingRequestLog searchingRequestLog = optional.get();
            searchingRequestLog.setAccessCount(searchingRequestLog.getAccessCount() + 1L);
            savedRequest = searchingRequestLogRepository.save(searchingRequestLog);
        } else {
            savedRequest = searchingRequestLogRepository.save(
                    SearchingRequestLog.builder()
                            .remoteAddress(request.getRemoteAddr())
                            .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
                            .method(httpMethod.name())
                            .path(path)
                            .query(query)
                            .accessCount(1L)
                            .build()
            );
        }
        logger.info("Saved request: " + savedRequest);
        return savedRequest;
    }
}
