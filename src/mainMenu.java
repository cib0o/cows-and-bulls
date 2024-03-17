import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class mainMenu extends JFrame{
    private final CardLayout cardLayout = new CardLayout();
    public Player p = null;

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

    private void switchUserInterface(String gameType) throws IOException {
        userInterface gameInterface = new userInterface(gameType, this, p); // Create new instance with gameType
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
        drawLeaderboard(g);
        drawPersonalStats(g);

        Font numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        g.setFont(numberFont);
        g.setColor(Color.black);
        if(p != null) {
            g.drawString("Logged in as: " + p.username, width - 300, 100);
        }


        revalidate();

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
        JButton help = new JButton();
        JButton login = new JButton();



        numberGame.setVisible(false);
        wordGame.setVisible(false);
        back.setVisible(false);


        startButton.setBounds(width/32 + 125, height/64 + 510 , 250,75);

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            numberGame.setVisible(true);
            wordGame.setVisible(true);
            back.setVisible(true);
            Timer timer = new Timer(1, ae -> testRepaint());
            timer.setRepeats(false);
            timer.start();
        });

        back.addActionListener(e -> {
            startButton.setVisible(true);
            numberGame.setVisible(false);
            wordGame.setVisible(false);
            back.setVisible(false);
            Timer timer = new Timer(1, ae -> testRepaint());
            timer.setRepeats(false);
            timer.start();
        });



        startButton.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_start(2).png?raw=true"))));
        numberGame.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_number-game(1).png?raw=true"))));
        wordGame.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_word-game(1).png?raw=true"))));
        back.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_back.png?raw=true"))));
        login.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_login.png?raw=true"))));
        help.setIcon(new ImageIcon(ImageIO.read(new URL("https://github.com/cib0o/cows-and-bulls/blob/master/src/Images/button_help.png?raw=true"))));
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

        help.setBounds(width-350, height - 175, 250, 75);
        help.setBorder(BorderFactory.createEmptyBorder());
        help.setContentAreaFilled(false);
        panel.add(help);

        login.setBounds(width - 350, 75, 250, 75);
        login.setBorder(BorderFactory.createEmptyBorder());
        login.setContentAreaFilled(false);
        panel.add(login);


        numberGame.addActionListener(e -> {
            try {
                switchUserInterface("nc");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        wordGame.addActionListener(e -> {
            try {
                switchUserInterface("lc");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        login.addActionListener(e -> login());



        return panel;
    }

    public void testRepaint(){
        System.out.println("Im repainting!");
        repaint();
        revalidate();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new mainMenu();
            } catch (IOException e) {
                e.printStackTrace(); }
        });
    }

    public void drawLeaderboard(Graphics g){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        g.setColor(Color.darkGray);
        g.fillRect(width/32 + 545, height/12 - 5,460,610);
        g.setColor(Color.lightGray);
        g.fillRect(width/32 + 550, height/12,450,600);
        g.setColor(Color.GRAY);
        g.fillRect(width/32 + 550, height/12,450,100);

        Font numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
        g.setFont(numberFont);
        g.setColor(Color.black);
        g.drawString("Leaderboard", width/32 + 620, height/12 +70);

        numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        g.setFont(numberFont);
        g.setColor(Color.black);
        g.drawString("Name |  Won Games  |  Bulls  |  Cows", width/32 + 565, height/12 +150);
        g.drawString("--------------------------------------------------------", width/32 + 550, height/12 +170);
    }

    public void drawPersonalStats(Graphics g){
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;

            g.setColor(Color.darkGray);
            g.fillRect(width/32 + 545, height/12 + 620,460,300);
            g.setColor(Color.lightGray);
            g.fillRect(width/32 + 550, height/12 + 625,450,290);
            g.setColor(Color.GRAY);
            g.fillRect(width/32 + 550, height/12 + 625,450,100);


        Font numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
        g.setFont(numberFont);
        g.setColor(Color.black);
        g.drawString("Personal Stats", width/32 + 600, height/12 +690);


        numberFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        g.setFont(numberFont);
        g.setColor(Color.black);

        if (this.p == null){
            g.drawString("Not Logged in.", width/32 + 680, height/12 +820);
        } else{
            g.drawString("Bulls: " + p.getBulls(), width/32 + 600, height/12 +750);
            g.drawString("Cows: " + p.getCows(), width/32 + 600, height/12 +750 + 24);
            g.drawString("Attempted: " + p.getCodesAttempted(), width/32 + 600, height/12 +750 + 48);
            g.drawString("Solved: " + p.getCodesDeciphered(), width/32 + 600, height/12 +750 + 70);
            g.drawString("Guesses: " + p.numberOfGuesses, width/32 + 600, height/12 +750 + 94);
        }
    }

    public void login(){
        String input = JOptionPane.showInputDialog(null, "Enter your Username:");
        if (existsAccount(input)){
            this.p = new Player(input);
            repaint();
            return;
        }
System.out.println("PLAYER doES NOT EXIST");
    }

    public boolean existsAccount(String input) {
        String filePath = "src/players.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                String name = parts[0];

                if (input.equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(null, "Match found: " + line);
                    return true;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

        public void createAccount(){

    }

    //[player][bulls][cows][attempted][solved][guesses][save]
}
