/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.mavenproject6.gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemDonated extends JFrame {

    private static final String DB_URL = "jdbc:derby://localhost:1527/SikunyeDB";
    private static final String USER = "Sikunye";
    private static final String PASS = "don1020";

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainCardContainer = new JPanel(cardLayout);

    private Color primaryCyan = new Color(0, 172, 193);
    private Color darkCyan = new Color(0, 131, 143);
    private Color neutralGrey = new Color(117, 117, 117);
    private Color darkGrey = new Color(66, 66, 66);
    private Color backgroundBg = new Color(245, 247, 250);
    private Color textColor = new Color(33, 33, 33);

    private JTextField txtItemID, txtReceiptID, txtItemName, txtQuantity, txtSearchInv;
    private JComboBox<String> comboCondition;
    private JTable inventoryTable;
    private DefaultTableModel inventoryModel;

    private JTextField txtDistID, txtDistItemID, txtBeneficiaryID, txtBeneficiaryName, txtDistQuantity;
    private JSpinner spinDistDate;
    private JTable distributionTable;
    private DefaultTableModel distributionModel;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ItemDonated() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        setTitle("Sikunye Charity System - Inventory & Distribution Control");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inventoryScreen = createInventoryScreen();
        JPanel distributionScreen = createDistributionScreen();

        mainCardContainer.add(inventoryScreen, "INVENTORY_CARD");
        mainCardContainer.add(distributionScreen, "DISTRIBUTION_CARD");

        add(mainCardContainer);

        loadInventoryData();
        loadDistributionData();
    }

    private JPanel createInventoryScreen() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(backgroundBg);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(darkCyan);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("Master Inventory Management (ITEMS_DONATED)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        txtSearchInv = createTextField();
        txtSearchInv.setPreferredSize(new Dimension(150, 32));
        JButton btnSearchInv = createStyledButton("Search", primaryCyan, 100, 32);
        btnSearchInv.addActionListener(e -> searchInventory());

        JLabel lblSearch = new JLabel("Search Item:");
        lblSearch.setForeground(Color.WHITE);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearchInv);
        searchPanel.add(btnSearchInv);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel formContainer = new JPanel(new BorderLayout(0, 10));
        formContainer.setOpaque(false);
        formContainer.setBorder(new EmptyBorder(0, 15, 15, 0));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 205, 210)), "Add / Edit Inventory Item"),
                new EmptyBorder(15, 15, 15, 15)
        ));

        formPanel.add(createFormLabel("Item ID (Auto):"));
        txtItemID = createTextField();
        txtItemID.setEditable(false);
        txtItemID.setBackground(new Color(235, 238, 242));
        formPanel.add(txtItemID);

        formPanel.add(createFormLabel("Receipt ID:"));
        txtReceiptID = createTextField();
        formPanel.add(txtReceiptID);

        formPanel.add(createFormLabel("Item Name:"));
        txtItemName = createTextField();
        formPanel.add(txtItemName);

        formPanel.add(createFormLabel("Quantity Available:"));
        txtQuantity = createTextField();
        formPanel.add(txtQuantity);

        formPanel.add(createFormLabel("Condition:"));
        String[] conditions = {"New", "Good", "Used - Like New", "Used - Fair", "Refurbished"};
        comboCondition = new JComboBox<>(conditions);
        comboCondition.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(comboCondition);

        formContainer.add(formPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        actionPanel.setOpaque(false);

        JButton btnAdd = createStyledButton("Add Stock", primaryCyan, 120, 35);
        JButton btnUpdate = createStyledButton("Update", darkCyan, 120, 35);
        JButton btnDelete = createStyledButton("Delete", neutralGrey, 120, 35);
        JButton btnClear = createStyledButton("Clear", darkGrey, 120, 35);

        btnAdd.addActionListener(e -> addInventoryItem());
        btnUpdate.addActionListener(e -> updateInventoryItem());
        btnDelete.addActionListener(e -> deleteInventoryItem());
        btnClear.addActionListener(e -> clearInventoryForm());

        actionPanel.add(btnAdd);
        actionPanel.add(btnUpdate);
        actionPanel.add(btnDelete);
        actionPanel.add(btnClear);

        formContainer.add(actionPanel, BorderLayout.SOUTH);
        panel.add(formContainer, BorderLayout.WEST);

        JPanel tableContainer = new JPanel(new BorderLayout(0, 10));
        tableContainer.setOpaque(false);
        tableContainer.setBorder(new EmptyBorder(0, 5, 15, 15));

        String[] cols = {"Item ID", "Receipt ID", "Item Name", "Available Stock", "Condition"};
        inventoryModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        inventoryTable = createStyledTable(inventoryModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel navButtonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navButtonBar.setOpaque(false);

        JButton btnViewAllDistributions = createStyledButton("View All Distributions", neutralGrey, 190, 40);
        btnViewAllDistributions.addActionListener(e -> {
            loadDistributionData();
            cardLayout.show(mainCardContainer, "DISTRIBUTION_CARD");
        });

        JButton btnDistributeItem = createStyledButton("Distribute Selected Item", primaryCyan, 200, 40);
        btnDistributeItem.addActionListener(e -> {
            int row = inventoryTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an item from the inventory table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String selectedItemID = inventoryModel.getValueAt(row, 0).toString();
            txtDistItemID.setText(selectedItemID);
            cardLayout.show(mainCardContainer, "DISTRIBUTION_CARD");
        });

        navButtonBar.add(btnViewAllDistributions);
        navButtonBar.add(btnDistributeItem);

        tableContainer.add(navButtonBar, BorderLayout.SOUTH);
        panel.add(tableContainer, BorderLayout.CENTER);

        inventoryTable.getSelectionModel().addListSelectionListener(e -> {
            int row = inventoryTable.getSelectedRow();
            if (row != -1) {
                txtItemID.setText(inventoryModel.getValueAt(row, 0).toString());
                txtReceiptID.setText(inventoryModel.getValueAt(row, 1).toString());
                txtItemName.setText(inventoryModel.getValueAt(row, 2).toString());
                txtQuantity.setText(inventoryModel.getValueAt(row, 3).toString());
                comboCondition.setSelectedItem(inventoryModel.getValueAt(row, 4).toString());
            }
        });

        return panel;
    }

    private JPanel createDistributionScreen() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(backgroundBg);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(darkCyan);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("Item Distribution Logging (ITEMS_DISTRIBUTED)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JPanel headerActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerActions.setOpaque(false);

        JButton btnDownloadReport = createStyledButton("Export Report (.txt)", primaryCyan, 180, 35);
        btnDownloadReport.addActionListener(e -> exportDistributionsToTxt());

        JButton btnBack = createStyledButton("Back to Inventory", darkGrey, 160, 35);
        btnBack.addActionListener(e -> {
            loadInventoryData();
            cardLayout.show(mainCardContainer, "INVENTORY_CARD");
        });

        headerActions.add(btnDownloadReport);
        headerActions.add(btnBack);

        headerPanel.add(headerActions, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel formContainer = new JPanel(new BorderLayout(0, 10));
        formContainer.setOpaque(false);
        formContainer.setBorder(new EmptyBorder(0, 15, 15, 0));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 205, 210)), "Record Distribution"),
                new EmptyBorder(15, 15, 15, 15)
        ));

        formPanel.add(createFormLabel("Dist. ID (Auto):"));
        txtDistID = createTextField();
        txtDistID.setEditable(false);
        txtDistID.setBackground(new Color(235, 238, 242));
        formPanel.add(txtDistID);

        formPanel.add(createFormLabel("Target Item ID:"));
        txtDistItemID = createTextField();
        formPanel.add(txtDistItemID);

        formPanel.add(createFormLabel("Beneficiary ID:"));
        txtBeneficiaryID = createTextField();
        formPanel.add(txtBeneficiaryID);

        formPanel.add(createFormLabel("Beneficiary Name:"));
        txtBeneficiaryName = createTextField();
        formPanel.add(txtBeneficiaryName);

        formPanel.add(createFormLabel("Dist. Quantity:"));
        txtDistQuantity = createTextField();
        formPanel.add(txtDistQuantity);

        formPanel.add(createFormLabel("Distribution Date:"));
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        spinDistDate = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinDistDate, "yyyy-MM-dd");
        spinDistDate.setEditor(dateEditor);
        spinDistDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(spinDistDate);

        formContainer.add(formPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        actionPanel.setOpaque(false);

        JButton btnAddDist = createStyledButton("Record Dist.", primaryCyan, 120, 35);
        JButton btnUpdateDist = createStyledButton("Update", darkCyan, 120, 35);
        JButton btnDeleteDist = createStyledButton("Delete", neutralGrey, 120, 35);
        JButton btnClearDist = createStyledButton("Clear", darkGrey, 120, 35);

        btnAddDist.addActionListener(e -> processDistribution());
        btnUpdateDist.addActionListener(e -> updateDistributionRecord());
        btnDeleteDist.addActionListener(e -> deleteDistributionRecord());
        btnClearDist.addActionListener(e -> clearDistributionForm());

        actionPanel.add(btnAddDist);
        actionPanel.add(btnUpdateDist);
        actionPanel.add(btnDeleteDist);
        actionPanel.add(btnClearDist);

        formContainer.add(actionPanel, BorderLayout.SOUTH);
        panel.add(formContainer, BorderLayout.WEST);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(new EmptyBorder(0, 5, 15, 15));

        String[] cols = {"Dist. ID", "Item ID", "Beneficiary ID", "Beneficiary Name", "Distributed Quantity", "Distribution Date"};
        distributionModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        distributionTable = createStyledTable(distributionModel);
        JScrollPane scrollPane = new JScrollPane(distributionTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panel.add(tableContainer, BorderLayout.CENTER);

        distributionTable.getSelectionModel().addListSelectionListener(e -> {
            int row = distributionTable.getSelectedRow();
            if (row != -1) {
                txtDistID.setText(distributionModel.getValueAt(row, 0).toString());
                txtDistItemID.setText(distributionModel.getValueAt(row, 1).toString());
                txtBeneficiaryID.setText(distributionModel.getValueAt(row, 2).toString());
                txtBeneficiaryName.setText(distributionModel.getValueAt(row, 3).toString());
                txtDistQuantity.setText(distributionModel.getValueAt(row, 4).toString());
                try {
                    Date date = dateFormat.parse(distributionModel.getValueAt(row, 5).toString());
                    spinDistDate.setValue(date);
                } catch (Exception ignored) {
                }
            }
        });

        return panel;
    }

    private void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; 
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 15, width);
            }
            TableCellRenderer headerRenderer = table.getColumnModel().getColumn(column).getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = table.getTableHeader().getDefaultRenderer();
            }
            Component headerComp = headerRenderer.getTableCellRendererComponent(table, table.getColumnModel().getColumn(column).getHeaderValue(), false, false, 0, column);
            width = Math.max(headerComp.getPreferredSize().width + 15, width);
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void exportDistributionsToTxt() {
        if (distributionModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No distribution records available to export.", "Export Notice", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Distribution Report");
        fileChooser.setSelectedFile(new File("Sikunye_Distribution_Report.txt"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File targetFile = fileChooser.getSelectedFile();
            if (!targetFile.getName().toLowerCase().endsWith(".txt")) {
                targetFile = new File(targetFile.getAbsolutePath() + ".txt");
            }

            try (PrintWriter writer = new PrintWriter(targetFile)) {
                writer.println("==========================================================================================");
                writer.println("                 SIKUNYE CHARITY DONATION SYSTEM                        ");
                writer.println("                   DISTRIBUTION REPORT LOG                                ");
                writer.println("Generated Date: " + dateFormat.format(new Date()));
                writer.println("==========================================================================================");
                writer.println();
                writer.printf("%-10s %-10s %-16s %-20s %-16s %-12s%n", "DIST ID", "ITEM ID", "BENEFICIARY ID", "BENEFICIARY NAME", "DIST QUANTITY", "DIST DATE");
                writer.println("------------------------------------------------------------------------------------------");

                for (int i = 0; i < distributionModel.getRowCount(); i++) {
                    String distID = distributionModel.getValueAt(i, 0).toString();
                    String itemID = distributionModel.getValueAt(i, 1).toString();
                    String benID = distributionModel.getValueAt(i, 2).toString();
                    String benName = distributionModel.getValueAt(i, 3).toString();
                    String qty = distributionModel.getValueAt(i, 4).toString();
                    String date = distributionModel.getValueAt(i, 5).toString();

                    writer.printf("%-10s %-10s %-16s %-20s %-16s %-12s%n", distID, itemID, benID, benName, qty, date);
                }

                writer.println("------------------------------------------------------------------------------------------");
                writer.println("TOTAL DISTRIBUTION RECORDS: " + distributionModel.getRowCount());
                writer.println("==========================================================================================");

                JOptionPane.showMessageDialog(this, "Distribution report saved successfully to:\n" + targetFile.getAbsolutePath(), "Export Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error writing file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processDistribution() {
        if (txtDistItemID.getText().trim().isEmpty() || txtBeneficiaryID.getText().trim().isEmpty() || txtBeneficiaryName.getText().trim().isEmpty() || txtDistQuantity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all distribution fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int itemID, beneficiaryID, distQuantity;
        try {
            itemID = Integer.parseInt(txtDistItemID.getText().trim());
            beneficiaryID = Integer.parseInt(txtBeneficiaryID.getText().trim());
            distQuantity = Integer.parseInt(txtDistQuantity.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Item ID, Beneficiary ID, and Quantity must be valid numbers.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (distQuantity <= 0) {
            JOptionPane.showMessageDialog(this, "Distribution quantity must be greater than zero.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);

            int currentStock = 0;
            String checkStockSql = "SELECT quantity FROM ITEMS_DONATED WHERE itemID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkStockSql)) {
                checkStmt.setInt(1, itemID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    currentStock = rs.getInt("quantity");
                } else {
                    JOptionPane.showMessageDialog(this, "Item ID " + itemID + " does not exist in inventory.", "Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            if (distQuantity > currentStock) {
                JOptionPane.showMessageDialog(this, "Insufficient stock! Available stock is only: " + currentStock, "Stock Deficit", JOptionPane.WARNING_MESSAGE);
                conn.rollback();
                return;
            }

            String insertDistSql = "INSERT INTO ITEMS_DISTRIBUTED (itemID, beneficiaryID, beneficiaryName, distributedQuantity, distributionDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertDistSql)) {
                insertStmt.setInt(1, itemID);
                insertStmt.setInt(2, beneficiaryID);
                insertStmt.setString(3, txtBeneficiaryName.getText().trim());
                insertStmt.setInt(4, distQuantity);
                Date selectedDate = (Date) spinDistDate.getValue();
                insertStmt.setDate(5, java.sql.Date.valueOf(dateFormat.format(selectedDate)));
                insertStmt.executeUpdate();
            }

            String updateStockSql = "UPDATE ITEMS_DONATED SET quantity = quantity - ? WHERE itemID = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                updateStmt.setInt(1, distQuantity);
                updateStmt.setInt(2, itemID);
                updateStmt.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Distribution recorded and " + distQuantity + " items deducted from stock!", "Success", JOptionPane.INFORMATION_MESSAGE);

            loadDistributionData();
            loadInventoryData();
            clearDistributionForm();

        } catch (Exception ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ignored) {}
            }
            JOptionPane.showMessageDialog(this, "Transaction failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
            }
        }
    }

    private void loadInventoryData() {
        inventoryModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS_DONATED")) {

            while (rs.next()) {
                inventoryModel.addRow(new Object[]{
                        rs.getInt("itemID"),
                        rs.getInt("receiptID"),
                        rs.getString("itemName"),
                        rs.getInt("quantity"),
                        rs.getString("itemCondition")
                });
            }
            resizeColumnWidth(inventoryTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadDistributionData() {
        distributionModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS_DISTRIBUTED")) {

            while (rs.next()) {
                distributionModel.addRow(new Object[]{
                        rs.getInt("distributionID"),
                        rs.getInt("itemID"),
                        rs.getInt("beneficiaryID"),
                        rs.getString("beneficiaryName"),
                        rs.getInt("distributedQuantity"),
                        rs.getDate("distributionDate")
                });
            }
            resizeColumnWidth(distributionTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addInventoryItem() {
        if (txtReceiptID.getText().trim().isEmpty() || txtItemName.getText().trim().isEmpty() || txtQuantity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all inventory fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO ITEMS_DONATED (receiptID, itemName, quantity, itemCondition) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(txtReceiptID.getText().trim()));
            pstmt.setString(2, txtItemName.getText().trim());
            pstmt.setInt(3, Integer.parseInt(txtQuantity.getText().trim()));
            pstmt.setString(4, comboCondition.getSelectedItem().toString());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item added to inventory successfully!");
            loadInventoryData();
            clearInventoryForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateInventoryItem() {
        if (txtItemID.getText().trim().isEmpty()) return;
        String sql = "UPDATE ITEMS_DONATED SET receiptID = ?, itemName = ?, quantity = ?, itemCondition = ? WHERE itemID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(txtReceiptID.getText().trim()));
            pstmt.setString(2, txtItemName.getText().trim());
            pstmt.setInt(3, Integer.parseInt(txtQuantity.getText().trim()));
            pstmt.setString(4, comboCondition.getSelectedItem().toString());
            pstmt.setInt(5, Integer.parseInt(txtItemID.getText().trim()));

            pstmt.executeUpdate();
            loadInventoryData();
            clearInventoryForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating item: " + ex.getMessage());
        }
    }

    private void deleteInventoryItem() {
        if (txtItemID.getText().trim().isEmpty()) return;
        String sql = "DELETE FROM ITEMS_DONATED WHERE itemID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(txtItemID.getText().trim()));
            pstmt.executeUpdate();
            loadInventoryData();
            clearInventoryForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting item: " + ex.getMessage());
        }
    }

    private void updateDistributionRecord() {
        if (txtDistID.getText().trim().isEmpty()) return;
        String sql = "UPDATE ITEMS_DISTRIBUTED SET itemID = ?, beneficiaryID = ?, beneficiaryName = ?, distributedQuantity = ?, distributionDate = ? WHERE distributionID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(txtDistItemID.getText().trim()));
            pstmt.setInt(2, Integer.parseInt(txtBeneficiaryID.getText().trim()));
            pstmt.setString(3, txtBeneficiaryName.getText().trim());
            pstmt.setInt(4, Integer.parseInt(txtDistQuantity.getText().trim()));
            Date selectedDate = (Date) spinDistDate.getValue();
            pstmt.setDate(5, java.sql.Date.valueOf(dateFormat.format(selectedDate)));
            pstmt.setInt(6, Integer.parseInt(txtDistID.getText().trim()));

            pstmt.executeUpdate();
            loadDistributionData();
            clearDistributionForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating distribution record: " + ex.getMessage());
        }
    }

    private void deleteDistributionRecord() {
        if (txtDistID.getText().trim().isEmpty()) return;
        String sql = "DELETE FROM ITEMS_DISTRIBUTED WHERE distributionID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(txtDistID.getText().trim()));
            pstmt.executeUpdate();
            loadDistributionData();
            clearDistributionForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting distribution record: " + ex.getMessage());
        }
    }

    private void searchInventory() {
        String keyword = txtSearchInv.getText().trim();
        if (keyword.isEmpty()) {
            loadInventoryData();
            return;
        }
        inventoryModel.setRowCount(0);
        String sql = "SELECT * FROM ITEMS_DONATED WHERE LOWER(itemName) LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                inventoryModel.addRow(new Object[]{
                        rs.getInt("itemID"),
                        rs.getInt("receiptID"),
                        rs.getString("itemName"),
                        rs.getInt("quantity"),
                        rs.getString("itemCondition")
                });
            }
            resizeColumnWidth(inventoryTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearInventoryForm() {
        txtItemID.setText("");
        txtReceiptID.setText("");
        txtItemName.setText("");
        txtQuantity.setText("");
        comboCondition.setSelectedIndex(0);
        inventoryTable.clearSelection();
    }

    private void clearDistributionForm() {
        txtDistID.setText("");
        txtDistItemID.setText("");
        txtBeneficiaryID.setText("");
        txtBeneficiaryName.setText("");
        txtDistQuantity.setText("");
        spinDistDate.setValue(new Date());
        distributionTable.clearSelection();
    }

    private JTextField createTextField() {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return f;
    }

    private JLabel createFormLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(textColor);
        return l;
    }

    private JButton createStyledButton(String text, Color bg, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        return btn;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setShowVerticalLines(false);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(178, 235, 242));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(darkCyan);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 32));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ItemDonated().setVisible(true));
    }
}
