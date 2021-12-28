package prereqchecker;
import java.util.*;

public class Graph{
    private final int V;
    private ArrayList<ArrayList<String>> adj;
    
    public Iterable<String> adj(int v){  
        return adj.get(v);  
    }
    public String adjRoot(int v){
        return adj.get(v).get(0);
    }

    public String adjFind(int v,int w){
        return adj.get(v).get(w);
    }
    public int adjSize(){
        return adj.size();
    }
    public int adjSizeAtIndex(int v){ //finds size of arrayList at specific index
        return adj.get(v).size();
    }
    /**
     * Instantiates a Directed Graph with V verticies. Implemented using an ArrayList of 
     * @param V number of verticies
     */
    public Graph(int V){
        this.V = V;
        adj =  new ArrayList<ArrayList<String>>();    //(ArrayList<String>[]) new ArrayList[V];
            for (int v = 0; v < V; v++){
                adj.add(v, new ArrayList<String>());
            }
    }
    /**
     * Adds a string w to a specified index v of a graph 
     * @param v vertex index
     * @param w String to be added
     */
    public void addEdge(int v, String w){
        adj.get(v).add(w);
    }

    /**
     * searches graph for string -> returns index of queried string 
     */
    public int search(String in){
        //Graph pob = new Graph(V);
        for (int i= 0 ;i < adj.size(); i++){
            if (adj.get(i).get(0).equals(in)){ //if we found advanced course
                return i;
            }
        }
        return -1; //not found
        }

    public String getNext(String in){ //returns next string in graph
        int index = search(in);
        for (int i=0; i < adj.get(index).size(); i++){
            if (adj.get(index).get(i).equals(in)){
                if (adj.get(index).size() == 1){//no more elements
                    return null;
                }
                if (adj.get(index).get(i+1) != null){ //ISSUE
                    return adj.get(index).get(i+1); //String of nested arrayList 
                }
                else{
                    StdOut.println("nextVal is: " + adj.get(index).get(i+1));
                    return null;
                }
            }
        }
        StdOut.println("FUCKEDDD UPPPP");
        return null;
    }

    public String getNextNext(String in){ //returns second element after input
        int index = search(in);

        //for (int i=1; i < adj.get(index).size(); i++){
        for (int i=0; i < adj.get(index).size(); i++){
            if (adj.get(index).get(i).equals(in)){
                if (adj.get(index).size() == 2){//no more elements
                    return null;
                }
                if (adj.get(index).get(i+2) != null){ //ISSUE
                    return adj.get(index).get(i+2); //String of the nested arrayList 
                }
            }
        }
        StdOut.println("FUCKEDDD UPPPP");
        return null;
    }

    public int specSize(String in){ //returns size of Arraylist at specific index
        for (int i=0; i < adj.size();i++){
            if (adj.get(i).get(0).equals(in)){
                return adj.size();
            }
        }
        return -1; //not found
    }
    public boolean exists(String in){
        for (int i = 0; i < adj.size();i++){ //traverse arrayList
            if (adj.get(i).get(0).equals(in)){ //if its same as input stirng
                if (adj.get(i).get(1) == null){ //if there is only one course (cs111 or mat151)
                    return false; //returns false -> can exit
                }
                else{
                    return true; //if we find target String and it has preReqs
                }
            }
        }
        return true;
    }
    }
