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

        DiGraph dg = new DiGraph(N);

        System.out.println("Choose one of the following operations:");
        System.out.println("- add edge (enter a)");
        System.out.println("- delete edge (enter d)");
        System.out.println("- edge count (enter e)");
        System.out.println("- vertex count (enter v)");
        System.out.println("- print graph (enter p)");
        System.out.println("- Quit (enter q)");

        int quit = 0;
        while(quit != 1)
        {
            System.out.println("Enter choice of operation:");

            char choice = in.next().charAt(0);
            String inLine = in.nextLine();

            if(inLine.length() > 0)
            {
                System.out.println("Invalid operation");
            }
            else {
                switch (choice) {
                    case 'a':
                        System.out.println("Add edge from vertex v to vertex u in following format: 'v u'");
                        int v = in.nextInt();
                        int u = in.nextInt();
                        if (dg.addEdge(v, u) == 1)
                            System.out.printf("Edge (%d,%d) was added%n", v, u);
                        else
                            System.out.printf("Edge (%d,%d) already exists%n", v, u);
                        break;

                    case 'd':
                        System.out.println("Delete edge from vertex v to vertex u in following format: 'v u'");
                        v = in.nextInt();
                        u = in.nextInt();
                        if (dg.deleteEdge(v, u) == 1)
                            System.out.printf("Edge (%d,%d) was deleted%n", v, u);
                        else
                            System.out.printf("Edge (%d, %d) does not exist%n", v, u);
                        break;

                    case 'e':
                        int edges = dg.edgeCount();
                        System.out.printf("Edge count: %d%n", edges);
                        break;

                    case 'v':
                        int vertex_count = dg.vertexCount();
                        System.out.printf("Vertex count: %d%n", vertex_count);
                        break;

                    case 'p':
                        System.out.println("The graph is the following:");
                        dg.print();
                        break;

                    case 'q':
                        quit = 1;
                        break;

                    default:
                        System.out.println("Invalid operation");
                        break;
                }
            }

        }




    }


}
