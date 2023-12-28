package Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class DefaultData {
    private static final String DB_URL_Lite = "jdbc:sqlite:src/main/java/Database/dict_t.db";
    public static HikariDataSource dataSource = null;

    public DefaultData() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL_Lite);
        config.setMaximumPoolSize(20);
        dataSource = new HikariDataSource(config);
    }
}