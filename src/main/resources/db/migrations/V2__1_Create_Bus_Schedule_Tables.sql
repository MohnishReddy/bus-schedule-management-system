CREATE TABLE buses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number_plate VARCHAR(15) NOT NULL UNIQUE,
    bus_number VARCHAR(10) NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255)
);

CREATE TABLE bus_stops (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL,
    is_active TINYINT(1),
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    pin_code VARCHAR(10) NOT NULL,
    country VARCHAR(255) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255)
);

CREATE TABLE routes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    pin_code VARCHAR(10) NOT NULL,
    country VARCHAR(255) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255)
);

CREATE TABLE schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT NOT NULL,
    route_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    expected_time_of_travel INT,
    working_days VARCHAR(7),
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (bus_id) REFERENCES buses(id),
    FOREIGN KEY (route_id) REFERENCES routes(id)
);

CREATE TABLE bus_stop_route_map (
    route_id INT,
    bus_stop_id INT,
    PRIMARY KEY (route_id, bus_stop_id),
    FOREIGN KEY (route_id) REFERENCES routes(id),
    FOREIGN KEY (bus_stop_id) REFERENCES bus_stops(id)
);
