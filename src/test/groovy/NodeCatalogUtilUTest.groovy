import domain.NodeCatalogItem
import org.junit.Before
import org.junit.Test
import util.NodeCatalogUtil

/**
 * Tests for {@link NodeCatalogUtil}
 */
class NodeCatalogUtilUTest {

    @Before
    void before() {
        NodeCatalogUtil.clear()
    }

    @Test
    void testAddCatalogDetails_Empty_ShouldUpdate(){

        assert NodeCatalogUtil.items.size() == 0

        NodeCatalogItem original = generateNewItem([:],false)
        NodeCatalogUtil.addCatalogDetails(original)

        NodeCatalogItem incumbent = generateNewItem([nodeKey: original.nodeKey])
        NodeCatalogUtil.addCatalogDetails(incumbent)

        assert NodeCatalogUtil.items.size() == 1

        NodeCatalogItem resulting = NodeCatalogUtil.items.get(0)
        assert resulting.nodeKey == incumbent.nodeKey
        assert resulting.region == incumbent.region
        assert resulting.market == incumbent.market
        assert resulting.siteId == incumbent.siteId
        assert resulting.facility == incumbent.facility
        assert resulting.anxCount == incumbent.anxCount
        assert resulting.dnxCount == incumbent.dnxCount
        assert resulting.nzCount == incumbent.nzCount
        assert resulting.fttpCount == incumbent.fttpCount
        assert resulting.isCINReady == incumbent.isCINReady
        assert resulting.cbTier == incumbent.cbTier
        assert resulting.residentialValue == incumbent.residentialValue
        assert resulting.cbValue == incumbent.cbValue
        assert resulting.totalProductValue == incumbent.totalProductValue
        assert resulting.utilization == incumbent.utilization
        assert resulting.quartile == incumbent.quartile
        assert resulting.trend == incumbent.trend
        assert resulting.node.compareTo(incumbent.node) == 0
    }

    @Test
    void testAddCatalogDetails_PartiallyPopulated_ShouldUpdate(){
        NodeCatalogItem original = generateNewItem(
                [
                    nodeKey: "${UUID.randomUUID().toString()}.${UUID.randomUUID().toString()}",
                    region : UUID.randomUUID().toString(),
                    market : UUID.randomUUID().toString(),
                    totalProductValue : new BigDecimal(Math.random() as int),
                ], false
        )
        NodeCatalogItem incumbent = generateNewItem(
                                            [
                                                    nodeKey: original.nodeKey,
                                                    siteId : UUID.randomUUID().toString(),
                                                    cbTier: UUID.randomUUID().toString(),
                                                    facility: UUID.randomUUID().toString(),
                                                    utilization : Math.random(),
                                                    trend : UUID.randomUUID().toString(),
                                                    anxCount: Math.random() as int,
                                                    dnxCount : Math.random() as int,
                                                    nzCount:Math.random() as int,
                                                    fttpCount:Math.random() as int,
                                                    isCINReady:(Math.random() as int) % 2 ? true : false,
                                                    quartile:UUID.randomUUID().toString()
                                            ], false
                                    )
        assert NodeCatalogUtil.items.size() == 0

        NodeCatalogUtil.addCatalogDetails(original)
        NodeCatalogUtil.addCatalogDetails(incumbent)

        assert NodeCatalogUtil.items.size() == 1

        NodeCatalogItem resulting = NodeCatalogUtil.items.get(0)
        assert resulting.nodeKey == original.nodeKey
        assert resulting.region == original.region
        assert resulting.market == original.market
        assert resulting.siteId == incumbent.siteId
        assert resulting.facility == incumbent.facility
        assert resulting.anxCount == incumbent.anxCount
        assert resulting.dnxCount == incumbent.dnxCount
        assert resulting.nzCount == incumbent.nzCount
        assert resulting.fttpCount == incumbent.fttpCount
        assert resulting.isCINReady == incumbent.isCINReady
        assert resulting.cbTier == incumbent.cbTier
        assert resulting.residentialValue == incumbent.residentialValue
        assert resulting.cbValue == incumbent.cbValue
        assert resulting.totalProductValue == incumbent.totalProductValue
        assert resulting.utilization == incumbent.utilization
        assert resulting.quartile == incumbent.quartile
        assert resulting.trend == incumbent.trend
        assert resulting.node == incumbent.node
    }

    @Test
    void testAddCatalogDetails_OverwritePopulated_ShouldUpdate(){
        NodeCatalogItem original = generateNewItem(
                [
                        nodeKey: "${UUID.randomUUID().toString()}.${UUID.randomUUID().toString()}",
                        region : UUID.randomUUID().toString(),
                        market : UUID.randomUUID().toString(),
                        totalProductValue : new BigDecimal(Math.random() as int),
                ], false
        )
        NodeCatalogItem incumbent = generateNewItem([nodeKey: original.nodeKey])
        assert NodeCatalogUtil.items.size() == 0

        NodeCatalogUtil.addCatalogDetails(original)
        NodeCatalogUtil.addCatalogDetails(incumbent)
        assert NodeCatalogUtil.items.size() == 1

        NodeCatalogUtil.addCatalogDetails(generateNewItem())
        assert NodeCatalogUtil.items.size() == 2

        NodeCatalogItem resulting = NodeCatalogUtil.items.find { it.nodeKey == original.nodeKey }
        assert resulting.nodeKey == incumbent.nodeKey
        assert resulting.region == incumbent.region
        assert resulting.market == incumbent.market
        assert resulting.siteId == incumbent.siteId
        assert resulting.facility == incumbent.facility
        assert resulting.anxCount == incumbent.anxCount
        assert resulting.dnxCount == incumbent.dnxCount
        assert resulting.nzCount == incumbent.nzCount
        assert resulting.fttpCount == incumbent.fttpCount
        assert resulting.isCINReady == incumbent.isCINReady
        assert resulting.cbTier == incumbent.cbTier
        assert resulting.residentialValue == incumbent.residentialValue
        assert resulting.cbValue == incumbent.cbValue
        assert resulting.totalProductValue == incumbent.totalProductValue
        assert resulting.utilization == incumbent.utilization
        assert resulting.quartile == incumbent.quartile
        assert resulting.trend == incumbent.trend
        assert resulting.node == incumbent.node
    }

    private static NodeCatalogItem generateNewItem(Map inputs = [:] , boolean fullyPopulate = true) {
        Map initialization
        if (fullyPopulate) {
            String nodeName = inputs.nodeName ?: UUID.randomUUID().toString()
            initialization =
                    [
                            region : inputs.region ?:  UUID.randomUUID().toString(),
                            market : inputs.market ?: UUID.randomUUID().toString(),
                            nodeKey: inputs.nodeKey ?: "${UUID.randomUUID().toString()}.$nodeName" ,
                            siteId : inputs.siteId ?: UUID.randomUUID().toString(),
                            cbTier: inputs.cbTier ?: UUID.randomUUID().toString(),
                            facility: inputs.facilty ?: UUID.randomUUID().toString(),
                            utilization : inputs.utilization ?: Math.random(),
                            trend : inputs.trend ?: UUID.randomUUID().toString(),
                            anxCount: inputs.anxCount ?: Math.random() as int,
                            dnxCount : inputs.dnxCount ?: Math.random() as int,
                            nzCount:inputs.nzCount ?: Math.random() as int,
                            fttpCount:inputs.fttpCount ?: Math.random() as int,
                            isCINReady:inputs.isCINReady ?: (Math.random() as int) % 2 ? true : false,
                            totalProductValue : inputs.totalProductValue ?: new BigDecimal(Math.random() as int),
                            cbValue : inputs.cbValue ?: new BigDecimal(Math.random() as int),
                            residentialValue : inputs.residentialValue ?: new BigDecimal(Math.random() as int),
                            quartile:inputs.quartile ?: UUID.randomUUID().toString()
                    ]
        } else {
            initialization = inputs ?: [nodeKey:"${UUID.randomUUID()}.${UUID.randomUUID()}"]
        }
        return new NodeCatalogItem(initialization)
    }


}
