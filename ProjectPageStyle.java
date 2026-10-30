package za.ac.cput.mavenproject6.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/** Shared visual elements for the three project detail pages. */
final class ProjectPageStyle {

    static final Color BACKGROUND = new Color(246, 248, 251);
    static final Color PRIMARY = new Color(24, 151, 184);
    static final Color DARK = new Color(35, 53, 65);
    static final Color MUTED = new Color(99, 115, 129);
    static final Font SECTION_FONT = new Font("Arial", Font.BOLD, 22);

    private ProjectPageStyle() {
    }

    static void styleBackButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 28, 12, 28));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    static JTextArea createParagraph(String text, int rows) {
        JTextArea paragraph = new JTextArea(text.replace("<br><br>", "\n\n"), rows, 45);
        paragraph.setFont(new Font("Arial", Font.PLAIN, 14));
        paragraph.setForeground(MUTED);
        paragraph.setLineWrap(true);
        paragraph.setWrapStyleWord(true);
        paragraph.setEditable(false);
        paragraph.setFocusable(false);
        paragraph.setOpaque(false);
        paragraph.setBorder(null);
        paragraph.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        paragraph.setMinimumSize(new Dimension(0, paragraph.getPreferredSize().height));
        paragraph.setMaximumSize(new Dimension(Integer.MAX_VALUE, paragraph.getPreferredSize().height));
        return paragraph;
    }

    static JPanel createFooter() {
        JPanel footer = new JPanel(new GridLayout(1, 3, 25, 0));
        footer.setBackground(DARK);
        footer.setBorder(new EmptyBorder(25, 45, 25, 45));
        footer.setPreferredSize(new Dimension(100, 180));

        footer.add(footerColumn("Sikunye Charity",
                "Together we are one<br>Supporting communities."));
        footer.add(footerColumn("Quick Links",
                "Home &nbsp; Items Donated &nbsp; Projects<br>"
                + "Donor &nbsp; Donation Received &nbsp; Contact"));
        footer.add(footerColumn("Follow Us", "f &nbsp; ◎ &nbsp; in"));
        return footer;
    }

    private static JPanel footerColumn(String title, String bodyText) {
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 15));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JLabel body = new JLabel("<html><div style='width:165px;'>"
                + bodyText + "</div></html>");
        body.setFont(new Font("Arial", Font.PLAIN, 12));
        body.setForeground(new Color(211, 221, 228));
        body.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        column.add(heading);
        column.add(Box.createVerticalStrut(12));
        column.add(body);
        return column;
    }

    static final class ScrollableContent extends JPanel implements Scrollable {
        @Override public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override public int getScrollableUnitIncrement(Rectangle visibleRect,
                int orientation, int direction) {
            return 18;
        }

        @Override public int getScrollableBlockIncrement(Rectangle visibleRect,
                int orientation, int direction) {
            return 120;
        }

        @Override public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}
