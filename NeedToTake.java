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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
	// WRITE YOUR CODE HERE
        String adjInput = args[0]; // adjlist.in
        String in = args[1]; //needtotake.in
        String out = args[2]; //needtotake.out
        String adjOut = "adjlist.out";

        int V =0;
        Graph ob = new Graph(V); //new graph object
        NeedToTake take = new NeedToTake();
        ValidPrereq preReq = new ValidPrereq();

        ArrayList<Node> Table = new ArrayList<Node>();
        ArrayList<String> target = new ArrayList<String>();
        ArrayList<String> taken = new ArrayList<String>();
        
        preReq.createArrayList(adjInput, adjOut, Table);
        take.makeGraph(ob,Table,V,adjInput,in,target,out);
        //take.buildTarget(ob,in);
        /*
        Input File Format:
                        Target Course                   cs437
                        num courses Taken                   2
                        List of courses taken           cs211
                                                        mat152


            Objective:
                Find courses you must take in order to take target course
                    Output indirect and direct preReqs
                ex, if target = 437 
                    then -> output = cs214, cs336, cs205


        Algorithim:
            //grab target course -> build list of preReqs all the way to the end (1dt method)

            //call prev class -> build list of coursesTaken (2nd method)

            //remove all courses from target list, that exist in coursesTaken list (3rd method)

            //print to file -> print all strings in target list (4th method)
        */
    }

    public void makeGraph(Graph ob, ArrayList<Node> Table, int V, String adjInputFile,String in, ArrayList<String> target,String out){
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
            for (int i=0; i < ob.adjSize(); i++){StdOut.println( "element " + i + " is : " + ob1.adj(i));}
            buildTarget(ob1,in,target,out);
            }


    //Build a list of all preReqs of target
    public void buildTarget(Graph ob,String in,ArrayList<String> target,String out){
        StdIn.setFile(in); //sets file

        String targetCourse = StdIn.readString();
        StdIn.readLine();
    
        //ArrayList<String> target = new ArrayList<String>(); //arrayList of courses taken

        int index1 = ob.search(targetCourse); //index where targetCourse occurs

        Queue<String> courses = new Queue<String>(); 
            if (index1 == -1){ //course not Found
                StdOut.println("Course: " + targetCourse + " not found!");
                return;
            }

        courses.enqueue(ob.adjRoot(index1)); //add to queue

        while (!courses.isEmpty()){ //runs thru queue as long as it contains something
            String val = courses.dequeue();
            target.add(val); //adds visited course to arrayList
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
        print(target);
        buildCoursesTaken(ob, in,target,out);
    }
    public void print(ArrayList<String> target){
        for (String x : target){
            StdOut.println(x);
        }
    }
    public void buildCoursesTaken(Graph ob, String in,ArrayList<String> target,String out){
        StdIn.setFile(in);
        StdIn.readLine();
        int num = StdIn.readInt();
        StdIn.readLine();
        ArrayList<String> taken = new ArrayList<String>();
        
        for (int i=0 ;i < num;i++){
        String course = StdIn.readString();
        StdIn.readLine();
        int index1 = ob.search(course); //index where targetCourse occurs

        Queue<String> courses = new Queue<String>(); 
            if (index1 == -1){ //course not Found
                StdOut.println("Course: " + course + " not found!");
                return;
            }

        courses.enqueue(ob.adjRoot(index1)); //add to queue

        while (!courses.isEmpty()){ //runs thru queue as long as it contains something
            String val = courses.dequeue();
            taken.add(val); //adds visited course to arrayList
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
        StdOut.println("-------------------------------");
        StdOut.println("COURSES TAKEN: ");
        StdOut.println("");
        print(taken);
        removeFromTarget(ob,target,taken,out);
    }

    }

    //helper for method3
    public boolean occurs(String in, ArrayList<String> taken){
        for (int i=0; i <taken.size();i++){
            if (taken.get(i).equals(in)){
                return true;
            }
        }
        return false;
    }   
    //method 3 -> remove intersection (remove courses from target that have already been taken)
    public void removeFromTarget(Graph ob,ArrayList<String> target, ArrayList<String> taken,String out){
        int i=0;
        while (i < target.size()){
            if (occurs(target.get(i),taken)){ //if it exists in taken 
                target.remove(i);//remove
                continue; //dosent increment i
            }
            i++;
        }
        //StdOut.println("--------------");
        //StdOut.println("FINAL LIST IS:");
        print(target);
        printTo(target,out);

    }
    public void printTo(ArrayList<String> target,String out){
        StdOut.setFile(out); //prints to needtotake.out
        for (int i=1; i < target.size();i++){
            StdOut.println(target.get(i));
        }
    }
    
}
