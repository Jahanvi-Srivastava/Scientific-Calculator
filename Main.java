import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class AdvancedCalculator extends JFrame {
    private JTextField textField;
    private StringBuilder inputExpression;
    private ScriptEngine scriptEngine;

    public AdvancedCalculator() {
        setTitle("Advanced Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputExpression = new StringBuilder();
        scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "sqrt",
                "C", "←", "MC", "MR"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout());
        add(textField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            switch (buttonText) {
                case "=":
                    calculateResult();
                    break;
                case "sqrt":
                    calculateSqrt();
                    break;
                case "sin":
                case "cos":
                case "tan":
                    applyTrigonometricFunction(buttonText);
                    break;
                case "C":
                    clearInput();
                    break;
                case "←":
                    backspace();
                    break;
                case "MC":
                    clearMemory();
                    break;
                case "MR":
                    recallMemory();
                    break;
                default:
                    appendToInput(buttonText);
            }
        }

        private void calculateResult() {
            try {
                String expression = inputExpression.toString();
                Object result = scriptEngine.eval(expression);
                textField.setText(result.toString());
                inputExpression.setLength(0);
                inputExpression.append(result.toString());
            } catch (ScriptException ex) {
                textField.setText("Error");
            }
        }

        private void calculateSqrt() {
            try {
                double number = Double.parseDouble(inputExpression.toString());
                double result = Math.sqrt(number);
                textField.setText(Double.toString(result));
                inputExpression.setLength(0);
                inputExpression.append(result);
            } catch (NumberFormatException ex) {
                textField.setText("Error");
            }
        }

        private void applyTrigonometricFunction(String functionName) {
            try {
                double angle = Double.parseDouble(inputExpression.toString());
                switch (functionName) {
                    case "sin":
                        textField.setText(Double.toString(Math.sin(Math.toRadians(angle))));
                        break;
                    case "cos":
                        textField.setText(Double.toString(Math.cos(Math.toRadians(angle))));
                        break;
                    case "tan":
                        textField.setText(Double.toString(Math.tan(Math.toRadians(angle))));
                        break;
                }
                inputExpression.setLength(0);
                inputExpression.append(textField.getText());
            } catch (NumberFormatException ex) {
                textField.setText("Error");
            }
        }

        private void clearInput() {
            textField.setText("");
            inputExpression.setLength(0);
        }

        private void appendToInput(String text) {
            textField.setText(textField.getText() + text);
            inputExpression.append(text);
        }

        private void backspace() {
            String currentText = textField.getText();
            if (!currentText.isEmpty()) {
                textField.setText(currentText.substring(0, currentText.length() - 1));
                inputExpression.setLength(0);
                inputExpression.append(textField.getText());
            }
        }

        private void clearMemory() {
            // memory clearing function
        }

        private void recallMemory() {
            // memory recalling function
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdvancedCalculator calculator = new AdvancedCalculator();
            calculator.setVisible(true);
        });
    }
}
