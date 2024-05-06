package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DatabaseTestSamples.*;
import static nl.ritense.demo.domain.ServerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatabaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Database.class);
        Database database1 = getDatabaseSample1();
        Database database2 = new Database();
        assertThat(database1).isNotEqualTo(database2);

        database2.setId(database1.getId());
        assertThat(database1).isEqualTo(database2);

        database2 = getDatabaseSample2();
        assertThat(database1).isNotEqualTo(database2);
    }

    @Test
    void servervandatabaseServerTest() throws Exception {
        Database database = getDatabaseRandomSampleGenerator();
        Server serverBack = getServerRandomSampleGenerator();

        database.setServervandatabaseServer(serverBack);
        assertThat(database.getServervandatabaseServer()).isEqualTo(serverBack);

        database.servervandatabaseServer(null);
        assertThat(database.getServervandatabaseServer()).isNull();
    }
}
