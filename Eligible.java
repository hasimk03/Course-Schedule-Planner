package prereqchecker;

import java.util.*;

import javax.lang.model.util.ElementScanner6;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
	// WRITE YOUR CODE HERE

        //adjInput = args[0];
        //eligibleInput = args[1];
        //eligibleOutput = args[2];

        String adjInputFile = args[0];//"adjlist.in";
        String adjOutputFile = "adjlist.out";

        String preReqInputFile = "validprereq.in";
        String preReqOutputFile = "prereq.out";

        String eligibleInput = args[1]; //"eligible.in";
        String eligibleOuput = args[2]; //"eligible.out";

        ValidPrereq preReq = new ValidPrereq();
        Eligible eligible = new Eligible();

        ArrayList<Node> Table = new ArrayList<Node>();
        int V = 0;
        Graph ob = new Graph(V); //creates new graph


        preReq.createArrayList(adjInputFile, adjOutputFile, Table);

        eligible.makeGraph(ob,Table,V,adjInputFile,eligibleInput,eligibleOuput);

    }

    public void makeGraph(Graph ob, ArrayList<Node> Table, int V, String adjInputFile, String eligibleInput, String eligibleOuput){
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
            //System.out.println("adjacent verticies are: " + ob1.adj(1));
            
            build(ob1,eligibleInput,eligibleOuput);
    }

    //build Arraylist of courses already taken
    public void build(Graph ob,String eligibleInput,String eligibleOuput){

        ArrayList<String> coursesTaken = new ArrayList<String>(); //arrayList of courses taken
        StdIn.setFile(eligibleInput);
        int num = StdIn.readInt();
        StdIn.readLine();

        for (int z=0; z < num; z++){//---->for loop, run num times 
        String course1 = StdIn.readString(); //reads first course
        StdIn.readLine();

        int index1 = ob.search(course1); //index where course1 occurs

        Queue<String> courses = new Queue<String>(); 
            if (index1 == -1){ //course not Found
                StdOut.println("Course: " + course1 + " not found!");
                return;
            }
        courses.enqueue(ob.adjRoot(index1)); //ad 

        while (!courses.isEmpty()){ //runs thru queue as long as it contains something
            String val = courses.dequeue();
            coursesTaken.add(val); //adds visited course to arrayList
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
        }
        }
        print(coursesTaken); //prints build 
        check(ob,coursesTaken,eligibleOuput);
        //print(coursesTaken);
    }



    public void print(ArrayList<String> in){
        for (String x : in){
            StdOut.println(x);
        }
    }
    public void printTo(ArrayList<String> in, String eligibleOuput){ //print arrayList to given file
        StdOut.setFile(eligibleOuput);
        for (String x : in){
            //count++;
            StdOut.println(x);
            //StdOut.println("Course " + count + " is: " + x);
        }
    }

    public boolean lookUp(ArrayList<String> coursesTaken, String in){ //checks if preReq has already been taken by looking it up in arrayList
        for (int i=0; i < coursesTaken.size();i++){
            if (coursesTaken.get(i).equals(in)){ //if found input string
                return true;
            }
        }
        return false;
    }
    
    //run thru each nested arrayList (2 loops), and check if preReqs of a given course exist in prev created arrayList
        //if yes-> add to new arrayList of eligible courses
    public void check(Graph ob, ArrayList<String> coursesTaken,String eligibleOuput){
        int count=1;
        ArrayList<String> eligible = new ArrayList<String>();
        for (int i=0; i < ob.adjSize();i++){ //runs thru all courses
            for (int j=1; j < ob.adjSizeAtIndex(i);j++){ //runs through all preReqs at course i
                if (lookUp(coursesTaken, ob.adjFind(i,j)) == true){ //exists in table
                    count++;
                    
                }
                if (ob.adjSizeAtIndex(i) == count){ //everyPreReq returned true
                    if (contains(ob.adjRoot(i),coursesTaken)){ //if course is not taken yet
                        StdOut.println(ob.adjRoot(i) + " added");
                        eligible.add(ob.adjRoot(i));
                    }
                }
                if (lookUp(coursesTaken,ob.adjFind(i,j)) == false){ //dosent exist in table
                    StdOut.println(ob.adjFind(i,j) + " broke ");
                    break;
                }
            }
            count=1;
        }
        //deleteDuplicate(ob,eligible,coursesTaken);
        printTo(eligible,eligibleOuput);
    }
    public boolean contains(String in, ArrayList<String> coursesTaken){ //checks if course given, has already been taken
        for (String x : coursesTaken){
            if (x.equals(in)){
                return false;
            }
        }
        return true;
    }
}
