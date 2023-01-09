package jdbc;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.util.Properties;

public class DBCPInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String poolConfig = sce.getServletContext().getInitParameter("poolConfig");
        Properties prop = new Properties();
        try {
            prop.load(new StringReader(poolConfig));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadJDBCDriver(prop);
        initConnectionPool(prop);
    }

    private void loadJDBCDriver(Properties prop) {
       String driverClass = prop.getProperty("jdbcdriver");
       try {
           Class.forName(driverClass);
       } catch (ClassNotFoundException ex) {
           throw new RuntimeException("JDBC 드라이버 로드 실패", ex);
       }
    }

    private void initConnectionPool(Properties prop) {
        try {
            String jdbcUrl = prop.getProperty("jdbcUrl");
            String username = prop.getProperty("dbUser");
            String pw = prop.getProperty("dbPass");

            ConnectionFactory connFactory =
                    new DriverManagerConnectionFactory(jdbcUrl, username, pw);
            PoolableConnectionFactory poolableConnFactory =
                    new PoolableConnectionFactory(connFactory, null);
            poolableConnFactory.setValidationQuery("select 1");

            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setTimeBetweenEvictionRunsMillis(1000L*60L*5L);
            poolConfig.setTestWhileIdle(true);
            poolConfig.setMinIdle(4);
            poolConfig.setMaxTotal(50);

            GenericObjectPool<PoolableConnection> connectionPool =
                    new GenericObjectPool<>(poolableConnFactory, poolConfig);
            poolableConnFactory.setPool(connectionPool);

            Class.forName("org.apache.commons.dbcp2.PoolingDriver");
            PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            String poolName = prop.getProperty("poolName");
            driver.registerPool(poolName, connectionPool);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
