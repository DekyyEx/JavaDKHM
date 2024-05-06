package hm1;

import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JFrame{
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 100;

    private final JTextField loginField = new JTextField();

    ChatWindow(){

        setTitle("ПОДКЛЮЧЕНИЕ К СЕРВЕРУ");
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        JPanel panel = new JPanel(new GridLayout(9, 1));

        JLabel jlb1 = new JLabel("Введите логин: ");
        panel.add(jlb1,BorderLayout.CENTER);
        panel.add(loginField,BorderLayout.CENTER);
        JLabel jlb2 = new JLabel("Введите пароль: ");
        JTextField jtf2 = new JTextField();
        panel.add(jlb2,BorderLayout.CENTER);
        panel.add(jtf2,BorderLayout.CENTER);
        JLabel jlb3 = new JLabel("Введите IP адрес сервера: ");
        JTextField jtf3 = new JTextField();
        panel.add(jlb3,BorderLayout.CENTER);
        panel.add(jtf3,BorderLayout.CENTER);
        JLabel jlb4 = new JLabel("Введите номер порта: ");
        JTextField jtf4 = new JTextField();
        panel.add(jlb4,BorderLayout.CENTER);
        panel.add(jtf4,BorderLayout.CENTER);

        JButton btnLogin = new JButton("Подключиться");

        panel.add(btnLogin,BorderLayout.AFTER_LINE_ENDS);



        add(panel);
        btnLogin.addActionListener(e -> {
            String login = loginField.getText().trim();
            new JChat(login);
        });

        setVisible(true);
    }
}

