CREATE TABLE IF NOT EXISTS users
(
    id        SERIAL PRIMARY KEY,
    email     VARCHAR(100) UNIQUE NOT NULL,
    username  VARCHAR(100)        NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    is_banned BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id   BIGINT      NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
