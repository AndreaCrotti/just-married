DROP TABLE family;
DROP TABLE people;

CREATE TABLE people  (
       id serial PRIMARY KEY,
       first_name VARCHAR,
       last_name VARCHAR,
       -- just a range would be enough?
       -- or know if it's a kid or not?
       -- but better to have the exact age if possible
       -- VARCHAR age,
       lunch BOOLEAN,
       dinner BOOLEAN,
       -- map of booleans which could default to
       -- {
       --    'celiac': False,
       --    'vegan': False,
       --    'vegetarian' False,
       --    'other': ?
       -- }
       -- Some conditions are mutually exclusive (vegan/vegetarian) but
       -- not all of them are?
       dietary JSON
);

CREATE TABLE family (
       id serial,
       family_name VARCHAR,
       contact_person INTEGER,
       family_members INTEGER[10],
       -- might need more structuring
       address VARCHAR,
       FOREIGN KEY(contact_person) REFERENCES people(id)
);

SELECT * FROM family;
