import database.DatabaseInitializer;
import ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // ==========================
        // INITIALIZE DATABASE
        // ==========================

        DatabaseInitializer.initialize();

        // ==========================
        // START APPLICATION
        // ==========================

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}