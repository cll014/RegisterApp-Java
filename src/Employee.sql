CREATE TABLE employee (
  record_id uuid NOT NULL DEFAULT gen_random_uuid(),
  employee_id int NOT NULL DEFAULT(0),
  first_name Char(128) NOT NULL DEFAULT(''),
  last_name Char(128) NOT NULL DEFAULT(''),
  password bytea NOT NULL DEFAULT(''),
  active boolean NOT NULL DEFAULT(FALSE),
  classification int NOT NULL DEFAULT(0),
  classification Char NOT NULL CHECK(classification in ('general manager', 'shift manager', 'cashier')),
  created_On timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT employee_pkey PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

--  A cheap way to prevent malicious actors from using employee IDs to determine
-- how many employees are in your company. Obviously this is not foolproof.
CREATE SEQUENCE employee_employeeid_seq AS int START WITH 253 INCREMENT BY 7 OWNED BY employee.employeeid;

ALTER TABLE employee ALTER employee_id SET DEFAULT nextval('employee_employeeid_seq');

CREATE INDEX ix_employee_employeeid
  ON employee
  USING btree(employee_id);

-----
CREATE TABLE Active_User (
  record_id uuid NOT NULL DEFAULT gen_random_uuid(),
  employee_id uuid NOT NULL,
  name Char(256) NOT NULL DEFAULT(''),
  classification Char NOT NULL CHECK(classification IN ('general manager', 'shift manager', 'cashier')),
  session_key Char(128) NOT NULL DEFAULT(''),
  created_On timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT activeUser_pkey PRIMARY KEY (record_id)
) WITH (
  OIDS=FALSE
);

CREATE INDEX ix_activeuser_employeeid
  ON active_User
  USING btree(employee_id);

CREATE INDEX ix_activeuser_sessionkey
  ON active_User
  USING btree(session_key);
