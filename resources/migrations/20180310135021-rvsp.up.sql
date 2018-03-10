CREATE TABLE rvsp (
       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
       coming BOOLEAN NOT NULL,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100),
       how_many INTEGER NOT NULL,
       phone_number VARCHAR(32),
       comment VARCHAR(300)
);
