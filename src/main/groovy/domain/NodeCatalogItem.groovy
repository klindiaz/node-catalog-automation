package domain

import util.LocationUtil

class NodeCatalogItem {
    String region
    String market
    String siteId
    String facility

    String nodeKey
    Integer anxCount = 0
    Integer dnxCount = 0
    Integer nzCount = 0
    Integer fttpCount = 0

    Boolean isCINReady
    String cbTier

    BigDecimal residentialValue = new BigDecimal("0.0")
    BigDecimal cbValue = new BigDecimal("0.0")
    BigDecimal totalProductValue = new BigDecimal("0.0")

    Double utilization
    String quartile
    String trend

    LocationUtil.LocationInfo getLocationInfo(){
        LocationUtil.getLocationInfo(this.market,this.facility)
    }

    String getRegion() {
        return getLocationInfo()?.region ?: this.region
    }

    String getMarket() {
        return getLocationInfo()?.market ?: this.market
    }

    String getFacility() {
        return getLocationInfo()?.facility ?: this.facility
    }

    String getFacilityCode() {
        return getLocationInfo()?.facilityKey ?: "Needs Review"
    }

    String getNode() {
        String result
        if (nodeKey) {
            String everythingAfterPeriod = (nodeKey.split("[.]")[1])
            if ( everythingAfterPeriod.indexOf("0") == 0 ) {
                result = everythingAfterPeriod.substring(1)
            } else {
                result = everythingAfterPeriod
            }
        } else {
            result = "ERROR"
        }
        result
    }

    int getTotalNodeActionCount() {
        anxCount + dnxCount + nzCount + fttpCount
    }

    void add(NodeCatalogItem foreignItem) {
        this.region = foreignItem.region ?: this.region
        this.region = foreignItem.region ?: this.region
        this.market = foreignItem.market ?: this.market
        this.facility = foreignItem.facility ?: this.facility
        this.siteId = foreignItem.siteId ?: this.siteId
        this.nodeKey = foreignItem.nodeKey ?: this.nodeKey

        this.anxCount += foreignItem.anxCount
        this.dnxCount += foreignItem.dnxCount
        this.nzCount += foreignItem.nzCount
        this.fttpCount += foreignItem.fttpCount

        this.isCINReady = foreignItem.isCINReady != null ? foreignItem.isCINReady : this.isCINReady
        this.cbTier = foreignItem.cbTier ?: this.cbTier

        this.cbValue.add(foreignItem.cbValue)
        this.residentialValue.add(foreignItem.residentialValue)
        this.totalProductValue.add(foreignItem.totalProductValue)

        this.utilization = foreignItem.utilization ?: this.utilization
        this.quartile = foreignItem.quartile ?: this.quartile
        this.trend = foreignItem.trend ?: this.trend
    }
}
