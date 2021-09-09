package src.AStar;
import src.CarPosition;
import src.HamiltonianPathSimulator;
import src.Position;
import src.readinput;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar
{
    public static final int v_h_cost = 10;
    public static final int diagonal_cost = 10;
    private Node[][] grid;
    private PriorityQueue<Node> openCells;
    private boolean[][] closedCells;
    private int startI, startJ;
    private int endI, endJ;

    public AStar(int width, int height, int si, int sj, int ei, int ej, int [][] blocks)
    {
        grid = new Node[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<Node>((Node N1,Node N2)->{
            return N1.finalCost<N2.finalCost ? -1: N2.finalCost <N1.finalCost ? 1:0;

        });
        startNode(si,sj);
        endNode(ei,ej);
        for (int i= 0; i<grid.length;i++){
            for(int j=0; j<grid[i].length;j++)
            {
                grid[i][j] = new Node(i,j);
                grid[i][j].heuristicCost = Math.abs(i-endI)+ Math.abs(j-endJ);
            }
        }

        grid[startI][startJ].finalCost = 0;

        for (int i = 0; i<blocks.length; i++){
            addBlocksOnCell(blocks[i][0], blocks[i][1]);
        }
    }
        public void addBlocksOnCell(int i, int j){
            grid[i][j] = null;
        }
        public void startNode(int i, int j){
            startI = i;
            startJ = j;
        }
        public void endNode(int i, int j){
            endI = i;
            endJ = j;
        }
        public void UpdateCostifNeeded(Node current, Node t, int cost){
            if (t == null || closedCells[t.i][t.j])
                return;
            
            int tFinalCost = t.heuristicCost + cost;
            boolean isOpen = openCells.contains(t);
            if (!isOpen ||tFinalCost < t.finalCost){
                t.finalCost = tFinalCost;
                t.parent = current;
                
                if(!isOpen)
                    openCells.add(t);
            }
        }
        public void process(){
            openCells.add(grid[startI][startJ]);
            Node current;
            while(true){
                current = openCells.poll();

                if (current== null)
                    break;
                
                closedCells[current.i][current.j] = true;
                if (current.equals(grid[endI][endJ]))
                    return;

                Node t;
                
                if (current.i -1 >= 0){
                    t = grid[current.i -1][current.j];
                    UpdateCostifNeeded(current, t, current.finalCost + v_h_cost);
                    if (current.j -1 >= 0){
                        t = grid[current.i -1][current.j -1];
                        UpdateCostifNeeded(current, t, current.finalCost + diagonal_cost);
                    }
                    if (current.j + 1 < grid[0].length){
                        t = grid[current.i -1][current.j +1];
                        UpdateCostifNeeded(current, t, current.finalCost + diagonal_cost);
                    }
                }
                if (current.j -1 >= 0){
                    t = grid[current.i][current.j -1];
                    UpdateCostifNeeded(current, t, current.finalCost + v_h_cost);
                    
                }
                if (current.j + 1 < grid[0].length){
                    t = grid[current.i ][current.j +1];
                    UpdateCostifNeeded(current, t, current.finalCost + v_h_cost);
                }
                if (current.i + 1 < grid.length){
                    t = grid[current.i +1][current.j];
                    UpdateCostifNeeded(current, t, current.finalCost + v_h_cost);
                    if (current.j - 1 >= 0){
                        t = grid[current.i +1][current.j -1];
                        UpdateCostifNeeded(current, t, current.finalCost + diagonal_cost);
                    }
                    if (current.j + 1 < grid[0].length){
                        t = grid[current.i +1][current.j +1];
                        UpdateCostifNeeded(current, t, current.finalCost + diagonal_cost);
                    }
                }
            }
            }
        public void display(){
            System.out.println("Grid:");
            for (int i=0;i<grid.length;i++){
                for (int j=0; j<grid[i].length;j++){
                    if (i == startI && j == startJ){
                        System.out.print("SO  ");
                    }
                    else if (i == endI && j == endJ)
                        System.out.print("EO  ");
                    else if (grid[i][j] != null)
                        System.out.printf("%-3d", 0);
                    else
                        System.out.print("BL  ");
                    
                }
                System.out.println();
            }
            System.out.println();
        }
        public void displayScores(){
            System.out.println("Scores for nodes:");

            for (int i=0;i<grid.length;i++){
                for (int j=0; j<grid[i].length;j++){
                    if (grid[i][j] !=null)
                        System.out.printf("%-3d", grid[i][j].finalCost);
                    else
                        System.out.print("BL  ");
                }System.out.println();
            }System.out.println();
        }
        public void displaySolution(){
            if (closedCells[endI][endJ]){
                System.out.println("Solution:");
                Node current = grid[endI][endJ];
                System.out.println(current);
                grid[current.i][current.j].solution = true;
                while(current.parent != null){
                    System.out.println(" -> " + current.parent);
                    grid[current.parent.i][current.parent.j].solution = true;
                    current = current.parent;
                }
                for (int i=0;i<grid.length;i++){
                    for (int j=0; j<grid[i].length;j++){
                        if (i == startI && j == startJ){
                            System.out.print("SO  ");
                        }
                        else if (i == endI && j == endJ)
                            System.out.print("EO  ");
                        else if (grid[i][j] != null)
                            System.out.printf("%-3s", grid[i][j].solution ? "X":"0");
                        else
                            System.out.print("BL  ");
                        }System.out.println();
                }System.out.println();

            }else
                System.out.println("No possible Path");

        }
        public static void main(String[] args){
            AStar astar = new AStar(5, 5, 0, 0, 3, 2, new int[][]{
                {0,4},{2,2},{3,1},{3,3},{2,1},{2,3}
            });
            astar.display();
            astar.process();
            astar.displayScores();
            astar.displaySolution();
        }
}
