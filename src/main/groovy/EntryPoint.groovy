import reader.ReaderUtil
import util.NodeCatalogUtil
import writer.NodeCatalogWriter

class EntryPoint {

    public static String folder = "/Users/kpl0510/Desktop/Node Catalog/Archive"

    static void main(String[] args) {
        (new File(ReaderUtil.outputFilename)).delete()

        String productValueFile = "$folder/Product Value Summary.xlsx"
        ReaderUtil.consumeProductValueWorkbook(productValueFile , "10.2.18")


        String nodeActionPlanFile = "$folder/CCRB_Official_2019_Plan-of-Record_21March_2019.xlsx"
        ReaderUtil.consumeNodeActionWorkbook(nodeActionPlanFile , "N+0 Node List")
        ReaderUtil.consumeNodeActionWorkbook(nodeActionPlanFile , "NX-DNX Node List")
        ReaderUtil.consumeNodeActionWorkbook(nodeActionPlanFile , "FTTP Node List")


        String congestionFile = "$folder/201903_March_Congestion_Report.xlsx"
        ReaderUtil.consumeCongestionWorkbook(congestionFile , "Sheet1")


        String cinReadinessFile = "$folder/Q4 2019 - RPHY CR -POI - 4_11_2019.xlsx"
        ReaderUtil.consumeCINReadiness(cinReadinessFile , "Projected State of Facilities")

        NodeCatalogWriter.INSTANCE.createWorkbook( NodeCatalogUtil.items ).build()
    }

}