package userInterface.panel;

import exception.ReadException;
import model.ReservationSearchResult;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class SearchReservationPanel extends JPanel {

    private MainWindow parent;
    private JSpinner spinnerStartDate;
    private JSpinner spinnerEndDate;
    private JButton btnSearch;
    private JTable tableResults;
    private DefaultTableModel tableModel;

    public SearchReservationPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- SECTION NORD : Critères de recherche (Les 2 JSpinners) ---
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Critères de recherche : Réservations entre deux dates"));

        SpinnerDateModel modelStart = new SpinnerDateModel();
        spinnerStartDate = new JSpinner(modelStart);
        spinnerStartDate.setEditor(new JSpinner.DateEditor(spinnerStartDate, "dd/MM/yyyy"));

        SpinnerDateModel modelEnd = new SpinnerDateModel();
        spinnerEndDate = new JSpinner(modelEnd);
        spinnerEndDate.setEditor(new JSpinner.DateEditor(spinnerEndDate, "dd/MM/yyyy"));

        btnSearch = new JButton("Rechercher");

        searchPanel.add(new JLabel("Date de début :"));
        searchPanel.add(spinnerStartDate);
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(new JLabel("Date de fin :"));
        searchPanel.add(spinnerEndDate);
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(btnSearch);

        this.add(searchPanel, BorderLayout.NORTH);

        // --- SECTION CENTRE : Résultats de la recherche (JTable) ---
        String[] columns = {"Date de Réservation", "Nb Personnes", "Client", "Ville", "Code Postal", "Table N°"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableResults = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableResults);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Résultats de la recherche"));
        this.add(scrollPane, BorderLayout.CENTER);

        // --- ACTION BOUTON ---
        btnSearch.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        // Extraction des LocalDate depuis les JSpinner
        Date dateStartVal = (Date) spinnerStartDate.getValue();
        Date dateEndVal = (Date) spinnerEndDate.getValue();

        LocalDate startDate = dateStartVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = dateEndVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        tableModel.setRowCount(0); // Vider le tableau avant la recherche

        try {
            ArrayList<ReservationSearchResult> results = parent.getApplicationController()
                    .searchReservationsBetweenDates(startDate, endDate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (ReservationSearchResult res : results) {
                tableModel.addRow(new Object[]{
                        res.getDate() != null ? res.getDate().format(formatter) : "N/A",
                        res.getNbPeople(),
                        res.getCustomerName(),
                        res.getCityName(),
                        res.getPostalCode(),
                        res.getTableNumber()
                });
            }

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No reservation found for this period.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Search error", JOptionPane.ERROR_MESSAGE);
        }
    }
}