CREATE TABLE family_members (
       id serial PRIMARY KEY,
       family_id INTEGER NOT NULL,
       person_id INTEGER NOT NULL,

       FOREIGN KEY(family_id) REFERENCES family(id),
       FOREIGN KEY(person_id) REFERENCES people(id)
);

CREATE UNIQUE INDEX family_members_idx ON family_members (family_id, person_id);
