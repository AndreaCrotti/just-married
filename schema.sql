DROP TABLE IF EXISTS family;
DROP TABLE IF EXISTS people;
DROP TYPE IF EXISTS sposini;

CREATE TYPE sposini AS ENUM ('enrica', 'andrea');

CREATE TABLE people  (
       id serial PRIMARY KEY,
       invited_by sposini NOT NULL,
       first_name VARCHAR,
       last_name VARCHAR,
       comment TEXT,
       -- just a range would be enough?
       -- or know if it's a kid or not?
       -- but better to have the exact age if possible
       -- VARCHAR age,
       lunch BOOLEAN NOT NULL,
       dinner BOOLEAN NOT NULL,

       -- possibly useful to make the tables
       -- could also be an array or a JSON field in theory
       speak_italian BOOLEAN NOT NULL,
       speak_english BOOLEAN NOT NULL,

       -- map of booleans which could default to
       -- {
       --    'celiac': False,
       --    'vegan': False,
       --    'vegetarian' False,
       --    'other': ?
       -- }
       -- Some conditions are mutually exclusive (vegan/vegetarian) but
       -- not all of them are?
       dietary JSON,
       -- validate in some other way that it's actually a valid
       -- phone number at least before using it in twillio??
       phone_number VARCHAR,
       email_address VARCHAR
);

CREATE TABLE family (
       id serial PRIMARY KEY,
       family_name VARCHAR NOT NULL,
       contact_person INTEGER NOT NULL,
       -- unfortunately no way to enforce a foreign key constraint on
       -- all the elements of the array
       family_members INTEGER[10],
       -- might need more structure if more analysis should be done or
       -- could be a simple string if only used for shipping purposes
       address VARCHAR NOT NULL,

       requires_accommodation BOOLEAN NOT NULL,

       FOREIGN KEY(contact_person) REFERENCES people(id)
);


INSERT INTO people (invited_by, first_name, last_name, lunch, dinner, speak_italian, speak_english, phone_number)
VALUES ('andrea', 'nome', 'cognome', true, true, true, false, '+39232322');

INSERT INTO family (family_name, contact_person, family_members, address, requires_accommodation) VALUES
('cro', 1, '{1}', 'Via ponzio pilato', true);

SELECT * FROM people;
SELECT * FROM family;
