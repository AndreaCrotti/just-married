ALTER TABLE family ADD COLUMN country VARCHAR(2);

CREATE TABLE confirmation (
       id serial PRIMARY KEY,

       family_id INTEGER NOT NULL,
       confirmed_by INTEGER NOT NULL,

       confirmed_in_date TIMESTAMP DEFAULT CURRENT_DATE,

       FOREIGN KEY(family_id) REFERENCES family(id),
       FOREIGN KEY(confirmed_by) REFERENCES people(id)
);
