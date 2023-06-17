CREATE TABLE customer (
    customer_id BIGSERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
)
CREATE TABLE station (
    id BIGSERIAL PRIMARY KEY,
    kwh INTEGER NOT NULL,
    last_name TEXT NOT NULL,
)
CREATE TABLE stations (
    station_id BIGSERIAL PRIMARY KEY,
    db_url TEXT NOT NULL,
    latitude INTEGER NOT NULL,
    longitude INTEGER NOT NULL,
)