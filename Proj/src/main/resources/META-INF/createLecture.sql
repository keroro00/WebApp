CREATE TABLE lectures (
    lecture_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lecture_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (lecture_id)
);

CREATE TABLE materials (
    material_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lecture_id INTEGER NOT NULL,
    file_name VARCHAR(50),
    content BLOB,
    PRIMARY KEY (material_id),
    FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id)
);


INSERT INTO lectures(lecture_name) VALUES ('testing');
