public class Database {
    private SkipList<String, Point> list;
    private QuadTree tree;
    public Database() {
        list = new SkipList<String, Point>();
        tree = new QuadTree();
    }
    public boolean pointCheck(String s, int x, int y) {
        boolean check = ((s.charAt(0) >= 'a' && s.charAt(0) <= 'z') || (s.charAt(0) >= 'A' && s.charAt(0) <= 'Z'));
        check &= (x < 1024 && x >= 0 && y < 1024 && y >= 0);
        for (int i = 1; i < s.length(); i++) {
            check &= ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || (s.charAt(i) == '_') || (s.charAt(i) >= '0' && s.charAt(i) <= '9'));
        }
        return check;
    }
    public boolean pointCheck(int x, int y) {
        return (x < 1024 && x >= 0 && y < 1024 && y >= 0);
    }
    public  static boolean isNumber(String test) {

        try {
            Integer.parseInt(test);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public void insert(Point element) {
        list.insert(element.getName(), element);
        tree.insert(element);
    }
    public LinkedList<String> search(String name) {
        SkipNode<String, Point>[] search_res = list.find(name);
        LinkedList<String> toReturn = new LinkedList<String>();
        if (search_res.length == 1) {
            toReturn.insert("Point Not Found: " + name);
        } else {
            for (int i = 0; i < search_res.length; i++) {
                if (search_res[i] != null) {
                    toReturn.insert("Point Found (" + search_res[i].key() + ", " + search_res[i].element().getX() + ", " + search_res[i].element.getY() + ")");
                }
            }
        }
        return toReturn;
    }
    public LinkedList<String> regionSearch(int x, int y, int width, int height) {
        int[] numOfVisits = {0};
        LinkedList<Point> search_res = tree.regionSearch(x, y, width, height, numOfVisits);
        LinkedList<String> res = new LinkedList<String>();
        res.insert("Points Intersecting Region: (" + x + ", " + y + ", " + width + ", " + height + ")");
        for (int i = 0; i < search_res.getSize(); i++) {
            res.insert("Point Found: (" + search_res.get(i).toString() + ")");
        }
        res.insert(numOfVisits[0] + " QuadTree Nodes Visited");
        return res;
    }
    public Point removeByPoint(int x, int y) {

        Point P_toRemove = tree.remove(x, y);
        if (P_toRemove == null) {
            return null;
        }
        list.remove(P_toRemove);
        return P_toRemove;
    }
    public Point removeByName(String name) {
        SkipNode<String, Point> ll_res = list.remove(name);
        if (ll_res != null) {
            tree.remove(ll_res.element);
            return ll_res.element;
        } else {
            return null;
        }
    }
    public LinkedList<String> dump() {
        LinkedList<String> result = new LinkedList<String>();
        result.insert("SkipList Dump:");
        SkipNode<String, Point>[] result2 = list.dump();
        for (int i = 0; i < result2.length; i++) {

            if (result2[i].element != null) {

                result.insert("level: " + (result2[i].forward.length) + " Value: (" + result2[i].element.toString() + ")");
            } else {
                result.insert("level: " + (result2[i].forward.length) + " Value: " + result2[i].element);
            }

        }
        result.insert("The SkipList's Size is: " + (result2.length - 1));

        LinkedList<String> treeDump = tree.dump();
        result.insert("QuadTree Dump:");
        for (int i = 0; i < treeDump.getSize(); i++) {
            result.insert(treeDump.get(i));
        }
        return result;
    }
    public LinkedList<String> duplicates() {
        return tree.duplicates();
    }
}