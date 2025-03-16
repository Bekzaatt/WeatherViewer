Create table Session(
    Id VARCHAR(100) PRIMARY KEY,
    UserId INT,
    ExpiresAt TIMESTAMP,
    FOREIGN KEY(UserId) REFERENCES Users(Id) ON DELETE CASCADE
)