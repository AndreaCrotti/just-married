DROP TABLE IF EXISTS family_members;
DROP INDEX IF EXISTS family_members_idx;

ALTER TABLE family DROP COLUMN id;

ALTER TABLE family ADD PRIMARY KEY (family_name);

--TODO: this should actually be a NOT NULL as well
ALTER TABLE guest ADD COLUMN family_name VARCHAR(32);
ALTER TABLE guest ADD FOREIGN KEY (family_name) REFERENCES family;

-- should we do a data migration as part of this as well ideally?
