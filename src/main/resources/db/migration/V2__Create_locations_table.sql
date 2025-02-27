Create table locations(
    Id ${id_type} PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    userId INT NOT NULL,
    Latitude DECIMAL NOT NULL,
    Longitude DECIMAL NOT NULL,
    FOREIGN KEY(userId) REFERENCES users(id)
);