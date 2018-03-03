-- useful enums
CREATE TYPE sposini AS ENUM ('enrica', 'andrea');
CREATE TYPE gender as ENUM ('male', 'female');
CREATE TYPE category AS ENUM ('family', 'work', 'friends');

CREATE CAST (varchar AS "sposini") WITH INOUT AS IMPLICIT;
-- guests table
CREATE TABLE guest  (
       id UUID PRIMARY KEY,

       first_name VARCHAR,
       last_name VARCHAR,
       comment TEXT,
       -- to count the number of kids define what's the actually age
       -- threshold for that
       kid BOOLEAN,

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
       email_address VARCHAR,

       group_id UUID NOT NULL
);

-- guests_group table
CREATE TABLE guests_group (
       id UUID PRIMARY KEY,
       group_name VARCHAR(32) NOT NULL,
       category category,

       invited_by sposini NOT NULL,
       comment TEXT,
       invitation_sent BOOLEAN default FALSE,

       ceremony BOOLEAN,
       lunch BOOLEAN,
       dinner BOOLEAN,

       contact_person UUID,

       country VARCHAR(2),
       -- address contains everything except for the country
       address VARCHAR,

       FOREIGN KEY(contact_person) REFERENCES guest(id)
);

ALTER TABLE guest ADD FOREIGN KEY(group_id) REFERENCES guests_group(id);

CREATE TABLE confirmation (
       id UUID PRIMARY KEY,
       coming BOOLEAN NOT NULL,
       guest_id UUID NOT NULL,

       -- person that confirmed, at this point it might not have been
       -- already created in people, so should be created first
       confirmed_in_date TIMESTAMP DEFAULT CURRENT_DATE,

       FOREIGN KEY(guest_id) REFERENCES guest(id)
);
