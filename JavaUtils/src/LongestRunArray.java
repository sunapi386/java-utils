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
        int n = (int) power(2, m);
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
        assert (A.length > 0);
        System.out.print("\nARRAY:");
        for (int i = 0; i < A.length; i++) {
            System.out.print(" " + A[i]);
        }
    }

    // Split array A into a smaller array from index low to high
    public static final int[] array_split(int[] A, int low, int high) {
        assert (high > low);
        int size = high - low;
        int[] smaller_A = new int[size];
        for (int i = 0; i < size; i++) {
            smaller_A[i] = A[low + i];
        }
        return smaller_A;
    }

    // Merges arrays A and B to C
    public static final int[] array_merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        for (int i = 0; i < A.length; i++) {
            C[i] = A[i];
        }
        for (int i = 0; i < B.length; i++) {
            C[i + A.length] = B[i];
        }
        return C;
    }

    // Find longest run that contains elements from both A_left and A_right
    // Note: there may not exist any runs with this property
    public static final int[] runner_merge(int[] A_left, int[] A_right) {
        // Done in O(n) by scanning to the left from end of A_left
        // and scanning to the right from beginning of A_right
        assert (A_left.length >= 1 && A_right.length >= 1);

        // Last element in A_left must be less than first element in A_right
        if (A_left[A_left.length - 1] >= A_right[0]) return new int[0];

        // Find left index starting from last element in A_left
        int index_left = A_left.length - 1;
        for (int i = index_left; i > 0; i--) {
            if (A_left[i - 1] >= A_left[i]) break;
            index_left--;
        }

        // Find right index starting from first element in A_right
        int index_right = 0;
        for (int i = 0; i < A_right.length - 1; i++) {
            if (A_right[i] >= A_right[i + 1]) break;
            index_right++;
        }

        assert (index_right < A_right.length);
        // Combine left and right and return it
        int[] A_middle = new int[A_left.length - index_left + index_right];
        A_middle = array_merge(array_split(A_left, index_left, A_left.length), array_split(A_right, 0, index_right));
        return A_middle;
    }

    // Divide and Conquer Algorithm
    public static final int[] runner(int[] given_A) {
        // Return atomic arrays
        if (given_A.length == 1) return given_A;

        // Otherwise divide
        int half = (int) (given_A.length / 2);
        // Make left and right sub-arrays
        int[] A_left = array_split(given_A, 0, half);
        int[] A_right = array_split(given_A, half, given_A.length);

        // Recurse left and right array
        int[] result_left = runner(A_left);
        int[] result_right = runner(A_right);
        int[] result_middle = runner_merge(result_left, result_right);
        if (DEBUG) {
            array_print(result_left);
            array_print(result_middle);
            array_print(result_right);
        }

        // PROBABLY WRONG
        int len_l = result_left.length;
        int len_r = result_right.length;
        int len_m = result_middle.length;
        if (len_m > len_l && len_m > len_r) {
            return result_middle;
        } else {
            return given_A;
        }



    }

    // Main program
    public static final void main(String[] args) {
        int[] A = build_array();
        int[] longest_runner_array = runner(A);

        System.out.println("\nRESULT: " + longest_runner_array.length + " " + longest_runner_array[0]);
        array_print(longest_runner_array);
    }
}
