import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SpinnerWheel extends JFrame {
    private ArrayList<String> names = new ArrayList<>();
    private JTextField nameField;
    private DefaultListModel<String> listModel;
    private JLabel resultLabel;

    public SpinnerWheel() {
        setTitle("Spinner Wheel");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameField = new JTextField(20);
        add(nameField);

        JButton addButton = new JButton("Add Name");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addName();
            }
        });

        listModel = new DefaultListModel<>();
        JList<String> nameList = new JList<>(listModel);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nameList.setPreferredSize(new Dimension(350, 150));
        add(new JScrollPane(nameList));

        JButton removeButton = new JButton("Remove Selected Name");
        add(removeButton);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeName(nameList.getSelectedIndex());
            }
        });

        JButton spinButton = new JButton("Spin the Wheel");
        add(spinButton);
        spinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spinWheel();
            }
        });

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(resultLabel);
    }

    private void addName() {
        String name = nameField.getText();
        if (!name.isEmpty()) {
            names.add(name);
            listModel.addElement(name);
            nameField.setText("");
        }
    }

    private void removeName(int index) {
        if (index >= 0 && index < names.size()) {
            names.remove(index);
            listModel.remove(index);
        }
    }

    private void spinWheel() {
        if (!names.isEmpty()) {
            Random random = new Random();
            String winner = names.get(random.nextInt(names.size()));
            resultLabel.setText("The winner is: " + winner);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SpinnerWheel().setVisible(true);
            }
        });
    }
}
