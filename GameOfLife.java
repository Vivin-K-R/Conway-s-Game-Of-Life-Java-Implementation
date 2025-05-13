import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLife extends JPanel implements ActionListener {
    private  int cell = 15; //each cell is 15x15 pixels
    private  int width = 40;
    private  int height = 40;
    private boolean[][] cells = new boolean[height][width];
    private Timer timer;

    public GameOfLife() {
        //Total pixel width and height (output panel)
        //In this example it's 600x600 pixels
        setPreferredSize(new Dimension(width * cell, height * cell));
        setBackground(Color.WHITE);

        // Random initial state
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                cells[y][x] = Math.random() < 0.5; // Each cell has 50% chance to be alive or dead
            }
        }
        timer = new Timer(1000, this); // For every 1s actionPerformed is called
        timer.start();

    }

    //Game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean[][] nextGen = new boolean[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int neighbors = countLiveNeighbors(x, y);
                if (cells[y][x])
                {
                    nextGen[y][x] = neighbors == 2 || neighbors == 3;
                }
                else
                {
                    nextGen[y][x] = neighbors == 3;
                }
            }
        }

        cells = nextGen;
        repaint(); // Calls paint component
    }

    private int countLiveNeighbors(int x, int y) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++)
        {
            for (int dx = -1; dx <= 1; dx++)
            {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && cells[ny][nx]) // Handles out of bound conditions
                    count++;
            }
        }
        return count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                if (cells[y][x])
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * cell, y * cell, cell, cell);
                }
                else
                {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x * cell, y * cell, cell, cell);
                }
            }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GameOfLife());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
