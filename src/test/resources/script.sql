CREATE TABLE IF NOT EXISTS lesson (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS professor (
                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR NOT NULL,
                                         age INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS student (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR NOT NULL,
                                       age INTEGER NOT NULL,
                                       course INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS lesson_student (
                                              lesson_id INTEGER REFERENCES lesson(id),
                                              student_id INTEGER REFERENCES student(id),
                                              PRIMARY KEY (lesson_id, student_id)
);

CREATE TABLE IF NOT EXISTS lesson_professor (
                                                lesson_id INTEGER REFERENCES lesson(id),
                                                professor_id INTEGER REFERENCES professor(id),
                                                PRIMARY KEY (lesson_id)
);
