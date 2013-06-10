import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 10/06/13
 * Time: 5:31 PM
 * Equilibrium index of an array is an index such that the sum of
 * elements at lower indexes is equal to the sum of elements at higher indexes.
 */
public class MostFrequentArrayElement {
    public static final int[] read_array(int size)
    {
        Scanner scanner = new Scanner(System.in);
        int[] A  = new int[size];
        for (int i = 0; i < size; i++) {
            A[i] = scanner.nextInt();
        }
        return A;
    }

    public static int mode(int[] Arr) {
        int maxKey =0;
        int maxCount = 0;
        int[] Counts = new int[Arr.length];
        for (int aArr : Arr) {
            int c = Counts[aArr]++;
            if (maxCount < c) {
                maxCount = c;
                maxKey = aArr;
            }
        }
        return maxKey;
    }

    public static void main(String[] args) {
        int[] Arr = read_array(8);
        System.out.println("MODE: " + mode(Arr));
    }
}
