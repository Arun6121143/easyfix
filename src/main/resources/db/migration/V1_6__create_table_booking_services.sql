CREATE TABLE IF NOT EXISTS easy_fix.booking_services(
  id INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT BY 1) NOT NULL,
  user_id INTEGER NOT NULL,
  service_provider_id INTEGER NOT NULL,
  service_id INTEGER NOT NULL,
  status VARCHAR DEFAULT 'PENDING',
  service_description VARCHAR,
  booking_date DATE,
  booking_time TIME,
  address varchar,
  booking_create_date TIMESTAMP WITH TIME ZONE NOT NULL,
  deleted_by VARCHAR,
  deleted_date TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT pk_easy_fix_booking_services PRIMARY KEY
  (
        id
  )

)