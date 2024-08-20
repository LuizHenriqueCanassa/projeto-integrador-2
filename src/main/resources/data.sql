CREATE TABLE USERS (
                       ID INTEGER NOT NULL,
                       USERNAME VARCHAR(100) NOT NULL,
                       PASSWORD VARCHAR(100) NOT NULL,
                       CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       UPDATED_AT TIMESTAMP
);

CREATE SEQUENCE SQ_USERS_ID START 1 INCREMENT 1 OWNED BY USERS.ID;

ALTER TABLE USERS ADD CONSTRAINT PK_ID_USERS PRIMARY KEY (ID);
ALTER TABLE USERS ADD CONSTRAINT UK_USERNAME_USERS UNIQUE (USERNAME);

CREATE TABLE ROLES (
                       ID INTEGER NOT NULL,
                       NAME VARCHAR(100) NOT NULL,
                       CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       UPDATED_AT TIMESTAMP
);

CREATE SEQUENCE SQ_ROLES_ID START 1 INCREMENT 1 OWNED BY ROLES.ID;

ALTER TABLE ROLES ADD CONSTRAINT PK_ID_ROLES PRIMARY KEY (ID);
ALTER TABLE ROLES ADD CONSTRAINT UK_NAME_ROLES UNIQUE (NAME);

CREATE TABLE USERS_ROLES (
                             USER_ID INTEGER NOT NULL,
                             ROLE_ID INTEGER NOT NULL
);

ALTER TABLE USERS_ROLES ADD CONSTRAINT FK_USERS_ID FOREIGN KEY (USER_ID) REFERENCES users(ID);
ALTER TABLE USERS_ROLES ADD CONSTRAINT FK_ROLES_ID FOREIGN KEY (ROLE_ID) REFERENCES roles(ID);

--senha-admin
INSERT INTO USERS
(ID, USERNAME, PASSWORD)
VALUES
    (nextval('SQ_USERS_ID'), 'admin', '$2a$12$IOOSbHD9umswOg2LOHl86uKcwKWnLfBmLdKgqGLF1GRaJaff8j3W.');

--senha-user
INSERT INTO USERS
(ID, USERNAME, PASSWORD)
VALUES
    (nextval('SQ_USERS_ID'), 'user', '$2a$12$MEoC5oOz11Df4DYqOjL/QeutqOthlcWVe3y7vkRku2gwvUU6lyZmO');

INSERT INTO ROLES
(ID, NAME)
VALUES
    (nextval('SQ_ROLES_ID'), 'ROLE_ADMIN');

INSERT INTO ROLES
(ID, NAME)
VALUES
    (nextval('SQ_ROLES_ID'), 'ROLE_USER');

INSERT INTO USERS_ROLES
(USER_ID, ROLE_ID)
VALUES
    (1,1);

INSERT INTO USERS_ROLES
(USER_ID, ROLE_ID)
VALUES
    (2,2);