import java.util.Arrays;
import java.util.Scanner;

/**
 * User: Jason Sun 20387090
 * Date: 25/07/13
 * Time: 4:29 PM

 * Longest Palindromic Subsequence. Finds the longest palindromic subsequence of a given string.
 * Example: "character" has LPS "carac", and "ABCABC" has LPS "ABA".
 *
 * This problem is solved using dynamic programming.
 * Suppose a n character string contains characters s = a1, ..., an
 * Define 1 <= i <= j <= n. And define L[i,j] to be length of the LPS of (ai, ..., aj).
 *
 * L[i,j] has the following recurrence relations:
 *  case1:  1                           if j = i
 *  case2:  2                           if j = i + 1    and ai  = aj
 *  case3:  1                           if j = i + 1    and ai != aj
 *  case4:  2 + L[i+1, j-1]             if j >= i + 2   and ai  = aj
 *  case5:  max{L[i+1, j], L[i, j-1]}   if j >= i + 2   and ai != aj
 *
 * There are n iterations in the dynamic algorithm; iterations 0 to n-1.
 * For each iteration k, compute all values L[i, i+k] where 1 <= i <= n-k.
 * The final answer is L[1, n]
 */

public class LPS {

    public static void main(String[] args) {
        Instance instance = new Instance();
        instance.init();
        instance.calc();
        instance.traceback();
        instance.show();
        //instance.print();    /* Debug printout. To MARKER/TA: uncomment if you want to see implementation */
    }

    /* Instance of a LPS problem */
    static class Instance {
        boolean DB = false;                 /* DEBUG flag; set this to true to trace output */
        /* Class members */
        private String   str;               /* Will be whatever string given to us for processing */
        private int n;                      /* Number of characters in string given */
        private int[][]  L;                 /* 2D Length array */
        private char[][] C;                 /* 2D Winner characters, for traceback usage*/
        private char[][] D;                 /* 2D Directions, consisting of {<, V, /, #} (left down left&down end) */
        private String   traceback_str;     /* We build this string up on traceback */
        private boolean ran;                /* Describes whether we've ran initializations; for assertion uses */

        /* Read a line and initializes all the class members */
        public void init() {
            Scanner s = new Scanner(System.in);
            str = s.nextLine();
            str = str.toUpperCase();
            n = str.length();
            L = new int[n + 1][n + 1];
            C = new char[n + 1][n + 1];
            for (char[] aC : C) Arrays.fill(aC, '-');
            D = new char[n + 1][n + 1];
            for (char[] aD : D) Arrays.fill(aD, '-');
            traceback_str = new String();
            ran = false;
        }

        /* Prints current "instance" information in a 2D table, for debug use. */
        public void print() {
            if (ran) {
                System.out.println("Instance str = " + str);
                System.out.printf("       ");
                for (int i = 0; i < str.length(); i++)
                    System.out.print(str.charAt(i) + "   ");
                System.out.println();
                for (int i = 1; i <= str.length(); i++) {
                    System.out.print("L[" + i + "] " + str.charAt(i-1));
                    for (int j = 1; j < L[i].length; j++)
                        System.out.print(" " + L[i][j] + C[i][j] + D[i][j]);
                    System.out.println();
                }

            } else {
                System.out.println("Instance not created");
            }
        }

        /* A helper to calc(). The recurrence relation described above is implemented in this.
         * Records length L[i,j] and its corresponding winner character and direction character. */
        private void get_length(int i, int j) {
            assert (ran);
            assert (i >= 1 && i <= str.length());
            assert (j >= 1 && j <= str.length());
            assert (i <= j);

            int result;
            char c;
            char d;
            if (j == i) {                                          /* case1 */
                result = 1;
                c = str.charAt(i-1);
                d = '#';
            }
            else if (j == i + 1) {
                if (str.charAt(i-1) == str.charAt(j-1)) {          /* case2 */
                    result = 2;
                    c = str.charAt(i-1);
                    d = '<';
                }
                else {                                             /* case3 */
                    result = 1;
                    c = str.charAt(i-1);
                    d = '<';
                }
            }
            else {   /* j >= i + 2 */
                if (str.charAt(i-1) == str.charAt(j-1)) {          /* case4 */
                    result = 2 + L[i+1][j-1];
                    c = str.charAt(i-1);
                    d = '/';
                }
                else {                                             /* case5 */
                    result = Math.max(L[i+1][j], L[i][j-1]);
                    /* There could be multiple LPS of same length
                     * so we need to determine a convention of
                      * which to use upon situations like
                      * L[i+1, j] == L[i, j-1] */
                    if (L[i+1][j] > L[i][j-1]) {
                        c = str.charAt(i);
                        d = 'V';
                    }
                    else {
                        c = str.charAt(j-1);
                        d = '<';
                    }
                }
            }
            if (DB) System.out.println("get_length(" + i + "," + j +") = " + result + c);
            C[i][j] = c;
            D[i][j] = d;
            L[i][j] = result;
        }

        /* Carries out calculations (iteration of k and i) on the given string */
        public void calc() {
            assert (!ran);
            ran = true;
            n = str.length();
            for (int k = 0; k < n; k++) {
                for (int i = 1; i <= n-k; i++) {
                    get_length(i, i+k);
                }
            }
        }

        /* Finds the traceback string */
        public void traceback() {
            assert (ran);
            traceback(1,n);
            String copy = traceback_str;

            for (int i = copy.length() - 2; i >= 0; i--)
                traceback_str += copy.charAt(i);
            if (DB) System.out.println("traceback string: " + traceback_str);
        }

        /* Internal recursion call for traceback() */
        private void traceback(int i, int j) {
            assert (ran);
            /* always start at 1, n */
            char currentchar = C[i][j];
            char direction = D[i][j];

            if (DB)
                System.out.println("tb" + i + "," + j + " dirct " + direction + " char " + currentchar);

            if (direction == '<')
                traceback(i, j-1);
            if (direction == 'V')
                traceback(i+1, j);
            if (direction == '/') {
                traceback_str += String.valueOf(currentchar);
                traceback(i + 1, j - 1);
            }
            if (direction == '#') {
                traceback_str += String.valueOf(currentchar);
            }
        }

        /* Prints longest LPS length and the LPS string */
        public void show() {
            System.out.println(L[1][n]);
            System.out.println(traceback_str);
        }
    }
}
