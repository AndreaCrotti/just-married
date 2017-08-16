CREATE TYPE category AS ENUM ('family', 'work', 'friends');
ALTER TABLE family ADD COLUMN category category;
CREATE INDEX category_people_idx ON family (category);
