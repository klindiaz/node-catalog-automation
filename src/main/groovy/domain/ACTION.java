package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum ACTION {
    PON("PON",0),
    N_PLUS_ZERO("N+0",0),
    FULL_DUPLEX("Full Duplex",4),
    DIGITAL("Digital",1),
    MID_SPLIT("Midsplit",3),
    NODE_SPLIT("Nodesplit",2),
    DECOMBINE("Decombine",5),
    NEEDS_REVIEW("Needs Review!!!",6)
    ;
    private static final Logger logger = LoggerFactory.getLogger(ACTION.class);

    public static final Map<String, ACTION> actionMap = new HashMap<>();

    public final String description;
    public final int order;

    static {
        actionMap.put("PON",PON);

        actionMap.put("MIN_N0",N_PLUS_ZERO);

        actionMap.put("DNX",DIGITAL);

        actionMap.put("MID_SLT",MID_SPLIT);

        actionMap.put("MIN_NS",NODE_SPLIT);
        actionMap.put("MIN_NS1",NODE_SPLIT);
        actionMap.put("MIN_NS2",NODE_SPLIT);
        actionMap.put("MIN_NS3",NODE_SPLIT);
        actionMap.put("MIN_NS4",NODE_SPLIT);

        actionMap.put("MIN_FD",FULL_DUPLEX);
        actionMap.put("MIN_DECO", DECOMBINE);
    }

    ACTION(String description,int order) {
        this.description = description;
        this.order = order;
    }

    public static ACTION getAction(String actionDescription) {
        Objects.requireNonNull(actionDescription , "getAction: actionDescription param cannot be null");
        ACTION action = actionMap.getOrDefault( actionDescription.toUpperCase() , NEEDS_REVIEW );

        if (action == NEEDS_REVIEW) {
            System.out.println( String.format("getAction: Could not find this sort of action ... %s",actionDescription.toUpperCase()) );
        }
        return action;
    }
}
