USE MASTER
GO

IF EXISTS(SELECT NAME FROM sys.databases WHERE NAME = 'ProiectFacultativ') BEGIN
	DROP DATABASE ProiectFacultativ
END
GO

CREATE DATABASE ProiectFacultativ
GO
USE ProiectFacultativ
GO

CREATE TABLE Addresses(
	ID INT PRIMARY KEY,
	county VARCHAR(50) NOT NULL, --era state dar e nume rezervat
	city VARCHAR(50) NOT NULL,
	street VARCHAR(50) NOT NULL,
	number INT NOT NULL,
	ZIPCode VARCHAR(50) NOT NULL

);
GO

CREATE TABLE Clients(
	ID INT PRIMARY KEY,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phone CHAR(10) NOT NULL,
	passwordC VARCHAR(50) NOT NULL,
	balance FLOAT NOT NULL,
	id_address INT CONSTRAINT FK_Clients_Addresses FOREIGN KEY REFERENCES Addresses(ID) NOT NULL
);
GO

CREATE TABLE Admins(
	ID INT PRIMARY KEY,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phone CHAR(10) NOT NULL,
	passwordA VARCHAR(50) NOT NULL,
	id_address INT CONSTRAINT FK_Admins_Addresses FOREIGN KEY REFERENCES Addresses(ID) NOT NULL
);
GO

CREATE TABLE Employees(
	ID INT PRIMARY KEY,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phone CHAR(10) NOT NULL,
	passwordE VARCHAR(50) NOT NULL,
	id_address INT CONSTRAINT FK_Employees_Addresses FOREIGN KEY REFERENCES Addresses(ID) NOT NULL
);
GO

CREATE TABLE Invoices(
	ID INT PRIMARY KEY,
	category VARCHAR(50) NOT NULL,
	payment FLOAT NOT NULL,
	penalty FLOAT NOT NULL,
	date_of_issue DATE NOT NULL,
	id_client INT CONSTRAINT FK_Invoices_Clients FOREIGN KEY REFERENCES Clients(ID) NOT NULL,
	id_employee INT CONSTRAINT FK_Invoices_Employees FOREIGN KEY REFERENCES Employees(ID) NOT NULL 
);
GO

CREATE TABLE Items( --trebuia elements dar e cuvant rezervat
	ID INT PRIMARY KEY,
	element VARCHAR(50) NOT NULL,
	price FLOAT NOT NULL,
	category VARCHAR(50) NOT NULL
);
GO

CREATE TABLE Contents(
	id_invoice INT CONSTRAINT FK_Contents_Invoices FOREIGN KEY REFERENCES Invoices(ID) NOT NULL,
	id_item INT CONSTRAINT FK_Contents_Items FOREIGN KEY REFERENCES Items(ID) NOT NULL,
	quantity INT NOT NULL,
	PRIMARY KEY(id_invoice,id_item,quantity)	
);
GO

--CREATE TABLE Categories(
--	ID INT PRIMARY KEY,
--	name VARCHAR(50)
--);
--GO
