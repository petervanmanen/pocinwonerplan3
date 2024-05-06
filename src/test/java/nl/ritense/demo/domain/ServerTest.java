package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DatabaseTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.ServerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Server.class);
        Server server1 = getServerSample1();
        Server server2 = new Server();
        assertThat(server1).isNotEqualTo(server2);

        server2.setId(server1.getId());
        assertThat(server1).isEqualTo(server2);

        server2 = getServerSample2();
        assertThat(server1).isNotEqualTo(server2);
    }

    @Test
    void heeftleverancierLeverancierTest() throws Exception {
        Server server = getServerRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        server.setHeeftleverancierLeverancier(leverancierBack);
        assertThat(server.getHeeftleverancierLeverancier()).isEqualTo(leverancierBack);

        server.heeftleverancierLeverancier(null);
        assertThat(server.getHeeftleverancierLeverancier()).isNull();
    }

    @Test
    void servervandatabaseDatabaseTest() throws Exception {
        Server server = getServerRandomSampleGenerator();
        Database databaseBack = getDatabaseRandomSampleGenerator();

        server.addServervandatabaseDatabase(databaseBack);
        assertThat(server.getServervandatabaseDatabases()).containsOnly(databaseBack);
        assertThat(databaseBack.getServervandatabaseServer()).isEqualTo(server);

        server.removeServervandatabaseDatabase(databaseBack);
        assertThat(server.getServervandatabaseDatabases()).doesNotContain(databaseBack);
        assertThat(databaseBack.getServervandatabaseServer()).isNull();

        server.servervandatabaseDatabases(new HashSet<>(Set.of(databaseBack)));
        assertThat(server.getServervandatabaseDatabases()).containsOnly(databaseBack);
        assertThat(databaseBack.getServervandatabaseServer()).isEqualTo(server);

        server.setServervandatabaseDatabases(new HashSet<>());
        assertThat(server.getServervandatabaseDatabases()).doesNotContain(databaseBack);
        assertThat(databaseBack.getServervandatabaseServer()).isNull();
    }
}
