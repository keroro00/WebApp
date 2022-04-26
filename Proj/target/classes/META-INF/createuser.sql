CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE user_information (
    username VARCHAR(50) NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('tony', '{noop}tonypw');
INSERT INTO user_roles(username, role) VALUES ('tony', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('tony', 'ROLE_ADMIN');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('tony', 'Ho Yatchuen', '67051212','-');
