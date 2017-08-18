ALTER TABLE family ADD COLUMN country VARCHAR(2);

CREATE TABLE confirmation (
       id serial PRIMARY KEY,
       coming BOOLEAN NOT NULL,

       -- person that confirmed, at this point it might not have been
       -- already created in people, so should be created first
       confirmed_by INTEGER NOT NULL,
       confirmed_in_date TIMESTAMP DEFAULT CURRENT_DATE,

       FOREIGN KEY(confirmed_by) REFERENCES people(id)
);
