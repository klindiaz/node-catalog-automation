import config.Configuration
import config.RunConfiguration
import domain.ACTION
import writer.domain.NAPRecord
import reader.util.ExcelReader
import reader.ReaderUtil
import writer.NormalizedNAPWriter

import java.text.SimpleDateFormat

class EntryPoint {
    private static final Map<String,NAPRecord> infoMap = [:]

    static void main(String[] args) {
        (new File(RunConfiguration.OUTPUT_FILENAME)).delete()
        SimpleDateFormat format = new SimpleDateFormat(Configuration.DATE_FORMAT);

        new ExcelReader(RunConfiguration.INGEST_FILENAME).eachLine([labels: true, offset: 0, sheet: RunConfiguration.INGEST_SHEET_NAME]) {
            String nodeIdVal = (node_id as String)
            if (nodeIdVal) {
                String regionKeyVal = ReaderUtil.getSiteId(sub_region_key as String)
//                String marketVal = market as String
                String marketDescVal = site_desc as String
                String siteIdVal = ReaderUtil.getSiteId(site_id as String)
                String facilityVal = facility as String
                int hhpVal = hhp as int
                String actionDateVal = format.format(new Date(node_action_date as String))

                ACTION action = ACTION.getAction( node_action as String )

                double aerialMilesVal = aerial_mileage as double
                double buriedMilesVal = buried_mileage as double
                String inPOIVal = poi as String

                addToInfoMap(
                    new NAPRecord (
                            [
                                    nodeId: nodeIdVal.contains(".0") ? nodeIdVal.split("[.]")[0] : nodeIdVal,
                                    regionKey: regionKeyVal,
//                                    marketKey: marketVal,
                                    marketDesc: marketDescVal,
                                    siteId: siteIdVal,
                                    facility: facilityVal,
                                    hhp: hhpVal,
                                    actionDate: actionDateVal,
                                    nodeActions: [action],
                                    aerialMiles: aerialMilesVal,
                                    buriedMiles: buriedMilesVal,
                                    inPOI: inPOIVal
                            ]
                    )
                )
            }
        }
        NormalizedNAPWriter.INSTANCE.createWorkbook(infoMap.values() as List).build()
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