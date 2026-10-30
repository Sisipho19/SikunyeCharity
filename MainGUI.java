package za.ac.cput.mavenproject6.gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {
        initComponents();
        setTitle("Sikunye Charity - Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("SIKUNYE CHARITY SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setForeground(new Color(46, 134, 222));
        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnBeneficiary = new JButton("Manage Beneficiaries");
        btnBeneficiary.setBackground(new Color(52, 152, 219));
        btnBeneficiary.setForeground(Color.WHITE);
        btnBeneficiary.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnNeeds = new JButton("Manage Needs");
        btnNeeds.setBackground(new Color(46, 204, 113));
        btnNeeds.setForeground(Color.WHITE);
        btnNeeds.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnBeneficiaryNeeds = new JButton("Manage Beneficiary Needs");
        btnBeneficiaryNeeds.setBackground(new Color(241, 196, 15));
        btnBeneficiaryNeeds.setForeground(Color.WHITE);
        btnBeneficiaryNeeds.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(btnBeneficiary);
        panel.add(btnNeeds);
        panel.add(btnBeneficiaryNeeds);

        add(panel, BorderLayout.CENTER);

        //btnBeneficiary.addActionListener(e -> new BeneficiaryGUI());
        //btnNeeds.addActionListener(e -> new NeedsGUI());
       // btnBeneficiaryNeeds.addActionListener(e -> new BeneficiaryNeedsGUI());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}