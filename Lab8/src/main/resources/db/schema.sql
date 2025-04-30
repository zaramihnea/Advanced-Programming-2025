-- Create tables if they don't exist
CREATE TABLE IF NOT EXISTS continents (
                                          id SERIAL PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS countries (
                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR(100) NOT NULL,
    code VARCHAR(3) NOT NULL UNIQUE,
    continent_id INTEGER NOT NULL,
    FOREIGN KEY (continent_id) REFERENCES continents(id)
    );

-- New table for cities
CREATE TABLE IF NOT EXISTS cities (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL,
    country_id INTEGER NOT NULL,
    capital BOOLEAN NOT NULL DEFAULT false,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id)
    );

-- Insert sample continents if not already present
INSERT INTO continents (name)
SELECT 'Africa' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'Africa');

INSERT INTO continents (name)
SELECT 'Asia' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'Asia');

INSERT INTO continents (name)
SELECT 'Europe' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'Europe');

INSERT INTO continents (name)
SELECT 'North America' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'North America');

INSERT INTO continents (name)
SELECT 'South America' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'South America');

INSERT INTO continents (name)
SELECT 'Oceania' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'Oceania');

INSERT INTO continents (name)
SELECT 'Antarctica' WHERE NOT EXISTS (SELECT 1 FROM continents WHERE name = 'Antarctica');

-- Insert sample countries if not already present
INSERT INTO countries (name, code, continent_id)
SELECT 'United States', 'USA', (SELECT id FROM continents WHERE name = 'North America')
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'USA');

INSERT INTO countries (name, code, continent_id)
SELECT 'Germany', 'DEU', (SELECT id FROM continents WHERE name = 'Europe')
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'DEU');

INSERT INTO countries (name, code, continent_id)
SELECT 'Japan', 'JPN', (SELECT id FROM continents WHERE name = 'Asia')
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'JPN');

INSERT INTO countries (name, code, continent_id)
SELECT 'Australia', 'AUS', (SELECT id FROM continents WHERE name = 'Oceania')
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'AUS');

INSERT INTO countries (name, code, continent_id)
SELECT 'Brazil', 'BRA', (SELECT id FROM continents WHERE name = 'South America')
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'BRA');