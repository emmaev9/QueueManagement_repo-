import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUI extends JFrame {
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JLabel name;
    JTextField clients, queues, interval, minArrival, maxArrival, minService, maxService;
    JFrame frame;
    JPanel inputPanel, outputPanel;
    JButton start, stop;
    JTextArea display;
    JTextArea displayClients;

    public GUI() {
        frame = new JFrame("Queue management");
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel clintsPanel = new JPanel();
        inputPanel = inputPan(inputPanel);
        outputPanel = outputPan(outputPanel);
        buttonsPanel = buttonsPan(buttonsPanel);

        mouseMove();

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(outputPanel, BorderLayout.PAGE_END);
        frame.add(buttonsPanel, BorderLayout.PAGE_START);


        frame.setResizable(true);
        frame.setVisible(true);

    }

    JPanel inputPan(JPanel panel) {
        Font font = new Font("Calibri", Font.TYPE1_FONT, 15);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Panel p0 = new Panel();
        Font f3 = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        name = new JLabel("Queue Management");
        name.setFont(f3);
        p0.setLayout(new FlowLayout());
        p0.add(name);
        //panel.add(p0);

        Panel p1 = new Panel();
        l1 = new JLabel("Number of clients:");
        clients = new JTextField(5);
        l1.setFont(font);

        p1.add(l1);
        p1.add(clients);
        p1.setLayout(new FlowLayout());
        panel.add(p1);

        Panel p2 = new Panel();
        p2.setLayout(new FlowLayout());
        l2 = new JLabel("Number of queues:");
        l2.setFont(font);
        queues = new JTextField(5);
        p2.add(l2);
        p2.add(queues);
        panel.add(p2);

        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout());
        l3 = new JLabel("Simulation time:");
        l3.setFont(font);
        interval = new JTextField(5);
        p3.add(l3);
        p3.add(interval);
        panel.add(p3);

        Panel p4 = new Panel();
        p4.setLayout(new FlowLayout());
        l4 = new JLabel("Min arrival time:");
        l4.setFont(font);
        minArrival = new JTextField(5);
        l5 = new JLabel("Max arrival time:");
        l5.setFont(font);
        maxArrival = new JTextField(5);
        p4.add(l4);
        p4.add(minArrival);
        p4.add(l5);
        p4.add(maxArrival);
        panel.add(p4);

        Panel p5 = new Panel();
        p5.setLayout(new FlowLayout());
        l6 = new JLabel("Min service time:");
        l6.setFont(font);
        minService = new JTextField(5);
        l7 = new JLabel("Max service time:");
        l7.setFont(font);
        maxService = new JTextField(5);
        p5.add(l6);
        p5.add(minService);
        p5.add(l7);
        p5.add(maxService);
        panel.add(p5);
        panel.setBackground(new Color(150, 255, 200));
        return panel;
    }

    JPanel clientsPanel(JPanel panel) {
        JPanel middlePanel = new JPanel();
        displayClients = new JTextArea(10, 20);
        displayClients.setEditable(false); // set textArea non-editable
        JScrollPane scroll = new JScrollPane(displayClients);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        middlePanel.add(scroll);
        panel.add(middlePanel);
        panel.setBackground(new Color(150, 255, 200));
        return panel;
    }

    JPanel outputPan(JPanel panel) {
        JPanel middlePanel = new JPanel();
        display = new JTextArea(22, 40);
        display.setEditable(false); // set textArea non-editable
        JScrollPane scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        middlePanel.add(scroll);
        panel.add(middlePanel);
        panel.setBackground(new Color(150, 255, 200));

        JPanel middlePanel2 = new JPanel();
        displayClients = new JTextArea(13, 30);
        displayClients.setEditable(false); // set textArea non-editable
        JScrollPane scroll2 = new JScrollPane(displayClients);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        middlePanel2.add(scroll2);
        panel.add(middlePanel2);
        panel.setBackground(new Color(150, 255, 200));


        return panel;
    }

    JPanel buttonsPan(JPanel panel) {
        start = new JButton("Start");
        //stop = new JButton("Stop");
        start.setBackground(new Color(40, 200, 40));
        //stop.setBackground(new Color(40,200,40));
        panel.setLayout(new FlowLayout());
        panel.add(start);
        //panel.add(stop);
        panel.setBackground(new Color(150, 255, 200));
        return panel;
    }

    public void mouseMoveOneButton(JButton button) {
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(250, 10, 200));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new java.awt.Color(40, 200, 40));
            }

            //  public void mousePressed(MouseEvent evt) {
            //      button.setBackground(new Color(250, 10, 200));
            //  }
        });
    }

    public void mouseMove() {
        mouseMoveOneButton(start);
        //mouseMoveOneButton(stop);
    }

    String getNumberOfClients() {
        return clients.getText();
    }

    String getNumberOfQueues() {
        return queues.getText();
    }

    String getInterval() {
        return interval.getText();
    }

    String getMinArrivalTime() {
        return minArrival.getText();
    }

    String getMaxArrivalTime() {
        return maxArrival.getText();
    }

    String getMinService() {
        return minService.getText();
    }

    String getMaxService() {
        return maxService.getText();
    }

    public void setStartActionListener(ActionListener actionListener) {

        start.addActionListener(actionListener);


    }

    public void setStopActionListener(ActionListener actionListener) {
        stop.addActionListener(actionListener);
    }

    public void printConsole(String text) {
        display.append(text);
    }

    public void printClients(String text) {
        displayClients.append(text);
    }
}
