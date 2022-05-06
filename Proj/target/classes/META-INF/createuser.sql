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

INSERT INTO users VALUES ('admin', '{noop}admin');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('admin', 'demo Full Name ', '12345678','-');
INSERT INTO users VALUES ('lecturer', '{noop}lecturer');
INSERT INTO user_roles(username, role) VALUES ('lecturer', 'ROLE_LECTURER');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('lecturer', 'demo Full Name ', '12345678','-');
INSERT INTO users VALUES ('student', '{noop}student');
INSERT INTO user_roles(username, role) VALUES ('student', 'ROLE_STUDENT');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('student', 'demo Full Name ', '12345678','-');