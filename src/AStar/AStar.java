package src.AStar;
import src.CarPosition;
import src.HamiltonianPathSimulator;
import src.Position;
import src.readinput;
import src.Position.Orientation;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar
{
    public static final int v_h_cost = 10;
    public static final int curves = 3;
    private static final int North=1, East= 2, South = 3, West =4;
    private Node[][] grid;
    private PriorityQueue<Node> openCells;
    private boolean[][] closedCells;
    private int startI, startJ;
    private int endI, endJ;
    private int ori;
    private int endori;

    public AStar(int width, int height, int si, int sj, int ei, int ej, int [][] blocks, int orientation, int endorientation)
    {
        grid = new Node[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<Node>((Node N1,Node N2)->{
            return N1.finalCost<N2.finalCost ? -1: N2.finalCost <N1.finalCost ? 1:0;

        });
        this.ori = orientation;
        this.endori = endorientation;
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
            if (i <3 && j<3)
            {
                return;
            }
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
        public void UpdateCostifNeeded(Node current, Node t, int cost, int Orientation, String Movement){
            if (t == null || closedCells[t.i][t.j])
                return;
            
            int tFinalCost = t.heuristicCost + cost;
            
            boolean isOpen = openCells.contains(t);
            if (!isOpen ||tFinalCost < t.finalCost){
                t.finalCost = tFinalCost;
                t.parent = current;
                t.movement = Movement;
                t.orientation = Orientation;
                if(!isOpen)
                    openCells.add(t);
            }
        }
        public void process(){
            grid[startI][startJ].orientation = ori;
            openCells.add(grid[startI][startJ]);
            Node current;
            while(true){
                current = openCells.poll();

                if (current== null)
                    break;
                
                closedCells[current.i][current.j] = true;
                if (current.equals(grid[endI][endJ]) && current.orientation == endori)
                    return;

                Node t;

                switch(current.orientation)
                {
                    //backward
                    case North:
                        if (current.j -2 >=0)
                        {
                            t = grid[current.i][current.j-1];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, North, "R,");
                        }
                        //forward
                        if (current.j+2 <grid[0].length)
                        {
                            t = grid[current.i][current.j+1];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, North, "F,");
                        }
                        if (current.j+3<grid[0].length && current.i+4<grid.length)
                        {
                            boolean noobs = true;
                            for (int i = -1; i<5;i++)
                            {
                                for (int j = 1;j<4;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i+3][current.j+2];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , East, "FR,");
                            }
                        }
                        if (current.j+3<grid[0].length && current.i-4>=0)
                        {
                            boolean noobs = true;
                            for (int i = -4; i<2;i++)
                            {
                                for (int j = 1;j<4;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i-3][current.j+2];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , West, "FL,");
                            }
                        }
                        break;
                    case(South):
                        //backward
                        if (current.j +2 <grid[0].length)
                        {
                            t = grid[current.i][current.j+1];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, South, "R,");
                        }
                        //forward
                        if (current.j-2 >= 0)
                        {
                            t = grid[current.i][current.j-1];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, South, "F,");
                        }
                        if (current.j-3>=0 && current.i-4>=0)
                        {
                            boolean noobs = true;
                            for (int i = -4; i<2;i++)
                            {
                                for (int j = -3;j<0;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i-3][current.j-2];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , West, "FR,");
                            }
                        }
                        if (current.j-3>=0 && current.i+4<grid.length)
                        {
                            boolean noobs = true;
                            for (int i = -1; i<5;i++)
                            {
                                for (int j = -3;j<0;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i+3][current.j-2];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , East, "FL,");
                            }
                        }
                        break;
                    case(East):
                        //forward
                        if (current.i +2 <grid[0].length)
                        {
                            t = grid[current.i+1][current.j];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, East, "F,");
                        }
                        //backward
                        if (current.i-2 >= 0)
                        {
                            t = grid[current.i-1][current.j];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, East, "R,");
                        }
                        if (current.j+4< grid[0].length && current.i+3<grid.length)
                        {
                            boolean noobs = true;
                            for (int i = 1; i<4;i++)
                            {
                                for (int j = -1;j<5;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i+2][current.j+3];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , North, "FL,");
                            }
                        }
                        if (current.j-4>=0 && current.i+3<grid.length)
                        {
                            boolean noobs = true;
                            for (int i = 1; i<4;i++)
                            {
                                for (int j = -4;j<2;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i+2][current.j-3];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , South, "FR,");
                            }
                        }
                        break;
                    case(West):
                        //forward
                        if (current.i -2 >= 0)
                        {
                            t = grid[current.i-1][current.j];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, West, "F,");
                        }
                        //backward
                        if (current.i+2 < grid.length)
                        {
                            t = grid[current.i+1][current.j];
                            UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, West, "R,");
                        }
                        if (current.j+4<grid[0].length && current.i-3>=0)
                        {
                            boolean noobs = true;
                            for (int i = -3; i<0;i++)
                            {
                                for (int j = -1;j<5;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i-2][current.j+3];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , North, "FR,");
                            }
                        }
                        if (current.j-4>=0 && current.i-3>=0)
                        {
                            boolean noobs = true;
                            for (int i = -3; i<0;i++)
                            {
                                for (int j = -4;j<2;j++)
                                {
                                    if (grid[current.i+i][current.j+j]==null)
                                        noobs = false;
                                }
                            }
                            if (noobs)
                            {
                                t = grid[current.i-2][current.j-3];
                                UpdateCostifNeeded(current, t,current.finalCost + 5*v_h_cost+ curves , South, "FL,");
                            }
                        }
                        break;

                }
                
                /*if (current.i -1 >= 0){
                    if (current.orientation == North)
                    {
                        
                        t = grid[current.i -1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+ curves, West, "R,FL,");
                    }
                    else if ( current.orientation == South)
                    {
                        t = grid[current.i -1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+ curves, West, "R,FR,");
                    }
                    else if (current.orientation == West){
                        t = grid[current.i -1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, West, "F,");
                    }
                    else if (current.orientation == East){
                        t = grid[current.i -1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, East, "R,");
                    }
                    
                }
                if (current.j -1 >= 0){
                    if (current.orientation == East)
                    {
                        t = grid[current.i][current.j -1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+curves, South, "R,FR,");
                    }
                    else if ( current.orientation == West)
                    {
                        t = grid[current.i][current.j -1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+curves,South,"R,FL,");
                    }
                    else if (current.orientation == South){
                        t = grid[current.i][current.j -1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost,South,"F,");
                    }
                    else if (current.orientation == North){
                        t = grid[current.i][current.j -1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost,North,"R,");
                    }
                    
                    
                }
                if (current.j + 1 < grid[0].length){
                    if (current.orientation == East)
                    {
                        t = grid[current.i ][current.j +1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+curves,North,"R,FL,");
                    }
                    else if ( current.orientation == West)
                    {
                        t = grid[current.i ][current.j +1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+curves,North,"R,FR,");
                    }
                    else if (current.orientation == North){
                        t = grid[current.i ][current.j +1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost,North,"F,");
                    }
                    else if (current.orientation == South){
                        t = grid[current.i ][current.j +1];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost,South,"R,");
                    }
                    
                }
                if (current.i + 1 < grid.length){
                    if (current.orientation == East)
                    {
                        t = grid[current.i +1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost, East,"F,");
                    }
                    else if ( current.orientation == South)
                    {
                        t = grid[current.i +1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+curves,East, "R,FL,");
                    }
                    else if (current.orientation == North){
                        t = grid[current.i +1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost+curves,East,"R,FR,");
                    }
                    else if (current.orientation == West){
                        t = grid[current.i +1][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + v_h_cost,West,"R,");
                    }
                    
                }*/
                 
                /*if (current.i -3 >= 0){
                    if (current.orientation == North)
                    {
                        t = grid[current.i -3][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+ curves, West, "R,R,R,FL,");
                    }
                    else if ( current.orientation == South)
                    {
                        t = grid[current.i -3][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+ curves, West, "R,R,R,FR,");
                    }
                    if (current.j -3 >=0)
                    {
                        if (current.orientation == East){
                            t = grid[current.i -3][current.j -3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+ curves, North, "R,R,R,RR,");
                        }
                        else if (current.orientation == North){
                            t = grid[current.i -3][current.j-3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+ curves, East, "R,R,R,RL,");
                        }
                    }
                    
                }
                if (current.j -3 >= 0){
                    if (current.orientation == East)
                    {
                        t = grid[current.i][current.j -3];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+curves, South, "R,R,R,FR,");
                    }
                    else if ( current.orientation == West)
                    {
                        t = grid[current.i][current.j -3];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+curves,South,"R,R,R,FL,");
                    }
                    if (current.i +3 < grid.length)
                    {
                        if(current.orientation == North){
                            t = grid[current.i+3][current.j -3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+curves,West,"R,R,R,RR,");
                        }
                        else if (current.orientation == West){
                            t = grid[current.i+3][current.j -3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+curves,North,"R,R,R,RL,");
                        }
                    }
                    
                }
                if (current.j + 3 < grid[0].length){
                    if (current.orientation == East)
                    {
                        t = grid[current.i ][current.j +3];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+curves,North,"R,R,R,FL,");
                    }
                    else if ( current.orientation == West)
                    {
                        t = grid[current.i ][current.j +3];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+curves,North,"R,R,R,FR,");
                    }
                    if (current.i -3 >= 0)
                    {
                        if (current.orientation == South)
                        {
                            t = grid[current.i-3][current.j +3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+curves,East,"R,R,R,RR,");
                        }
                        else if (current.orientation == East)
                        {
                            t = grid[current.i-3][current.j +3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+curves,South,"R,R,R,RL,");
                        }
                    }
                    
                }
                if (current.i + 3 < grid.length){
                    if (current.orientation == North){
                        t = grid[current.i +3][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+curves,East,"R,R,R,FR,");
                    }
                    else if ( current.orientation == South)
                    {
                        t = grid[current.i +3][current.j];
                        UpdateCostifNeeded(current, t, current.finalCost + 3*v_h_cost+curves,East, "R,R,R,FL,");
                    }
                    if (current.j + 3 < grid[0].length)
                    {
                        if (current.orientation == South){
                            t = grid[current.i +3][current.j+3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+curves,East, "R,R,R,RL,");
                        }
                        else if (current.orientation == East){
                            t = grid[current.i +3][current.j+3];
                            UpdateCostifNeeded(current, t, current.finalCost + 6*v_h_cost+curves,East, "R,R,R,RR,");
                        }
                    }
                }*/
            }
        }
        public void display(){
            System.out.println("Grid:");
            for (int i=0;i<grid.length;i++){
                for (int j=0; j<grid[i].length;j++){
                    if (i == startI && j == startJ){
                        System.out.print("S  ");
                    }
                    else if (i == endI && j == endJ)
                        System.out.print("E  ");
                    else if (grid[i][j] != null)
                        System.out.printf("%-3d", 0);
                    else
                        System.out.print("B  ");
                    
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
                        System.out.print("B  ");
                }System.out.println();
            }System.out.println();
        }
        public ArrayList<Node> displaySolution(){
            ArrayList<Node> trip = new ArrayList<Node>();
            if (closedCells[endI][endJ]){
                System.out.println("Solution:");
                Node current = grid[endI][endJ];
                trip.add(current);
                System.out.println(current);
                grid[current.i][current.j].solution = true;
                while(current.parent != null){
                    //System.out.println(current.parent.movement);
                    //System.out.println(current.parent.orientation);
                    System.out.println(" -> " + current.parent);
                    trip.add(current.parent);
                    grid[current.parent.i][current.parent.j].solution = true;
                    current = current.parent;
                }
                for (int i=0;i<grid.length;i++){
                    for (int j=0; j<grid[i].length;j++){
                        if (i == startI && j == startJ){
                            System.out.print("S  ");
                        }
                        else if (i == endI && j == endJ)
                            System.out.print("E  ");
                        else if (grid[i][j] != null)
                            System.out.printf("%-3s", grid[i][j].solution ? "X":"0");
                        else
                            System.out.print("B  ");
                        }System.out.println();
                }System.out.println();

            }else
                System.out.println("No possible Path");
            return trip;

        }
        public Node getSolutionNode()
        {
            if(closedCells[endI][endJ])
                return grid[endI][endJ];
            else
                System.out.println("No possible Path");
            return null;
        }
        public static void main(String[] args){
            AStar astar = new AStar(15, 15, 1, 1, 5, 6, new int[][]{
            }, North, South);
            astar.display();
            astar.process();
            astar.displayScores();
            astar.displaySolution();
        }
}
