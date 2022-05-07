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

CREATE TABLE comment (
    place VARCHAR(10) NOT NULL,
    username VARCHAR(50) NOT NULL,
    id INTEGER NOT NULL,
    comment VARCHAR(200) NOT NULL,
    PRIMARY KEY (place,username,id)
);


CREATE TABLE lectures (
    lecture_id INTEGER NOT NULL,
    lecture_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (lecture_id)
);

CREATE TABLE materials (
    material_id INTEGER NOT NULL,
    lecture_id INTEGER NOT NULL,
    file_name VARCHAR(50),
    content BLOB,   
    MIME VARCHAR(20),
    PRIMARY KEY (material_id),
    FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id)
);
CREATE TABLE poll_qa (
    id INTEGER NOT NULL,
    poll_q VARCHAR(200) NOT NULL,
    poll_a_a VARCHAR(100) NOT NULL,
    poll_a_b VARCHAR(100) NOT NULL,
    poll_a_c VARCHAR(100) NOT NULL,
    poll_a_d VARCHAR(100) NOT NULL,
    total INT NOT NULL,
    number_of_a INTEGER NOT NULL,
    number_of_b INTEGER NOT NULL,
    number_of_c INTEGER NOT NULL,
    number_of_d INTEGER NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE user_poll_history (
    id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    history_id INTEGER NOT NULL,
    answer VARCHAR(50) NOT NULL,
    PRIMARY KEY (id,username,history_id),
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (id) REFERENCES poll_qa(id)
);

INSERT INTO users VALUES ('admin', '{noop}admin');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('admin', 'Admin Full Name', '12345678','-');
INSERT INTO users VALUES ('lecturer', '{noop}lecturer');
INSERT INTO user_roles(username, role) VALUES ('lecturer', 'ROLE_LECTURER');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('lecturer', 'Lecturer Full Name ', '12345678','-');
INSERT INTO users VALUES ('student', '{noop}student');
INSERT INTO user_roles(username, role) VALUES ('student', 'ROLE_STUDENT');
INSERT INTO user_information(username, fullname, phone, address) VALUES ('student', 'Student Full Name ', '12345678','-');

INSERT INTO poll_qa VALUES (1,'Which date do you prefer for the mid-term test?','8/5', '10/5', '11/5', '12/5', 0, 0, 0, 0, 0);

INSERT INTO lectures VALUES (1,'Take Home Assignment');

INSERT INTO comment VALUES('Lecture1','student',1,'thank you!');


