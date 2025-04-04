CREATE TABLE public.booking
(
	booking_ SERIAL PRIMARY KEY,
	booking_status booking_status NOT NULL,
	check_in timestamp NOT NULL,
	check_out timestamp NOT NULL,
	create_at timestamp DEFAULT (now()):: timestamp (0) without time zone NOT NULL,
	unit_ INT NOT NULL,
	user_ INT NOT NULL,
	CONSTRAINT booking_fk1 FOREIGN KEY (unit_) REFERENCES public.unit(unit_) ON UPDATE RESTRICT ON DELETE CASCADE
);