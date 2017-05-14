package org.ip.primitives;

import java.util.Scanner;

public class DayOfProgrammer {
	public static final int LEAP_YEAR_DAY_OF_PROGRAMMER = (256-(31+29+31+30+31+30+31+31));
    public static final int DAY_OF_PROGRAMMER = (256-(31+28+31+30+31+30+31+31));
    public static final int SPECIAL_DAY_OF_PROGRAMMER = (256-(31+15+31+30+31+30+31+31));
    public static final int MONTH_DAY_OF_PROGRAMMER = 9;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int y = in.nextInt();
            int day = y < 1918 ? julian(y) : y == 1918 ? special1918(y) : gregorian(y);
            System.out.format("%02d.%02d.%04d%n", day, MONTH_DAY_OF_PROGRAMMER,y);
        }
    }
    public static boolean isLeapYearJulian(int y) {
        return y % 4 == 0;
    }
    public static boolean isLeapYearGregorian(int y) {
        return y % 400 == 0 || (y % 4 == 0 && y % 100 != 0);
    }
    public static int julian(int y) {
        return isLeapYearJulian(y) ? LEAP_YEAR_DAY_OF_PROGRAMMER : DAY_OF_PROGRAMMER;
    }
    public static int gregorian(int y) {
        return isLeapYearGregorian(y) ? LEAP_YEAR_DAY_OF_PROGRAMMER : DAY_OF_PROGRAMMER;
    }
    public static int special1918(int y) {
        return SPECIAL_DAY_OF_PROGRAMMER; 
    }
}
