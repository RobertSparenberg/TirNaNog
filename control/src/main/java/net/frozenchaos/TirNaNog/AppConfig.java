package net.frozenchaos.TirNaNog;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class AppConfig {
    private final Properties properties;

    @Autowired
    public AppConfig(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public DataSource customDataSource() {
        return new MysqlDataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(customDataSource());
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPersistenceXmlLocation("common-persistence.xml");
        factory.setJpaProperties(properties);
        System.out.println(properties.getProperty("spring.datasource.username", "Unknown"));
        return factory;
    }
}
