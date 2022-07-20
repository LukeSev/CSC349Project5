// Authors: Lucas Loughner (lloughne) and Spencer Gregory (sgrego03)
// Date: 7/23/22
// Assignment: Project5

import java.util.*;

public class DiGraph {

    private LinkedList<Integer>[] vertices;

    public DiGraph(int N)
    {
        this.vertices = new LinkedList[N];
        for(int i = 0; i < N; i++)
            this.vertices[i] = new LinkedList<Integer>();
    }

    public int addEdge(int from, int to)
    {
        if(!this.vertices[from-1].contains(to-1))
        {
            this.vertices[from - 1].add(to - 1);
            return 1;
        }
        return 0;
    }

    public int deleteEdge(int from, int to)
    {
        if(this.vertices[from-1].contains(to-1))
        {
            this.vertices[from - 1].removeFirstOccurrence(to - 1);
            return 1;
        }
        return 0;
    }

    public int edgeCount()
    {
        int edges = 0;
        for(int i = 0; i < this.vertices.length; i++)
            edges += this.vertices[i].size();
        return edges;
    }

    public int vertexCount()
    {
        return this.vertices.length;
    }

    public void print()
    {
        for(int i = 0; i < this.vertices.length; i++)
        {
            System.out.printf("%d is connected to:", i+1);
            for(int j = 0; j < this.vertices[i].size(); j++)
            {
                if(j == 0)
                    System.out.printf(" %d", (int)this.vertices[i].get(j)+1);
                else
                    System.out.printf(", %d", (int)this.vertices[i].get(j)+1);
            }
            System.out.println();
        }

    }

    private int[] indegrees()
    {
        int[] indeg = new int[this.vertices.length + 1]; // Natural indexing for vertices

        for(int i = 0; i < this.vertices.length; i++)
        {
            // For each vertex, look for what it's connected to and increase connected verts' indegrees
            for(int j = 0; j < this.vertices[i].size(); j++)
            {
                indeg[this.vertices[i].get(j)+1]++; // increment indegree count using natural indexing
            }
        }

        // For testing indegrees
        /*
        System.out.println();
        for(int i = 0; i < indeg.length; i++)
            System.out.printf("%d: %d%n", i, indeg[i]);
         */

        return indeg;
    }

    public int[] topSort() throws IllegalArgumentException
    {
        // Get indegrees and create list to be returned later
        int N = this.vertices.length; // For readability
        int[] indeg = this.indegrees();
        int[] sorted = new int[N];

        // Create queue
        LinkedList<Integer> queue = new LinkedList<Integer>();

        for(int i = 1; i < N; i++) // using natural indexing
        {
            if(indeg[i] == 0)
                queue.addLast(i-1);
        }
        int ind = 0, vert, curr;
        while(!queue.isEmpty())
        {
            vert = queue.removeFirst();
            sorted[ind] = vert+1;
            ind++; // Increment index of output array

            for(int k = 0; k < this.vertices[vert].size(); k++)
            {
                curr = this.vertices[vert].get(k);
                indeg[curr+1] = indeg[curr+1]-1; // indeg uses natural indexing, vertices doesn't (great design I know)
                if(indeg[curr+1] == 0)
                    queue.addLast(curr);
            }
        }
        if(ind != N)
            throw new IllegalArgumentException("Cyclic graph");
        return sorted;
    }

}
