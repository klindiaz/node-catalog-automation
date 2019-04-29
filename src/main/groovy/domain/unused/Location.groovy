package domain.unused

import util.LocationUtil

class Location {
    private String region
    private String market
    private String facility
    private String facilityCode

    void setRegion(String region) {
        this.region = LocationUtil.getStandardizedName(region)
    }

    void setRegion(String market , String facility) {
        this.region = LocationUtil.getRegionName(market,facility)
    }

    void setMarket(String market) {
        this.market = LocationUtil.getStandardizedName(market)
    }

    void setFacility(String facility) {
        this.facility = LocationUtil.getStandardizedName(facility)
    }

    void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode
    }

    LocationUtil.LocationInfo getLocationInfo(){
        LocationUtil.getLocationInfo(this.market,this.facility)
    }

    String getRegion() {
        return getLocationInfo().region
    }

    String getMarket() {
        return getLocationInfo().getMarket()
    }

    String getFacility() {
        return getLocationInfo().facility
    }

    String getFacilityCode() {
        return getLocationInfo().getFacilityKey()
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Location location = (Location) o

        if (facility != location.facility) return false
        if (facilityCode != location.facilityCode) return false
        if (market != location.market) return false
        if (region != location.region) return false

        return true
    }

    int hashCode() {
        int result
        result = (region != null ? region.hashCode() : 0)
        result = 31 * result + (market != null ? market.hashCode() : 0)
        result = 31 * result + (facility != null ? facility.hashCode() : 0)
        result = 31 * result + (facilityCode != null ? facilityCode.hashCode() : 0)
        return result
    }
}
