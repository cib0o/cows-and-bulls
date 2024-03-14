import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class mainMenu extends JFrame{
    private final CardLayout cardLayout = new CardLayout();

    public mainMenu() throws IOException {
        setTitle("Bulls and Cows");
        setLayout(cardLayout);
        setBackground(Color.decode("#cfae76"));

        //ToolKit is to get the information about the monitor and other hardware things.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainMenuPanel = createMainMenuPanel();

        add(mainMenuPanel, "MainMenu");

        cardLayout.show(getContentPane(), "MainMenu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void switchUserInterface(String gameType) {
        Player p = new Player(); // temp before we get the log in
        userInterface gameInterface = new userInterface(gameType); // Create new instance with gameType
        getContentPane().add(gameInterface, "GameInterface"); // Add to CardLayout
        cardLayout.show(getContentPane(), "GameInterface"); // Switch to the new interface
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        BufferedImage title;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        try {
            title = ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/bulls%20and%20cows.png?raw=true"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2d.drawImage(title, width/32, height/64, 500, 500, this);

    }

    private JPanel createMainMenuPanel() throws IOException {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#cfae76"));


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        setBounds(0, 0, width, height);

        JButton startButton = new JButton();
        JButton numberGame = new JButton();
        JButton wordGame = new JButton();
        JButton back = new JButton();


        numberGame.setVisible(false);
        wordGame.setVisible(false);
        back.setVisible(false);


        startButton.setBounds(width/32 + 125, height/64 + 510 , 250,75);

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            numberGame.setVisible(true);
            wordGame.setVisible(true);
            back.setVisible(true);
        });

        back.addActionListener(e -> {
            startButton.setVisible(true);
            numberGame.setVisible(false);
            wordGame.setVisible(false);
            back.setVisible(false);
        });



        startButton.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_start(2).png?raw=true"))));
        numberGame.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_number-game(1).png?raw=true"))));
        wordGame.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_word-game(1).png?raw=true"))));
        back.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_back.png?raw=true"))));
        panel.add(startButton);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setContentAreaFilled(false);

        numberGame.setBounds(width/32 + 125, height/64 + 510 , 250,75);
        numberGame.setBorder(BorderFactory.createEmptyBorder());
        numberGame.setContentAreaFilled(false);
        panel.add(numberGame);

        wordGame.setBounds(width/32 + 125, height/64 + 510 + 80 , 250,75);
        wordGame.setBorder(BorderFactory.createEmptyBorder());
        wordGame.setContentAreaFilled(false);
        panel.add(wordGame);

        back.setBounds(width/32 + 125, height/64 + 510 + 160 , 250,75);
        back.setBorder(BorderFactory.createEmptyBorder());
        back.setContentAreaFilled(false);
        panel.add(back);


        numberGame.addActionListener(e -> switchUserInterface("nc"));
        wordGame.addActionListener(e -> switchUserInterface("lc"));

        return panel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new mainMenu();
            } catch (IOException e) {
                e.printStackTrace(); }
        });
    }
}
