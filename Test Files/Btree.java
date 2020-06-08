public class Btree {
    Node root = null;

    public void insert(String key) {
        if (root == null) {
            root = new Node();
            root.addKey(key);
        } else {
            Node n = root;
            boolean hasValue = n.keyListManager(key);
            while (n.n1 != null) {
                hasValue = n.keyListManager(key);
                if(!hasValue) {
                    n = n.findNextNode(key); //goes down the tree to find the appropriate leaf node for the key
                }
            }
            if(!hasValue){
                n.addKey(key);
            }
            if (n.isfull()) {
                split(n);
            } else {
                while (n.parent != null) {
                    n = n.parent;
                }
                root = n;
            }
        }
    }

    public boolean find(String key, Node n) {
        if (root == null) {
            return false;
        } else {
            if (n.find(key)) {
                return true;
            }
            if (!n.isLeaf()) {
                n = n.findNextNode(key);
                return find(key, n);
            } else {
                return false;
            }
        }
    }

    public void split(Node n) {
        Node top, left, right;

        left = new Node();
        right = new Node();
        top = new Node();

        left.addKey(n.k1.key);
        left.n1 = n.n1;
        left.n2 = n.n2;
        right.addKey(n.k3.key);
        right.addKey(n.k4.key);
        right.n1 = n.n3;
        right.n2 = n.n4;
        right.n3 = n.n5;

        boolean isRoot = (n.parent == null);

        if (((n.n1 != null)) && isRoot) {
            top.addKey(n.k2.key);

            left.n1 = n.n1;
            left.n2 = n.n2;
            right.n1 = n.n3;
            right.n2 = n.n4;
            right.n3 = n.n5;

            left.n1.parent = left;
            left.n2.parent = left;

            right.n1.parent = right;
            right.n2.parent = right;
            right.n3.parent = right;

            left.parent = top;
            right.parent = top;
            top.n1 = left;
            top.n2 = right;

            top.parent = null;

            root = top;

        } else {

            if (isRoot) {

                top.addKey(n.k2.key);
                left.parent = top;
                right.parent = top;

                top.n1 = left;
                top.n2 = right;

                top.parent = null;

                root = top;

            } else {

                n.parent.addKey(n.k2.key);
                int nodeNum = n.parent.currentKeyPositionFinder(n.k2.key);

                if (n.n1 != null) {
                    left.n1.parent = left;
                }
                if (n.n2 != null) {
                    left.n2.parent = left;
                }
                if (n.n3 != null) {
                    right.n1.parent = right;
                }
                if (n.n4 != null) {
                    right.n2.parent = right;
                }
                if (n.n5 != null) {
                    right.n3.parent = right;
                }

                n = n.parent;
                left.parent = n;
                right.parent = n;

                switch (nodeNum) {
                    case 1:
                        if (n.n2 != null) {
                            n.shiftNodesright(1);
                        }
                        n.n1 = left;
                        n.n2 = right;
                        break;

                    case 2:
                        if (n.n3 != null) {
                            n.shiftNodesright(2);
                        }
                        n.n2 = left;
                        n.n3 = right;
                        break;

                    case 3:
                        if (n.n4 != null) {
                            n.shiftNodesright(3);
                        }
                        n.n3 = left;
                        n.n4 = right;
                        break;

                    case 4:
                        n.n4 = left;
                        n.n5 = right;
                        break;

                    default:
                        System.out.println("broke");
                        break;
                }


                if (n.isfull()) {
                    split(n);
                } else {
                    while (n.parent != null) {
                        n = n.parent;
                    }

                    root = n;
                }
            }
        }
    }
}