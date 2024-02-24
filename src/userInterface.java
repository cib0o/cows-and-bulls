import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class userInterface extends JPanel implements KeyListener {
    private char[] inputBuffer = new char[4];
    private int inputBufferCount = 0;
    private MyPanel panel;
    static Game g = new Game();


    public userInterface() {

        g.requestCode(); //probably need to make a variable to vary between nc & lc, bandaid solution for tests

        //ToolKit is to get the information about the monitor and other hardware things.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setSize(width, height);
        setLayout(null);

        final int[] cows = {0};
        final int[] bulls = {0};

        // This is declared like this so its proportional to every screen it's on
        int cowBoundX = width / 32;
        int bullBoundX = width / 4 * 3;
        int cowBoundY = height / 2 - height / 8;
        int bullBoundY = height / 2 - height / 8;

        panel = new MyPanel(cows, bulls, cowBoundX, cowBoundY, bullBoundX, bullBoundY, inputBuffer, inputBufferCount);
        add(panel);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();

        setVisible(true);

        // this is pretty complicated but the reasons for the component listener is that when we switch from the main menu
        // to this class, we don't have focus in here for the actionListeners, this regains it.

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                requestFocusInWindow();
            }

        });
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        // Checking if the character is a digit and that there's still space in the input buffer
        if (Character.isDigit(c) && inputBufferCount < inputBuffer.length) {
            inputBuffer[inputBufferCount++] = c;
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Deleting a number when backspace is pressed and there's something to undo
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && inputBufferCount > 0) {
            inputBuffer[--inputBufferCount] = '\0';
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        // Else if enter is pressed and there are 4 numbers there, submit the guess
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && inputBufferCount == 4) {
            int[] cowsBulls = g.enterGuess(new String(inputBuffer));
            if (cowsBulls[1] == 4){
                System.out.println(g.guesses.size() + " is your score!");
            }
            panel.setCows(cowsBulls[0]);
            panel.setBulls(cowsBulls[1]);
            System.out.println(cowsBulls[0] + " cows " + cowsBulls[1] + " Bulls");

            //reset the input buffer

            inputBufferCount = 0;
            Arrays.fill(inputBuffer, '\0');
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {} // this is just here because of the "implements" at the top

    public static void main(String[] args) {
        g.requestCode();
    }
}

// new class for the panel which deals with displaying shit
class MyPanel extends JPanel {
    private int inputBufferCount;
    private final char[] inputBuffer;
    private BufferedImage cowImage, bullImage;
    int[] cows, bulls;
    int cowBoundX, cowBoundY, bullBoundX, bullBoundY;

    public MyPanel(int[] cows, int[] bulls, int cowBoundX, int cowBoundY, int bullBoundX, int bullBoundY, char[] inputBuffer, int inputBufferCount) {
        this.cows = cows;
        this.bulls = bulls;
        this.cowBoundX = cowBoundX;
        this.cowBoundY = cowBoundY;
        this.bullBoundX = bullBoundX;
        this.bullBoundY = bullBoundY;
        this.inputBuffer = inputBuffer;
        this.inputBufferCount = inputBufferCount;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        setBounds(0, 0, width, height);

        try {

            // reading from the git
            cowImage = ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/cow.png?raw=true"));
            bullImage = ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/bull.png?raw=true"));
            System.out.println("Images loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load images");
            e.printStackTrace();
        }
    }


    //next 3 methods are for setting stuff outside the panel class when the panel also needs to know about it or the information is in here already
    public void setInputBufferCount(int count) {
        this.inputBufferCount = count;
    }

    public void setCows(int cows){
        this.cows[0] = cows;
    }
    public void setBulls(int bulls){
        this.bulls[0] = bulls;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Font numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
        g.setFont(numberFont);

        FontMetrics metrics = g.getFontMetrics();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // this for loop draws the input rectangles and the numbers that are in the input buffer
        for(int i = 0; i<4; i++) {
            g.setColor(Color.darkGray);
            g.fillRect((((width / 2 )-160*2)+i*160) -5, (height / 4 * 2) - 5, 160, (150*5/3) + 10);
            g.setColor(Color.lightGray);
            g.fillRect(((width / 2 )-160*2)+i*160, height / 4 * 2, 150, 150*5/3);

            if (i < inputBufferCount) {
                String text = Character.toString(inputBuffer[i]);
                int textWidth = metrics.stringWidth(text);
                int textHeight = metrics.getHeight();
                int x = (((width / 2 )-160*2)+i*160) + (150 - textWidth) / 2;
                int y = (height / 4 * 2) + ((150*5/3) - textHeight) / 2 + metrics.getAscent();
                g.setColor(Color.black);
                g.drawString(text, x, y);
            }
        }

        int count = 0;
        for (Object o : userInterface.g.guesses) {
            count++;
        }

            for (int i = 0 ; i < count && i < 5; i++) {
                int x = (width / 2 ) - 72;
                int y = ((height / 4 * 2) + ((150 * 5 / 3)) / 2 ) - i*50 - 200;
                g2d.setColor(Color.black);
                Font guessFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
                g2d.setFont(guessFont);
                g2d.drawString(userInterface.g.guesses.get(userInterface.g.guesses.size() - i - 1).substring(0,4), x, y);

                g2d.setColor(Color.darkGray);
                Font guessFontCowBull = new Font(Font.SANS_SERIF, Font.BOLD, 38);
                g2d.setFont(guessFontCowBull);
                g2d.drawString("Cows:" + userInterface.g.guesses.get(userInterface.g.guesses.size() - i - 1).charAt(4), x-170, y);
                g2d.drawString("Bulls:" + userInterface.g.guesses.get(userInterface.g.guesses.size() - i - 1).charAt(5), x+150, y);
            }

        drawAnimals(g2d, cowImage, cows[0], cowBoundX, cowBoundY);
        drawAnimals(g2d, bullImage, bulls[0], bullBoundX, bullBoundY);
    }


    //drawing the animals in the same bounding box by finding the nearest square root rounded up and making a square of the images
    private void drawAnimals(Graphics2D g2d, BufferedImage image, int count, int boundX, int boundY) {
        int gridSize = (int) Math.ceil(Math.sqrt(count));
        int imageWidth = (gridSize > 0) ? 400 / gridSize : 0;
        int imageHeight = (gridSize > 0) ? 400 / gridSize : 0;

        for (int i = 0; i < count; i++) {
            int row = i / gridSize;
            int col = i % gridSize;
            int x = boundX + col * imageWidth;
            int y = boundY + row * imageHeight;
            g2d.drawImage(image, x, y, imageWidth, imageHeight, this);
        }
    }

}





