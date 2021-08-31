package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readinput{
    public readinput(){
        try{
            File input = new File("src/input.txt"); //specify relative to src folder
            Scanner inputreader = new Scanner(input);
            while (inputreader.hasNextLine())
            {
                String data = inputreader.nextLine();
                String[] obstacledata = data.split("\\s*,\\s*");
                System.out.println(obstacledata[0]); //create obstacles here
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

