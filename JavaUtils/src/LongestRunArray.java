import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;

/**
 * Divide and conquer style longest run in array
 * Input is m, followed by m number of ints in the array A.
 * Output is l length of longest run in array A and first array element in this run.
 * Author: Jason Sun 20387090
 * Date: 30/05/13
 * Time: 7:27 PM
 */
public class LongestRunArray {
    public static final boolean DEBUG = true;

    // Read m then m numbers from standard input, returning array A of size m
    public static final int[] build_array() {
        Scanner s = new Scanner(System.in);
        int m = s.nextInt();
        int n = (int) power(2,m);
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = s.nextInt();
        }
        if (DEBUG) {
            System.out.print("Read " + n + " elements: ");
            for (int i = 0; i < A.length; i++) {
                System.out.print(" " + A[i]);
            }
        }
        return A;
    }

    // Debugging: prints an array content
    public static final void array_print(int[] A) {
        System.out.print("\nARRAY:");
        for (int i = 0; i < A.length; i++) {
            System.out.print(" " + A[i]);
        }
    }

    // Helps to split array A into a smaller array from index low to high
    public static final int[] array_split(int[] A, int low, int high) {
        assert(high > low);
        int size = high - low;
        int[] smaller_A = new int[size];
        for (int i = 0; i < smaller_A.length; i++) {
            smaller_A[i] = A[low + i];
        }
        return smaller_A;
    }

    // Divide and Conquer Algorithm
    public static final int[] run(int[] given_A) {
        int half = (int) (given_A.length * 0.5);

        int[] A_left = array_split(given_A, 0, half);
        int[] A_right = array_split(given_A, half, given_A.length);
        if(DEBUG) {
            array_print(A_left);
            array_print(A_right);
        }


        int[] runner_Array = {0};
        return runner_Array;
    }

    // Main program
    public static final void main(String[] args) {
        int[] A = build_array();
        int[] longest_runner_array = run(A);

        System.out.println("\nRESULT: " + longest_runner_array.length + " " + longest_runner_array[0]);
    }
}
