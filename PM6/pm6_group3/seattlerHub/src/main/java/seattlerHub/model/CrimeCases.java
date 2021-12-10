package seattlerHub.model;

import java.util.Date;

public class CrimeCases {
  protected Integer crimeId;
  protected Date reportDate;
  protected String offenseType;
  protected String offense;
  protected String district;
  protected String address;
  protected Double longitude;
  protected Double latitude;
  protected Neighborhoods neighborhoods;

  public CrimeCases(Integer crimeId, Date reportDate, String offenseType, String offense,
      String district, String address, Double longitude, Double latitude,
      Neighborhoods neighborhoods) {
    this.crimeId = crimeId;
    this.reportDate = reportDate;
    this.offenseType = offenseType;
    this.offense = offense;
    this.district = district;
    this.address = address;
    this.longitude = longitude;
    this.latitude = latitude;
    this.neighborhoods = neighborhoods;
  }

  public Integer getCrimeId() {
    return crimeId;
  }

  public void setCrimeId(Integer crimeId) {
    this.crimeId = crimeId;
  }

  public Date getReportDate() {
    return reportDate;
  }

  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }

  public String getOffenseType() {
    return offenseType;
  }

  public void setOffenseType(String offenseType) {
    this.offenseType = offenseType;
  }

  public String getOffense() {
    return offense;
  }

  public void setOffense(String offense) {
    this.offense = offense;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
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

  public Neighborhoods getNeighborhoods() {
    return neighborhoods;
  }

  public void setNeighborhoods(Neighborhoods neighborhoods) {
    this.neighborhoods = neighborhoods;
  }
}
