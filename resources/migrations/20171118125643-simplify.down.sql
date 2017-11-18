ALTER TABLE family DROP PRIMARY KEY (family_name);
ALTER TABLE family ADD COLUMN id SERIAL;

ALTER TABLE guest DROP column family_name;

CREATE TABLE family_members (
       id serial PRIMARY KEY,
       family_id INTEGER NOT NULL,
       person_id INTEGER NOT NULL,

       FOREIGN KEY(family_id) REFERENCES family(id),
       FOREIGN KEY(person_id) REFERENCES guest(id)
);

CREATE UNIQUE INDEX family_members_idx ON family_members (family_id, person_id);
