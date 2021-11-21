package review.tools;

import java.sql.Timestamp;
import java.util.ArrayList;
import review.dal.*;
import review.model.*;
import review.model.Restaurants.Cuisine;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author Kejian Tong
 *
 */
public class Inserter {

	public static void main(String[] args) throws SQLException {

		
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
		FoodCartRestaurantsDao foodCartRestaurantsDao = FoodCartRestaurantsDao.getInstance();
		RecommendationsDao recommendationsDao = RecommendationsDao.getInstance();
		ReservationsDao reservationsDao = ReservationsDao.getInstance();
		RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
		ReviewsDao reviewsDao = ReviewsDao.getInstance();
		SitDownRestaurantDao sitDownRestaurantDao = SitDownRestaurantDao.getInstance();
		TakeOutRestaurantsDao takeOutRestaurantsDao = TakeOutRestaurantsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();

		
		Users user1 = new Users("a1", "aa1", "abc", "K", "abc@gmail.com", "111");
		user1 = usersDao.create(user1);
		Users user2 = new Users("b1", "bb1", "bcd", "K", "abc2@gmail.com", "112");
		user2 = usersDao.create(user2);
		Users user3 = new Users("c1", "cc1", "cde", "K", "abc3@gmail.com", "113");
		user3 = usersDao.create(user3);

		Date date1 = new Date();
		long number1 = 666435678900L;
		CreditCards creditCard1 = new CreditCards(number1, date1, user1);
		creditCard1 = creditCardsDao.create(creditCard1);
		long number2 = 888435678921L;
		CreditCards creditCard2 = new CreditCards(number2, date1, user2);
		creditCard2 = creditCardsDao.create(creditCard2);
		long number3 = 999435678921L;
		CreditCards creditCard3 = new CreditCards(number3, date1, user3);
		creditCard3 = creditCardsDao.create(creditCard3);

		Companies company1 = new Companies("meta", "great");
		company1 = companiesDao.create(company1);

		Restaurants restaurant1 = new Restaurants("a", "great", "soup", "9am-10pm", true,
				Cuisine.AFRICAN,
				"street1", "street2", "Seattle", "WA", 98002, company1);
		restaurant1 = restaurantsDao.create(restaurant1);
		Restaurants restaurant2 = new Restaurants("b", "great", "soup", "9am-10pm", true,
				Cuisine.AFRICAN,
				"street1", "street2", "Seattle", "WA", 98001, company1);
		restaurant2 = restaurantsDao.create(restaurant2);
		Restaurants restaurant3 = new Restaurants("c", "great", "soup", "9am-10pm", true,
				Cuisine.AFRICAN,
				"street1", "street2", "Seattle", "WA", 98002, company1);
		restaurant3 = restaurantsDao.create(restaurant3);

		TakeOutRestaurants takeOutRestaurant1 = new TakeOutRestaurants(1, "a", "great", "soup",
				"9am-10pm", true, Cuisine.AFRICAN,
				"street1", "street2", "Seattle", "WA", 98002, company1, 10);
		takeOutRestaurant1 = takeOutRestaurantsDao.create(takeOutRestaurant1);

		SitDownRestaurant sitDownRestaurant1 = new SitDownRestaurant(2, "b", "great", "soup",
				"9am-10pm", true, Cuisine.AFRICAN,
				"street1", "street2", "Seattle", "WA", 98002, company1, 1000);
		sitDownRestaurant1 = sitDownRestaurantDao.create(sitDownRestaurant1);

		FoodCartRestaurants foodCartRestaurant1 = new FoodCartRestaurants(3, "c", "great", "soup",
				"9am-10pm", true, Cuisine.AFRICAN,
				"street1", "street2", "Seattle", "WA", 98002, company1, true);
		foodCartRestaurant1 = foodCartRestaurantsDao.create(foodCartRestaurant1);

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Reviews review1 = new Reviews(timestamp, "great", 4.5, user1, takeOutRestaurant1);
		review1 = reviewsDao.create(review1);

		Timestamp start = new Timestamp(date.getTime());
		Timestamp end = new Timestamp(date.getTime());

		Reservations reservation1 = new Reservations(start, end, 10, user1, restaurant1);
		reservation1 = reservationsDao.create(reservation1);

		Recommendations recommendation1 = new Recommendations(user1, restaurant1);
		recommendation1 = recommendationsDao.create(recommendation1);

		
		Users u1 = usersDao.getUserByUserName("a1");
		System.out.format("Reading user: u:%s p:%s f:%s l:%s e:%s p:%s \n",
				u1.getUserName(), u1.getPassword(), u1.getFirstName(), u1.getLastName(), u1.getEmail(),
				u1.getPhone());
		List<Users> uList1 = new ArrayList<>();
		for (Users u : uList1) {
			System.out.format("Looping users: u:%s p:%s f:%s l:%s e:%s p:%s \n",
					u.getUserName(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getEmail(),
					u.getPhone());
			usersDao.delete(user1);
		}

		CreditCards cc1 = creditCardsDao.getCreditCardByCardNumber(666435678903L);
		List<CreditCards> ccl1 = creditCardsDao.getCreditCardsByUserName("a1");
		System.out.format("Reading creditCard: c:%s, u:%s \n",
				cc1.getCardNumber(), cc1.getUser().getUserName());
		for (CreditCards c : ccl1) {
			System.out.format("Looping creditCards: c:%s, u:%s \n",
					c.getCardNumber(), c.getUser().getUserName());
		}

		Companies c1 = companiesDao.getCompanyByCompanyName("meta");
		System.out.format("Reading company: c:%s, a:%s \n",
				c1.getCompanyName(), c1.getAbout());

		Restaurants r1 = restaurantsDao.getRestaurantById(1);
		System.out.format(
				"Reading restaurant: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s\n",
				r1.getRestaurantId(), r1.getDescription(), r1.getMenu(), r1.getHours(), r1.getActive(),
				r1.getCuisine(), r1.getStreet1(),
				r1.getStreet2(), r1.getCity(), r1.getState(), r1.getZip(),
				r1.getCompany().getCompanyName());
		
		List<Restaurants> rl = restaurantsDao.getRestaurantsByCompanyName("meta");
		
		for (Restaurants r : rl) {
			System.out.format(
					"Looping restaurants: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s\n",
					r.getRestaurantId(), r.getDescription(), r.getMenu(), r.getHours(), r.getActive(),
					r.getCuisine(), r.getStreet1(),
					r.getStreet2(), r.getCity(), r.getState());
		}

		TakeOutRestaurants t1 = takeOutRestaurantsDao.getTakeOutRestaurantById(1);
		System.out.format(
				"Reading restaurant: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s\n",
				t1.getRestaurantId(), t1.getDescription(), t1.getMenu(), t1.getHours(), t1.getActive(),
				t1.getCuisine(), t1.getStreet1(),
				t1.getStreet2(), t1.getCity(), t1.getState(), t1.getZip(),
				t1.getCompany().getCompanyName());
		List<TakeOutRestaurants> tl = takeOutRestaurantsDao.getTakeOutRestaurantsByCompanyName("meta");
		for (TakeOutRestaurants t : tl) {
			System.out.format(
					"Looping takeOutRestaurants: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s, maxT: %s\n",
					t.getRestaurantId(), t.getDescription(), t.getMenu(), t.getHours(), t.getActive(),
					t.getCuisine(), t.getStreet1(),
					t.getStreet2(), t.getCity(), t.getState(), t.getMaxWaitingTime());
		}

		SitDownRestaurant s1 = sitDownRestaurantDao.getSitDownRestaurantById(2);
		System.out.format(
				"Reading sitDownRestaurant: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s, cap:%s\n",
				s1.getRestaurantId(), s1.getDescription(), s1.getMenu(), s1.getHours(), s1.getActive(),
				s1.getCuisine(), s1.getStreet1(),
				s1.getStreet2(), s1.getCity(), s1.getState(), s1.getZip(), s1.getCompany().getCompanyName(),
				s1.getCapacity());
		List<SitDownRestaurant> sl = sitDownRestaurantDao.getSitDownRestaurantsByCompanyName("meta");
		for (SitDownRestaurant s : sl) {
			System.out.format(
					"Looping sitDownRestaurants: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s, cap: %s\n",
					s.getRestaurantId(), s.getDescription(), s.getMenu(), s.getHours(), s.getActive(),
					s.getCuisine(), s.getStreet1(),
					s.getStreet2(), s.getCity(), s.getState(), s.getCapacity());
		}

		FoodCartRestaurants f1 = foodCartRestaurantsDao.getFoodCartRestaurantById(3);
		System.out.format(
				"Reading foodCartRestaurant: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s, l:%s\n",
				f1.getRestaurantId(), f1.getDescription(), f1.getMenu(), f1.getHours(), f1.getActive(),
				f1.getCuisine(), f1.getStreet1(),
				f1.getStreet2(), f1.getCity(), f1.getState(), f1.getZip(), f1.getCompany().getCompanyName(),
				f1.getLicensed());
		List<FoodCartRestaurants> fl = foodCartRestaurantsDao
				.getFoodCartRestaurantsByCompanyName("meta");
		for (FoodCartRestaurants f : fl) {
			System.out.format(
					"Looping foodCartRestaurants: i:%s, d:%s, m:%s, h:%s, a:%s, c: %s, s1:%s, s2:%s, city: %s, state: %s, z:%s, cn: %s, l:%s\n",
					f.getRestaurantId(), f.getDescription(), f.getMenu(), f.getHours(), f.getActive(),
					f.getCuisine(), f.getStreet1(),
					f.getStreet2(), f.getCity(), f.getState(), f.getLicensed());
		}

		List<Reviews> rwl1 = reviewsDao.getReviewsByUserName("a1");
		for (Reviews rw : rwl1) {
			System.out.format("Looping reviews: cre:%s, c:%s, r:%s, u:%s, r:%s\n",
					rw.getCreated(), rw.getContent(), rw.getRating(), rw.getUser().getUserName(),
					rw.getRestaurant().getRestaurantId());
		}
		List<Reservations> rvl1 = reservationsDao.getReservationsByUserName("a1");
		for (Reservations rv : rvl1) {
			System.out.format("Looping reservations: s:%s, e:%s, size:%s, u:%s, r:%s\n",
					rv.getStart(), rv.getEnd(), rv.getSize(), rv.getUser().getUserName(),
					rv.getRestaurant().getRestaurantId());
		}

		List<Recommendations> rcl1 = recommendationsDao.getRecommendationsByUserName("a1");
		for (Recommendations rc : rcl1) {
			System.out.format("Looping recommendations: u:%s, r:%s\n",
					rc.getUser().getUserName(), rc.getRestaurant().getRestaurantId());
		}
	}
}
