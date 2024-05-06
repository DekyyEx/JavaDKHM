import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JChat extends JFrame{
    private static final int HEIGHT = 555;
    private static final int WIDTH = 507;
    private static final int WINDOW_POSX = 650;
    private static final int WINDOW_POSY = 100;


    private static final String LOG_FILE = "chat.txt";
    private final JTextArea textOutput = new JTextArea("");
    private final JTextField textInput = new JTextField();

    JChat(String login) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CHAT WINDOW");
        setBounds(WINDOW_POSX, WINDOW_POSY, WIDTH, HEIGHT);

        textOutput.setEditable(false);
        textOutput.setBackground(Color.GRAY);

        loadChatHistory();

        JPanel grid = new JPanel(new GridLayout(4, 1));
        grid.add(textOutput);
        JLabel label = new JLabel("ВВЕДИТЕ СООБЩЕНИЕ ");
        grid.add(label);
        grid.add(textInput);
        JButton buttonConnect = new JButton("ОТПРАВИТЬ");
        grid.add(buttonConnect);

        buttonConnect.addActionListener(e -> sendMessage(login));

        textInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(login);
                }
            }
        });

        add(grid);
        setVisible(true);
    }

    private void sendMessage(String login) {
        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss")) + " " + login + " : " + textInput.getText() + "\n";
        textOutput.append(message);
        saveMessageToFile(message);
        textInput.setText("");
    }

    private void saveMessageToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChatHistory() {
        File file = new File(LOG_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    textOutput.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
