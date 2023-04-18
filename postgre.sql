CREATE TABLE users
(
    id       serial
        CONSTRAINT users_pk PRIMARY KEY,
    login    varchar NOT NULL,
    password varchar,
    is_admin bool DEFAULT FALSE
);

CREATE TABLE service
(
    id       serial
        CONSTRAINT service_pk PRIMARY KEY,
    service_name    varchar NOT NULL,
    duration integer
);

CREATE TABLE record
(
  id serial
      CONSTRAINT record_pk PRIMARY KEY,
  user_id     integer references users (id) ON UPDATE CASCADE ON DELETE SET NULL,
  car_name varchar,
  date timestamp with time zone,
  duration integer
);

CREATE TABLE record_service
(
    record_id     integer references record (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    service_id  integer references service (id) ON UPDATE CASCADE ON DELETE SET NULL,
    PRIMARY KEY (record_id, service_id)
);

INSERT INTO users(login, password, is_admin)
VALUES
    ('admin', 'admin', true),
    ('superAdmin', 'admin', true),
    ('user', 'user', false),
    ('you', 'you', false);

INSERT INTO service(service_name, duration)
VALUES
    ('Ежедневное мытье машины', 30),
    ('Мытье машины', 60),
    ('Долгое мытье машины', 90);