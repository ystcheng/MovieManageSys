package GUI;

import MovieOperation.MovieController;
import MovieOperation.MovieControllerManager;
import UserOperation.UserController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class creates a panel that when it renders, it shows the necessary components of the current login's user's info.
 * Extends the BaseGUI class so it doesn't need to re initializes the menu screen, as well as rewiring the events.
 */
public class User extends BaseGUI {
    private JPanel jPanel;

    public User(UserController userController, JFrame frame) {
        super(frame, userController);
        this.jPanel = super.getjPanel();

        initContent();
    }

    private void initContent() {

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(5, 5));

        JPanel panel = new JPanel(new SpringLayout());

        String[] labels = {
                "Username:", super.getUserController().getUsername(),
                "Email:", super.getUserController().getEmail(),
                "First Name:", super.getUserController().getFirstName(),
                "Last Name:", super.getUserController().getLastName()
        };

        for (int i = 0; i < labels.length; i++ ) {
            JLabel label = new JLabel(labels[i]);
            int size = i % 2 == 0 ? 20 : 18;
            label.setFont(new Font("Serif", Font.PLAIN, size));
            panel.add(label);
        }

        SpringUtilities.makeCompactGrid(panel, labels.length / 2, 2, 0, 0, 50, 10);
        content.add(panel, BorderLayout.NORTH);

        MovieTableModel movieTableModel = new MovieTableModel(
                initTable(super.getUserController().getWatchedMovies()));
        JTable jTable = new JTable(movieTableModel);

        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    rowDoubleClicked((Integer) target.getValueAt(row, -1));
                }
            }
        });

        jTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBorder(new TitledBorder("Watched History"));

        content.add(scrollPane, BorderLayout.CENTER);

        jPanel.add(content, BorderLayout.CENTER);
    }

    private ArrayList<MovieController> initTable(LinkedList<Integer> keys) {
        ArrayList<MovieController> movieObjects = new ArrayList<>();

        for (Integer key : keys)
            movieObjects.add(MovieControllerManager.getMovieController(key));

        return movieObjects;
    }

    private void rowDoubleClicked(int id) {
        new MainGUI(2, super.getUserController(), super.getFrame(), id);
    }

    @Override
    void setMainButton(ActionEvent e) {
        new MainGUI(0, super.getUserController(), super.getFrame());
    }

}
