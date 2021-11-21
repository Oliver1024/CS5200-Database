package review.model;
import java.util.Date;

/**
 * 
 * @author Kejian Tong
 *
 */
public class CreditCards {
  protected Long cardNumber;
  protected Date expiration;
  protected Users user;

  public CreditCards(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public CreditCards(Long cardNumber, Date expiration, Users user) {
    this.cardNumber = cardNumber;
    this.expiration = expiration;
    this.user = user;
  }

  public CreditCards(Date expiration, Users user) {
    this.expiration = expiration;
    this.user = user;
  }

  public Long getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Date getExpiration() {
    return expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }


  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }
}
