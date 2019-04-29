package writer

import com.jameskleeh.excel.ExcelBuilder
import domain.NodeCatalogItem
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import reader.ReaderUtil

import java.awt.Color

class NodeCatalogWriter {
    XSSFWorkbook workbook

    public static NodeCatalogWriter INSTANCE = new NodeCatalogWriter()
    private NodeCatalogWriter(){}

    NodeCatalogWriter createWorkbook(List<NodeCatalogItem> records) {
        def headerFormat = [
                font           : [bold: true, size: 11.5],
                backgroundColor: Color.LIGHT_GRAY,
                alignment      : HorizontalAlignment.CENTER,
        ]
        XSSFWorkbook workbook = ExcelBuilder.build {
            sheet("Node Catalog") {
                row {
                    cell("Region", headerFormat)
                    cell("Market", headerFormat)
//                    cell("Market Code", headerFormat)
                    cell("Market Key", headerFormat)
                    cell("Facility", headerFormat)
                    cell("Facility Key", headerFormat)

                    cell("Node Name", headerFormat)
                    cell("Node Key", headerFormat)
                    cell("N+X Count", headerFormat)
                    cell("DNX Count", headerFormat)
                    cell("NZ Count", headerFormat)
                    cell("FTTP Count", headerFormat)
                    cell("Total Number Of Node Actions", headerFormat)
                    cell("CIN Ready", headerFormat)
                    cell("CB Tier", headerFormat)
                    cell("Residental Product Value", headerFormat)
                    cell("CB Product Value", headerFormat)
                    cell("Total Product Value", headerFormat)
                    cell("Node Utilization", headerFormat)
                    cell("Quartile", headerFormat)
                }

                records.each {
                    record ->
                        row {
                            cell(record?.region ?: "N/A")
                            cell(record?.market ?: "N/A")
//                            cell(record?.marketCode)
                            cell(record.siteId ?: "N/A")
                            cell(record?.facility ?: "N/A")
                            cell(record?.facilityCode)
                            cell(record.node)
                            cell(record.nodeKey)
                            cell(record.anxCount)
                            cell(record.dnxCount)
                            cell(record.nzCount)
                            cell(record.fttpCount)
                            cell(record.totalNodeActionCount)
                            cell(record.isCINReady ?: false)
                            cell(record.cbTier ?: "N/A")
                            cell(record.residentialValue ?: "N/A")
                            cell(record.cbValue ?: "N/A")
                            cell(record.totalProductValue ?: "N/A")
                            cell(record.utilization ?: "N/A")
                            cell(record.quartile ?: "N/A")
                        }
                }
            }
        }
        this.workbook = workbook

        return this
    }

    void build() {
        try {
            OutputStream fo = new FileOutputStream(ReaderUtil.outputFilename)
            this.workbook.write( fo );
        } catch(Exception e) {
            throw e;
        }
    }

}
