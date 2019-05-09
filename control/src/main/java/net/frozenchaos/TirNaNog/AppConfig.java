package net.frozenchaos.TirNaNog;

//@Configuration
public class AppConfig {
//    private static final java.lang.String DB_USERNAME = "net.frozenchaos.TirNaNog.db.username";
//    private static final java.lang.String DB_PASSWORD = "net.frozenchaos.TirNaNog.db.password";
//    private static final java.lang.String DB_PORT = "net.frozenchaos.TirNaNog.db.port";
//    private final TirNaNogProperties properties;
//
//    public AppConfig() throws IOException {
//        this.properties = new TirNaNogProperties();
//        properties.load(new FileInputStream("application.properties"));
//    }
//
//    @Bean
//    public TirNaNogProperties tirNaNogProperties() {
//        return properties;
//    }
//
//    @Bean
//    public DataSource customDataSource() {
//        MysqlDataSource mysqlDataSource = new MysqlDataSource();
//        mysqlDataSource.setDatabaseName("TirNaNog");
//        mysqlDataSource.setUser(properties.getProperty(DB_USERNAME));
//        mysqlDataSource.setPassword(properties.getProperty(DB_PASSWORD));
//        mysqlDataSource.setServerName("localhost");
//        mysqlDataSource.setPort(Integer.valueOf(properties.getProperty(DB_PORT, "3306")));
//        return mysqlDataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean() {
//            @Override
//            public void setResourceLoader(ResourceLoader resourceLoader) {
//                int i = 0;
//                //do nothing so the default doesn't get overridden; the override is not able to find persistence.xml
//            }
//        };
//        factory.setDataSource(customDataSource());
//        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        factory.setPersistenceXmlLocation("persistence.xml");
//        factory.setPersistenceUnitName("TirNaNog");
//        factory.setJpaDialect(new HibernateJpaDialect());
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        return factory;
//    }
}
