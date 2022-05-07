aCREATE TABLE lectures (
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


INSERT INTO lectures VALUES (1,'testing');
