/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.mavenproject6.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BeneficiaryGUI extends JFrame {

    private JTextField txtId, txtFirstName, txtLastName, txtFamilySize, txtAddress, txtLiving;
    private JTable tblBeneficiaries;
    private DefaultTableModel tableModel;
    //private BeneficiaryDAO dao = new BeneficiaryDAO();

    public BeneficiaryGUI() {
        initComponents();
        setTitle("Sikunye Charity - Beneficiary Management");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        //viewBeneficiaries();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Beneficiary Details"));

        inputPanel.add(new JLabel("Beneficiary ID:"));
        txtId = new JTextField(); inputPanel.add(txtId);
        inputPanel.add(new JLabel("First Name:"));
        txtFirstName = new JTextField(); inputPanel.add(txtFirstName);
        inputPanel.add(new JLabel("Last Name:"));
        txtLastName = new JTextField(); inputPanel.add(txtLastName);
        inputPanel.add(new JLabel("Family Size:"));
        txtFamilySize = new JTextField(); inputPanel.add(txtFamilySize);
        inputPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField(); inputPanel.add(txtAddress);
        inputPanel.add(new JLabel("Living Situation:"));
        txtLiving = new JTextField(); inputPanel.add(txtLiving);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        JButton btnAdd = createButton("ADD", new Color(46, 204, 113));
        JButton btnView = createButton("VIEW ALL", new Color(52, 152, 219));
        JButton btnUpdate = createButton("UPDATE", new Color(241, 196, 15));
        JButton btnDelete = createButton("DELETE", new Color(231, 76, 60));
        JButton btnClear = createButton("CLEAR", new Color(149, 165, 166));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        String[] columns = {"ID", "First Name", "Last Name", "Family Size", "Address", "Living Situation"};
        tableModel = new DefaultTableModel(columns, 0);
        tblBeneficiaries = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblBeneficiaries);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Beneficiary List"));
        scrollPane.setPreferredSize(new Dimension(800, 250));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

//        btnAdd.addActionListener(e -> addBeneficiary());
//        btnView.addActionListener(e -> viewBeneficiaries());
//        btnUpdate.addActionListener(e -> updateBeneficiary());
//        btnDelete.addActionListener(e -> deleteBeneficiary());
        btnClear.addActionListener(e -> clearFields());

        tblBeneficiaries.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblBeneficiaries.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtFirstName.setText(tableModel.getValueAt(row, 1).toString());
                    txtLastName.setText(tableModel.getValueAt(row, 2).toString());
                    txtFamilySize.setText(tableModel.getValueAt(row, 3).toString());
                    txtAddress.setText(tableModel.getValueAt(row, 4).toString());
                    txtLiving.setText(tableModel.getValueAt(row, 5).toString());
                }
            }
        });
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(85, 28));
        return btn;
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            BeneficiaryGUI myGUI =
                    new BeneficiaryGUI();

            myGUI.setVisible(true);
        });
    }

//    private void addBeneficiary() {
//        try {
//            Beneficiary b = new Beneficiary(
//                Integer.parseInt(txtId.getText().trim()),
//                txtFirstName.getText().trim(),
//                txtLastName.getText().trim(),
//                Integer.parseInt(txtFamilySize.getText().trim()),
//                txtAddress.getText().trim(),
//                txtLiving.getText().trim()
//            );
//            if (dao.addBeneficiary(b)) {
//                JOptionPane.showMessageDialog(this, "✅ Beneficiary added!");
//                clearFields();
//                viewBeneficiaries();
//            } else {
//                JOptionPane.showMessageDialog(this, "❌ Failed to add.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
//        }
//    }

//    private void viewBeneficiaries() {
//        tableModel.setRowCount(0);
//        List<Beneficiary> list = dao.getAllBeneficiaries();
//        for (Beneficiary b : list) {
//            tableModel.addRow(new Object[]{
//                b.getBeneficiaryID(), b.getFirstName(), b.getLastName(),
//                b.getFamilySize(), b.getAddress(), b.getLivingSituation()
//            });
//        }
//    }
//
//    private void updateBeneficiary() {
//        try {
//            Beneficiary b = new Beneficiary(
//                Integer.parseInt(txtId.getText().trim()),
//                txtFirstName.getText().trim(),
//                txtLastName.getText().trim(),
//                Integer.parseInt(txtFamilySize.getText().trim()),
//                txtAddress.getText().trim(),
//                txtLiving.getText().trim()
//            );
//            if (dao.updateBeneficiary(b)) {
//                JOptionPane.showMessageDialog(this, "✅ Beneficiary updated!");
//                clearFields();
//                viewBeneficiaries();
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
//        }
//    }
//
//    private void deleteBeneficiary() {
//        try {
//            int id = Integer.parseInt(txtId.getText().trim());
//            if (JOptionPane.showConfirmDialog(this, "Delete Beneficiary ID: " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//                if (dao.deleteBeneficiary(id)) {
//                    JOptionPane.showMessageDialog(this, "✅ Beneficiary deleted!");
//                    clearFields();
//                    viewBeneficiaries();
//                }
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Please select a beneficiary first.");
//        }
//    }

    private void clearFields() {
        txtId.setText(""); txtFirstName.setText(""); txtLastName.setText("");
        txtFamilySize.setText(""); txtAddress.setText(""); txtLiving.setText("");
    }
}