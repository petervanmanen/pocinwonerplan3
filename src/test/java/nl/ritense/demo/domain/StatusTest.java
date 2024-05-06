package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StatusTestSamples.*;
import static nl.ritense.demo.domain.StatustypeTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Status.class);
        Status status1 = getStatusSample1();
        Status status2 = new Status();
        assertThat(status1).isNotEqualTo(status2);

        status2.setId(status1.getId());
        assertThat(status1).isEqualTo(status2);

        status2 = getStatusSample2();
        assertThat(status1).isNotEqualTo(status2);
    }

    @Test
    void isvanStatustypeTest() throws Exception {
        Status status = getStatusRandomSampleGenerator();
        Statustype statustypeBack = getStatustypeRandomSampleGenerator();

        status.setIsvanStatustype(statustypeBack);
        assertThat(status.getIsvanStatustype()).isEqualTo(statustypeBack);

        status.isvanStatustype(null);
        assertThat(status.getIsvanStatustype()).isNull();
    }

    @Test
    void heeftZaakTest() throws Exception {
        Status status = getStatusRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        status.setHeeftZaak(zaakBack);
        assertThat(status.getHeeftZaak()).isEqualTo(zaakBack);

        status.heeftZaak(null);
        assertThat(status.getHeeftZaak()).isNull();
    }
}
