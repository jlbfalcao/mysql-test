import org.slf4j.LoggerFactory;

import java.sql.*;

// java -jar 'jdbc:mysql://hostname:port/dbname' 'root', 'pass'
public class Main {

    private static final long ONE_MINUTE = 60000;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, SQLException {

        String pass;
        if (args.length > 2) {
            pass = args[2];
        } else {
            pass = "";
        }

        Class.forName("com.mysql.jdbc.Driver");
        long t0 = System.currentTimeMillis();
        try (Connection conn = DriverManager.getConnection(args[0], args[1], pass)) {
            for (int i = 40; i <= 80; i += 20) {

                logger.info("ping");
                t0 = System.currentTimeMillis();

                Statement stmt = conn.createStatement();
                ResultSet res = stmt.executeQuery("/* ping */ SELECT 1");
                res.next();

                logger.info("ping result={} - duration: {}ms", res.getInt(1), System.currentTimeMillis() - t0);

                logger.info("sleep {}minutes", i);
                Thread.sleep(i * ONE_MINUTE);
            }
        } catch (Exception e) {
            logger.info("error after {}ms", System.currentTimeMillis() - t0);
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
