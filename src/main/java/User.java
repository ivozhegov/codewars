import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class User {

    private static final int[] RANKS = {-8,-7,-6,-5,-4,-3,-2,-1,1,2,3,4,5,6,7,8};

    private static final int PREV_RANK_POINTS = 1;
    private static final int SAME_RANK_POINTS = 3;
    private static final int NEXT_RANK_POINTS = 10;
    private static final int RANK_UP_POINTS = 100;

    private int currentRankIndex;

    public int rank;
    public int progress;

    public User() {
        rank = RANKS[currentRankIndex];
        progress = 0;
    }

    public void incProgress(int activityRank) {
        int rankIndex = getRankIndex(activityRank);
        if (rankIndex < currentRankIndex - 1 || currentRankIndex == RANKS.length - 1) {
            return;
        }
        if (rankIndex == currentRankIndex - 1) {
            progress += PREV_RANK_POINTS;
        } else if (rankIndex == currentRankIndex) {
            progress += SAME_RANK_POINTS;
        } else {
            int diff = rankIndex - currentRankIndex;
            progress += NEXT_RANK_POINTS * diff * diff;
        }
        if (progress >= RANK_UP_POINTS) {
            currentRankIndex += progress / RANK_UP_POINTS;
            progress = progress % RANK_UP_POINTS;
            if (currentRankIndex >= RANKS.length - 1) {
                currentRankIndex = RANKS.length - 1;
                progress = 0;
            }
        }
        rank = RANKS[currentRankIndex];
    }

    private int getRankIndex(int rank) {
        for (int i = 0; i < RANKS.length; i++) {
            if (rank == RANKS[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("Incorrect rank value");
    }



    @Test
    public void testNew() {
        User user = new User();
        assertEquals(-8, user.rank);
        assertEquals(0, user.progress);
    }

    @Test
    public void testIncProgress_sameRank() {
        User user = new User();
        user.incProgress(-8);
        assertEquals(-8, user.rank);
        assertEquals(3, user.progress);
    }

    @Test
    public void testIncProgress_nextRank() {
        User user = new User();
        user.incProgress(-7);
        assertEquals(-8, user.rank);
        assertEquals(10, user.progress);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncProgress_wrongRank1() {
        User user = new User();
        user.incProgress(-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncProgress_wrongRank2() {
        User user = new User();
        user.incProgress(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncProgress_wrongRank3() {
        User user = new User();
        user.incProgress(0);
    }

    @Test
    public void testRankUp_oneRank() {
        User user = new User();
        user.incProgress(-4);
        assertEquals(-7, user.rank);
        assertEquals(60, user.progress);
    }

    @Test
    public void testRankUp_manyRanks() {
        User user = new User();
        user.incProgress(-1);
        assertEquals(-4, user.rank);
        assertEquals(90, user.progress);
    }

    @Test
    public void testIncProgress_prevRank() {
        User user = new User();
        user.incProgress(-1);
        assertEquals(-4, user.rank);
        assertEquals(90, user.progress);

        user.incProgress(-5);
        assertEquals(-4, user.rank);
        assertEquals(91, user.progress);

        user.incProgress(-6);
        assertEquals(-4, user.rank);
        assertEquals(91, user.progress);

        user.incProgress(-7);
        assertEquals(-4, user.rank);
        assertEquals(91, user.progress);
    }

    @Test
    public void testRankUp_onTop() {
        User user = new User();
        user.incProgress(8);
        assertEquals(8, user.rank);
        assertEquals(0, user.progress);

        user.incProgress(8);
        assertEquals(8, user.rank);
        assertEquals(0, user.progress);
    }

}
