package org.ip.primitives;
import java.util.Scanner;
public class Drawer {

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        /*
         * 20 16
9 6 5
16 14 8 14
         */
        int w = 20;
        int h = 16;
        int circleX = 9;
        int circleY = 6;
        int r = 5;
        int x1 = 16;
        int y1 = 14;
        int x3 = 8;
        int y3 = 14;
        // your code goes here
        
        Rect rect = new Rect(new Point(x1,y1), new Point(x3,y3));
        Line[] lines = getLines(rect);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                char c = isWithinCircle(x,y,circleX,circleY,r) || isWithinSquare(x,y,lines) ? '#' : '.';
                System.out.print(c);
            }
            System.out.println("");
        }
        
    }
    public static final double EPSILON = 0.00001;
    public static Line[] getLines(Rect rect) {
        Line[] lines = new Line[4];
        lines[0] = getLine(rect.A,rect.B);
        lines[1] = getLine(rect.B,rect.C);
        lines[2] = getLine(rect.C,rect.D);
        lines[3] = getLine(rect.D,rect.A);
        return lines;
    }
    public static class Point {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x=x;this.y=y;
        }
        public String toString() {
            return "Point:" + this.x + "," + this.y;
        }
    }
    public static class Rect {
        public Point A;
        public Point B;
        public Point C;
        public Point D;
        public Rect(Point p1, Point p3) {
            int xc = (p1.x + p3.x)/2, yc = (p1.y + p3.y)/2;    // Center point
            int xd = (p1.x - p3.x)/2, yd = (p1.y - p3.y)/2  ;    // Half-diagonal

            int x2 = xc - yd, y2 = yc + xd;    // Third corner
            int x4 = xc + yd, y4 = yc - xd;    // Fourth corner
            this.A = p1;
            this.C = p3;
            this.B = new Point(x2,y2);
            this.D = new Point(x4,y4);
        }
        public String toString() {
            return "Rect:[" +this.A.toString() + "," + this.B.toString() + "," + this.C.toString() + "," + this.D.toString() + "]";
        }
    }
    public static Vector vector(Point p1, Point p2) {
        return new Vector(p2.x - p1.x, p2.y-p1.y);
    }

    public static int dot(Vector u, Vector v) {
        return u.x * v.x + u.y * v.y; 
    }
    public static Line getLine(Point p1, Point p2) {
        int A = -(p2.y - p1.y);
        int B = p2.x - p1.x;
        int C = -(A * p1.x + B * p1.y);
        return new Line(A,B,C);
    }
    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    public static boolean isWithinCircle(int x, int y, int circleX, int circleY, int r) {
        return distance(x,y,circleX,circleY) - r < EPSILON;
    }
    public static class Line {
        public int a;
        public int b;
        public int c;
        public Line(int a, int b, int c) {this.a = a; this.b = b;this.c = c;}
        public int compute(int x, int y){return this.a*x+this.b*y+c;}
    }
    public static class Vector {
        public int x;
        public int y;
        public Vector(int x, int y) {this.x = x; this.x = y;}
        public int compute(int x, int y){return this.x*x+this.y*y;}
    }
    public static boolean isWithinSquare(Point m, Rect r) {
        Vector AB = vector(r.A, r.B);
        Vector AM = vector(r.A, m);
        Vector BC = vector(r.B, r.C);
        Vector BM = vector(r.B, m);
        int dotABAM = dot(AB, AM);
        int dotABAB = dot(AB, AB);
        int dotBCBM = dot(BC, BM);
        int dotBCBC = dot(BC, BC);
        return 0 <= dotABAM && dotABAM <= dotABAB && 0 <= dotBCBM && dotBCBM <= dotBCBC;
    }
    public static boolean isWithinSquare(int x, int y, Line[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].compute(x,y) < -EPSILON) return false;
        }
        return true;
    }
}
