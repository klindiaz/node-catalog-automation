package domain.unused

import com.google.common.collect.HashMultiset
import com.google.common.collect.Multiset

class Node {
    Location locationDetails

    String key
    String fullName
    String shortName
    List<String> actions = []
    Multiset<String> actionCounts = HashMultiset.create()

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        groovy.util.Node node = (groovy.util.Node) o

        if (key != node.key) return false

        return true
    }

    int hashCode() {
        return (key != null ? key.hashCode() : 0)
    }
}
