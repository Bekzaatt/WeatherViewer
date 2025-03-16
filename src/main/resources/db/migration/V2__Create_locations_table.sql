Create table Locations(
    Id ${id_type} PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    UserId INT NOT NULL,
    Latitude DECIMAL NOT NULL,
    Longitude DECIMAL NOT NULL,
    FOREIGN KEY(UserId) REFERENCES Users(Id) ON DELETE CASCADE
);