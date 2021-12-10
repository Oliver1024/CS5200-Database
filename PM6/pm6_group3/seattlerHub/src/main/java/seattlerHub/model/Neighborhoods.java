package seattlerHub.model;

public class Neighborhoods {
  protected Integer zipcode;
  protected Integer avgRentalPriceOneBedroom;

  public Neighborhoods(Integer zipcode, Integer avgRentalPriceOneBedroom) {
    this.zipcode = zipcode;
    this.avgRentalPriceOneBedroom = avgRentalPriceOneBedroom;
  }

  public Integer getZipcode() {
    return zipcode;
  }

  public void setZipcode(Integer zipcode) {
    this.zipcode = zipcode;
  }

  public Integer getAvgRentalPriceOneBedroom() {
    return avgRentalPriceOneBedroom;
  }

  public void setAvgRentalPriceOneBedroom(Integer avgRentalPriceOneBedroom) {
    this.avgRentalPriceOneBedroom = avgRentalPriceOneBedroom;
  }
}
