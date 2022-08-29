// On my honor:
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or unmodified.
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
// - I have not discussed coding details about this project with
// anyone other than the my partner, instructor, ACM/UPE tutors
// or the TAs assigned to this course.
// I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.





import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Point2 {

    public static List<String> Split(String input) {
        List<String> list = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                if (temp.length() != 0) {
                    list.add(temp);
                    temp = "";
                }

            } else {
                temp += input.charAt(i);
            }
        }
        if (temp.length() != 0) {
            list.add(temp);
        }
        return list;
    }

    public static void main(String[] args) {
        LinkedList<String> result;
        String filename = args[0], command, point_name = "";
        Database database = new Database();
        Scanner cin = null;
        Point temp_point = new Point();
        Dictionary visit = new Hashtable();
        int point_x = 0, point_y = 0, point_w = 0, point_h = 0;
        String input = "";
        try {
            cin = new Scanner(new File(filename));
            while (cin.hasNextLine()) {
                input = input.concat(cin.nextLine());
                List<String> line = Split(input);

                if(line.size()==0)continue;
                command = line.get(0);
                point_name = "";

                if ("insert".equals(command)) {
                    point_name = "";
                    boolean che=false;
                    for (int i = 1; i < line.size() - 2; i++) {
                        if(che)point_name+= " ";
                        point_name += line.get(i);
                        che=true;
                    }
                    point_x = Integer.parseInt(line.get(line.size() - 2));
                    point_y = Integer.parseInt(line.get(line.size() - 1));

                    Point temp = new Point(point_x, point_y, point_name);

                    if (visit.get(temp.toString()) != null) {
                        System.out.println("Point REJECTED: (" + temp.toString() + ")");
                    } else {
                        if (database.pointCheck(point_name, point_x, point_y)) {
                            System.out.println("Point Inserted: (" + temp.toString() + ")");
                            database.insert(temp);
                            visit.put(temp.toString(), true);
                        } else {
                            System.out.println("Point REJECTED: (" + temp.toString() + ")");
                        }
                    }
                } else if ("search".equals(command)) {
                    point_name = "";
                    boolean che=false;
                    for (int i = 1; i < line.size(); i++) {
                        if(che)point_name+= " ";
                        point_name += line.get(i);
                        che=true;
                    }
                    result = database.search(point_name);
                    for (int i = 0; i < result.getSize(); i++) {
                        System.out.println(result.get(i));
                    }
                } else if ("regionsearch".equals(command)) {
                    point_x = Integer.parseInt(line.get(1));
                    point_y = Integer.parseInt(line.get(2));
                    point_w = Integer.parseInt(line.get(3));
                    point_h = Integer.parseInt(line.get(4));
                    if (point_w > 0 && point_h > 0) {
                        result = database.regionSearch(point_x, point_y, point_w, point_h);
                        for (int i = 0; i < result.getSize(); i++) {
                            System.out.println(result.get(i));
                        }
                    } else {
                        System.out.println("Rectangle Rejected: (" + point_x + ", " + point_y + ", " + point_w + ", " + point_h + ")");
                    }
                } else if ("duplicates".equals(command)) {
                    System.out.println("Duplicate Points:");
                    result = database.duplicates();
                    for (int i = 0; i < result.getSize(); i++) {
                        System.out.println("(" + result.get(i) + ")");
                    }
                } else if ("remove".equals(command)) {
                    if (line.size() == 3 && database.isNumber(line.get(line.size() - 2)) && database.isNumber(line.get(line.size() - 2))) {
                        point_x = Integer.parseInt(line.get(line.size() - 2));
                        point_y = Integer.parseInt(line.get(line.size() - 1));
                        if (!database.pointCheck(point_x, point_y)) {
                            System.out.println("Point Rejected: (" + point_x + ", " + point_y + ")");
                        } else {
                            temp_point = database.removeByPoint(point_x, point_y);
                            if (temp_point == null) {
                                System.out.println("point Not Found: (" + point_x + ", " + point_y + ")");
                            } else {
                                System.out.println("Point (" + temp_point.toString() + ") Removed");
                                visit.remove(temp_point.toString());
                            }
                        }
                    } else {
                        point_name = "";
                        boolean che=false;
                        for (int i = 1; i < line.size() ; i++) {
                            if(che)point_name+= " ";
                            point_name += line.get(i);
                            che=true;
                        }
                        temp_point = database.removeByName(point_name);
                        if (temp_point == null) {
                            System.out.println("point Not Removed: " + point_name);
                        } else {
                            System.out.println("Point (" + temp_point.toString() + ") Removed");
                            visit.remove(temp_point.toString());

                        }
                    }
                } else if ("dump".equals(command)) {
                    result = database.dump();
                    for (int i = 0; i < result.getSize(); i++) {
                        System.out.println(result.get(i));
                    }
                }
                input = "";
            }
        } catch (FileNotFoundException error) {
            error.printStackTrace();
        }

    }

}
