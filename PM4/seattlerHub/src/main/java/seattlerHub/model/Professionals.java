package seattlerHub.model;

public class Professionals extends Users{
  protected String company;

  public Professionals(String userName, String password, String firstName, String lastName,
      String email, Integer phone, Housing housing, Boolean ifBioVisible,
      Boolean isAuthenticatedResident, String company) {
    super(userName, password, firstName, lastName, email, phone, housing, ifBioVisible,
        isAuthenticatedResident);
    this.company = company;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}
