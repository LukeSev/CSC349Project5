// Authors: Lucas Loughner (lloughne) and Spencer Gregory (sgrego03)
// Date: 7/23/22
// Assignment: Project5

import java.util.*;

public class DiGraph {

    private LinkedList<Integer>[] vertices;

    private class VertexInfo {
        int d, pred;

        public VertexInfo(int d, int pred)
        {
            this.d = d;
            this.pred = pred;
        }
    }

    private class TreeNode {
        int vert;
        LinkedList<TreeNode> children;

        public TreeNode(int vert, LinkedList<TreeNode> children)
        {
            this.vert = vert;
            this.children = children;
        }
    }

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

    private VertexInfo[] BFS(int s)
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int N = this.vertexCount();
        VertexInfo[] VA = new VertexInfo[N];

        for(int i = 0; i < N; i++)
            VA[i] = new VertexInfo(-1, -1);

        VA[s-1].d = 0;
        queue.addLast(s); // Queue uses natural indexing

        while(!queue.isEmpty())
        {
            int u = queue.removeFirst() - 1;
            for(int k = 0; k < this.vertices[u].size(); k++)
            {
                int v = this.vertices[u].get(k);
                if(VA[v].d == -1)
                {
                    VA[v].d = VA[u].d + 1;
                    VA[v].pred = u;
                    queue.addLast(v+1);
                }
            }
        }
        return VA;
    }

    public boolean isTherePath(int from, int to)
    {
        VertexInfo[] VA = BFS(from);
        if(VA[to-1].d != -1)
            return true;
        else
            return false;
    }

    public int lengthOfPath(int from, int to)
    {
        int count = 0;
        VertexInfo[] VA = BFS(from);
        if(!this.isTherePath(from, to))
            count = -1;

        to -= 1;
        while(VA[to].pred != -1)
        {
            count++;
            to = VA[to].pred;
        }

        return count;
    }

    public void printPath(int from, int to)
    {
        VertexInfo[] VA = BFS(from);
        String path;
        if(VA[to-1].d == -1)
            path = "No Path";
        else {
            path = "";
            to -= 1;
            while (from != to+1)
            {
                path = "-->" + (to + 1) + path;
                to = VA[to].pred;
            }
            path = from + path;
        }
        System.out.println(path);
    }

    private TreeNode buildTree(int s)
    {
        VertexInfo[] VA = BFS(s);
        int N = this.vertices.length;
        int root;

        TreeNode[] nodes = new TreeNode[N];
        for(int i = 0; i < N; i++)
            nodes[i] = new TreeNode(i, new LinkedList<TreeNode>());

        for(int j = 0; j < VA.length; j++)
        {
            if(VA[j].pred != -1)
                nodes[VA[j].pred].children.addLast(nodes[j]);
        }
        return nodes[s-1];
    }

    public void printTree(int s)
    {
        this.printTree_recursive(this.buildTree(s), 0);
    }

    private void printTree_recursive(TreeNode node, int level)
    {
        for(int i = 0; i < level; i++)
            System.out.print("    ");
        System.out.println(node.vert+1);

        int numchildren = node.children.size();
        for(int j = 0; j < numchildren; j++)
            printTree_recursive(node.children.get(j), level+1);
    }

}
