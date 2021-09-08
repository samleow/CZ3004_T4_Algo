package src.AStar;

import java.util.List;

public class Node implements Comparable<Node>{
    private static int idCounter = 0;
    public int id;

    public Node parent = null;

    public List<Edge> neighbours;

    public double f = Double.MAX_VALUE;
    public double g = Double.MAX_VALUE;

    public double h;

    Node(double h){
        this.h = h;
        this.id = idCounter++;
        this.neighbours = new ArrayList<Edge>();
    }

    @Override
    public int compareTo(Node n){
        return Double.compare(this.f,n.f);
    }

    public static class Edge{
        Edge(int weight, Node node){
            this.weight = weight;
            this.node = node;
        }

        public int weight;
        public Node node;
    }
    public void addBranch(int weight, Node node){
        Edge newEdge = new Edge(weight, node);
        neighbours.add(newEdge);
    }
    public double calculateHeuristic(Node target){
        return this.h;
    }
}