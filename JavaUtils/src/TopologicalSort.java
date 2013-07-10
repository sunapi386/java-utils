import java.util.*;


/**
 * User: Jason
 * Date: 08/07/13
 * Time: 2:51 PM
 * Implementation of the topological sort algorithm.
 * Input will be given as n, the number of vertices, then the adjacency that is null terminated
 * With graph implementation from http://opendatastructures.org/ods-java/12_2_AdjacencyLists_Graph_a.html
 * Note: Vertices are not allowed to be 0, they start at vertex 1, 2, 3, ...
 */

public class TopologicalSort {
    static boolean DB = false;  // toggles printing of debug tracing information
    static void db_print(String s) {   // helper to shorten debug print statements
        if (DB) System.out.println(s);
    }

    static class Algorithm {
        char[] c;           // colour[v]
        int[] pi;           // predecessor[v]
        int[] d;            // discover[v]
        int[] f;            // finish[v]
        Stack<Integer> s;   // stack
        boolean DAG;
        int time;
        int back_u, back_v; // memo back edge uv upon first encounter
        Graph g;

        // Implementation of DFSvisit, slide 119
        public void DFSvisit (int v) {
            db_print("DFSvisit " + v);
            c[v] = 'g';
            time++;
            d[v] = time;
            db_print("c[" + v + "] = g , d[" + v + "] = " + time);

            List<Integer> adj_v = g.outEdges(v);    // get adj[v]
            for (int i = 0; i < adj_v.size(); i++) {
                int w = adj_v.get(i);

                db_print("\tFrom " + v + " to adj: " + w);
                assert (w != 0);

                if (c[w] == 'w') {
                    db_print("\tc[" + w + "] = w");
                    pi[w] = v;
                    db_print("\tpi[" + v + "] = " + w + "\n going to " + w);
                    DFSvisit(w);

                }
                if (c[w] == 'g') {
                    db_print("\tc[" + w + "] = g");
                    db_print("\tBack edge [u,v] : [" + w + "," + v + "]");

                    if (DAG) { // then this uv edge is first encounter of back edge - memoize
                        back_u = w;
                        back_v = v;
                    }

                    DAG = false;

                }
            }

            c[v] = 'b';
            db_print("\tc[" + v + "] = b");
            s.push(v);
            time++;
            f[v] = time;
            db_print("\tf[" + v + "] = " + time);
        }

        // Returns a String of Top Ordering via DFS
        // Implementation of DFS, slide 118
        // O(n + m)
        public String DFS(Graph graph) {
            g = graph;
            int n_v = g.nVertices() + 1; // we add 1 to make it easy to refer by vertex
            c = new char[n_v];    // create color array
            pi = new int[n_v];    // create predecessor array
            d = new int[n_v];     // create discover array
            f = new int[n_v];     // create finish array
            s = new Stack<Integer>();       // init stack s
            DAG = true;
            time = 0;

            for(int v = 1; v < n_v; v++) {
                c[v] = 'w';
                pi[v] = 0;
            }

            for(int v = 1; v < n_v; v++)
                if (c[v] == 'w') DFSvisit(v);

            String top_order = new String();

            if (DAG) {
                db_print("DAG true! Top order:");

                // just print out the stack, that'll be our top order
                while (!s.empty()) top_order = top_order + " " + String.valueOf(s.pop());

            } else {

                db_print("DAG false\t back edge uv: " + back_u + " " + back_v);
                // use pi[u] trace until v, and print from v to u
                Stack<Integer> back_edge_stack = new Stack<Integer>();

                int cur = back_v;
                back_edge_stack.push(cur);
                while (pi[cur] != back_u) {
                    cur = pi[cur];
                    back_edge_stack.push(cur);
                }
                back_edge_stack.push(back_u);
                back_edge_stack.push(back_v);

                db_print("Cycle: " + back_edge_stack);

                while (!back_edge_stack.empty()) {
                    top_order += back_edge_stack.pop() + " ";
                }
            }

            return top_order.trim();
        }

        // Debug helper, prints out the DFS table
        // v, d[v], f[v], pi[v]
        public void print() {
            System.out.println("v\t|d[v]|f[v]|pi[v]");
            System.out.println("------------------------");
            for (int i = 1; i <= g.nVertices(); i++) {
                System.out.println(i + "\t|" + d[i] + "\t|" + f[i] + "\t|" + pi[i]);
            }
            System.out.println("------------------------");
        }
    }

    // A representation of a graph implemented as List, to abstract
    // away the implementation details when writing DFS algorithm
    static class Graph {
        int n;
        List<Integer>[] adj;

        // createGraph() reads n, makes n rows. Reading each row until it sees '0'
        // O(n + m)
        public void createGraph() {
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            // create a adj list of n elements
            // we do not use vertex 0, so the list starts at 1.
            adj = (List<Integer>[])new List[n+1];
            int j;
            for (int i = 1; i <= n; i++) {
                adj[i] = new ArrayList<>();
                j = s.nextInt();
                while(j != 0) {                 // expect 0 termination
                    addEdge(i,j);
                    j = s.nextInt();
                }
            }

        }

        // addEdge(i, j) just appends value of j to list adj[i]
        // O(1)
        public void addEdge(int i, int j) {
            adj[i].add(j);
        }

        // removeEdge operation searches through adj[i] until j is found, then removes it
        // O(deg(i)) time
        public void removeEdge(int i, int j) {
            Iterator<Integer> it = adj[i].iterator();
            while (it.hasNext()) {
                if (it.next() == j) {
                    it.remove();
                    return;
                }
            }
        }

        // hasEdge(i, j) searches through adj[i] until it finds j
        // O(deg(i))
        public boolean hasEdge(int i, int j) {
            return adj[i].contains(j);
        }

        // outEdges(i) returns adj[i]
        // O(1)
        public List<Integer> outEdges(int i) {
            return adj[i];
        }

        // inEdges(i) scans over ever vertex j and checks if edge (i,j) exists, and
        // if so, adds j to output list
        // O(n + m)
        public List<Integer> inEdges(int i) {
            List<Integer> edges = new ArrayList<>();
            for(int j = 0; j < n; j++) {
                if (adj[j].contains(i)) {
                    edges.add(j);
                }
            }
            return edges;
        }

        // print() summarizes the graph
        public void print() {
            System.out.println("print(): Graph size " + n);
            for (int i = 0; i < adj.length; i++) {
                System.out.println("Vertex " + i + ": " + outEdges(i));
            }
        }

        // Getter
        public int nVertices() {
            return n;
        }

    }



    public static void main(String[] args) {
        // Read a graph, record it in Graph class, adjacency list style
        Graph graph = new Graph();
        graph.createGraph();
//        graph.print();    // Debug print out of graph as adj list

        // DFS Algorithm implementation in Algorithm class
        Algorithm algorithm = new Algorithm();
        String output = algorithm.DFS(graph);   // Run DFS algorithm on graph
        System.out.println(output);
//        algorithm.print();   // Debug print out of DFS table: v, d[v], f[v], pi[v]

    }
}
