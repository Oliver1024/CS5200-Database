package seattlerHub.model;

public class Housing {
  protected int housingId;
  protected Neighborhoods neighborhoods;
  protected String name;
  protected String address;
  protected Double longitude;
  protected Double latitude;
  protected Integer rentalPrice;

  public Housing(int housingId, Neighborhoods neighborhoods, String name,
      String address, Double longitude, Double latitude, Integer rentalPrice) {
    this.housingId = housingId;
    this.neighborhoods = neighborhoods;
    this.name = name;
    this.address = address;
    this.longitude = longitude;
    this.latitude = latitude;
    this.rentalPrice = rentalPrice;
  }
  

  public Housing(Neighborhoods neighborhoods, String name, String address, Integer rentalPrice) {
	this.neighborhoods = neighborhoods;
	this.name = name;
	this.address = address;
	this.rentalPrice = rentalPrice;
}



public int getHousingId() {
    return housingId;
  }

  public void setHousingId(int housingId) {
    this.housingId = housingId;
  }

  public Neighborhoods getNeighborhoods() {
    return neighborhoods;
  }

  public void setNeighborhoods(Neighborhoods neighborhoods) {
    this.neighborhoods = neighborhoods;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Integer getRentalPrice() {
    return rentalPrice;
  }

  public void setRentalPrice(Integer rentalPrice) {
    this.rentalPrice = rentalPrice;
  }
}
