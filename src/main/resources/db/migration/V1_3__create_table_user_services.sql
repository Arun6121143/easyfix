CREATE TABLE IF NOT EXISTS easy_fix.user_services (
    user_id BIGINT NOT NULL,
    service VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, service),
    FOREIGN KEY (user_id) REFERENCES easy_fix.user(id) ON DELETE CASCADE
)