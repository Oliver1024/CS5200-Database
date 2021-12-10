package seattlerHub.model;

public class Students extends Users{
  protected String school;

  public Students(String userName, String password, String firstName, String lastName,
      String email, Integer phone, Housing housing, Boolean ifBioVisible,
      Boolean isAuthenticatedResident, String school) {
    super(userName, password, firstName, lastName, email, phone, housing, ifBioVisible,
        isAuthenticatedResident);
    this.school = school;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }
}
