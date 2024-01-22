CREATE TABLE roles
(
    role_id UUID PRIMARY KEY DEFAULT (UUID()),
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    user_id  UUID PRIMARY KEY DEFAULT (UUID()),
    name     VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);


INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_PATIENT'),
       ('ROLE_DOCTOR'),
       ('ROLE_ASSISTANT');