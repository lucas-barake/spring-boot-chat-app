CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE message
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    content     TEXT      NOT NULL,
    timestamp   TIMESTAMP NOT NULL,
    sender_id   BIGINT    NOT NULL,
    receiver_id BIGINT    NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES user (id),
    FOREIGN KEY (receiver_id) REFERENCES user (id)
);