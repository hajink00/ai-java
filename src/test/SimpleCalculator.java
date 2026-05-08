package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Custom JPanel for gradient background
class GradientPanel extends JPanel {
    private Color startColor;
    private Color endColor;

    public GradientPanel(Color start, Color end) {
        this.startColor = start;
        this.endColor = end;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Paint from top (startColor) to bottom (endColor)
        java.awt.Paint paint = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(paint);

        g2d.fillRect(0, 0, width, height);
    }
}

public class SimpleCalculator extends JFrame implements ActionListener {

    private JTextField num1Field;
    private JTextField num2Field;
    private JLabel resultLabel;
    private JButton clearButton;
    private JButton decimalButton;
    private JButton[] digitButtons = new JButton[10]; // 0-9
    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;

    private boolean isNum1Active = true; // Tracks which text field is currently active for input

    // Predefined colors for buttons
    private Color[] buttonColors = {
        new Color(255, 102, 102), // Red for 0 (example)
        new Color(255, 153, 51),  // Orange for 1
        new Color(255, 204, 51),  // Yellow for 2
        new Color(153, 255, 51),  // Light Green for 3
        new Color(51, 255, 255),  // Cyan for 4
        new Color(51, 153, 255),  // Blue for 5
        new Color(153, 51, 255),  // Purple for 6
        new Color(255, 51, 153),  // Pink for 7
        new Color(102, 102, 102), // Dark Gray for 8
        new Color(139, 69, 19)    // Brown for 9
    };
    private Color clearButtonColor = new Color(220, 20, 60); // Crimson Red for Clear
    private Color operatorButtonColor = new Color(70, 130, 180); // Steel Blue for operators
    private Color operatorTextColor = Color.WHITE;
    private Color decimalButtonColor = new Color(192, 192, 192); // Silver for decimal

    public SimpleCalculator() {
        setTitle("간단 계산기");
        setSize(450, 500); // Increased size for keypad
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use GradientPanel as the main content pane
        GradientPanel mainPanel = new GradientPanel(new Color(220, 230, 240), new Color(245, 245, 245)); // Light blue to white gradient
        mainPanel.setLayout(new BorderLayout());

        // --- Input Panel ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(240, 240, 240)); // Lighter gray for input panel

        num1Field = new JTextField(10);
        num2Field = new JTextField(10);
        JLabel num1Label = new JLabel("숫자 1:");
        JLabel num2Label = new JLabel("숫자 2:");
        
        num1Label.setForeground(new Color(0x333333));
        num2Label.setForeground(new Color(0x333333));

        inputPanel.add(num1Label);
        inputPanel.add(num1Field);
        inputPanel.add(num2Label);
        inputPanel.add(num2Field);

        // --- Calculator Panel (Keypad and Operators) ---
        JPanel calcPanel = new JPanel(new BorderLayout());
        calcPanel.setBackground(new Color(230, 230, 230)); // Background for calc area

        // Keypad Panel
        JPanel keypadPanel = new JPanel(new GridLayout(5, 4, 5, 5)); // 5 rows, 4 columns for keypad
        keypadPanel.setBackground(new Color(220, 220, 220)); // Background for keypad

        // Add digit buttons (0-9)
        for (int i = 0; i < 10; i++) {
            digitButtons[i] = new JButton(String.valueOf(i));
            digitButtons[i].setBackground(buttonColors[i]); // Assign unique color
            digitButtons[i].setForeground(Color.WHITE); // White text for digits
            digitButtons[i].setFont(new Font("SansSerif", Font.BOLD, 16));
            digitButtons[i].addActionListener(this);
        }

        // Add decimal and clear buttons
        decimalButton = new JButton(".");
        decimalButton.setBackground(decimalButtonColor);
        decimalButton.setForeground(Color.BLACK);
        decimalButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        decimalButton.addActionListener(this);

        clearButton = new JButton("C");
        clearButton.setBackground(clearButtonColor);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        clearButton.addActionListener(this);

        // Arrangement of keypad buttons in the grid
        keypadPanel.add(clearButton);
        keypadPanel.add(decimalButton);
        keypadPanel.add(new JLabel()); // Placeholder
        keypadPanel.add(new JLabel()); // Placeholder

        keypadPanel.add(digitButtons[7]);
        keypadPanel.add(digitButtons[8]);
        keypadPanel.add(digitButtons[9]);
        keypadPanel.add(new JLabel()); // Placeholder

        keypadPanel.add(digitButtons[4]);
        keypadPanel.add(digitButtons[5]);
        keypadPanel.add(digitButtons[6]);
        keypadPanel.add(new JLabel()); // Placeholder

        keypadPanel.add(digitButtons[1]);
        keypadPanel.add(digitButtons[2]);
        keypadPanel.add(digitButtons[3]);
        keypadPanel.add(new JLabel()); // Placeholder

        keypadPanel.add(digitButtons[0]); // Place '0'
        keypadPanel.add(new JLabel()); // Placeholder
        keypadPanel.add(new JLabel()); // Placeholder
        keypadPanel.add(new JLabel()); // Placeholder

        // Operator Buttons Panel
        JPanel operatorPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 rows, 1 column for operators
        operatorPanel.setBackground(new Color(210, 210, 210)); // Slightly darker background for operators

        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");

        // Style operator buttons
        addButton.setBackground(operatorButtonColor);
        addButton.setForeground(operatorTextColor);
        subtractButton.setBackground(operatorButtonColor);
        subtractButton.setForeground(operatorTextColor);
        multiplyButton.setBackground(operatorButtonColor);
        multiplyButton.setForeground(operatorTextColor);
        divideButton.setBackground(operatorButtonColor);
        divideButton.setForeground(operatorTextColor);
        
        addButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        subtractButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        multiplyButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        divideButton.setFont(new Font("SansSerif", Font.BOLD, 16));


        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);

        operatorPanel.add(addButton);
        operatorPanel.add(subtractButton);
        operatorPanel.add(multiplyButton);
        operatorPanel.add(divideButton);

        // Combine keypad and operators in the calcPanel
        calcPanel.add(keypadPanel, BorderLayout.CENTER);
        calcPanel.add(operatorPanel, BorderLayout.EAST);

        // --- Result Panel ---
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.setBackground(new Color(200, 200, 200)); // Gray background for result

        resultLabel = new JLabel("결과: ");
        resultLabel.setForeground(Color.DARK_GRAY);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18)); // Larger, bold font

        resultPanel.add(resultLabel);

        // Add panels to the main content pane (GradientPanel)
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(calcPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel); // Set the GradientPanel as the content pane

        setLocationRelativeTo(null); // Center the frame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Handle digit buttons
        for (int i = 0; i < 10; i++) {
            if (source == digitButtons[i]) {
                if (isNum1Active) {
                    num1Field.setText(num1Field.getText() + i);
                } else {
                    num2Field.setText(num2Field.getText() + i);
                }
                return;
            }
        }

        // Handle decimal button
        if (source == decimalButton) {
            if (isNum1Active) {
                if (!num1Field.getText().contains(".")) {
                    num1Field.setText(num1Field.getText() + ".");
                }
            } else {
                if (!num2Field.getText().contains(".")) {
                    num2Field.setText(num2Field.getText() + ".");
                }
            }
            return;
        }

        // Handle Clear button
        if (source == clearButton) {
            num1Field.setText("");
            num2Field.setText("");
            resultLabel.setText("결과: ");
            isNum1Active = true; // Reset to input for num1
            return;
        }

        // Handle Operator buttons (+, -, *, /)
        if (source == addButton || source == subtractButton || source == multiplyButton || source == divideButton) {
            // Check if both input fields have valid numbers
            boolean num1Present = !num1Field.getText().isEmpty();
            boolean num2Present = !num2Field.getText().isEmpty();
            
            if (num1Present && num2Present) {
                // Both numbers are entered, perform calculation
                try {
                    double num1 = Double.parseDouble(num1Field.getText());
                    double num2 = Double.parseDouble(num2Field.getText());
                    double result = 0;
                    String operator = ((JButton)source).getText(); // Get operator from the clicked button

                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 == 0) {
                                resultLabel.setText("오류: 0으로 나눌 수 없습니다.");
                                return; // Stop here if division by zero
                            }
                            result = num1 / num2;
                            break;
                    }
                    
                    resultLabel.setText("결과: " + result);
                    
                    // Clear fields and reset for a new calculation
                    num1Field.setText("");
                    num2Field.setText("");
                    isNum1Active = true; // Reset to input for num1

                } catch (NumberFormatException ex) {
                    resultLabel.setText("잘못된 입력입니다.");
                } catch (Exception ex) {
                    resultLabel.setText("오류가 발생했습니다.");
                    ex.printStackTrace();
                }
            } else if (num1Present) {
                // If only num1 is entered and an operator is pressed, prepare for num2 input
                isNum1Active = false;
            }
            // If num1 is empty, pressing an operator does nothing until num1 is entered.
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculator calculator = new SimpleCalculator();
            calculator.setVisible(true);
        });
    }
}
