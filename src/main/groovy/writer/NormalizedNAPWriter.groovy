package writer

import com.jameskleeh.excel.ExcelBuilder
import config.Configuration
import config.RunConfiguration
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import writer.domain.NAPRecord

import java.awt.*
import java.util.List

class NormalizedNAPWriter {
    XSSFWorkbook workbook

    public static NormalizedNAPWriter INSTANCE = new NormalizedNAPWriter()
    private NormalizedNAPWriter(){}

    NormalizedNAPWriter createWorkbook(List<NAPRecord> records) {
        def headerFormat = [
                font           : [bold: true, size: 11.5],
                backgroundColor: Color.LIGHT_GRAY,
                alignment      : HorizontalAlignment.CENTER,
        ]
        XSSFWorkbook workbook = ExcelBuilder.build {
            sheet("NAP") {
                row {
                    cell("Region", headerFormat)
                    cell("Market", headerFormat)
                    cell("Site ID", headerFormat)
                    cell("Facility", headerFormat)
                    cell("Facility TLA", headerFormat)
                    cell("Node ID", headerFormat)
                    cell("Action Date", headerFormat)
                    cell("Action Driver", headerFormat)
                    cell("Action Code", headerFormat)
                    cell("Node Action", headerFormat)
                    cell("HHP", headerFormat)
                    cell("Res HHP", headerFormat)
                    cell("MDU HHP", headerFormat)
                    cell("Buried Mileage", headerFormat)
                    cell("Aerial Mileage", headerFormat)
                    cell("RPD Count", headerFormat)
                    cell("POI", headerFormat)
                    cell("Node Type", headerFormat)

                    cell("CMTS", headerFormat)
                    cell("Service Group", headerFormat)
//                    cell("TLA", headerFormat)
                    cell("OSP Market", headerFormat)
                }

                records.each {
                    record ->
                        row {
                            cell(record?.region ?: "N/A")
                            cell(record?.market ?: "N/A")
                            cell(record.siteId ?: "N/A")
                            cell(record?.facility ?: "N/A")
//                            cell(record?.facilityCode)
                            cell(record?.facilityTLA)
                            cell(record.nodeId)
                            cell(record.actionDate)
                            cell(record.actionDriver)
                            cell(record.actionDescriptor)
                            cell(record.actionToTake)
                            cell(record.hhp)
                            cell(record.resHHP)
                            cell(record.mduHHP)
                            cell(record.buriedMiles)
                            cell(record.aerialMiles)
                            cell(Math.ceil(Configuration.MILEAGE_TO_RPD_RATIO * (record.totalMiles)))
                            cell(record.inPOI)
                            cell(record.nodeType)

                            cell(record.cmts)
                            cell(record.serviceGroup)
//                            cell(record.facilityTLA)
                            cell(record.ospMarket)
                        }
                }
            }
        }
        this.workbook = workbook

        return this
    }

    void build(final String scenario) {
        try {
            OutputStream fo = new FileOutputStream(String.format(RunConfiguration.OUTPUT_FILENAME,scenario))
            this.workbook.write( fo );
        } catch(Exception e) {
            throw e;
        }
    }

}
