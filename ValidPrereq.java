package prereqchecker;
import java.util.*;
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

       // if ( args.length < 3 ) {
           // StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
           // return;
       // }
	// WRITE YOUR CODE HERE
    String adjOutputFile = "adjOutputFile.in";

    String adjInputFile = args[0];
    String preReqInputFile = args[1];
    String preReqOutputFile = args[2];

    //AdjList adj = new AdjList();
    ValidPrereq preReq = new ValidPrereq();

    ArrayList<Node> Table = new ArrayList<Node>();
    int V = 0;

    preReq.createArrayList(adjInputFile, adjOutputFile, Table);

    Graph ob = new Graph(V); //creates new graph

    preReq.makeGraph(ob,Table,V,adjInputFile,preReqInputFile,preReqOutputFile);
    
    }

    public void createArrayList(String adjInputFile, String adjOutputFile, ArrayList<Node> Table){
        //take output of store and creates Nodes for each readString
        //each index in arrayList corresp. -> line in stdOut
        AdjList adj = new AdjList();
        adj.store(adjInputFile,adjOutputFile,Table); //stores arrayList
        //ArrayList<Node> TableCopy = new ArrayList<Node>(adj.getTable(Table)); //copies Table into table copy

                //System.out.println("First course is: " + TableCopy.get(0).getData());
                //System.out.println("Second course is: " + TableCopy.get(1).getData());
                //System.out.println("Second course de preReq is: " + TableCopy.get(1).getRight().getData());


    }

    public void makeGraph(Graph ob, ArrayList<Node> Table, int V, String adjInputFile,String preReqInputFile, String preReqOutputFile){
        int count =0;
        StdIn.setFile(adjInputFile);
        V = StdIn.readInt(); //number of vertexes
        Graph ob1 = new Graph(V);
            for (Node x : Table){
                ob1.addEdge(count,x.getData());

                    if (x.getRight() != null){
                        ob1.addEdge(count,x.getRight().getData());
                    }
                    if (x.getLeft() != null){
                        ob1.addEdge(count,x.getLeft().getData());
                    }
                count++;
            }
            System.out.println("adjacent verticies are: " + ob1.adj(1));
            
            addpreReq(ob1, preReqInputFile,preReqOutputFile);
    }

    public void addpreReq(Graph ob, String preReqInputFile, String preReqOutputFile){
        //V = 10;
        StdIn.setFile(preReqInputFile);

        String advancedCourse = StdIn.readString();
        StdIn.readLine(); //skips line

        String preReqCourse = StdIn.readString();
        StdIn.readLine(); 

        //search graph for advanced course
        int index = ob.search(advancedCourse); //CALL ADJ NOT OB
        ob.addEdge(index,preReqCourse); //adds preReq to correct spot

        checkViolations(ob, advancedCourse, preReqCourse,preReqOutputFile);

    }
    /**
     * Checks the occurence of advancedCourse and preReqCourse and determines if the prospective seqeuence 
     * prevents the user from taking any classes
     */
    public boolean checkViolations(Graph ob,String advancedCourse, String preReq, String preReqOutputFile){
        StdOut.setFile(preReqOutputFile);
        int index1 = ob.search(advancedCourse); //index where AdvancedCourse occurs
        int count=0;
        int len = ob.adjSize();
        Queue<String> courses = new Queue<String>();  
        courses.enqueue(ob.adjRoot(index1)); //ad

        while (!courses.isEmpty()){ //runs thru queue as long as it contains something
            String val = courses.dequeue();
            count++;
            //StdOut.println("parent = " + val);

                if (ob.getNext(val) != null){ //left exists
                    courses.enqueue(ob.getNext(val));
                        //StdOut.println("1st enqueue: " + ob.getNext(val));
                }
                else{
                        //StdOut.println("ELSE: 1st val is: " + ob.getNext(val));
                    continue;
                }

                if (ob.getNextNext(val) != null){ //right exists
                    courses.enqueue(ob.getNextNext(val));
                        //StdOut.println("2nd enqueue: " + ob.getNextNext(val));
                }
                else{
                        //StdOut.println("ELSE: 2nd val is: " + ob.getNextNext(val));
                    continue;
                }
            
            if (count > len*3){ //constant cycle count > arrayList.length * 3
                    //StdOut.print("Cycle found -> print NO");
                StdOut.println("NO");
                return false;
            }
        }
        StdOut.println("YES");
        return true;
        }
        
        /*
    public void makeTree(String preReqInputFile, ArrayList<Node> Table, Node root){
        //read input file and create Tree of proposed prereq
        StdIn.setFile(preReqInputFile); //sets StdIn File
        Node advCourse = new Node(StdIn.readString(),null,null,null);
        StdIn.readLine();
        Node prereq = new Node(StdIn.readString(),null,null,null);
        StdIn.readLine();

        //stores proposed preReq in correct childNode
            for (Node y : Table){  
                if (root.getRight() == null){ //right is empty
                    root.setRight(new Node(y.getData(),null,null,null)); //store prereq in right
                }
                else if (root.getLeft() == null){ //left is empty
                    root.setLeft(new Node(y.getData(),null,null,null)); //store pre req in left
                }
                else if (root.getRight() != null && root.getLeft() != null){ //both are empty
                    root.setCheck(new Node(y.getData(),null,null,null)); //store preReq in extra space
                }
            }
            //Node temp = new Node(null,null,null,null);
            //traversal BFS
            for (Node z : Table){
                if (z.getData().equals(advCourse.getData())){
                    Queue<Node>  q = new LinkedList<Node>(); //create queue
                    q.add(z);
                    Node root2 = z;
                    while (!q.isEmpty()){
                        Node temp = q.remove(); //temp = z

                        //System.out.println(temp.data() + " ");

                            if (temp.right != null){
                                //lookup temp.right in arrayList
                                Node val = lookup(Table,temp.right);
                                root2.right = val;
                                q.add(val);
                                continue;
                            }
                            if (temp.check != null){
                                //lookup temp.right in arrayList
                                Node val = lookup(Table,temp.check);
                                root2.check = val;
                                q.add(val);
                                continue;
                            }
                            if (temp.left != null){
                                //lookup temp.right in arrayList
                                Node val = lookup(Table,temp.left);
                                root2.left = val;
                                q.add(val);
                                continue;
                            }
                    }
                }
            }
            //for (Node x : Table){ treeRecursion(x, advCourse, prereq, root);}
        //Make sure proposed prereq is included in tree
    }
    public Node lookup(ArrayList<Node> Table, Node temp){ //method checks if temp Node exists in arrayList and returns that Node
        for (Node s : Table){
            if (s.getData().equals(temp.getData())){
                return s;
            }
        }
        return null;
    }

    public void treeRecursion(Node x ,Node advCourse,Node pp, Node root){
        if (x.getData().equals(advCourse.getData())){ //if we found advanced course
            root = x;

            if (x.getRight() != null){ //check if right exists
                treeRecursion(x,advCourse,pp,root);
                if (x.getLeft() != null){ //check if left exists

                }
            }
        }
    }
        
    public void traverseTree(){

    }

    /*
        (-> means requires) (ignore lab prereqs and any coreqs)
    proposed : PEE2 -> DE 

    Real: PEE2 -> PEE1 + Mat251 + Phy227
            PEE1 -> Phy124 + Mat152
                Phy124 -> Mat151 + Phy123
                Mat152 -> Mat151
            Mat251 -> Mat152
                Mat152 -> Mat151
            Phy227 -> Phy124 + Mat152
                Phy124 -> Phy123
                Mat152 -> Mat151

    Real: DE -> ED
            ED -> PEE2
                PEE2 ->


    Potential solution(1)
        read input file and create Tree of proposed prereq
        read through adj arrayList and create full tree of complete prereqs
            Make sure proposed prereq is included in tree
        traverse tree
            store root
            check if root exists > 1
                if it does -> proposed prereq violates rule
            
    */
    
}
