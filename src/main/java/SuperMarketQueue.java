import java.util.Collections;
import java.util.PriorityQueue;

public class SuperMarketQueue {

    public static void main(String[] args) {
        System.out.println(solveSuperMarketQueue(new int[] { 2, 2, 3, 3, 4, 4 }, 2));
        System.out.println(solveSuperMarketQueue(new int[] {}, 1));
    }

    public static int solveSuperMarketQueue(int[] customers, int n) {
        PriorityQueue<Integer> tills = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            tills.add(0);
        }
        for (int customer : customers) {
            tills.add(tills.remove() + customer);
        }
        return Collections.max(tills);
    }

    public static int solveSuperMarketQueue2(int[] customers, int n) {
        Tills tills = new Tills(n);
        for (int customer : customers) {
            tills.add(customer);
        }
        return tills.getMax();
    }

    private static class Tills {

        private int[] array;

        private Tills(int n) {
            array = new int[n];
        }

        private void add(int value) {
            array[0] += value;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                } else {
                    break;
                }
            }
        }

        private int getMax() {
            return array[array.length - 1];
        }
    }

    public static int solveSuperMarketQueue1(int[] customers, int n) {

        int[] tills = new int[n];
        int customerIndex = 0;
        int minInTills = 0;
        int result = 0;

        boolean done;

        do {
            done = true;

            for (int i = 0; i < tills.length; i++) {
                if (tills[i] == 0 && customerIndex < customers.length) {
                    tills[i] = customers[customerIndex++];
                    if (tills[i] < minInTills || minInTills == 0) {
                        minInTills = tills[i];
                    }
                }
            }
            for (int i = 0; i < tills.length; i++) {
                if (tills[i] > 0) {
                    tills[i] -= minInTills;
                    done = false;
                }
            }
            result += minInTills;
            minInTills = 0;

        } while (!done);

        return result;
    }

}
