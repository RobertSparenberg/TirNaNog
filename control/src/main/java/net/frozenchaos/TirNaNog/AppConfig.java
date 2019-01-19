package net.frozenchaos.TirNaNog;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class AppConfig {
    private static final java.lang.String DB_USERNAME = "net.frozenchaos.TirNaNog.db.username";
    private static final java.lang.String DB_PASSWORD = "net.frozenchaos.TirNaNog.db.password";
    private static final java.lang.String DB_PORT = "net.frozenchaos.TirNaNog.db.port";
    private final Properties properties;

    public AppConfig() throws IOException {
        this.properties = new Properties();
        properties.load(new FileInputStream("application.properties"));
    }

    @Bean
    public DataSource customDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setDatabaseName("TirNaNog");
        mysqlDataSource.setUser(properties.getProperty(DB_USERNAME));
        mysqlDataSource.setPassword(properties.getProperty(DB_PASSWORD));
        mysqlDataSource.setServerName("localhost");
        mysqlDataSource.setPort(Integer.valueOf(properties.getProperty(DB_PORT, "3306")));
        return mysqlDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean() {
            @Override
            public void setResourceLoader(ResourceLoader resourceLoader) {
                int i = 0;
                //do nothing so the default doesn't get overridden; the override is not able to find persistence.xml
            }
        };
        factory.setDataSource(customDataSource());
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPersistenceXmlLocation("persistence.xml");
        factory.setPersistenceUnitName("TirNaNog");
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return factory;
    }
}
