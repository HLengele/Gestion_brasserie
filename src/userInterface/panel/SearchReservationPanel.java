package userInterface.panel;

import exception.ReadException;
import model.Reservation;
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

        String[] columns = {"Date", "Nb Personnes", "Client", "Email", "Table N°", "Emplacement Table"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableResults = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableResults);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Résultats de la recherche"));
        this.add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        Date dateStartVal = (Date) spinnerStartDate.getValue();
        Date dateEndVal = (Date) spinnerEndDate.getValue();

        LocalDate startDate = dateStartVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = dateEndVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        tableModel.setRowCount(0);

        try {
            ArrayList<Reservation> results = parent.getApplicationController()
                    .searchReservationsBetweenDates(startDate, endDate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Reservation res : results) {
                tableModel.addRow(new Object[]{
                        res.getDate() != null ? res.getDate().format(formatter) : "",
                        res.getNbPeople(),
                        res.getCustomer().getName(),
                        res.getCustomer().getEmail(),
                        res.getTable().getTableNumber(),
                        res.getTable().getLocation() != null ? res.getTable().getLocation() : "Standard"
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