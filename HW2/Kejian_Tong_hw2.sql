
CREATE SCHEMA IF NOT EXISTS RestaurantReview;
USE RestaurantReview;

# check if those tables exist

DROP TABLE IF EXISTS Recommendations;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS SitDownRestaurant;
DROP TABLE IF EXISTS TakeOutRestaurant;
DROP TABLE IF EXISTS FoodCartRestaurant;
DROP TABLE IF EXISTS Restaurants;
DROP TABLE IF EXISTS Companies;
DROP TABLE IF EXISTS CreditCards;
DROP TABLE IF EXISTS Users;


CREATE TABLE Users (
  UserName VARCHAR(255),
  Password VARCHAR(255) NOT NULL,
  FirstName VARCHAR(255) NOT NULL,
  LastName VARCHAR(255) NOT NULL,
  Email VARCHAR(255),
  Phone VARCHAR(255),
  CONSTRAINT pk_Users_UserName PRIMARY KEY (UserName)
);


CREATE TABLE CreditCards (
    CardNumber BIGINT,
    Expiration DATE NOT NULL,
    UserName VARCHAR(255) NOT NULL,
    CONSTRAINT pk_CreditCards_CardNumber PRIMARY KEY (CardNumber),
    CONSTRAINT fk_CreditCards_UserName FOREIGN KEY (UserName)
      REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Companies (
    CompanyName VARCHAR(255),
    About VARCHAR(255),
    CONSTRAINT pk_Companies_CompanyName PRIMARY KEY (CompanyName)
);

CREATE TABLE Restaurants (
    RestaurantId INTEGER AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Description VARCHAR(255),
    Menu VARCHAR(255) NOT NULL,
    Hours VARCHAR(255) NOT NULL,
    Active Boolean NOT NULL,
    Cuisine ENUM('AFRICAN', 'AMERICAN', 'ASIAN', 'EUROPEAN', 'HISPANIC'),
    Street1 VARCHAR(255) NOT NULL,
    Street2 VARCHAR(255),
    City VARCHAR(255) NOT NULL,
    State VARCHAR(255) NOT NULL,
    Zip INTEGER NOT NULL,
    CompanyName VARCHAR(255),
    CONSTRAINT pk_Restaurants_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_Restaurants_CompanyName FOREIGN KEY (CompanyName)
      REFERENCES Companies(CompanyName) ON UPDATE CASCADE ON DELETE SET NULL
);


CREATE TABLE SitDownRestaurant(
    RestaurantId INTEGER,
    Capacity INTEGER NOT NULL,
    CONSTRAINT pk_SitDownRestaurant_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_SitDownRestaurant_RestaurantId FOREIGN KEY (RestaurantId)
      REFERENCES Restaurants(RestaurantId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE TakeOutRestaurant(
    RestaurantId INTEGER,
    MaxWaitTime INTEGER NOT NULL,
    CONSTRAINT pk_TakeOutRestaurant_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_TakeOutRestaurant_RestaurantId FOREIGN KEY (RestaurantId)
      REFERENCES Restaurants(RestaurantId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FoodCartRestaurant(
    RestaurantId INTEGER,
    Licensed BOOLEAN NOT NULL,
    CONSTRAINT pk_FoodCartRestaurant_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_FoodCartRestaurant_RestaurantId FOREIGN KEY (RestaurantId)
      REFERENCES Restaurants(RestaurantId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reservations (
    ReservationId INTEGER AUTO_INCREMENT,
    Start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    End TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Size INTEGER NOT NULL,
    UserName VARCHAR(255),
    RestaurantId INTEGER,
    CONSTRAINT pk_Reservations_ReservationId PRIMARY KEY (ReservationId),
    CONSTRAINT fk_Reservations_UserName FOREIGN KEY (UserName) 
      REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_Reservations_RestaurantId FOREIGN KEY (RestaurantId)
      REFERENCES SitDownRestaurant(RestaurantId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reviews (
    ReviewId INTEGER AUTO_INCREMENT,
    Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Content VARCHAR(255) NOT NULL,
    Rating DECIMAL(2,1) NOT NULL,
    UserName VARCHAR(255),
    RestaurantId INTEGER,
    CONSTRAINT pk_Reviews_ReviewId PRIMARY KEY (ReviewId),
    CONSTRAINT fk_Reviews_UserName FOREIGN KEY (UserName)
      REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_Reviews_RestaurantId FOREIGN KEY (RestaurantId)
      REFERENCES Restaurants(RestaurantId) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT uq_Reviews_UserNanme_RestaurantId UNIQUE (UserName, RestaurantId)
);

CREATE TABLE Recommendations (
    RecommendationId INTEGER AUTO_INCREMENT,
    UserName VARCHAR(255),
    RestaurantId INTEGER,
    CONSTRAINT pk_Recommendations_RecommendationId PRIMARY KEY (RecommendationId),
    CONSTRAINT fk_Recommendations_UserName FOREIGN KEY (UserName)
    REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_Recommendations_RestaurantId FOREIGN KEY (RestaurantId)
      REFERENCES Restaurants(RestaurantId) ON UPDATE CASCADE ON DELETE SET NULL
);


# INSERT DATA TO EACH TABLE
INSERT INTO Users(UserName,Password,FirstName,LastName, Email, Phone)
  VALUES('Tom1','123','Tom', 'TT', 't@gmail.com', '2060000000');
INSERT INTO Users(UserName,Password,FirstName,LastName, Email, Phone)
  VALUES('Jay2','456','Jay', 'JJ', 'j@gmail.com', '2060000001');

INSERT INTO Companies(CompanyName, About)
  VALUES('Amazon', "onlineshoping");
INSERT INTO Companies(CompanyName, About)
  VALUES('FB', "socialmedia");
  
INSERT INTO Companies(CompanyName, About)
  VALUES('Google', "onlineshoping");
INSERT INTO Companies(CompanyName, About)
  VALUES('Apple', "socialmedia");

INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName)
  VALUES('a','food','food1','6',TRUE,'AFRICAN','11AVE','APT1','Seattle','WA',98101,'Amazon');
INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName)
  VALUES('b','water','water1','5',TRUE,'ASIAN','12AVE','APT2','Seattle','WA',98101,'FB');
  
  

INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName)
  VALUES('c','burg','burg1','3',TRUE,'EUROPEAN','15AVE','APT3','Seattle','WA',98102,'Google');
INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName)
  VALUES('d','noodle','noodle1','7',TRUE,'AMERICAN','16AVE','APT5','Seattle','WA',98103,'Apple');





INSERT INTO SitDownRestaurant(RestaurantId, Capacity)
  VALUES(1,10);
INSERT INTO SitDownRestaurant(RestaurantId, Capacity)
  VALUES(2,8);

INSERT INTO TakeOutRestaurant(RestaurantId, MaxWaitTime)
  VALUES(1,2);
INSERT INTO TakeOutRestaurant(RestaurantId, MaxWaitTime)
  VALUES(2,3);

INSERT INTO FoodCartRestaurant(RestaurantId,Licensed)
  VALUES(1,TRUE);
INSERT INTO FoodCartRestaurant(RestaurantId,Licensed)
  VALUES(2,TRUE);

INSERT INTO Reservations(ReservationId,Size,UserName,RestaurantId)
  VALUES(1,5,'Tom1',1);
INSERT INTO Reservations(ReservationId,Size,UserName,RestaurantId)
  VALUES(2,5,'Jay2',2);

INSERT INTO Reviews(ReviewId,Content,Rating,UserName,RestaurantId)
  VALUES(1,'good',9.5,'Tom1',1);
INSERT INTO Reviews(ReviewId,Content,Rating,UserName,RestaurantId)
  VALUES(2,'good',9.6,'Jay2',2);

INSERT INTO Recommendations(RecommendationId,UserName,RestaurantId)
  VALUES(11,'Tom1',1);
INSERT INTO Recommendations(RecommendationId,UserName,RestaurantId)
  VALUES(12,'Jay2',2);

LOAD DATA INFILE '/tmp/creditcards.csv' INTO TABLE CreditCards
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES;

/**
You may need LOAD DATA LOCAL INFILE for Workbench 6.3, but remove LOCAL for Workbench 8.0+.
Contents of /tmp/creditcards.csv:
CardNumber,Expiration,UserName
"0000111122223333","2022-01-01","Tom1"
"0000111122225555","2023-01-01","Jay2"

*/










