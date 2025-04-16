CREATE TABLE schedule (
                          id BIGSERIAL PRIMARY KEY,
                          event_id BIGINT,
                          title VARCHAR(200),
                          description VARCHAR(200),
                          images JSONB,
                          location JSONB,
                          started_at TIMESTAMP,
                          finished_at TIMESTAMP
);

CREATE TABLE schedule_notification_cron (
                                            id BIGSERIAL PRIMARY KEY,
                                            schedule_id BIGINT,
                                            started_at TIMESTAMP,
                                            is_canceled BOOLEAN
);

CREATE TABLE suplier (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(200),
                         phone VARCHAR(200),
                         email VARCHAR(200),
                         description VARCHAR(200),
                         type VARCHAR(50),
                         location JSONB
);

CREATE TABLE rel_event_supplier (
                                    id BIGSERIAL PRIMARY KEY,
                                    event_id BIGINT,
                                    supplier_id BIGINT
);

CREATE TABLE feed_back (
                           id BIGSERIAL PRIMARY KEY,
                           event_id BIGINT,
                           user_id BIGINT,
                           content VARCHAR(200),
                           images JSONB,
                           created_at TIMESTAMP,
                           updated_at TIMESTAMP
);
