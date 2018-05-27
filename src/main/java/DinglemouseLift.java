import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DinglemouseLift {

    @Test
    public void testUp() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[]{5,5,5}, // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = DinglemouseLift.theLift(queues,5);
        assertArrayEquals(new int[]{0,2,5,0}, result);
    }

    @Test
    public void testDown() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[]{1,1}, // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = DinglemouseLift.theLift(queues,5);
        assertArrayEquals(new int[]{0,2,1,0}, result);
    }

    @Test
    public void testUpAndUp() {
        final int[][] queues = {
                new int[0], // G
                new int[]{3}, // 1
                new int[]{4}, // 2
                new int[0], // 3
                new int[]{5}, // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = DinglemouseLift.theLift(queues,5);
        assertArrayEquals(new int[]{0,1,2,3,4,5,0}, result);
    }

    @Test
    public void testDownAndDown() {
        final int[][] queues = {
                new int[0], // G
                new int[]{0}, // 1
                new int[0], // 2
                new int[0], // 3
                new int[]{2}, // 4
                new int[]{3}, // 5
                new int[0], // 6
        };
        final int[] result = DinglemouseLift.theLift(queues,5);
        assertArrayEquals(new int[]{0,5,4,3,2,1,0}, result);
    }

//    @Test
//    public void testDownAndDown1() {
//        final int[][] queues = {
//                new int[]{1}, // G
//                new int[]{2}, // 1
//                new int[]{3}, // 2
//                new int[]{4}, // 3
//                new int[]{2}, // 4
//                new int[]{3}, // 5
//                new int[0], // 6
//        };
//        final int[] result = DinglemouseLift.theLift(queues,5);
//        assertArrayEquals(new int[]{0,1,2,3,4,2,5,3,0}, result);
//    }

    @Test
    public void testUpAndDown1() {
        final int[][] queues = {
                new int[]{3}, // G
                new int[]{2}, // 1
                new int[]{0}, // 2
                new int[]{2}, // 3
                new int[0], // 4
                new int[0], // 5
                new int[]{5}, // 6
        };
        final int[] result = DinglemouseLift.theLift(queues,5);
        assertArrayEquals(new int[]{0,1,2,3,6,5,3,2,0}, result);
    }

    @Test
    public void testRandom1() {
        final int[][] queues = {
                new int[0],
                new int[0],
                new int[]{4,0,0},
                new int[]{2,1,2,0},
                new int[]{1,0,2},
        };
        final int[] result = DinglemouseLift.theLift(queues,4);
        assertArrayEquals(new int[]{0, 2, 4, 3, 2, 1, 0, 3, 2, 1, 0}, result);
    }

    @Test
    public void testRandom2() {
        final int[][] queues = {
                new int[]{6,12,6,9}, //0
                new int[]{2,14,6,14}, //1
                new int[]{14,0,6,16}, //2
                new int[0], //3
                new int[]{7,17,0,7}, //4
                new int[]{4,8}, //5
                new int[]{2,15,15,0}, //6
                new int[]{1,1,8,6}, //7
                new int[0], //8
                new int[0], //9
                new int[]{11,16,8,13}, //10
                new int[]{2,0}, //11
                new int[0], //12
                new int[]{9,14,3,8}, //13
                new int[]{4,15,0}, //14
                new int[]{10,8}, //15
                new int[]{11,13,6,14}, //16
                new int[0], //17
        };
        final int[] result = DinglemouseLift.theLift(queues,5);
        assertArrayEquals(new int[]{0, 1, 2, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 15, 14, 13, 11, 10, 9, 8, 7, 6, 5, 4, 2, 1, 0, 1, 2, 4, 5, 6, 7, 8, 10, 13, 14, 16, 15, 14, 13, 11, 8, 7, 6, 5, 4, 3, 0, 4, 5, 7, 8, 17, 0}, result);
    }

    @Test
    public void testRandom3() {
        final int[][] queues = {
                new int[]{8,8,6},//0
                new int[]{9,7,3},//1
                new int[]{9,4,4,7},//2
                new int[0],//3
                new int[]{6},//4
                new int[]{8,3},//5
                new int[]{9,1,4},//6
                new int[]{11,6,6,11},//7
                new int[0],//8
                new int[0],//9
                new int[]{7,6,2,5},//10
                new int[]{9,3,1},//11
        };
        final int[] result = DinglemouseLift.theLift(queues,2);
        assertArrayEquals(new int[]{0, 1, 2, 4, 5, 6, 7, 8, 11, 10, 9, 7, 6, 5, 3, 1, 0, 1, 2, 4, 5, 6, 7, 9, 11, 10, 7, 6, 5, 4, 1, 2, 3, 4, 5, 6, 7, 11, 10, 6, 5, 3, 2, 4, 5, 8, 9, 10, 5, 2, 4, 7, 0}, result);
    }

    @Test
    public void testRandom4() {
        final int[][] queues = {
                new int[]{6,5},//0
                new int[]{4,0,4},//1
                new int[]{6,1,0},//2
                new int[]{4,2},//3
                new int[]{2},//4
                new int[]{1,1,6,2},//5
                new int[]{1,2,2},//6
        };
        final int[] result = DinglemouseLift.theLift(queues,1);
        assertArrayEquals(new int[]{0, 1, 2, 3, 5, 6, 5, 4, 3, 2, 1, 0, 1, 2, 3, 5, 6, 5, 4, 3, 2, 1, 2, 3, 4, 6, 5, 4, 3, 2, 0, 1, 2, 3, 4, 5, 4, 3, 1, 2, 3, 6, 5, 4, 3, 1, 3, 4, 5, 4, 3, 2, 4, 3, 2, 3, 2, 0}, result);
    }

    @Test
    public void testRandom5() {
        final int[][] queues = {
                new int[]{4},//0
                new int[]{},//1
                new int[]{},//2
                new int[]{2},//3
                new int[]{2},//4
                new int[]{2},//5
                new int[]{},//6
        };
        final int[] result = DinglemouseLift.theLift(queues,1);
        assertArrayEquals(new int[]{0, 4, 5, 4, 3, 2, 4, 3, 2, 3, 2, 0}, result);
    }

    private static final int EMPTY_SPOT = -1;

    private static void log(final int[][] queues, final int capacity) {
        System.out.println("capacity: " + capacity);
        System.out.println("queues: ");
        for (int i = 0; i < queues.length; i++) {
            int[] queue = queues[i];
            if (queue.length == 0) {
                System.out.print("new int[0],");
            } else {
                System.out.print("new int[]{");
                String separator = "";
                for (int q : queue) {
                    System.out.print(separator + q);
                    separator = ",";
                }
                System.out.print("},");
            }
            System.out.print(" //" + i);
            System.out.println();
        }
    }

    public static int[] theLift(final int[][] queues, final int capacity) {
        log(queues, capacity);

        List<Integer> stops = new ArrayList<>();

        int[] lift = new int[capacity];
        Arrays.fill(lift, EMPTY_SPOT);

        boolean directionUp = true;
        int floor;

        boolean hasStopsUp = true;
        boolean hasStopsDown = true;

        while (hasStopsUp || hasStopsDown) {
            if (directionUp) {
                hasStopsUp = false;
            } else {
                hasStopsDown = false;
            }
            for (int i = 0; i < queues.length; i++) {
                floor = directionUp ? i : queues.length - i - 1;
                if (isStop(lift, directionUp, floor, queues)) {
                    if (stops.isEmpty() || stops.get(stops.size() - 1) != floor) {
                        stops.add(floor);
                    }

                    if (directionUp) {
                        hasStopsUp = true;
                    } else {
                        hasStopsDown = true;
                    }

                    if (isLiftEmpty(lift) && !continueToHighestLowest(queues, directionUp, floor) && canChangesDirection(queues, directionUp)) {
                        // change direction
                        directionUp = !directionUp;
                        i = queues.length - i - 2;
                    }
                }
            }
            directionUp = !directionUp;
        }

        return convertToResult(stops);
    }

    private static boolean isStop(int[] lift, boolean directionUp, int floor, int[][] queues) {
        boolean isStop = false;

        for (int i = 0; i < lift.length; i++) {
            if (lift[i] == floor) {
                isStop = true;
                lift[i] = EMPTY_SPOT;
            }
        }

        int[] queue = queues[floor];
        for (int i = 0; i < queue.length; i++) {
            if ((directionUp && queue[i] > floor) || (!directionUp && queue[i] < floor)) {
                isStop = true;
                if (getOn(lift, queue[i])) {
                    queue[i] = EMPTY_SPOT;
                } else {
                    break;
                }
            }
        }
        removeEmptySpots(floor, queues);

        return isStop;
    }

    private static boolean getOn(int[] lift, int targetFloor) {
        for (int i = 0; i < lift.length; i++) {
            if (lift[i] == EMPTY_SPOT) {
                lift[i] = targetFloor;
                return true;
            }
        }
        return false;
    }

    private static void removeEmptySpots(int floor, int[][] queues) {
        int[] queue = queues[floor];
        int count = 0;
        for (int q : queue) {
            if (q == EMPTY_SPOT) {
                count++;
            }
        }
        if (count > 0) {
            int[] newQueue = new int[queue.length - count];
            int newQueueIndex = 0;
            for (int q : queue) {
                if (q != EMPTY_SPOT) {
                    newQueue[newQueueIndex++] = q;
                }
            }
            queues[floor] = newQueue;
        }
    }

    private static boolean isLiftEmpty(int[] lift) {
        for (int i = 0; i < lift.length; i++) {
            if (lift[i] != EMPTY_SPOT) {
                return false;
            }
        }
        return true;
    }

    //When empty the Lift tries to be smart. For example,
    //If it was going up then it may continue up to collect the highest floor person wanting to go down
    //If it was going down then it may continue down to collect the lowest floor person wanting to go up
    private static boolean continueToHighestLowest(int[][] queues, boolean directionUp, int floor) {
        for (int i = queues.length - 1; i > floor; i--) {
            int nextFloor = directionUp ? i : queues.length - i - 1;
            if (queues[nextFloor].length > 0) {
                return true;
            }
        }
        return false;
    }

    //The Lift never changes direction until there are no more people wanting to get on/off in the direction it is already travelling
    private static boolean canChangesDirection(int[][] queues, boolean directionUp) {
        for (int floor = 0; floor < queues.length; floor++) {
            if (queues[floor].length > 0) {
                for (int q : queues[floor]) {
                    if ((directionUp && q > floor) || (!directionUp && q < floor)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static int[] convertToResult(List<Integer> stops) {
        if (stops.isEmpty() || stops.get(0) != 0) {
            stops.add(0, 0);
        }
        if (stops.get(stops.size() - 1) != 0) {
            stops.add(0);
        }

        System.out.println(stops);

        int[] result = new int[stops.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = stops.get(i);
        }
        return result;
    }

}
