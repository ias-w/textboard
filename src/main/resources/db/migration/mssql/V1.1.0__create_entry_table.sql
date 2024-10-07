CREATE SEQUENCE entry_seq
    START WITH 1
    INCREMENT BY 50
;

CREATE TABLE entry
(
    id            bigint NOT NULL,
    title         varchar(255),
    text          varchar(1023),
    author        varchar(255),
    creation_date datetimeoffset(6),
    CONSTRAINT pk_entry PRIMARY KEY (id)
)
;
