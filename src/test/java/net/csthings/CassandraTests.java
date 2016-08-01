package net.csthings;

import org.testng.annotations.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;

public class CassandraTests {
    String local = "192.168.0.10";

    @Test
    public void connectToLocal() {
        Cluster cluster = Cluster.builder().addContactPoint(local).withPort(9042)
                .withRetryPolicy(DefaultRetryPolicy.INSTANCE).withProtocolVersion(ProtocolVersion.V3).build();
        System.out.println(cluster.getMetadata().getClusterName());
        Session sesh = cluster.connect("test1");
    }
}
