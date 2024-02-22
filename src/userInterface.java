import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class userInterface extends JFrame implements KeyListener {
    private char[] inputBuffer = new char[4];
    private int inputBufferCount = 0;
    private MyPanel panel;

    public userInterface() {
        setTitle("Bulls and Cows");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        final int[] cows = {0};
        final int[] bulls = {0};

        int cowBoundX = width / 32;
        int bullBoundX = width / 4 * 3;
        int cowBoundY = height / 2 - height / 8;
        int bullBoundY = height / 2 - height / 8;

        panel = new MyPanel(cows, bulls, cowBoundX, cowBoundY, bullBoundX, bullBoundY, inputBuffer, inputBufferCount);
        add(panel);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isDigit(c) && inputBufferCount < inputBuffer.length) {
            inputBuffer[inputBufferCount++] = c;
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && inputBufferCount > 0) {
            inputBuffer[--inputBufferCount] = '\0';
            panel.setInputBufferCount(inputBufferCount);
            panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(userInterface::new);
    }
}

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
            cowImage = ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/cow.png?raw=true"));
            bullImage = ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/bull.png?raw=true"));
            System.out.println("Images loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load images");
            e.printStackTrace();
        }
    }

    public void setInputBufferCount(int count) {
        this.inputBufferCount = count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Font numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
        g.setFont(numberFont);

        cows[0] = 1;
        bulls[0] = 1;

        FontMetrics metrics = g.getFontMetrics();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;


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
        drawAnimals(g2d, cowImage, cows[0], cowBoundX, cowBoundY);
        drawAnimals(g2d, bullImage, bulls[0], bullBoundX, bullBoundY);
    }

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





