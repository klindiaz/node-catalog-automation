package writer.domain

import domain.ACTION
import domain.NodeAction
import domain.NodeLocation
import reader.ReaderUtil
import util.ActionUtil
import util.LocationUtil

class NAPRecord implements NodeAction , NodeLocation {
    String nodeId
    String regionKey
    String marketDesc
    String siteId
    String facility
    int hhp
    String actionDate
    List<ACTION> nodeActions
    double aerialMiles
    double buriedMiles
    String inPOI

    LocationUtil.LocationInfo getLocationInfo(){
        LocationUtil.getLocationInfo(this.marketDesc,this.facility)
    }

    String getRegion() {
        return getLocationInfo()?.region ?: this.regionKey
    }

    String getMarket() {
        return getLocationInfo()?.market ?: this.marketDesc
    }

    String getFacility() {
        return getLocationInfo()?.facility ?: this.facility
    }

    String getFacilityCode() {
        return getLocationInfo()?.facilityKey ?: "Needs Review"
    }

    String getUniqueIdentifier() {
        "${ReaderUtil.getNodeKey(siteId,nodeId)} - $actionDate"
    }

    String getActionDescriptor() {
        nodeActions.sort().join("/")
    }

    double getTotalMiles(){
        aerialMiles + buriedMiles
    }

    @Override
    List<ACTION> getNodeActions() {
        return nodeActions
    }

    String getActionToTake() {
        return ActionUtil.getActionDescription(nodeActions)
    }
}