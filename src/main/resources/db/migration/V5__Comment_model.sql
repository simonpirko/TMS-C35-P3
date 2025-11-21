CREATE SEQUENCE comment_seq START 1 INCREMENT 1;

CREATE TABLE comment (
                         id BIGINT PRIMARY KEY DEFAULT nextval('comment_seq'),
                         post_id BIGINT NOT NULL,
                         user_id BIGINT,
                         text VARCHAR(255) NOT NULL,
                         create_dt TIMESTAMP,

                         CONSTRAINT fk_comment_user FOREIGN KEY (user_id)
                             REFERENCES users (id)
);