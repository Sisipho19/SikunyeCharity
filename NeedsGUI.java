package za.ac.cput.mavenproject6.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NeedsGUI extends JFrame {

    private JTable tblNeeds;
    private DefaultTableModel tableModel;
   // private NeedsDAO needsDAO = new NeedsDAO();

    public NeedsGUI() {
        initComponents();
        setTitle("Sikunye Charity - Manage Needs");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        //loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));

        JLabel title = new JLabel("Needs List", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(46, 134, 222));
        add(title, BorderLayout.NORTH);

        String[] columnNames = {"Code", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblNeeds = new JTable(tableModel);
        tblNeeds.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tblNeeds);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Needs List"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnRefresh = new JButton("REFRESH");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefresh.setPreferredSize(new Dimension(100, 30));
        //btnRefresh.addActionListener(e -> loadTableData());
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
          SwingUtilities.invokeLater(() -> new NeedsGUI());
    }

//    private void loadTableData() {
//        tableModel.setRowCount(0);
//        List<Needs> needs = needsDAO.getAllNeeds();
//        for (Needs n : needs) {
//            tableModel.addRow(new Object[]{n.getNeedsCode(), n.getNeedsDescription()});
//        }
//    }
}