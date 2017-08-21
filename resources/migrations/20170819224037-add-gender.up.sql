CREATE TYPE gender as ENUM ('male', 'female');
ALTER TABLE people ADD COLUMN gender gender;
