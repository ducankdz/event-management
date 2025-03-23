CREATE TABLE users
(
    id         BIGINT PRIMARY KEY,
    email      VARCHAR(255),
    password   VARCHAR(255),
    full_name  VARCHAR(255),
    avatar     VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE user_roles
(
    user_id BIGINT,
    role_id BIGINT
);

CREATE TABLE event
(
    id              BIGINT PRIMARY KEY,
    owner_id        BIGINT,
    updated_user_id BIGINT,
    name            VARCHAR(255),
    avatar          VARCHAR(255),
    images          JSON,
    description     TEXT,
    state           VARCHAR(50),
    location        JSON,
    schedule        JSON,
    started_at      TIMESTAMP,
    ended_at        TIMESTAMP,
    is_deleted      BOOLEAN,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE notification
(
    id         UUID PRIMARY KEY,
    type       VARCHAR(255),
    action     VARCHAR(50),
    code       VARCHAR(50),
    user_id    BIGINT,
    title      VARCHAR(255),
    content    TEXT,
    data       JSON,
    read       BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE rel_task_user
(
    id      BIGINT PRIMARY KEY,
    task_id BIGINT,
    user_id BIGINT
);

CREATE TABLE stage
(
    id              BIGINT PRIMARY KEY,
    event_id        BIGINT,
    created_user_id BIGINT,
    updated_user_id BIGINT,
    name            VARCHAR(255),
    stage           VARCHAR(50),
    description     TEXT,
    ended_at        TIMESTAMP,
    started_at      TIMESTAMP,
    is_deleted      BOOLEAN,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE task
(
    id              BIGINT PRIMARY KEY,
    created_user_id BIGINT,
    updated_user_id BIGINT,
    event_id        BIGINT,
    stage_id        BIGINT,
    name            VARCHAR(255),
    description     TEXT,
    state           VARCHAR(50),
    images          JSON,
    budget          BIGINT,
    is_deleted      BOOLEAN,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE rel_event_user
(
    id       BIGINT PRIMARY KEY,
    user_id  BIGINT,
    event_id BIGINT
);