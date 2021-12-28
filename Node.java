package prereqchecker;


/**
 * Class implements a Node with a data value storing the courseID and a next pointer to the next node
 * Nodes are apart of LinkedLists which will be tied to specifc indeces of a hashMap
 */
public class Node {
    public String data; //stores course
    public Node left;
    public Node right;
    public Node check; //holds additional preReq for ValidPrereq class


    public Node(){
        data = null;
        left = null;
        right = null;
        check = null;
    }

    public Node(String d, Node l, Node r, Node c){ //initializes a Node
        data = d;
        left = l;
        right = r;
        check = c;
    }

    public void setLeft(Node left){ //sets left node
        this.left =left;
    }

    public void setRight(Node right){ //sets right node
        this.right = right;
    }

    public void setData(String data){ //sets Data to input
        this.data = data;
    }
    public void setCheck(Node check){ //set check
        this.check = check;
    }

    public Node getLeft(){ //gets left Node
        return left;
    }

    public Node getRight(){ //gets right node
        return right;
    }

    public String getData(){
        return data;
    }

    public Node check(){
        return check;
    }
    public boolean isEmpty(){
        return data.isEmpty();
    }

}
