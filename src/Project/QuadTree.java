public class QuadTree {     
    private QuadNode flynode;
    private QuadNode root; 
    private int count_Elements; 
    public QuadTree() {
        flynode = new EmptyNode();
        root = flynode;
        count_Elements = 0;
    }
    public void insert(Point element) {
        root = root.add(element, 0, 0, 1024);
        count_Elements++;
    }
    public Point remove(int x, int y) {
        Point[] deleted = {null};
        root = root.remove(x, y, 0, 0, 1024, deleted);
        if (deleted[0] != null) {
            count_Elements--;
            return deleted[0];
        }
        return null;
    }
    public Point remove(Point toRemove) {
        Point[] deleted = {null};
        root = root.remove(toRemove, 0, 0, 1024, deleted);
        count_Elements--;
        return deleted[0];
    }
    public LinkedList<String> duplicates() {
        LinkedList<String> res = new LinkedList<String>();
        root.findDuplicates(res);
        return res;
    }
    public LinkedList<Point> regionSearch(int x, int y, int width, int height, int[] numOfVisits) {
        LinkedList<Point> res = new LinkedList<Point>();
        res = root.regionSearch(x, y, width, height,
                res, 0, 0, 1024, numOfVisits);

        return res;
    }
    public LinkedList<String> dump() {
        int[] numOfVisits = {0};
        LinkedList<String> list = new LinkedList<String>();
        if (count_Elements == 0)
        {
            String temp = "Node at 0, 0, 1024: Empty";
            list.insert(temp);
            temp = "QuadTree Size: 1 QuadTree Nodes Printed.";
            list.insert(temp);
        }
        else
        {
            root.getContents(0, 0, 1024, list, 0, numOfVisits);
            list.insert("QuadTree Size: " + numOfVisits[0] + " QuadTree Nodes Printed.");
        }

        return list;
    }
    public class EmptyNode implements QuadNode { 
        public EmptyNode() {

        }
        public QuadNode add(Point element, int currentX, int currentY, int split) {
            LeafNode temp = new LeafNode();
            temp.add(element, currentX, currentY, split);
            return temp;
        }
        public LinkedList<Point> find(int x, int y, int currentX, int currentY, int split, LinkedList<Point> result) {
            return result;
        }

       
        public LinkedList<String> getContents(int currentX, int currentY, int split, LinkedList<String> list, int numOfIndents, int[] numOfVisits) {
            String temp = "";
            for (int i = 0; i < numOfIndents; i++) {
                temp = temp + "  ";
            }
            temp = temp + "Node at " + ((Integer) currentX).toString() + ", " + ((Integer) currentY).toString() + ", " + ((Integer) split).toString() + ": Empty";
            list.insert(temp);
            numOfVisits[0]++;
            return list;
        }

        
        public LinkedList<String> findDuplicates(LinkedList<String> list) {
            return list;
        }

        public QuadNode remove(int x, int y, int currentX, int currentY, int split, Point[] removed) {
            return this;
        }

        
        public QuadNode remove(Point toRemove, int currentX, int currentY, int split, Point[] removed) {
            return this;
        }

        public LinkedList<Point> regionSearch(int x, int y, int width, int height, LinkedList<Point> result, int currentX, int currentY, int split, int[] numOfVisits) {
            numOfVisits[0]++;
            return result;
        }

    }
    public class LeafNode implements QuadNode {
        public LinkedList<Point> list;
        public LeafNode() {
            list = new LinkedList<Point>();
        }
        public LeafNode(LinkedList<Point> newList) {
            list = newList;
        }
        public QuadNode add(Point element, int currentX, int currentY, int split) {
            if (list.getSize() == 0) {
                list.insert(element);
                return this;
            } else if (list.getSize() < 3) {
                list.insert(element);
                return this;
            } else if (list.getSize() >= 3) {
                int same = 0;
                for (int i = 0; i < list.getSize(); i++) {
                    if ((list.get(i)).compareTo(element) == 0) {
                        same++;
                    }

                }

                if (same == list.getSize()) {
                    list.insert(element);
                    return this;
                }
            }

            QuadNode newInternal = new InternalNode();
            for (int i = 0; i < list.getSize(); i++) {
                newInternal = newInternal.add(list.get(i), currentX, currentY, split);
            }
            newInternal = newInternal.add(element, currentX, currentY, split);
            return newInternal;

        }
        public LinkedList<String> getContents(int currentX, int currentY, int split, LinkedList<String> result, int numOfIndents, int[] numOfVisits) {
            String indents = "";
            String temp = "";
            for (int i = 0; i < numOfIndents; i++) {
                indents = indents + "  ";
            }
            temp = temp + indents;
            temp = temp + "Node at " + ((Integer) currentX).toString() + ", " + ((Integer) currentY).toString() + ", " + ((Integer) split).toString() + ":";
            result.insert(temp);
            temp = "";
            for (int i = 0; i < this.list.getSize(); i++) {

                temp = indents + "(" + this.list.get(i).toString() + ")";
                result.insert(temp);
            }
            numOfVisits[0]++;
            return result;
        }
        public LinkedList<String> findDuplicates(LinkedList<String> result) {

            for (int i = 0; i < this.list.getSize(); i++) {
                for (int j = i + 1; j < this.list.getSize(); j++) {
                    Point temp1 = this.list.get(i);
                    Point temp2 = this.list.get(j);
                    if (temp1.compareTo(temp2) == 0) {

                        String temp = ((Integer) temp1.getX()).toString() + ", " + ((Integer) temp1.getY()).toString();

                        if (result.exists(temp) == false) {
                            result.insert(temp);
                        }
                    }
                }

            }

            return result;
        }
        public QuadNode remove(int x, int y, int currentX, int currentY, int split, Point[] removed) {
            int i = 0;
            for (i = 0; i < list.getSize(); i++) {
                if (x == list.get(i).getX() && y == list.get(i).getY()) {removed[0] = list.get(i);
                    list.delete(i);
                    break;
                }
            }
            if (list.getSize() == 0) {

                return flynode;
            } else {
                return this;
            }
        }
        public QuadNode remove(Point toRemove, int currentX, int currentY, int split, Point[] removed) {
            int index;
            for (index = 0; index < list.getSize(); index++) {
                if (toRemove == list.get(index)) {
                    removed[0] = list.get(index);
                    list.delete(index);
                    break;
                }
            }
            if (list.getSize() == 0) {

                return flynode;
            }
            else {
                return this;
            }
        }
        public LinkedList<Point> regionSearch(int x, int y, int height, int width, LinkedList<Point> result, int currentX, int currentY, int split, int[] numOfVisits) {

            numOfVisits[0]++;

            for (int i = 0; i < list.getSize(); i++) {
                int tempX = (list.get(i)).getX();
                int tempY = (list.get(i)).getY();

                if (tempX >= x && tempX < x + width && tempY >= y && tempY < y + height) {
                    result.insert(list.get(i));
                }
            }

            return result;

        }
    }
    public class InternalNode implements QuadNode {

        private QuadNode nW;
        private QuadNode nE;
        private QuadNode sW;
        private QuadNode sE;
        public InternalNode() {
            nW = (QuadNode) flynode;
            nE = (QuadNode) flynode;
            sW = (QuadNode) flynode;
            sE = (QuadNode) flynode;
        }
        public QuadNode add(Point element, int currentX, int currentY, int split) {

            int bound = split / 2;
            int xBound = currentX + bound;
            int yBound = currentY + bound;
            if (element.getX() < xBound && element.getY() < yBound) {

                nW = nW.add(element, currentX, currentY, bound);
                return this;
            } else if (element.getX() >= xBound && element.getY() < yBound) {

                nE = nE.add(element, xBound, currentY, bound);
                return this;
            } else if (element.getX() < xBound && element.getY() >= yBound) {

                sW = sW.add(element, currentX, yBound, bound);
                return this;
            } else {

                sE = sE.add(element, xBound, yBound, bound);
                return this;
            }

        }
        public LinkedList<String> getContents(int currentX, int currentY, int bound, LinkedList<String> list, int numOfIndents, int[] numOfVisits) {
            int split = bound / 2;
            String temp = "";
            for (int i = 0; i < numOfIndents; i++) {
                temp = temp + "  ";
            }
            temp = temp + "Node at " + ((Integer) currentX).toString() + ", " + ((Integer) currentY).toString() + ", " + ((Integer) bound).toString() + ": Internal";
            list.insert(temp);
            list = nW.getContents(currentX, currentY, split, list, numOfIndents + 1, numOfVisits);
            list = nE.getContents(currentX + split, currentY, split, list, numOfIndents + 1, numOfVisits);
            list = sW.getContents(currentX, currentY + split, split, list, numOfIndents + 1, numOfVisits);
            list = sE.getContents(currentX + split, currentY + split, split, list, numOfIndents + 1, numOfVisits);
            numOfVisits[0]++;
            return list;
        }
        public LinkedList<String> findDuplicates(LinkedList<String> list) {

            if (nW != flynode) {
                list = nW.findDuplicates(list);
            }

            if (nE != flynode) {
                list = nE.findDuplicates(list);
            }

            if (sW != flynode) {
                list = sW.findDuplicates(list);
            }

            if (sE != flynode) {
                list = sE.findDuplicates(list);
            }

            return list;
        }
        public QuadNode remove(int x, int y, int currentX, int currentY, int split, Point[] removed) {
            int half = split / 2;

            if (x < currentX + half && y < currentY + half) {
                nW = nW.remove(x, y, currentX, currentY, half, removed);
            } else if (x >= currentX + half && y < currentY + half) {
                nE = nE.remove(x, y, currentX + half, currentY, half, removed);
            } else if (x < currentX + half && y >= currentY + half) {
                sW = sW.remove(x, y, currentX, currentY + half, half, removed);
            } else {
                sE = sE.remove(x, y, currentX + half, currentY + half, half, removed);
            }

            return trim();
        }

        public QuadNode remove(Point toRemove, int currentX, int currentY, int split, Point[] removed) {
            int half = split / 2;
            int x = toRemove.getX();
            int y = toRemove.getY();

            if (x < currentX + half && y < currentY + half) {
                nW = nW.remove(toRemove, currentX, currentY, half, removed);
            } else if (x >= currentX + half && y < currentY + half) {
                nE = nE.remove(toRemove, currentX + half, currentY, half, removed);
            } else if (x < currentX + half && y >= currentY + half) {
                sW = sW.remove(toRemove, currentX, currentY + half, half, removed);
            } else {
                sE = sE.remove(toRemove, currentX + half, currentY + half, half, removed);
            }

            return trim();
        }
        private QuadNode trim() {

            if (nW.getClass().getName().compareTo("QuadTree$LeafNode") == 0 && nE == flynode && sW == flynode && sE == flynode) {
                return nW;
            } else if (nW == flynode && nE.getClass().getName().compareTo("QuadTree$LeafNode") == 0 && sW == flynode && sE == flynode) {
                return nE;
            } else if (nW == flynode && nE == flynode && sW.getClass().getName().compareTo("QuadTree$LeafNode") == 0 && sE == flynode) {
                return sW;
            } else if (nW == flynode && nE == flynode && sW == flynode && sE.getClass().getName().compareTo("QuadTree$LeafNode") == 0) {
                return sE;
            } else {

                LinkedList<Point> results = new LinkedList<Point>();

                if (nW.getClass().getName().compareTo("QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) nW).list.getSize(); i++) {
                        results.insert(((LeafNode) nW).list.get(i));
                    }

                }
                if (nE.getClass().getName().compareTo("QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) nE).list.getSize(); i++) {
                        results.insert(((LeafNode) nE).list.get(i));
                    }

                }
                if (sW.getClass().getName().compareTo("QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) sW).list.getSize(); i++) {
                        results.insert(((LeafNode) sW).list.get(i));
                    }

                }
                if (sE.getClass().getName().compareTo("QuadTree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode) sE).list.getSize(); i++) {
                        results.insert(((LeafNode) sE).list.get(i));
                    }

                }

                if (results.getSize() == 3) {
                    return new LeafNode(results);
                }
                return this;
            }

        }
        public LinkedList<Point> regionSearch(int x, int y, int width, int height, LinkedList<Point> result, int currentX, int currentY, int split, int[] numOfVisits) {
            numOfVisits[0]++;
            int half = split / 2;
            int xBound = currentX + half;
            int yBound = currentY + half;
            int yMax = y + height - 1;
            int xMax = x + width - 1;
            LinkedList<Point> found = result;

            if (xBound > x && yBound > y)
                found = nW.regionSearch(x, y, width, height, found, currentX, currentY, half, numOfVisits);

            if (xBound <= xMax && yBound > y)
                found = nE.regionSearch(x, y, width, height, found, xBound, currentY, half, numOfVisits);

            if (xBound > x && yBound <= yMax)
                found = sW.regionSearch(x, y, width, height, found, currentX, yBound, half, numOfVisits);

            if (xMax >= xBound && yMax >= yBound)
                found = sE.regionSearch(x, y, width, height, found, xBound, yBound, half, numOfVisits);

            return found;
        }

    }
}
