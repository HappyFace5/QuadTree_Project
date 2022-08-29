public class LinkedList<elm> {
    private int size;
    private Node node;

    public LinkedList() {
        size = 0;
        node = null;
    }
    private class Node {

        Node next; 
        elm element; 


        public Node(elm element) {
            this.element = element;
            next = null;
        }


        public elm getElement() {
            return element;
        }


        public void setElement(elm element) {
            this.element = element;
        }


        public Node getNext() {
            return next;
        }


        public void setNext(Node nextNode) {
            next = nextNode;
        }
    }
    public elm get(int index) {
        Node current = node;
        elm temp = current.element;
        if (index == 0) {
            return temp;
        } else {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            temp = current.getElement();
        }
        return temp;
    }
    public int getSize() {
        return size;
    }
    public boolean exists(elm element) {
        if (size == 0)
        {
            return false;
        }
        else
        {
            Node current = node;

            while (true) {
                if(current==null)
                {
                    break;
                }
                if (current.getElement().equals(element)) {
                    return true;
                }

                current = current.getNext();
            }
        }
        return false;
    }
    public void insert(elm element) {
        Node temp = new Node(element);
        if (node == null) {
            node = temp;
        }
        else {
            Node current = node;


            while (current.getNext() != null)
                current = current.getNext();

            current.setNext(temp);

        }
        size++;
    }
    public boolean delete(int index) {
        if (index == 0) {
            node = node.getNext();
            size--;
            return true;
        }
        Node curr = node;

        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        if (curr.getNext() == null) {
            curr.setNext(null);
        } else {
            curr.setElement(curr.getNext().getElement());
            curr.setNext(curr.getNext().getNext());
        }
        size--;
        return true;

    }

}
