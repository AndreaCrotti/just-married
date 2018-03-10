CREATE TABLE rvsp (
       -- automatic fields that should not be overriden
       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
       -- user required fields
       coming BOOLEAN NOT NULL,
       name VARCHAR(100) NOT NULL,
       how_many INTEGER NOT NULL,
       -- optional fields
       email VARCHAR(100),
       phone_number VARCHAR(32),
       comment VARCHAR(300)
);
