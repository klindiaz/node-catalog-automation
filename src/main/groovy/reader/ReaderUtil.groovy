package reader

import domain.NodeCatalogItem
import util.NodeCatalogUtil

class ReaderUtil {
    public final static String outputFilename = "/Users/kpl0510/Desktop/Node Catalog/Node Catalog.xlsx"
    static String getSiteId(String siteId) {
        siteId?.contains(".0") ? siteId.split(".0")[0] : siteId
    }

    static BigDecimal getProductValue(String totalCost) {
        totalCost ? new BigDecimal((totalCost) as String) : new BigDecimal("0.0")
    }

    static Double getUtilization(String utilization) {
        utilization ? Double.parseDouble((utilization as String)) : 0.0
    }

    static String getNodeKey(String siteId , String nodeName) {
        "A-${siteId}.$nodeName"
    }
    static void consumeProductValueWorkbook(String filename , String sheetName) {
        new ExcelReader(filename).eachLine([labels: true, offset: 0, sheet: sheetName]) {
            String nodeName = node as String
            if (nodeName) {
                String siteId = getSiteId(site_id as String)
                String nodeKey = getNodeKey(siteId , nodeName)
                BigDecimal residentialValue = getProductValue(residential_value as String)
                BigDecimal cbValue = getProductValue(cb_value as String)
                BigDecimal totalProductValue = getProductValue(node_value as String)

                NodeCatalogUtil.addCatalogDetails(
                        new NodeCatalogItem(
                                [
                                        siteId : siteId,
                                        nodeKey: nodeKey,
                                        facility: sub_system_desc,
                                        cbValue:cbValue,
                                        residentialValue:residentialValue,
                                        totalProductValue:totalProductValue
                                ]
                        )
                )
            }
        }
    }

    static void consumeNodeActionWorkbook(String filename , String sheetName) {
        new ExcelReader(filename).eachLine([labels: true, offset: 0, sheet: sheetName]) {
            String nodeKey = node_key as String
            if (nodeKey) {
                String market = market as String
                String siteId = getSiteId(site_id as String)
                String facility = facility as String
                String actionType = (action_type as String).toUpperCase()
                String cbTier = cb_tier as String

                NodeCatalogUtil.addCatalogDetails(
                        new NodeCatalogItem(
                                [
                                        market : market,
                                        siteId : siteId,
                                        nodeKey: nodeKey,
                                        facility: facility,
                                        nzCount : actionType ? 1 : 0,
                                        anxCount : actionType == "N+X" ? 1 : 0,
                                        dnxCount : actionType == "DNX" ? 1 : 0,
                                        fttpCount : actionType == "FTTP" ? 1 : 0,
                                        cbTier : cbTier
                                ]
                        )
                )
            }
        }
    }

    static void consumeCongestionWorkbook(String filename , String sheetName) {
        new ExcelReader(filename).eachLine([labels: true, offset: 0, sheet: sheetName]) {
            String nodeKey = key as String
            if (nodeKey) {
                String market = market as String
                String region = region as String
                String facility = facility as String
                Double utilization = getUtilization(utilization as String)
                String cbTier = cb_risk_tier as String
                String trend = trend as String
                NodeCatalogUtil.addCatalogDetails(
                        new NodeCatalogItem(
                                [
                                        region : region,
                                        market : market,
                                        nodeKey: nodeKey,
                                        cbTier: cbTier,
                                        facility: facility,
                                        utilization : utilization,
                                        trend : trend
                                ]
                        )
                )
            }
        }
    }

    static void consumeCINReadiness(String filename , String sheetName) {
        new ExcelReader(filename).eachLine([labels: true, offset: 0, sheet: sheetName]) {
            String facilityKey = facility_key as String
            if (facilityKey) {
                NodeCatalogUtil.items.each {
                    if ( it.facilityCode == facilityKey ) {
                        it.isCINReady = true
                    }
                }
            }
        }
    }
}

