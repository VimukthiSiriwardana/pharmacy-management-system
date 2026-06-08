USE PharmacyDB;
GO

-- Drop tables if they exist
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Medicines;
DROP TABLE IF EXISTS Users;
GO

-- Create Users table
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(100) NOT NULL,
    Username NVARCHAR(100) UNIQUE NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Role NVARCHAR(50) NOT NULL DEFAULT 'user'
);
GO

-- Insert default users (optional)
INSERT INTO Users (Name, Username, Email, Password, Role)
VALUES 
('Admin User', 'admin', 'admin@example.com', 'adminpass', 'admin'),
('Pharmacist', 'pharma', 'pharma@example.com', 'pharmapass', 'pharmacist'),
('Manager', 'manager', 'manager@example.com', 'managerpass', 'manager'),
('IT Support', 'itsupport', 'it@example.com', 'itpass', 'it_support');
GO

-- Create Medicines table
CREATE TABLE Medicines (
    MedicineID INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(100) NOT NULL,
    Type NVARCHAR(50) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    ExpiryDate DATE NOT NULL,
    Stock INT NOT NULL
);
GO

-- Create Orders table
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY IDENTITY,
    UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    OrderDate DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(50) DEFAULT 'Pending'
);
GO

-- Check tables

SELECT * FROM Users;
SELECT * FROM Orders;
SELECT * FROM Medicines;