import org.junit.Test
import util.ActionUtil

import static domain.ACTION.*

/**
 * Tests for {@link ActionUtil}
 */
class ActionUtilUTest {

    @Test
    void testGetActionDescription_SingleActions_ShouldReturnDescriptions() {
        assert PON.description == ActionUtil.getActionDescription([PON])
        assert N_PLUS_ZERO.description == ActionUtil.getActionDescription([N_PLUS_ZERO])
        assert FULL_DUPLEX.description == ActionUtil.getActionDescription([FULL_DUPLEX])
        assert DIGITAL.description == ActionUtil.getActionDescription([DIGITAL])
        assert MID_SPLIT.description == ActionUtil.getActionDescription([MID_SPLIT])
        assert NODE_SPLIT.description == ActionUtil.getActionDescription([NODE_SPLIT])
        assert DECOMBINE.description == ActionUtil.getActionDescription([DECOMBINE])
        assert NEEDS_REVIEW.description == ActionUtil.getActionDescription([NEEDS_REVIEW])
    }

    @Test
    void testGetActionDescription_NZ_Permutations() {
        assert N_PLUS_ZERO.description + " " + MID_SPLIT.description == ActionUtil.getActionDescription([MID_SPLIT,N_PLUS_ZERO])

        assert N_PLUS_ZERO.description + " " + DIGITAL.description + " " + MID_SPLIT.description ==
                ActionUtil.getActionDescription([MID_SPLIT,N_PLUS_ZERO,DIGITAL])

        assert N_PLUS_ZERO.description + " " + DECOMBINE.description == ActionUtil.getActionDescription([N_PLUS_ZERO,DECOMBINE])

        assert N_PLUS_ZERO.description + " " + DIGITAL.description + " " + MID_SPLIT.description + " " + DECOMBINE.description==
                ActionUtil.getActionDescription([MID_SPLIT,DECOMBINE,N_PLUS_ZERO,DIGITAL])

        assert N_PLUS_ZERO.description + " " + FULL_DUPLEX.description == ActionUtil.getActionDescription([FULL_DUPLEX,N_PLUS_ZERO])
    }

    @Test
    void testGetActionDescription_Digital_Permutations() {
        assert DIGITAL.description + " " + NODE_SPLIT.description == ActionUtil.getActionDescription([NODE_SPLIT,DIGITAL])
        assert DIGITAL.description + " " + NODE_SPLIT.description + " " + MID_SPLIT.description == ActionUtil.getActionDescription([MID_SPLIT,DIGITAL,NODE_SPLIT])
        assert DIGITAL.description + " " + MID_SPLIT.description == ActionUtil.getActionDescription([MID_SPLIT,DIGITAL])
    }

    @Test
    void testGetActionDescription_Decombine_Permutations() {
        assert PON.description + " " + DECOMBINE.description == ActionUtil.getActionDescription([DECOMBINE,PON])
        assert MID_SPLIT.description + " " + DECOMBINE.description == ActionUtil.getActionDescription([DECOMBINE,MID_SPLIT])
        assert FULL_DUPLEX.description + " " + DECOMBINE.description == ActionUtil.getActionDescription([DECOMBINE,FULL_DUPLEX])
    }

    @Test
    void testGetActionDescription_Invalid_ShouldReturnNeedsReview() {
        assert NEEDS_REVIEW.description == ActionUtil.getActionDescription([NEEDS_REVIEW,PON,N_PLUS_ZERO])
    }

}
