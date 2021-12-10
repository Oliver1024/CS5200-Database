package seattlerHub.model;

public class Parks {
  protected Integer parkId;
  protected String parkName;
  protected String address;
  protected Neighborhoods neighborhoods;
  protected Double longitude;
  protected Double latitude;

  public Parks(Integer parkId, String parkName, String address,
      Neighborhoods neighborhoods, Double longitude, Double latitude) {
    this.parkId = parkId;
    this.parkName = parkName;
    this.address = address;
    this.neighborhoods = neighborhoods;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Integer getParkId() {
    return parkId;
  }

  public void setParkId(Integer parkId) {
    this.parkId = parkId;
  }

  public String getParkName() {
    return parkName;
  }

  public void setParkName(String parkName) {
    this.parkName = parkName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Neighborhoods getNeighborhoods() {
    return neighborhoods;
  }

  public void setNeighborhoods(Neighborhoods neighborhoods) {
    this.neighborhoods = neighborhoods;
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
