-- useful enums
CREATE TYPE sposini AS ENUM ('enrica', 'andrea');
CREATE TYPE gender as ENUM ('male', 'female');
CREATE TYPE category AS ENUM ('family', 'work', 'friends');

-- guests table
CREATE TABLE guest  (
       id serial PRIMARY KEY,
       first_name VARCHAR,
       last_name VARCHAR,
       comment TEXT,
       -- to count the number of kids define what's the actually age
       -- threshold for that
       kid BOOLEAN,

       -- possibly useful to make the tables
       -- could also be an array or a JSON field in theory
       speak_italian BOOLEAN,
       speak_english BOOLEAN,
       gender gender,

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

-- family table
CREATE TABLE family (
       id serial PRIMARY KEY,
       -- simply use the family name as key here
       -- no need for extra fancy keys
       family_name VARCHAR(32) NOT NULL,
       category category,

       invited_by sposini NOT NULL,
       comment TEXT,

       lunch BOOLEAN,
       dinner BOOLEAN,

       contact_person INTEGER,

       country VARCHAR(2),
       -- address contains everything except for the country
       address VARCHAR,

       requires_accommodation BOOLEAN,

       FOREIGN KEY(contact_person) REFERENCES guest(id)
);


CREATE TABLE family_members (
       id serial PRIMARY KEY,
       family_id INTEGER NOT NULL,
       person_id INTEGER NOT NULL,

       FOREIGN KEY(family_id) REFERENCES family(id),
       FOREIGN KEY(person_id) REFERENCES guest(id)
);

CREATE UNIQUE INDEX family_members_idx ON family_members (family_id, person_id);

CREATE TABLE confirmation (
       id serial PRIMARY KEY,
       coming BOOLEAN NOT NULL,

       invited_in_date TIMESTAMP DEFAULT CURRENT_DATE,
       -- person that confirmed, at this point it might not have been
       -- already created in people, so should be created first
       confirmed_by INTEGER NOT NULL,
       confirmed_in_date TIMESTAMP DEFAULT CURRENT_DATE,

       FOREIGN KEY(confirmed_by) REFERENCES guest(id)
);
