/**
 * Student: Jason Sun 20387090
 * Date: 13/06/13
 * Time: 8:25 PM
 * Implementation of greedy algorithm for finding
 * the minimized maximum completion time of schedules.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Scheduler {

    /*Reads n then subsequent n pairs of numbers*/
    public static ArrayList<Schedule> read_schedule() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();

        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int c = scanner.nextInt();
            int m = scanner.nextInt();
            schedules.add(new Schedule(c, m));
        }
        return schedules;
    }

    /*Sorts the schedules list, calculate time for each, and returns the max time*/
    public static int process_schedules(ArrayList<Schedule> schedules) {
        int max_completion_time = 0;
        int overhead_time = 0;
        Collections.sort(schedules);
        Collections.reverse(schedules);

        for (Schedule s : schedules) {
            overhead_time += s.c;
            int current_completion_time = overhead_time + s.m;
            if (max_completion_time < current_completion_time) {
                max_completion_time = current_completion_time;
            }
        }

        return max_completion_time;
    }


    public static void main(String[] args) {
        ArrayList<Schedule> schedules = read_schedule();
        int max_time = process_schedules(schedules);
        System.out.println(max_time);
    }

    /*This declares what a Schedule entry is;
    * the c and m values, as integers
    * and implements the Comparable compareTo method*/
    public static class Schedule implements Comparable<Schedule> {
        public Integer c;
        public Integer m;

        public Schedule(int c, int m) {
            this.c = c;
            this.m = m;
        }

        @Override
        public int compareTo(Schedule o) {
            // first compare by m
            int compared_result = m.compareTo(o.m);
            // if m is the same, then compare by c.
            if (compared_result == 0) return c.compareTo(o.c);
            return compared_result;
        }
    }
}
