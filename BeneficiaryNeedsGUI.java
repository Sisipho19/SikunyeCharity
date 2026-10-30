/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package za.ac.cput.mavenproject6.gui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BeneficiaryNeedsGUI extends JFrame {

    private JComboBox<String> cmbBeneficiaries;
    private JComboBox<String> cmbNeeds;
    private JSpinner spinnerQuantity;
    private JButton btnAssign, btnRemove, btnRefresh;
    private JList<String> listAssignedNeeds;
    private DefaultListModel<String> listModel;
    private JTable tblBeneficiaries;
    private DefaultTableModel tableModel;

//    private BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
//    private NeedsDAO needsDAO = new NeedsDAO();

    public BeneficiaryNeedsGUI() {
        initComponents();
        setTitle("Sikunye Charity - Beneficiary Needs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

//        loadBeneficiaries();
//        loadNeedsDropdown();
//        loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Assign Needs to Beneficiary"));
        topPanel.setBackground(new Color(240, 248, 255));

        topPanel.add(new JLabel("Select Beneficiary:"));
        cmbBeneficiaries = new JComboBox<>();
        topPanel.add(cmbBeneficiaries);

        topPanel.add(new JLabel("Select Need:"));
        cmbNeeds = new JComboBox<>();
        topPanel.add(cmbNeeds);

        topPanel.add(new JLabel("Quantity:"));
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        topPanel.add(spinnerQuantity);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Beneficiary Needs"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAssign = makeButton("ASSIGN", new Color(46, 204, 113));
        btnRemove = makeButton("REMOVE", new Color(231, 76, 60));
        btnRefresh = makeButton("REFRESH", new Color(52, 152, 219));
        buttonPanel.add(btnAssign);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnRefresh);

        listModel = new DefaultListModel<>();
        listAssignedNeeds = new JList<>(listModel);
        listAssignedNeeds.setBorder(BorderFactory.createTitledBorder("Assigned Needs"));
        JScrollPane scrollNeeds = new JScrollPane(listAssignedNeeds);
        scrollNeeds.setPreferredSize(new Dimension(200, 120));

        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollNeeds, BorderLayout.CENTER);

        String[] columnNames = {"ID", "First Name", "Last Name", "Family Size", "Living Situation"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblBeneficiaries = new JTable(tableModel);
        tblBeneficiaries.setRowHeight(20);

        JScrollPane scrollTable = new JScrollPane(tblBeneficiaries);
        scrollTable.setBorder(BorderFactory.createTitledBorder("Beneficiary List"));
        scrollTable.setPreferredSize(new Dimension(780, 150));

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(scrollTable, BorderLayout.SOUTH);

//        btnAssign.addActionListener(e -> assignNeed());
//        btnRemove.addActionListener(e -> removeNeed());
//        btnRefresh.addActionListener(e -> {
//            loadBeneficiaries();
//            loadNeedsDropdown();
//            loadTableData();
//        });

//        cmbBeneficiaries.addActionListener(e -> {
//            String selected = (String) cmbBeneficiaries.getSelectedItem();
//            if (selected != null && !selected.isEmpty()) {
//                try {
//                    int id = Integer.parseInt(selected.split(" - ")[0]);
//                    refreshAssignedNeeds(id);
//                } catch (NumberFormatException ex) { /* ignore */ }
//            }
//        });

//        tblBeneficiaries.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int row = tblBeneficiaries.getSelectedRow();
//                if (row >= 0) {
//                    int id = (int) tableModel.getValueAt(row, 0);
//                    String name = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);
//                    cmbBeneficiaries.setSelectedItem(id + " - " + name);
//                    refreshAssignedNeeds(id);
//                }
//            }
//        });
//    }

    

//    private void loadBeneficiaries() {
//        cmbBeneficiaries.removeAllItems();
//        List<Beneficiary> list = beneficiaryDAO.getAllBeneficiaries();
//        for (Beneficiary b : list) {
//            cmbBeneficiaries.addItem(b.getBeneficiaryID() + " - " + b.getFirstName() + " " + b.getLastName());
//        }
//    }

//    private void loadNeedsDropdown() {
//        cmbNeeds.removeAllItems();
//        List<Needs> needs = needsDAO.getAllNeeds();
//        for (Needs n : needs) {
//            cmbNeeds.addItem(n.getNeedsDescription() + " (" + n.getNeedsCode() + ")");
//        }
//    }

//    private void loadTableData() {
//        tableModel.setRowCount(0);
//        List<Beneficiary> list = beneficiaryDAO.getAllBeneficiaries();
//        for (Beneficiary b : list) {
//            tableModel.addRow(new Object[]{
//                b.getBeneficiaryID(), b.getFirstName(), b.getLastName(),
//                b.getFamilySize(), b.getLivingSituation()
//            });
//        }
//    }

//    private void refreshAssignedNeeds(int beneficiaryID) {
//        listModel.clear();
//        List<String> needs = beneficiaryDAO.getNeedsForBeneficiary(beneficiaryID);
//        for (String need : needs) {
//            listModel.addElement(need);
//        }
//    }

//    private void assignNeed() {
//        try {
//            String selectedBeneficiary = (String) cmbBeneficiaries.getSelectedItem();
//            String selectedNeed = (String) cmbNeeds.getSelectedItem();
//
//            if (selectedBeneficiary == null || selectedNeed == null) {
//                JOptionPane.showMessageDialog(this, "Please select both a beneficiary and a need!");
//                return;
//            }
//
//            int beneficiaryID = Integer.parseInt(selectedBeneficiary.split(" - ")[0]);
//            int needsCode = Integer.parseInt(selectedNeed.replaceAll(".*\\((\\d+)\\)", "$1"));
//            int quantity = (int) spinnerQuantity.getValue();
//
//            if (beneficiaryDAO.assignNeedToBeneficiary(beneficiaryID, needsCode, quantity)) {
//                JOptionPane.showMessageDialog(this, "✅ Need assigned successfully!");
//                refreshAssignedNeeds(beneficiaryID);
//            } else {
//                JOptionPane.showMessageDialog(this, "❌ Failed to assign. It may already be assigned.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
//        }
//    }

//    private void removeNeed() {
//        try {
//            String selectedBeneficiary = (String) cmbBeneficiaries.getSelectedItem();
//            if (selectedBeneficiary == null) {
//                JOptionPane.showMessageDialog(this, "Please select a beneficiary!");
//                return;
//            }
//
//            int beneficiaryID = Integer.parseInt(selectedBeneficiary.split(" - ")[0]);
//            String selectedNeed = listAssignedNeeds.getSelectedValue();
//
//            if (selectedNeed == null) {
//                JOptionPane.showMessageDialog(this, "Please select a need from the list to remove!");
//                return;
//            }
//
//            String needDesc = selectedNeed.replaceAll(" \\(x\\d+\\)", "");
//            int needsCode = beneficiaryDAO.getNeedsCodeByDescription(needDesc);
//
//            if (needsCode == -1) {
//                JOptionPane.showMessageDialog(this, "Need not found!");
//                return;
//            }
//
//            if (JOptionPane.showConfirmDialog(this, "Remove '" + needDesc + "'?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//                if (beneficiaryDAO.removeNeedFromBeneficiary(beneficiaryID, needsCode)) {
//                    JOptionPane.showMessageDialog(this, "✅ Need removed!");
//                    refreshAssignedNeeds(beneficiaryID);
//                } else {
//                    JOptionPane.showMessageDialog(this, "❌ Failed to remove need.");
//                }
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
//        }
   }
    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 28));
        return btn;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BeneficiaryNeedsGUI());
    }
}