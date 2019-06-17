package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum ACTION {
    N_PLUS_ZERO,
    DIGITAL,
    MID_SPLIT,
    NODE_SPLIT,
    NEEDS_REVIEW
    ;
    private static final Logger logger = LoggerFactory.getLogger(ACTION.class);

    public static final Map<String, ACTION> actionMap = new HashMap<>();

    static {
        actionMap.put("MIN_N0",N_PLUS_ZERO);

        actionMap.put("DNX",DIGITAL);

        actionMap.put("MID_SLT",MID_SPLIT);

        actionMap.put("MIN_NS",NODE_SPLIT);
        actionMap.put("MIN_NS1",NODE_SPLIT);
        actionMap.put("MIN_NS2",NODE_SPLIT);
        actionMap.put("MIN_NS3",NODE_SPLIT);
        actionMap.put("MIN_NS4",NODE_SPLIT);
    }

    public static ACTION getAction(String actionDescription) {
        Objects.requireNonNull(actionDescription , "getAction: actionDescription param cannot be null");
        ACTION action = actionMap.getOrDefault( actionDescription.toUpperCase() , NEEDS_REVIEW );

        logger.warn( String.format("getAction: Could not find this sort of action ... %s",actionDescription.toUpperCase()) );

        return action;
    }
}
