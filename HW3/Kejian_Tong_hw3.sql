
# Pre steps:
# Please run the CREATE and INSERT SQL statements from Professor provided, then run the SELECT code

USE ReviewApplication;
# 1. What is the average review rating for each of the top 10 rated restaurants? Include the restaurant name in the result set.
SELECT Restaurants.name AS REST_NAME, AVG(Reviews.rating) AS AVGRATING 
FROM Restaurants 
INNER JOIN 
	Reviews 
ON 
	Restaurants.RestaurantId = Reviews.RestaurantID 
GROUP BY 
	Restaurants.RestaurantID 
ORDER BY AVGRATING DESC LIMIT 10;


# 2. How many users have created more than one review?

SELECT COUNT(*) AS USER_CNT FROM 
	(SELECT COUNT(*) AS CNT FROM Reviews GROUP BY UserName HAVING COUNT(*) > 1) AS RESUT;


# 3. What day of the week is most popular for making a reservation? Use the DAYOFWEEK() function on the ‘Start’ column.
SELECT DAYOFWEEK(Reservations.Start) AS DAYS, COUNT(ReservationId) 
FROM Reservations 
INNER JOIN 
	SitDownRestaurant 
ON 
	Reservations.RestaurantID = SitDownRestaurant.RestaurantID 
GROUP BY DAYS 
ORDER BY COUNT(ReservationId) 
DESC LIMIT 1;


# 4. What are the distinct UserNames for users that have made multiple reservations at any given SitDownRestaurant?

SELECT DISTINCT(Reservations.UserName), COUNT(ReservationId)  
FROM Reservations 
INNER JOIN 
	SitDownRestaurant 
ON Reservations.RestaurantID = SitDownRestaurant.RestaurantID 
GROUP BY 
	Reservations.UserName 
HAVING COUNT(*) > 1;

# 5. Identify the number of credit cards per provider. The credit card provider is determined by the leading digit(s) of the CardNumber. 

	SELECT PROVIDER, COUNT(*) AS CNT 
		FROM (SELECT CardNumber,
			CASE 
				WHEN
					LEFT (CAST(CardNumber AS CHAR), 2) IN ('34', '37')
				THEN 'AmericanExpress'  
				WHEN
					LEFT (CAST(CardNumber AS CHAR), 4) IN ('6011') OR 
                    LEFT (CAST(CardNumber AS CHAR), 3) IN ('644', '645', '646', '647','648','649') OR
                    LEFT (CAST(CardNumber AS CHAR), 2) IN ('65') 
				THEN 'Discover' 
				WHEN
                    LEFT (CAST(CardNumber AS CHAR), 2) IN ('51', '52', '53', '54','55')
				THEN 'MasterCard' 
				WHEN
					LEFT (CAST(CardNumber AS CHAR), 1) IN ('4')
				THEN 'Visa'  
                ELSE 'NA'
			END AS PROVIDER FROM CreditCards) AS RES
				GROUP BY PROVIDER;

	
# 6. What is the total number of active restaurants for each type of restaurant (SitDownRestaurant, TakeOutRestaurant, FoodCartRestaurant)?
 SELECT 'Sitdown' AS TYPE, COUNT(Restaurants.RestaurantId) FROM Restaurants,SitDownRestaurant
	WHERE Restaurants.RestaurantId = SitDownRestaurant.RestaurantId AND Restaurants.Active = TRUE
UNION
	SELECT 'TakeOut' AS TYPE, COUNT(Restaurants.RestaurantId) FROM Restaurants,TakeOutRestaurant
		WHERE Restaurants.RestaurantId = TakeOutRestaurant.RestaurantId AND Restaurants.Active = TRUE
UNION
	SELECT 'FoodCart' AS TYPE, COUNT(Restaurants.RestaurantId) FROM Restaurants,FoodCartRestaurant
		WHERE Restaurants.RestaurantId = FoodCartRestaurant.RestaurantId AND Restaurants.Active = TRUE;


#7. Which UserNames have not created a review, nor created a recommendation, nor created a reservation? 
# In other words, users that have not created any of the following: reviews, recommendations, reservations.

SELECT Users.UserName from Users
	LEFT OUTER JOIN Recommendations ON Users.UserName = Recommendations.UserName 
    LEFT OUTER JOIN Reservations ON Users.UserName = Reservations.UserName 
    LEFT OUTER JOIN Reviews ON Users.UserName = Reviews.UserName 
WHERE Reviews.ReviewID is NULL 
AND Recommendations.RecommendationID is NULL 
AND Reviews.ReviewID is NULL;



# 8. What is the ratio of the total number of recommendations to the total number of reviews?
SELECT CntRecommendation/CntReview AS Ratio 
FROM (
	SELECT COUNT(*) AS CntRecommendation 
FROM Recommendations) AS Rec
CROSS JOIN 
    (SELECT COUNT(*) AS CntReview FROM Reviews) AS Rev; 


# 9. Of all the users that have created a reservation at a SitDownRestaurant, what is the percentage that the same user has recommended the same SitDownRestaurant? 
# To calculate this percentage, consider {UserName, RestaurantId} reservation tuples as the denominator and their intersecting {UserName, RestaurantId} recommendation tuples at the numerator.

SELECT SUM(IF(Rec.UserName IS NOT NULL,1,0))/COUNT(*) AS RESULT 
FROM
	(select UserName, RestaurantID from Reservations group by UserName, RestaurantId) AS Res
LEFT OUTER JOIN 
	(select UserName, Recommendations.RestaurantId AS RestaurantId from Recommendations 
INNER JOIN 
	SitDownRestaurant
ON Recommendations.RestaurantId = SitDownRestaurant.RestaurantId

GROUP BY 
	UserName, Recommendations.RestaurantId) AS Rec
ON 
	Res.UserName = Rec.UserName
AND 
	Res.RestaurantID = Rec.RestaurantId;

# 10. Which UserNames have created more than twice the number of recommendations than number of reviews? 
# Also take into account users that have not created recommendations or reviews -- if a user has created one recommendation but zero reviews, then that user should be included in the result set.


SELECT Rec.UserName, IF(Rec.UserName IS NULL,0, Rec.Rec_CNT) AS Rec_Total,IF(Rev.UserName IS NULL,0, Rev.Rev_CNT) AS Rev_Total 
FROM
	(SELECT UserName, COUNT(*) AS Rec_CNT
FROM Recommendations
GROUP BY UserName) AS Rec
LEFT OUTER JOIN
	(SELECT UserName, COUNT(*) AS Rev_CNT
FROM Reviews
GROUP BY
 UserName) AS Rev
    on Rev.UserName = Rec.UserName
WHERE IF(Rec.UserName is NULL,0,Rec.Rec_CNT) > 2*(IF(Rev.UserName is NULL, 0, Rev.Rev_CNT));






