import config.Configuration
import config.RunConfiguration
import domain.ACTION
import reader.ReaderUtil
import reader.util.ExcelReader
import writer.NormalizedNAPWriter
import writer.domain.NAPRecord

import java.text.SimpleDateFormat

class EntryPoint {
    private static final Map<String,NAPRecord> infoMap = [:]

    static void main(String[] args) {
        (new File(RunConfiguration.OUTPUT_FILENAME)).delete()
        SimpleDateFormat format = new SimpleDateFormat(Configuration.DATE_FORMAT);
        String npaScenario

        new ExcelReader(RunConfiguration.INGEST_FILENAME).eachLine([labels: true, offset: 0, sheet: RunConfiguration.INGEST_SHEET_NAME]) {
            String nodeIdVal = (node_id as String)
            try {
                if (nodeIdVal) {
                    String regionKeyVal = ReaderUtil.getSiteId(sub_region_key as String)
                    String marketDescVal = site_desc as String
                    String siteIdVal = ReaderUtil.getSiteId(site_id as String)
                    String facilityVal = facility as String
                    int hhpVal = (int) Double.parseDouble(hhp as String)
                    int mduHHP = (int) Double.parseDouble(mdu_hhp as String)
                    int resHHP = (int) Double.parseDouble(res_hhp as String)
                    String actionDateVal = format.format(new Date(node_action_date as String))
                    String actionDriver = node_action_driver as String

                    ACTION action = ACTION.getAction( node_action as String )

                    double aerialMilesVal = aerial_mileage as double
                    double buriedMilesVal = buried_mileage as double
                    String inPOIVal = poi as String
                    String nodeType = node_type

                    String cmts = cmts as String
                    String serviceGroup = service_grp as String
                    String facilityTLA = tla as String
                    String ospMarket = osp_market as String
                    npaScenario = scenario as String

                    addToInfoMap(
                        new NAPRecord (
                                [
                                        nodeId: nodeIdVal.contains(".0") ? nodeIdVal.split("[.]")[0] : nodeIdVal,
                                        regionKey: regionKeyVal,
                                        marketDesc: marketDescVal,
                                        siteId: siteIdVal,
                                        facility: facilityVal,
                                        hhp: hhpVal,
                                        mduHHP: mduHHP,
                                        resHHP: resHHP,
                                        actionDate: actionDateVal,
                                        actionDriver: actionDriver,
                                        nodeActions: [action],
                                        aerialMiles: aerialMilesVal,
                                        buriedMiles: buriedMilesVal,
                                        inPOI: inPOIVal,
                                        nodeType : nodeType,
                                        cmts:cmts,
                                        serviceGroup:serviceGroup,
                                        facilityTLA:facilityTLA,
                                        ospMarket:ospMarket,
                                        scenario:scenario
                                ]
                        )
                    )
                }
            } catch ( Exception e ) {
                e.printStackTrace()
            }
        }
        NormalizedNAPWriter.INSTANCE.createWorkbook(infoMap.values() as List).build(npaScenario)
        System.out.println("DONE")
    }

    static addToInfoMap(NAPRecord info) {
        if ( infoMap.containsKey(info.uniqueIdentifier) ) {
            NAPRecord current = infoMap.get( info.uniqueIdentifier )
            current.nodeActions.addAll(info.nodeActions)
        } else {
            infoMap.put(info.uniqueIdentifier , info)
        }
    }
}