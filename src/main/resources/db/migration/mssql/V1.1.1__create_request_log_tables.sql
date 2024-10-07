CREATE SEQUENCE request_log_seq
    START WITH 1
    INCREMENT BY 50
;

CREATE TABLE request_log
(
    id             bigint NOT NULL,
    remote_address varchar(255),
    user_agent     varchar(255),
    method         varchar(255),
    path           varchar(255),
    created_at     datetimeoffset(6),
    CONSTRAINT pk_requestlog PRIMARY KEY (id)
)
;

CREATE TABLE searching_request_log
(
    id    bigint NOT NULL,
    query varchar(1023),
    CONSTRAINT pk_searchingrequestlog PRIMARY KEY (id),
    CONSTRAINT fk_searching_request_log_on_id
        FOREIGN KEY (id) REFERENCES request_log (id)
)
;

CREATE TABLE creation_request_log
(
    id       bigint NOT NULL,
    entry_id bigint,
    CONSTRAINT pk_creationrequestlog PRIMARY KEY (id),
    CONSTRAINT fk_creation_request_log_on_id
        FOREIGN KEY (id) REFERENCES request_log (id),
    CONSTRAINT fk_creation_request_log_on_entry
        FOREIGN KEY (entry_id) REFERENCES entry (id)
)
;
