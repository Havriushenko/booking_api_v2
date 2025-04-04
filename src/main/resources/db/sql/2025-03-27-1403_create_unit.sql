CREATE TABLE public.unit
(
    unit_ serial PRIMARY KEY,
    number_of_rooms INT NOT NULL,
    unit_type unit_type NOT NULL,
    floor INT,
    tax NUMERIC(3, 2) DEFAULT 0.15 NOT NULL,
    base_price NUMERIC(12, 2) NOT NULL,
    total_price NUMERIC(12, 2) NOT NULL
);