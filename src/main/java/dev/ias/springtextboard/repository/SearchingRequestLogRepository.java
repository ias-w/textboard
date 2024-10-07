package dev.ias.springtextboard.repository;

import dev.ias.springtextboard.model.requestlog.SearchingRequestLog;

import java.util.Optional;

public interface SearchingRequestLogRepository extends LoggedRequestRepository<SearchingRequestLog> {

    Optional<SearchingRequestLog> findByRemoteAddressAndUserAgentAndMethodAndPathAndQuery(
            String remoteAddress, String userAgent, String method, String path, String query
    );
}
