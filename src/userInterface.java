import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class userInterface extends JPanel implements KeyListener {
    char[] inputBuffer = new char[4];
    private int inputBufferCount = 0;
    private final MyPanel panel;
    public static Player p = new Player();
    private final String gameType;

    public static Game g = new Game(p, "nc");
    private JFrame parentFrame;
    boolean playerWon = false;

    public userInterface(String gametype, JFrame parentFrame) {

        g.requestCode(gametype);
        this.gameType = gametype;
        this.parentFrame = parentFrame;

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

        panel = new MyPanel(this, cows, bulls, cowBoundX, cowBoundY, bullBoundX, bullBoundY, inputBuffer, inputBufferCount);
        add(panel);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        setBackground(Color.decode("#cfae76"));

        setVisible(true);

        // this is pretty complicated but the reasons for the component listener is that when we switch from the main menu
        // to this class, we don't have focus in here for the actionListeners, this regains it.

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                requestFocusInWindow();
            }
        });


        JButton show = new JButton("Show");
        JButton hint = new JButton("Hint");

        show.setVisible(true);
        hint.setVisible(true);

        show.addActionListener(e -> {
            g.showSolution();
            requestFocusInWindow();
        });

        hint.addActionListener(e -> {
            g.revealHint();
            requestFocusInWindow();
        }
        );

        show.setBounds(width/2 + 160, height-150 , 120,40);
        show.setBorder(BorderFactory.createEmptyBorder());
        show.setContentAreaFilled(true);
        panel.add(show);

        hint.setBounds(width/2 - 160, height-150 , 120,40);
        hint.setBorder(BorderFactory.createEmptyBorder());
        hint.setContentAreaFilled(true);
        panel.add(hint);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        // Checking if the character is a digit and that there's still space in the input buffer
        if (gameType.equals("nc")){
        if (Character.isDigit(c) && inputBufferCount < inputBuffer.length) {
            inputBuffer[inputBufferCount++] = c;
            g.buffer = inputBuffer;
            g.gameType = gameType;
            g.checkGuess(inputBuffer);
            System.out.println("Input buffer is: " + String.valueOf(inputBuffer));
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }
        } else if (Character.isAlphabetic(c) && inputBufferCount < inputBuffer.length) {
            inputBuffer[inputBufferCount++] = c;
            g.buffer = inputBuffer;
            g.gameType = gameType;
            g.checkGuess(inputBuffer);
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }

    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if(inputBufferCount < 1) {
                throw new IndexOutOfBoundsException(""){
                };
            }


            inputBufferCount--;
            inputBuffer = g.undoGuess();
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();

        } if (e.getKeyCode() == KeyEvent.VK_ENTER && inputBufferCount == 4) {

            int[] cowsBulls = g.enterGuess(new String(inputBuffer).trim(), gameType);
            if (cowsBulls[1] == 4){
                playerWon = true;
                panel.repaint();
            }
            panel.setCows(cowsBulls[0]);
            panel.setBulls(cowsBulls[1]);
            System.out.println(cowsBulls[0] + " cows " + cowsBulls[1] + " Bulls");


            Arrays.fill(inputBuffer, '\0');
            inputBufferCount = 0;
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            CardLayout cl = (CardLayout)(parentFrame.getContentPane().getLayout());
            cl.show(parentFrame.getContentPane(), "MainMenu");
        }


    }



    @Override
    public void keyReleased(KeyEvent e) {} // this is just here because of the "implements" at the top

    public static void main(String[] args) {

    }
}

// new class for the panel which deals with displaying shit
class MyPanel extends JPanel {
    private int inputBufferCount;
    private final char[] inputBuffer;
    private BufferedImage cowImage, bullImage;
    int[] cows, bulls;
    int cowBoundX, cowBoundY, bullBoundX, bullBoundY;
    private userInterface ui;

    public MyPanel(userInterface ui, int[] cows, int[] bulls, int cowBoundX, int cowBoundY, int bullBoundX, int bullBoundY, char[] inputBuffer, int inputBufferCount) {
        this.ui = ui;
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
        setLayout(null);

        setBounds(0, 0, width, height);
        setBackground(Color.decode("#cfae76"));

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

        if (ui.playerWon) {
            drawWinScreen(g);
        } else {
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

        int centerX = screenSize.width / 2;


        int historyBoxWidth = 500;
        int historyBoxStartX = centerX - (historyBoxWidth / 2);
        g2d.setColor(Color.darkGray);
        g2d.fillRect(historyBoxStartX - 5, ((height / 4 * 2) + ((150 * 5 / 3)) / 2 ) - 250 - 205, 510, 270);
        g2d.setColor(Color.lightGray);
        g2d.fillRect(historyBoxStartX, ((height / 4 * 2) + ((150 * 5 / 3)) / 2 ) - 250 - 200, 500, 260);


        int count = 0;
        for (Object ignored : userInterface.g.guesses) {
            count++;
        }



        int maxTextWidth = historyBoxWidth - 20;
        int startY = ((height / 4 * 2) + ((150 * 5 / 3)) / 2 ) - 200;
        g2d.setColor(Color.black);




        for (int i = 0 ; i < userInterface.g.guesses.size() && i < 5; i++) {
            String guessText = userInterface.g.guesses.get(userInterface.g.guesses.size() - i - 1).substring(0,4);
            Font guessFontCowBull = new Font(Font.SANS_SERIF, Font.BOLD, 38);

            String cowsText = "Cows:" + userInterface.g.guesses.get(userInterface.g.guesses.size() - i - 1).charAt(4);
            String bullsText = "Bulls:" + userInterface.g.guesses.get(userInterface.g.guesses.size() - i - 1).charAt(5);



            int textWidthGuess = metrics.stringWidth(guessText);




            int textWidthCows = metrics.stringWidth(cowsText);
            int textWidthBulls = metrics.stringWidth(bullsText);

            int xGuess = historyBoxStartX + (maxTextWidth - textWidthGuess) / 2 + 10;
            int xCows = historyBoxStartX + (maxTextWidth - textWidthCows) / 4 - 50;
            int xBulls = historyBoxStartX + 3 * (maxTextWidth - textWidthBulls) / 4 + 100;

            int y = startY - i * 50;


            g2d.setFont(numberFont);
            g2d.setColor(Color.black);
            g2d.drawString(guessText, xGuess, y);
            g2d.setFont(guessFontCowBull);
            g2d.setColor(Color.darkGray);
            g2d.drawString(cowsText, xCows, y);
            g2d.drawString(bullsText, xBulls, y);
        }

        int x = (width / 2 ) - 200;
        int y = ((height / 4 * 2) + ((150 * 5 / 3)) / 2 ) - 5*50 - 220;

        g2d.setColor(Color.black);
        g.setFont(numberFont);
        g2d.drawString("Your score is: " + userInterface.g.guesses.size(), x, y);


        drawAnimals(g2d, cowImage, cows[0], cowBoundX, cowBoundY);
        drawAnimals(g2d, bullImage, bulls[0], bullBoundX, bullBoundY);
    }}

    private void drawWinScreen(Graphics g) {
        // Set the font and center the text
        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String winText = "You Won!";
        int x = (getWidth() - fm.stringWidth(winText)) / 2;
        int y = (getHeight() / 2) + fm.getAscent() / 4;

        g.drawString(winText, x, y);
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






