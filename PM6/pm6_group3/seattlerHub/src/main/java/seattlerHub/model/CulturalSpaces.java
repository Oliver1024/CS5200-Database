package seattlerHub.model;

public class CulturalSpaces {
  protected Integer culturalSpaceId;
  protected String culturalSpaceName;
  protected String dominantDiscipline;
  protected Neighborhoods neighborhoods;
  protected String address;
  protected Double longitude;
  protected Double latitude;

  public CulturalSpaces(Integer culturalSpaceId, String culturalSpaceName,
      String dominantDiscipline, Neighborhoods neighborhoods, String address,
      Double longitude, Double latitude) {
    this.culturalSpaceId = culturalSpaceId;
    this.culturalSpaceName = culturalSpaceName;
    this.dominantDiscipline = dominantDiscipline;
    this.neighborhoods = neighborhoods;
    this.address = address;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Integer getCulturalSpaceId() {
    return culturalSpaceId;
  }

  public void setCulturalSpaceId(Integer culturalSpaceId) {
    this.culturalSpaceId = culturalSpaceId;
  }

  public String getCulturalSpaceName() {
    return culturalSpaceName;
  }

  public void setCulturalSpaceName(String culturalSpaceName) {
    this.culturalSpaceName = culturalSpaceName;
  }

  public String getDominantDiscipline() {
    return dominantDiscipline;
  }

  public void setDominantDiscipline(String dominantDiscipline) {
    this.dominantDiscipline = dominantDiscipline;
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
