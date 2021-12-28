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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        String InputFile = args[0];
        String outputFile = args[1];
        //StdOut.println("InputFile is: " + InputFile);
        AdjList myobj1 = new AdjList();
        ArrayList<Node> Table = new ArrayList<Node>();

        myobj1.store(InputFile,outputFile,Table);

        myobj1.getTable(Table);
        myobj1.print(Table,outputFile);
        //StdOut.println("WORKED");

    }
    public ArrayList<Node> getTable(ArrayList<Node> Table){
        //StdOut.println("First course is: " + Table.get(0).getData());
        return Table;
    }
    

   public void store(String InputFile,String outputFile,ArrayList<Node> Table){ //stores everything into Table
    StdIn.setFile(InputFile); //operates on StdIn as inputFile
    int numVertex = StdIn.readInt();
    
   //ArrayList<Node> Table = new ArrayList<Node>(); //creates ArrayList of size = numVertex
        int i=0;
        while (i < numVertex){ //stores all courses in arrayList
            String in1 = StdIn.readString();
                //StdOut.println("Course " + i + " is: " + in1);
            Node temp = new Node(in1,null,null,null); //stores courseID in temp
            Table.add(temp); //adds to arrayList
                StdIn.readLine();
                //StdOut.println("Line " + i + " is: " + StdIn.readLine());
            i++;
        }
        //StdOut.println("Table size is: " + Table.size());
        
        int edges = StdIn.readInt();
                        //StdOut.println("Line is: " + StdIn.readLine());
        int j=0;
        while (StdIn.hasNextLine()){
            String in2 = StdIn.readString();
            String in3 = StdIn.readString();
            int c = index(Table,in2);
                        //StdOut.println("String is: " + in2 + " |  c is: " + c);

                if (c== -1){
                    StdOut.println("Object not found in ArrayList!");
                    continue;
                }
                if (Table.get(c).getRight() == null){ //if right child is empty
                    Node temp = new Node(in3,null,null,null);
                    Table.get(c).setRight(temp); //setRight to temp
                        //StdOut.println("Course is: " + Table.get(c).getData() + " |  Right child is: " + Table.get(c).getRight().getData());
                }   
                else if (Table.get(c).getLeft() ==null){ //if left is empty
                    Node temp = new Node(in3,null,null,null);
                    Table.get(c).setLeft(temp); //setLeft to temp
                        //StdOut.println("Course is: " + Table.get(c).getData() + " |  Left child is: " + Table.get(c).getLeft().getData());
                }
                else { //if both are full
                        //StdOut.println("Course is: " + Table.get(c).getData() + " |  right child is: " + Table.get(c).getRight().getData() + " |   Left child is: " + Table.get(c).getLeft().getData());
                }
                        //StdOut.print("Line " + j + " is: " + StdIn.readLine());
                        StdIn.readLine();
                j++;
        }
        
   }

   public int index(ArrayList<Node> Table, String compare){ //method returns index of object that contains input String
    int i=0;
        for (Node x : Table){ //run through Table
            if (x.getData().equals(compare)){
                return i;
            }
            i++;
        }
    return -5; //fucked up
   }

   public void print(ArrayList<Node> Table,String outputFile){ //prints out courses add --> String outputFile
        StdOut.setFile(outputFile);
        for (Node x : Table){
            //StdOut.println(x.getData()); //prints course
            if (x.getRight() == null && x.getLeft() == null){ //if course has no children
                StdOut.println(x.getData()); //print course
                continue;
            }
            if (x.getRight() == null){ //if right child does not exists
                StdOut.println(x.getData()); //+ "---------> right does not exist"
                continue;
            }
            else if (x.getLeft() == null){ //if right exists and left does not 
                StdOut.println(x.getData() + " " + x.getRight().getData()); //prints right child  + " Right child is ---> " 
            }
            else{ //right and left are non null
                StdOut.println(x.getData() + " " + x.getRight().getData() + " " + x.getLeft().getData()); //"right child -> " +  + " left is --> " 
            }

        }
        StdOut.close();
        
   }
   
}
