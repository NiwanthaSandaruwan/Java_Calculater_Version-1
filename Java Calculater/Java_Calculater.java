/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package java_calculater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author niwan
 */
public class Java_Calculater extends JFrame {

    private JTextField displayField;
    private String currentInput = "";

    public  Java_Calculater(){
        setTitle("Java Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // Set calculator look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getContentPane().setBackground(new Color(240, 240, 240));
    

        // Create display field
        displayField = new JTextField();
        displayField.setFont(new Font("Monospaced Bold", Font.PLAIN, 70));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(0, 0, 0));
        String[][] buttonLabels = {
            {"C", "+/-", "%", "/"},
            {"7", "8", "9", "*"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"0", ".", " ", "="}
        };
            
        Map<String, Color> buttonColors = new HashMap<>();
        buttonColors.put("/", new Color(255, 153, 0));
        buttonColors.put("*", new Color(255, 153, 0));
        buttonColors.put("-", new Color(255, 153, 0));
        buttonColors.put("+", new Color(255, 153, 0));
        buttonColors.put("=", new Color(255, 153, 0));
        
        buttonColors.put("C", new Color(102, 102, 102));  
        buttonColors.put("+/-", new Color(102, 102, 102));  
        buttonColors.put("%", new Color(102, 102, 102));    
        
        buttonColors.put("1", new Color(153, 153, 153));
        buttonColors.put("2", new Color(153, 153, 153)); 
        buttonColors.put("3", new Color(153, 153, 153)); 
        buttonColors.put("4", new Color(153, 153, 153)); 
        buttonColors.put("5", new Color(153, 153, 153)); 
        buttonColors.put("6", new Color(153, 153, 153)); 
        buttonColors.put("7", new Color(153, 153, 153)); 
        buttonColors.put("8", new Color(153, 153, 153)); 
        buttonColors.put("9", new Color(153, 153, 153)); 
        buttonColors.put("0", new Color(153, 153, 153)); 
        buttonColors.put(" ", new Color(153, 153, 153));
        buttonColors.put(".", new Color(153, 153, 153)); 
        
        for (String[] row : buttonLabels) {
            for (String label : row) {
                JButton button = new JButton(label);
                button.setFont(new Font("Lucida Grande", Font.BOLD, 24));
                Color buttonColor = buttonColors.getOrDefault(label, new Color(240, 240, 240));
                button.setBackground(buttonColor);
                button.setForeground(Color.WHITE);
                button.addActionListener(new ButtonClickListener());
                buttonPanel.add(button);
            }
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();
            if (buttonText.equals("=")) {
                try {
                    currentInput = String.valueOf(eval(currentInput));
                } catch (Exception ex) {
                    currentInput = "Error";
                }
            } else if (buttonText.equals("C")) {
                currentInput = "";
            } else if (buttonText.equals("+/-")) {
                // Implement +/- functionality here
            } else if (buttonText.equals("%")) {
                // Implement % functionality here
            } else {
                currentInput += buttonText;
            }
            displayField.setText(currentInput);
        }
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = expression.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Java_Calculater());
        // TODO code application logic here
    }
    
}
