package seattlerHub.model;

public class Schools {
  protected Integer schoolId;
  protected String schoolName;
  protected SchoolType schoolType;
  protected Neighborhoods neighborhoods;
  protected String address;
  protected Double longitude;
  protected Double latitude;
  public enum SchoolType{
    Public,Private;
  }

  public Schools(Integer schoolId, String schoolName, SchoolType schoolType,
      Neighborhoods neighborhoods, String address, Double longitude, Double latitude) {
    this.schoolId = schoolId;
    this.schoolName = schoolName;
    this.schoolType = schoolType;
    this.neighborhoods = neighborhoods;
    this.address = address;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Integer getSchoolId() {
    return schoolId;
  }

  public void setSchoolId(Integer schoolId) {
    this.schoolId = schoolId;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public SchoolType getSchoolType() {
    return schoolType;
  }

  public void setSchoolType(SchoolType schoolType) {
    this.schoolType = schoolType;
  }

  public Neighborhoods getNeighborhoods() {
    return neighborhoods;
  }

  public void setNeighborhoods(Neighborhoods neighborhoods) {
    this.neighborhoods = neighborhoods;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
}
