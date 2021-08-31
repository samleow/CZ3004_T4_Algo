package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.Position.Orientation;

public class readinput{
    public readinput(){
        try{
            File input = new File("src/input.txt"); //specify relative to src folder
            Scanner inputreader = new Scanner(input);
            List<Position> obstacles = new ArrayList<Position>(); 
            while (inputreader.hasNextLine())
            {
                String data = inputreader.nextLine();
                String[] obstacledata = data.split("\\s*,\\s*");
                if (obstacledata[2].equals("NORTH"))
                {
                    obstacles.add(new Position(Integer.parseInt(obstacledata[0]), Integer.parseInt(obstacledata[1]), Orientation.NORTH));
                }
                else if (obstacledata[2].equals("EAST"))
                {
                    obstacles.add(new Position(Integer.parseInt(obstacledata[0]), Integer.parseInt(obstacledata[1]), Orientation.EAST));
                }
                else if (obstacledata[2].equals("WEST"))
                {
                    obstacles.add(new Position(Integer.parseInt(obstacledata[0]), Integer.parseInt(obstacledata[1]), Orientation.WEST));
                }
                else if (obstacledata[2].equals("SOUTH"))
                {
                    obstacles.add(new Position(Integer.parseInt(obstacledata[0]), Integer.parseInt(obstacledata[1]), Orientation.SOUTH));
                }
                    
            }
            for (Position ac:obstacles)
            {
                System.out.println(ac.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }

    public static void main (String args[])
    {
        readinput a = new readinput();
    }

}

