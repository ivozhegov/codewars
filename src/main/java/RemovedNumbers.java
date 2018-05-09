import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class RemovedNumbers {

    public static List<long[]> removNb(long n) {

        long sum = (n + 1) * n / 2;

        long start = 1;

        List<long[]> result = new ArrayList<>();
        for (long a = start; a <= n; a++) {
            if ((sum - a) % (a + 1) == 0) {
                long b = (sum - a) / (a + 1);
                if (b <= n) {
                    result.add(new long[]{a, b});
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        print(removNb(26));
        print(removNb(1000000));
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void print(List<long[]> result) {
        for (long[] arr : result) {
            List<Long> list = new ArrayList<>();
            for (long a : arr) {
                list.add(a);
            }
            System.out.print(list);
        }
        System.out.println();
    }
}