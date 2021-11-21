CREATE SCHEMA IF NOT EXISTS SeattlerHub;
USE SeattlerHub;

DROP TABLE IF EXISTS CrimeCases;
DROP TABLE IF EXISTS Schools;
DROP TABLE IF EXISTS Restaurants;
DROP TABLE IF EXISTS CulturalSpaces;
DROP TABLE IF EXISTS Parks;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Professionals;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Housing;
DROP TABLE IF EXISTS Neighborhoods;



CREATE TABLE Neighborhoods (
    Zipcode INT,
    AvgRentalPriceOneBedroom INT,
    CONSTRAINT pk_Zipcode PRIMARY KEY(Zipcode)
);

CREATE TABLE Housing (
    HousingId INT AUTO_INCREMENT,
    Zipcode INT,
    Longitude DECIMAL(13,10) NOT NULL,
    Latitude DECIMAL(13,10) NOT NULL,
    RentalPrice INT,
    CONSTRAINT pk_Housing_HousingId PRIMARY KEY (HousingId),
    CONSTRAINT fk_Housing_Zipcode FOREIGN KEY (Zipcode)
        REFERENCES Neighborhoods(Zipcode) 
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Users (
    UserName VARCHAR(255),
    Password VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Phone BIGINT,
    HousingId INT DEFAULT NULL,
    IfBioVisible BOOLEAN DEFAULT TRUE,
    IsAuthenticatedResident BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_Users_UserName PRIMARY KEY(UserName),
    CONSTRAINT fk_Users_HousingId FOREIGN KEY(HousingId)
        REFERENCES Housing(HousingId) 
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Students (
    UserName VARCHAR(255),
    School VARCHAR(255),
    CONSTRAINT pk_Students_UserName PRIMARY KEY(UserName),
    CONSTRAINT fk_Students_UserName FOREIGN KEY(UserName)
        REFERENCES Users(UserName) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Professionals (
    UserName VARCHAR(255),
    Company VARCHAR(255),
    CONSTRAINT pk_Professionals_UserName PRIMARY KEY(UserName),
    CONSTRAINT fk_Professionals_UserName FOREIGN KEY(UserName)
        REFERENCES Users(UserName) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reviews (
    ReviewId INT AUTO_INCREMENT,
    Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Content TEXT NOT NULL,
    Rating DECIMAL(2,1) NOT NULL,
    UserName VARCHAR(255),
    HousingID INT,
    CONSTRAINT pk_Reviews_ReviewId PRIMARY KEY(ReviewId),
    CONSTRAINT fk_Reviews_UserName FOREIGN KEY(UserName)
        REFERENCES Users(UserName) 
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_Reviews_HousingId FOREIGN KEY(HousingId)
        REFERENCES Housing(HousingId) 
        ON UPDATE CASCADE ON DELETE SET NULL
);


CREATE TABLE CrimeCases (
    CrimeId INT AUTO_INCREMENT,
    ReportDate DATE NOT NULL,
    OffenseType TEXT,
	Offense TEXT,
    District VARCHAR(255),
    Address VARCHAR(255),
    Longitude DECIMAL(13, 10) NOT NULL,
    Latitude DECIMAL(13, 10) NOT NULL,
    Zipcode INT,
    CONSTRAINT pk_CrimeId PRIMARY KEY(CrimeId),
    CONSTRAINT fk_Crime_Zipcode FOREIGN KEY (Zipcode)
        REFERENCES Neighborhoods(Zipcode) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Schools (
    SchoolId INT AUTO_INCREMENT,
    SchoolName VARCHAR(255) NOT NULL,
    Type ENUM('Private', 'Public'),
    Zipcode INT,
    Address VARCHAR(255) NOT NULL,
    Latitude DECIMAL(13,10) NOT NULL,
    Longitude DECIMAL(13,10) NOT NULL,
    CONSTRAINT pk_Schools_SchoolId PRIMARY KEY (SchoolId),
    CONSTRAINT fk_Schools_Zipcode FOREIGN KEY (Zipcode)
        REFERENCES Neighborhoods(Zipcode) 
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Restaurants(
    RestaurantId INT AUTO_INCREMENT,
    RestaurantName VARCHAR(255),
    Description LONGTEXT,
    Zipcode INT,
    Longitude DECIMAL(13,10) NOT NULL,
    Latitude DECIMAL(13,10) NOT NULL,
    Address LONGTEXT NOT NULL,
    CONSTRAINT pk_Restaurants_RestaurantId PRIMARY KEY(RestaurantId),
    CONSTRAINT fk_Restaurants_Zipcode FOREIGN KEY(Zipcode)
        REFERENCES Neighborhoods(Zipcode) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CulturalSpaces (
    CulturalSpaceId INT(11) AUTO_INCREMENT,
    CulturalSpaceName VARCHAR(255) NOT NULL,
    DominantDiscipline VARCHAR(255),
    Zipcode INT,
    Address VARCHAR(255) NOT NULL,
    Longitude DECIMAL(13,10) NOT NULL,
    Latitude DECIMAL(13,10) NOT NULL,
    CONSTRAINT pk_Cultural_Space_Id PRIMARY KEY (CulturalSpaceId),
    CONSTRAINT fk_Cultural_Space_Zipcode FOREIGN KEY(Zipcode)
        REFERENCES Neighborhoods(Zipcode) 
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Parks (
    ParkId INT AUTO_INCREMENT,
    ParkName VARCHAR(255) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Zipcode INT,
    Longitude DECIMAL(13,10) NOT NULL,
    Latitude DECIMAL(13,10) NOT NULL,
    CONSTRAINT pk_Park_Id PRIMARY KEY(ParkId),
    CONSTRAINT fk_Park_Zipcode FOREIGN KEY(Zipcode)
        REFERENCES Neighborhoods(Zipcode) 
        ON UPDATE CASCADE ON DELETE CASCADE
);


LOAD DATA Local INFILE '/Users/oliver/Desktop/5200/PM2/datasource/zipcode.csv' INTO TABLE Neighborhoods
	FIELDS TERMINATED BY ','
	LINES TERMINATED BY '\n'
	IGNORE 1 LINES;

INSERT INTO Housing(Zipcode,Longitude,Latitude,RentalPrice)
    VALUES(98101,-122.3088675920,47.6122182900,1900),
        (98102,-122.3162381740,47.6109782500,1800),
        (98104,-122.3250633970,47.5970936300,1300),
        (98121,-122.3443533330,47.6132712100,2200),
        (98109,-122.3371469110,47.6226204900,1800),
        (98107,-122.3796420760,47.6633896700,1800),
        (98125,-122.3448342630,47.7135479600,1800);

INSERT INTO Users(UserName,Password,FirstName,LastName,Email,Phone,HousingId,IfBioVisible)
    VALUES('Bruce','password','Bruce','C','bruce@mail.com','5555555', 1, TRUE),
        ('TT','password','Tony','D','tony@mail.com','5555555', 2, TRUE),
        ('DK','password','Daniel','K','dan@mail.com','5555555', 3, FALSE),
        ('James','password','James','M','james@mail.com','5555555', NULL, FALSE),
        ('Steve','password','Steve','N','steve@mail.com','5555555', 5, FALSE);

INSERT INTO Students(UserName, School) 
	VALUES('Bruce', 'NEU'),
        ('TT', 'UW');
    
INSERT INTO Professionals(UserName, Company)
	VALUES('DK', 'Facebook'),
        ('James', 'Google'),
        ('Steve', 'Boeing');

INSERT INTO Reviews(Content,Rating,UserName,HousingID)
  VALUES('Delightful!',5.0,'Bruce',1),
	    ('Superb!',5.0,'Bruce',2),
        ('Superb!',5.0,'Bruce',3),
        ('Not good',1.0,'James',4),
        ('Not great',2.0,'TT',5),
        ('Not good',3.0,'James',6),
	    ('Not nice',4.0,'Steve',7);

LOAD DATA Local INFILE '/Users/oliver/Desktop/5200/PM2/datasource/crime.csv' INTO TABLE CrimeCases
	FIELDS TERMINATED BY ','
	LINES TERMINATED BY '\n'
	IGNORE 1 LINES
	(@dummy, @dummy, @dummy, @dummy, 
	@ReportDate,@dummy, @dummy, 
	OffenseType, Offense, @dummy, @dummy, @dummy, @dummy, 
	District, Address, Longitude, Latitude)
    SET 
    ReportDate =  STR_TO_DATE(LEFT(@ReportDate, 10), '%m/%d/%Y'),
    Zipcode =  (
    select if (lower(District) like "%central%", 98101, (
    select if (lower(District) like "%belltown%", 98121, (
	select if (lower(District) like "%slu%", 98109, (
    select if (lower(District) like "%capitol%", 98102, (
    select if (lower(District) like "%university%", 98105, (
    select if (lower(District) like "%roosevelt%", 98115, (
    select if (lower(District) like "%first hill%", 98122, (
    select if (lower(District) like "%pioneer square%", 98104, (
    select if (lower(District) like "%northgate%", 98125, (
	select if (lower(District) like "%queen anne%", 98119, (
    select if (lower(District) like "%ballard%", 98107, (
    select if (lower(District) like "%chinatown%", 98104, (
    select if (lower(District) like "%brighton%", 98118, 00000)  
    )))))))))))))))))))))))));


LOAD DATA LOCAL INFILE '/Users/oliver/Desktop/5200/PM2/datasource/Private_Schools.csv' INTO TABLE Schools
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (Longitude, Latitude, @dummy, @dummy, 
    SchoolName, Address, @dummy, @dummy, 
    Zipcode, @dummy, @dummy, @dummy, @dummy, @dummy)
    SET Type = 'Private';
  
LOAD DATA LOCAL INFILE '/Users/oliver/Desktop/5200/PM2/datasource/Public_Schools.csv' INTO TABLE Schools
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (Longitude, Latitude, @dummy, @dummy, 
    SchoolName, Address, @dummy, @dummy, @dummy, @dummy, 
    Zipcode, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy)
    SET Type = 'Public';
  
LOAD DATA LOCAL INFILE '/Users/oliver/Desktop/5200/PM2/datasource/Restaurants.csv' INTO TABLE Restaurants
    FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
    LINES TERMINATED BY '\n' 
    IGNORE 1 LINES
    (Longitude, Latitude, @dummy, @dummy, 
    RestaurantName, Address, @dummy, @dummy, @dummy, 
    Description, @dummy, @dummy,
    Zipcode);

LOAD DATA LOCAL INFILE'/Users/oliver/Desktop/5200/PM2/datasource/culture_space.csv' INTO TABLE CulturalSpaces
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES 
    (CulturalSpaceName, DominantDiscipline, @dummy, Latitude, Longitude, Address)
    SET Zipcode = (select if (Address like "%981__", RIGHT(Address, 5), 0));

LOAD DATA LOCAL INFILE '/Users/oliver/Desktop/5200/PM2/datasource/park.csv' INTO TABLE Parks
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (@dummy,@dummy,ParkName,Address,Zipcode,Longitude,Latitude);