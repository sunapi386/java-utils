import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Names {
    // Takes a list of names as input, prints them sorted by last name as last, first
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Name> names = new ArrayList<Name>();

        while (in.hasNext()) {
            String first = in.next();
            if (!in.hasNext()) break;
            String last = in.next();

            names.add(new Name(first, last));
        }

        Collections.sort(names);

        for (Name n : names) {
            System.out.println(n.last + ", " + n.first);
        }
    }

    static class Name implements Comparable<Name> /* Comparable provides an order for Name */ {

        public String first;
        public String last;

        Name(String first, String last) {
            this.first = first;
            this.last = last;
        }

        /* This implements Comparable.
         * Returns a negative value for this < that, 0 for this == that, and a positive value for
         * this > that.
         * See http://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html for more details
         */
        public int compareTo(Name o) {
            int c = last.compareTo(o.last);
            if (c == 0) return first.compareTo(o.first);
            else return c;
        }
    }
}
