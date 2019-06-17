package util

import domain.ACTION

class ActionUtil {
    private static final String N_PLUS_ZERO_MIDSPLIT =  "N+0 Midsplit";
    private static final String N_PLUS_ZERO =  "N+0";
    private static final String DIGITAL_NODE_SPLIT_MIDSPLIT =  "Digital Nodesplit Midsplit";
    private static final String DIGITAL_MIDSPLIT =  "Digital Midsplit";
    private static final String DIGITAL_NODE_SPLIT =  "Digital Nodesplit";
    private static final String MIDSPLIT_NODE_SPLIT =  "Midsplit NS";
    private static final String MIDSPLIT =  "Midsplit";
    private static final String NODE_SPLIT =  "NS";
    private static final String NEEDS_REVIEW =  "NEEDS REVIEW!!!";

    static String getActionDescription(List<ACTION> actions) {
        boolean isNPlusZero = actions.contains(ACTION.N_PLUS_ZERO)
        boolean isDigital = actions.contains(ACTION.DIGITAL)
        boolean isMidSplit = actions.contains(ACTION.MID_SPLIT)
        boolean isNodeSplit = actions.contains(ACTION.NODE_SPLIT)

        if (isNPlusZero && isMidSplit) {
            return N_PLUS_ZERO_MIDSPLIT
        } else if (isNPlusZero && !isMidSplit) {
            return N_PLUS_ZERO
        } else if (isDigital && isNodeSplit && isMidSplit &&!isNPlusZero) {
            return DIGITAL_NODE_SPLIT_MIDSPLIT
        } else if (isDigital && isMidSplit && !isNPlusZero && !isNodeSplit) {
            return DIGITAL_MIDSPLIT
        } else if (isDigital && isNodeSplit && !isMidSplit && !isNPlusZero) {
            return DIGITAL_NODE_SPLIT
        } else if (isNodeSplit && !isNPlusZero && !isDigital && !isMidSplit) {
            return NODE_SPLIT
        } else if (isMidSplit && isNodeSplit && !isNPlusZero && !isDigital) {
            return MIDSPLIT_NODE_SPLIT
        } else if (isMidSplit && !isNPlusZero && !isDigital && !isNodeSplit) {
            return MIDSPLIT
        } else {
            return NEEDS_REVIEW
        }
    }
}
