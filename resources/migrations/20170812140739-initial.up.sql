CREATE TYPE sposini AS ENUM ('enrica', 'andrea');

CREATE TABLE people  (
       id serial PRIMARY KEY,
       first_name VARCHAR,
       last_name VARCHAR,
       comment TEXT,
       -- to count the number of kids define what's the actually age
       -- threshold for that
       kid BOOLEAN NOT NULL,

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
       -- should be required at least for the main contact person, not
       -- sure how to add that kind of constraint directly inside
       -- Postgres and if that's actually possible
       email_address VARCHAR
);

CREATE TABLE family (
       id serial PRIMARY KEY,
       invited_by sposini NOT NULL,
       comment TEXT,

       lunch BOOLEAN NOT NULL,
       dinner BOOLEAN NOT NULL,

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
