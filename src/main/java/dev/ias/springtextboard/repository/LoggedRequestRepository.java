package dev.ias.springtextboard.repository;

import dev.ias.springtextboard.model.requestlog.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoggedRequestRepository<T extends RequestLog> extends JpaRepository<T, Long> {

    Optional<T> findByRemoteAddressAndUserAgentAndMethodAndPath(
            String remoteAddress, String userAgent, String method, String path
    );
}
