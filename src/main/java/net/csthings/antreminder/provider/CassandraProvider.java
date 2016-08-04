package net.csthings.antreminder.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import net.csthings.config.CassandraSettings;

@Configuration
@ConditionalOnClass({ Cluster.class })
public class CassandraProvider {

//    @Autowired
//    private CassandraSettings cassandraSettings;
//
//    private static final Logger LOG = LoggerFactory.getLogger(CassandraProvider.class);
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Cluster cluster() {
//        return new Cluster.Builder().addContactPoint(cassandraSettings.getContactPoints())
//                .withPort(cassandraSettings.getPort()).build();
//    }
//
//    @Bean
//    public Session session() throws Exception {
//        return cluster().connect(cassandraSettings.getKeyspace());
//    }
// FIXME
}
