CREATE TABLE movie (
    id SERIAL PRIMARY KEY,
    category_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    year INTEGER NOT NULL,
    priority INTEGER NOT NULL,
    person_id INTEGER NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);