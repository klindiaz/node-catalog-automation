package reader

class ReaderUtil {
    static String getSiteId(String siteId) {
        siteId?.contains(".0") ? siteId.split("[.]")[0] : siteId
    }

    static String getNodeKey(String siteId , String nodeName) {
        "A-${getSiteId(siteId)}.$nodeName"
    }
}

