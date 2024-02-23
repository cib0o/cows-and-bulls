import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class mainMenu extends JFrame{
    private final CardLayout cardLayout = new CardLayout();

    public mainMenu() {
        setTitle("Bulls and Cows");
        setLayout(cardLayout);

        //ToolKit is to get the information about the monitor and other hardware things.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainMenuPanel = createMainMenuPanel();
        userInterface gameInterface = new userInterface();

        add(mainMenuPanel, "MainMenu");
        add(gameInterface, "GameInterface");

        cardLayout.show(getContentPane(), "MainMenu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
            title = ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/bulls%20and%20cows.png?raw=true"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2d.drawImage(title, width/32, height/64, 500, 500, this);

    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        setBounds(0, 0, width, height);

        JButton startButton = new JButton("Start");
        startButton.setBounds(width/32 + 125, height/64 + 510 , 250,75);
        startButton.addActionListener(e -> cardLayout.show(getContentPane(), "GameInterface"));
        panel.add(startButton);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(mainMenu::new);
    }
}
