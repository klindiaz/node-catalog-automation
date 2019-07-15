package util

import domain.ACTION

import static domain.ACTION.NEEDS_REVIEW

class ActionUtil {

    static String getActionDescription(List<ACTION> actions) {
        DescriptionBuilder builder = new DescriptionBuilder()
        Set<ACTION> uniqueActions = new HashSet<>(actions)

        uniqueActions.each { builder.add(it) }

        return builder.build()
    }

    private static final class DescriptionBuilder {
        List<ACTION> descriptions = new LinkedList<>()
        StringBuilder builder = new StringBuilder()

        private static final String DEFAULT_DESCRIPTION = NEEDS_REVIEW.description
        private static final String SPACE = " "

        DescriptionBuilder add(ACTION action) {
            descriptions.add(action)
            this
        }

        String build() {
            if ( !descriptions.contains(NEEDS_REVIEW) ) {
                descriptions.sort{it.order}.each {
                    action ->
                        if (this.builder.size() > 0) {
                            this.builder.append(SPACE).append(action.description)
                        } else {
                            this.builder.append(action.description)
                        }
                }
            }
            builder.length() > 0 ? builder.toString(): DEFAULT_DESCRIPTION
        }
    }
}
