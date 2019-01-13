package net.frozenchaos.TirNaNog;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class AppConfig {
    private final Properties properties;

    public AppConfig() throws IOException {
        this.properties = new Properties();
        properties.load(new FileInputStream("application.properties"));
        int i = 0;
    }

    @Bean
    public DataSource customDataSource() {
        return new MysqlDataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean() throws Exception {
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
        factory.setJpaProperties(properties);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return factory;
    }
}
