package seattlerHub.model;

public class Users {

  protected String userName;
  protected String password;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected Integer phone;
  protected Housing housing;
  protected Boolean ifBioVisible;
  protected Boolean isAuthenticatedResident;

  public Users(String userName, String password, String firstName, String lastName,
      String email, Integer phone, Housing housing, Boolean ifBioVisible,
      Boolean isAuthenticatedResident) {
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.housing = housing;
    this.ifBioVisible = ifBioVisible;
    this.isAuthenticatedResident = isAuthenticatedResident;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer phone) {
    this.phone = phone;
  }

  public Housing getHousing() {
    return housing;
  }

  public void setHousing(Housing housing) {
    this.housing = housing;
  }

  public Boolean getIfBioVisible() {
    return ifBioVisible;
  }

  public void setIfBioVisible(Boolean ifBioVisible) {
    this.ifBioVisible = ifBioVisible;
  }

  public Boolean getAuthenticatedResident() {
    return isAuthenticatedResident;
  }

  public void setAuthenticatedResident(Boolean authenticatedResident) {
    isAuthenticatedResident = authenticatedResident;
  }
}