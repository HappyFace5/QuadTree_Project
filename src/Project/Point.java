 public class Point implements Comparable {
    private int x_axis_co, y_axis_co;
    private String name;
    Point(){
        
    }
    public Point(int x, int y, String n) {
        x_axis_co = x;
        y_axis_co = y;
        this.name = n;
    }
    public int getY() {
        return y_axis_co;
    }
    public int getX() {
         return x_axis_co;
     }
     public String getName() {
        return name;
    }
    public String toString() {
        return getName() + ", " + x_axis_co + ", " + y_axis_co;
    }
    public boolean equals(Object other) {
        int checkX = ((Point) other).x_axis_co;
        int checkY = ((Point) other).y_axis_co;
        if (this.x_axis_co == checkX && this.y_axis_co == checkY && this.name.compareTo(((Point) other).name) == 0) {
            return true;
        } 
        else {
            return false;
        }

    }
    public int compareTo(Object o) {
        int checkX = ((Point) o).x_axis_co;
        int checkY = ((Point) o).y_axis_co;

        if (checkX == this.x_axis_co && checkY == this.y_axis_co) {
            return 0;
        } else {
            return -1;
        }
    }
}
