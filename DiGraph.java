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

}
