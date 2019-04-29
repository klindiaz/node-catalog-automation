package util

import domain.NodeCatalogItem

class NodeCatalogUtil {
    static Map<String, NodeCatalogItem> items = [:]

    static void addCatalogDetails(final NodeCatalogItem item) {
        NodeCatalogItem anchor = items.get(item.nodeKey)

        if (!anchor) {
            items.put(item.nodeKey , item)
        } else {
            anchor.add(item)
            items.put(anchor.nodeKey , anchor)
        }
    }

    static List<NodeCatalogItem> getItems() {
        items.values() as List
    }

    static void clear(){
        items = [:]
    }
}
