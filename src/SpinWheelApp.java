import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinWheelApp extends JFrame {

    private List<String> namesList = new ArrayList<>();
    private JPanel wheelPanel;
    private JLabel resultLabel;

    public SpinWheelApp() {
        super("Spin Wheel App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a custom component for spinning wheel
        wheelPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw wheel background
                g2.setColor(Color.WHITE);
                g2.fillOval(50, 50, 500, 500);

                // Draw wheel border
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(50, 50, 500, 500);

                // Draw separation lines and names
                int radius = 250;
                int centerX = 300;
                int centerY = 300;
                int numNames = namesList.size();
                double angleStep = 2 * Math.PI / numNames;
                FontMetrics fm = g2.getFontMetrics();
                int labelHeight = fm.getHeight();

                for (int i = 0; i < numNames; i++) {
                    double angle = i * angleStep - Math.PI / 2; // Start from top

                    // Calculate line end position
                    int lineX = (int) (centerX + radius * Math.cos(angle));
                    int lineY = (int) (centerY + radius * Math.sin(angle));

                    // Draw separation line
                    g2.drawLine(centerX, centerY, lineX, lineY);

                    // Calculate position to ensure names stay within the circle
                    double midAngle = (i + 0.5) * angleStep - Math.PI / 2;
                    int labelWidth = fm.stringWidth(namesList.get(i));
                    int nameX = (int) (centerX + (radius - 50) * Math.cos(midAngle)) - labelWidth / 2;
                    int nameY = (int) (centerY + (radius - 50) * Math.sin(midAngle)) + labelHeight / 4;

                    g2.drawString(namesList.get(i), nameX, nameY);
                }
            }
        };
        wheelPanel.setPreferredSize(new Dimension(600, 600));

        // Add components to the main panel
        mainPanel.add(wheelPanel, BorderLayout.CENTER);

        // Panel for input and buttons
        JPanel controlPanel = new JPanel(new FlowLayout());
        JTextField nameField = new JTextField(20);
        JButton addButton = new JButton("Add Name");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    addName(name);
                    nameField.setText("");
                }
            }
        });
        controlPanel.add(nameField);
        controlPanel.add(addButton);

        // Spin button
        JButton spinButton = new JButton("Spin!");
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinWheel();
            }
        });
        controlPanel.add(spinButton);

        // Result label
        resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        controlPanel.add(resultLabel);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public void addName(String name) {
        namesList.add(name);
        wheelPanel.repaint(); // Refresh the wheel display
    }

    private void spinWheel() {
        if (namesList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add names before spinning!");
            return;
        }

        Random random = new Random();
        int index = random.nextInt(namesList.size());
        String winner = namesList.get(index);
        resultLabel.setText("Winner: " + winner);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpinWheelApp::new);
    }
}
