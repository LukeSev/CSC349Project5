// Authors: Lucas Loughner (lloughne) and Spencer Gregory (sgrego03)
// Date: 7/23/22
// Assignment: Project5

import java.util.Scanner;

public class DiGraphTest {

    public static void main(String[] args)
    {
        // Get info from user
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number of vertices: ");
        int N = in.nextInt();
        in.nextLine();

        DiGraph dg = new DiGraph(N);

        System.out.println("Choose one of the following operations:");
        System.out.println("- add edge (enter a)");
        System.out.println("- delete edge (enter d)");
        System.out.println("- edge count (enter e)");
        System.out.println("- vertex count (enter v)");
        System.out.println("- print graph (enter p)");
        System.out.println("- topological sort (enter t)");
        System.out.println("- is there a path between vertices (enter i)");
        System.out.println("- find length of shortest path between vertices (enter l)");
        System.out.println("- print shortest path between vertices (enter s)");
        System.out.println("- print breadth-first-tree from vertex (enter b)");
        System.out.println("- Quit (enter q)");

        int quit = 0;
        while(quit != 1)
        {
            System.out.println("Enter choice of operation:");
            String inLine = in.nextLine();

            if(inLine.length() > 1)
            {
                System.out.println("Invalid operation");
            }
            else {
                switch (inLine) {
                    case "a":
                        System.out.println("Add edge from vertex v to vertex u in following format: 'v u'");
                        int v = in.nextInt();
                        int u = in.nextInt();
                        in.nextLine();
                        if (dg.addEdge(v, u) == 1)
                            System.out.printf("Edge (%d,%d) was added%n", v, u);
                        else
                            System.out.printf("Edge (%d,%d) already exists%n", v, u);
                        break;

                    case "d":
                        System.out.println("Delete edge from vertex v to vertex u in following format: 'v u'");
                        v = in.nextInt();
                        u = in.nextInt();
                        in.nextLine();
                        if (dg.deleteEdge(v, u) == 1)
                            System.out.printf("Edge (%d,%d) was deleted%n", v, u);
                        else
                            System.out.printf("Edge (%d, %d) does not exist%n", v, u);
                        break;

                    case "e":
                        int edges = dg.edgeCount();
                        System.out.printf("Edge count: %d%n", edges);
                        break;

                    case "v":
                        int vertex_count = dg.vertexCount();
                        System.out.printf("Vertex count: %d%n", vertex_count);
                        break;

                    case "p":
                        System.out.println("The graph is the following:");
                        dg.print();
                        break;

                    case "t":
                        try
                        {
                            int[] sorted = dg.topSort();
                            System.out.printf("Topological sorting of vertices:%n");
                            System.out.print(sorted[0]);
                            for (int i = 1; i < sorted.length; i++)
                                System.out.printf(",%d", sorted[i]);
                            System.out.println();
                        }
                        catch(Exception e)
                        {
                            System.out.println("ERROR: Cyclic Graph");
                        }
                        break;

                    case "i":
                        System.out.println("Enter two vertices to see if there's a path to/from (in form to from)");
                        int from = in.nextInt();
                        int to = in.nextInt();
                        in.nextLine();
                        boolean is = dg.isTherePath(from, to);
                        if(is)
                            System.out.println("There is a path between the vertices");
                        else
                            System.out.println("There is NOT a path between the vertices");
                        break;

                    case "l":
                        System.out.println("Enter vertices to find length of path from/to:");
                        from = in.nextInt();
                        to = in.nextInt();
                        in.nextLine();

                        int count = dg.lengthOfPath(from, to);
                        System.out.printf("Length of path from %d to %d: %d%n", from, to, count);
                        break;

                    case "s":
                        System.out.println("Enter vertices to print path from/to:");
                        from = in.nextInt();
                        to = in.nextInt();
                        in.nextLine();
                        dg.printPath(from, to);
                        break;

                    case "q":
                        System.out.println("Goodbye");
                        quit = 1;
                        break;

                    case "b":
                        System.out.println("Enter vertex to build tree from:");
                        int root = in.nextInt();
                        in.nextLine();
                        System.out.println("Breadth-First-Tree:");
                        dg.printTree(root);
                        break;

                    default:
                        System.out.println("Invalid operation");
                        break;
                }
            }
        }
    }

}
