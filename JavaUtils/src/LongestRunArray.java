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
    // To the Marker: set DEBUG flag to true to see debug statements. It is helpful.
    public static final boolean DEBUG = true;

    // Scans m then scans n=2^m numbers from standard input, returning array of size n
    public static final int[] build_array() {
        Scanner s = new Scanner(System.in);
        int m = s.nextInt();
        int n = (int) power(2, m);
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = s.nextInt();
        }
        return A;
    }

    // Debugging: prints an array content
    public static final void array_print(int[] A) {
        assert (A.length > 0);
        System.out.print("ARRAY:");
        for (int i = 0; i < A.length; i++) {
            System.out.print(" " + A[i]);
        }
        System.out.println();
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

    // Find middle run that contains elements from both A_left and A_right
    // Note: there may not exist any runs with this property
    public static final int[] runner_middle(int[] A_left, int[] A_right) {
        // Done in O(n) by scanning to the left from end of A_left
        // and scanning to the right from beginning of A_right
        assert (A_left.length >= 1 && A_right.length >= 1);

        // Last element in A_left must be less than first element in A_right
        if (A_left[A_left.length - 1] >= A_right[0]) return new int[0];

        // Find left index starting from last to first element in A_left
        int index_left = A_left.length - 1;
        for (int i = index_left; i > 0; i--) {
            if (A_left[i - 1] >= A_left[i]) break;
            index_left--;
        }

        // Find right index starting from first to last element in A_right
        int index_right = 0;
        for (int i = 0; i < A_right.length - 1; i++) {
            if (A_right[i] >= A_right[i + 1]) break;
            index_right++;
        }

        // Combine left and right and return it
        int[] left = array_split(A_left, index_left, A_left.length);
        int[] right = array_split(A_right, 0, index_right + 1);
        return array_merge(left, right);
    }

    public static final int[] runner_merge(int[] result_left, int[] result_middle, int[] result_right) {

        // Use leftmost long run convention

        int[] result_result = result_right;
        if (result_middle.length >= result_right.length) result_result = result_middle;
        if (result_left.length >= result_middle.length) result_result = result_left;

        if (DEBUG) {
            System.out.print("Returning ");
            array_print(result_result);
            System.out.println("---");
        }

        return result_result;
    }

    // Divide and Conquer Algorithm
    public static final int[] runner(int[] given_A) {
        // If atomic, return
        if (given_A.length == 1) return given_A;

        // Otherwise divide and recurse left and right sub-arrays
        int half = (int) (given_A.length / 2);
        int[] A_left = array_split(given_A, 0, half);
        int[] A_right = array_split(given_A, half, given_A.length);
        int[] runner_left = runner(A_left);
        int[] runner_right = runner(A_right);

        // Check middle
        int[] runner_middle = runner_middle(A_left, A_right);

        // Return longest runner
        int[] longest_runner = runner_merge(runner_left, runner_middle, runner_right);
        return longest_runner;
    }

    // Main program
    public static final void main(String[] args) {
        int[] A = build_array();
        int[] longest_runner_array = runner(A);
        if (DEBUG) {
            array_print(A);
            System.out.println("\nLongest Array: ");
            array_print(longest_runner_array);
        }
        System.out.println(longest_runner_array.length + " " + longest_runner_array[0]);

    }














}
