package net.frozenchaos.TirNaNog;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
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

        return factory;
    }
}
