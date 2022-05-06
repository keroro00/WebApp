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
/*INSERT INTO poll_qa VALUES (1,'demoQ','A', 'B', 'C', 'D', 0, 0, 0, 0, 0);
INSERT INTO user_poll_history VALUES (1,'demo', 0,'A');*/
