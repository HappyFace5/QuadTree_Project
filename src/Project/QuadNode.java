
public interface QuadNode {

    public abstract QuadNode add(Point element, int currentX, int currentY, int split);

    public abstract LinkedList<String> getContents(int currentX, int currentY, int split, LinkedList<String> list, int numOfIndents, int[] numOfVisits);

    public abstract LinkedList<String> findDuplicates(LinkedList<String> list);

    public abstract QuadNode remove(int x, int y, int currentX, int currentY, int split, Point[] removed);

    public abstract QuadNode remove(Point toRemove, int currentX, int currentY, int split, Point[] removed);

    public abstract LinkedList<Point> regionSearch(int x, int y, int width, int height, LinkedList<Point> result, int currentX, int currentY, int split, int[] numOfVisits);

}
