CREATE TABLE comment (
    place VARCHAR(10) NOT NULL,
    username VARCHAR(50) NOT NULL,
    id INTEGER NOT NULL,
    comment VARCHAR(200) NOT NULL,
    PRIMARY KEY (place,username,id)
);


