package main;

import mazeObjects.Maze;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel{

    private JFrame frame = new JFrame("A* Pathfinder Implementation");


    private static Main main = new Main();
    private Maze maze;

    private ImageLoader finder = new ImageLoader();


    private Boolean started = false; // To prevent painting null pointer if the path finding code takes a long time.




    public void paintComponent(Graphics g){
        if(!started){ // If the maze route hasn't been calculated yet, don't render any graphics.
            return;
        }
        main.maze.render((Graphics2D) g); // The rendering for each object is done through the object itself. The maze object handles its own rendering.
    }


    public static void main(String[] args) throws InterruptedException {
        main.frame.add(main); // Sets up the graphical frame.
        main.frame.setVisible(true);
        main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        main.maze = main.finder.loadMaze("Mazes/ObstacleMaze.png"); // Change this part to solve a different maze.
        main.maze.solve();

        main.started = true;

        main.repaint(); // To save on performance when the program is open.
        // Instead of rendering a new image every interval, as nothing is going to change, one graphical display is made once then used for the rest of the duration of the program.
        // I estimate this method for graphics halves processor usage when program is open.



    }
}
