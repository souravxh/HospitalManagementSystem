import database.DBConnection;
import database.DatabaseInitializer;

import java.sql.Connection;

public class SQLiteTest {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {

            System.out.println("================================");
            System.out.println("✅ SQLITE IS WORKING!");
            System.out.println("================================");

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            DatabaseInitializer.initialize();

        } else {

            System.out.println("❌ SQLITE CONNECTION FAILED!");
        }
    }
}