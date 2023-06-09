import java.util.*;

/** Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

   /** Main method. */
   public static void main (String[] args) {
      GraphTask a = new GraphTask();
      a.run();
   }

   /** Actual main method to run examples and everything. */
   public void run() {
      Graph g1 = new Graph ("G");
      g1.createRandomSimpleGraph (6, 9);
      Graph d1 = g1;
      Graph b1 = g1.clone();
      Graph g2 = new Graph("L");
      g2.createRandomSimpleGraph(2000, 4000);
      Graph b2 = g2.clone();
      Graph g3 = new Graph("L");
      g3.createRandomSimpleGraph(100, 300);
      Graph b3 = g3.clone();
      Graph g4 = new Graph("L");
      g4.createRandomSimpleGraph(345, 360);
      Graph b4 = g4.clone();
      Graph g5 = new Graph("L");
      g5.createRandomSimpleGraph(232, 231);
      Graph b5 = g5.clone();
      System.out.println("For debugging purposes");
      if (!g1.toString().equals(b1.toString())) throw new AssertionError("Should be equal");
      if (g1 != d1) throw new AssertionError("Shallow copy should be not original");
      if (g1 == b1) throw new AssertionError("Deep clone should be original");
      if (!g2.toString().equals(b2.toString())) throw new AssertionError("Should be equal");
      if (g2 == b2) throw new AssertionError("Deep clone should be original");
      if (!g3.toString().equals(b3.toString())) throw new AssertionError("Should be equal");
      if (g3 == b3) throw new AssertionError("Deep clone should be original");
      if (!g4.toString().equals(b4.toString())) throw new AssertionError("Should be equal");
      if (g4 == b4) throw new AssertionError("Deep clone should be original");
      if (!g5.toString().equals(b5.toString())) throw new AssertionError("Should be equal");
      if (g5 == b5) throw new AssertionError("Deep clone should be original");
      if (g1.toString().equals(b3.toString())) throw new AssertionError("Should be not equal");
      b2.createRandomSimpleGraph(10, 30);
      if (g2.toString().equals(b2.toString())) throw new AssertionError("Should be not equal");
      g4.createRandomSimpleGraph(34, 39);
      if (g4.toString().equals(b4.toString())) throw new AssertionError("Should be not equal");
      Graph deepTestO = new Graph ("G");
      deepTestO.createRandomSimpleGraph (6, 12);
      Graph deepTestC = deepTestO.clone();
      if (!deepTestC.id.equals(deepTestO.id)) throw new AssertionError("Should be equal");
      if (deepTestC.info != deepTestO.info) throw new AssertionError("Should be equal");
      if (deepTestC == deepTestO) throw new AssertionError("Deep clone should be original");
      Vertex dtof = deepTestO.first;
      Vertex dtcf = deepTestC.first;
      while (dtof != null) {
         System.out.println("Vertex clone id: " + dtcf.id + " Vertex original id: " + dtof.id);
         System.out.println("Are vertices the same? " + (dtcf == dtof));
         if (!dtof.id.equals(dtcf.id)) throw new AssertionError("Vertices id should be equal");
         if (dtof.info != dtcf.info) throw new AssertionError("Vertices info should be equal");
         if (dtof == dtcf) throw new AssertionError("Deep clone of vertex should be original");
         if (dtof.first != null) {
            Arc dtoa = dtof.first;
            Arc dtca = dtcf.first;
            while (dtoa != null) {
               if (dtoa == dtca) throw new AssertionError("Deep clone of arc should be original");
               if (dtoa.target == dtca.target) throw new AssertionError("Deep clone of arc targets" +
                       " should be original");
               if (!dtoa.id.equals(dtca.id)) throw new AssertionError("Arcs id should be equal");
               if (dtoa.info != dtca.info) throw new AssertionError("Arcs info should be equal");
               if (dtoa.target.id != dtca.target.id) throw new AssertionError("Arc targets" +
                       " id should be equal");
               System.out.println("Arc clone id: " + dtca.id + " Arc original id: " + dtoa.id);
               System.out.println("Are arcs the same? " + (dtca == dtoa));
               System.out.println("Arc clone target id: " + dtca.target.id + " Arc original target id: " + dtoa.target.id);
               System.out.println("Are arcs targets the same? " + (dtca.target == dtoa.target));
               dtoa = dtoa.next;
               dtca = dtca.next;
            }
         }
         dtof = dtof.next;
         dtcf = dtcf.next;
      }
   }

   class Vertex {

      private String id;
      private Vertex next;
      private Arc first;
      private int info = 0;
      // You can add more fields, if needed

      Vertex (String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

      /** Deep clone of vertices.
       * Side effect: looses all arcs, to keep arcs use clone method from Graph object.
       * @return deep cloned vertices
       */
      @Override
      public Vertex clone() {
         Vertex clonedVertex = new Vertex(this.id, null, null);
         clonedVertex.info = this.info;
         if (this.next != null) {
            clonedVertex.next = this.next.clone();
         }
         return clonedVertex;
      }
   }


   /** Arc represents one arrow in the graph. Two-directional edges are
    * represented by two Arc objects (for both directions).
    */
   class Arc {

      private String id;
      private Vertex target;
      private Arc next;
      private int info = 0;
      // You can add more fields, if needed

      Arc (String s, Vertex v, Arc a) {
         id = s;
         target = v;
         next = a;
      }

      Arc (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

      /** Deep clone of arcs.
       * @param vertexList the list of new vertices in order as they follow in graph
       *                   to map arc targets to new cloned vertices.
       * @return deep cloned arcs with targets correlated with vertexList
       */
      public Arc clone(List<Vertex> vertexList) {
         Arc clonedArc = new Arc(this.id, null, null);
         clonedArc.info = this.info;
         clonedArc.target = vertexList.get(this.target.info);
         if (this.next != null) {
            clonedArc.next = this.next.clone(vertexList);
         }
         return clonedArc;
      }
   } 


   class Graph {

      private String id;
      private Vertex first;
      private int info = 0;
      // You can add more fields, if needed

      Graph (String s, Vertex v) {
         id = s;
         first = v;
      }

      Graph (String s) {
         this (s, null);
      }

      @Override
      public String toString() {
         String nl = System.getProperty ("line.separator");
         StringBuffer sb = new StringBuffer (nl);
         sb.append (id);
         sb.append (nl);
         Vertex v = first;
         while (v != null) {
            sb.append (v.toString());
            sb.append (" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append (" ");
               sb.append (a.toString());
               sb.append (" (");
               sb.append (v.toString());
               sb.append ("->");
               sb.append (a.target.toString());
               sb.append (")");
               a = a.next;
            }
            sb.append (nl);
            v = v.next;
         }
         return sb.toString();
      }

      public Vertex createVertex (String vid) {
         Vertex res = new Vertex (vid);
         res.next = first;
         first = res;
         return res;
      }

      public Arc createArc (String aid, Vertex from, Vertex to) {
         Arc res = new Arc (aid);
         res.next = from.first;
         from.first = res;
         res.target = to;
         return res;
      }

      /**
       * Create a connected undirected random tree with n vertices.
       * Each new vertex is connected to some random existing vertex.
       * @param n number of vertices added to this graph
       */
      public void createRandomTree (int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex [n];
         for (int i = 0; i < n; i++) {
            varray [i] = createVertex ("v" + String.valueOf(n-i));
            if (i > 0) {
               int vnr = (int)(Math.random()*i);
               createArc ("a" + varray [vnr].toString() + "_"
                  + varray [i].toString(), varray [vnr], varray [i]);
               createArc ("a" + varray [i].toString() + "_"
                  + varray [vnr].toString(), varray [i], varray [vnr]);
            } else {}
         }
      }

      /**
       * Create an adjacency matrix of this graph.
       * Side effect: corrupts info fields in the graph
       * @return adjacency matrix
       */
      public int[][] createAdjMatrix() {
         info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int [info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res [i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      /**
       * Create a connected simple (undirected, no loops, no multiple
       * arcs) random graph with n vertices and m edges.
       * @param n number of vertices
       * @param m number of edges
       */
      public void createRandomSimpleGraph (int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException ("Too many vertices: " + n);
         if (m < n-1 || m > n*(n-1)/2)
            throw new IllegalArgumentException 
               ("Impossible number of edges: " + m);
         first = null;
         createRandomTree (n);       // n-1 edges created here
         Vertex[] vert = new Vertex [n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int)(Math.random()*n);  // random source
            int j = (int)(Math.random()*n);  // random target
            if (i==j) 
               continue;  // no loops
            if (connected [i][j] != 0 || connected [j][i] != 0) 
               continue;  // no multiple edges
            Vertex vi = vert [i];
            Vertex vj = vert [j];
            createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected [i][j] = 1;
            createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected [j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }

      /**
       * Creates deep clone of this graph.
       * Firstly, creates new graph object and copies parameters of this to new.
       * Secondly, using vertices clone method clones vertices.
       * Thirdly, creates list of vertices for simplification of next operations.
       * Fourthly, for each vertex of this creates clones of arcs with providing list of new vertices to map
       * arcs targets to new cloned vertices.
       * All cloning are working on recursive logic.
       * @return deep cloned graph identical by content to this
       */
      @Override
      public Graph clone() {
         Graph clonedGraph = new Graph(this.id, this.first.clone());
         clonedGraph.info = this.info;
         List<Vertex> vertexList = new ArrayList<>();
         Vertex currentVertex = clonedGraph.first;
         while (currentVertex != null) {
            vertexList.add(currentVertex);
            currentVertex = currentVertex.next;
         }
         Vertex baseVertex = this.first;
         int i = 0;
         while (baseVertex != null) {
            vertexList.get(i).first = baseVertex.first.clone(vertexList);
            baseVertex = baseVertex.next;
            i++;
         }
         return clonedGraph;
      }
   }
} 

