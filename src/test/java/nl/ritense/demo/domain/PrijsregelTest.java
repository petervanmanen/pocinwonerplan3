package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PrijsafspraakTestSamples.*;
import static nl.ritense.demo.domain.PrijsregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrijsregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijsregel.class);
        Prijsregel prijsregel1 = getPrijsregelSample1();
        Prijsregel prijsregel2 = new Prijsregel();
        assertThat(prijsregel1).isNotEqualTo(prijsregel2);

        prijsregel2.setId(prijsregel1.getId());
        assertThat(prijsregel1).isEqualTo(prijsregel2);

        prijsregel2 = getPrijsregelSample2();
        assertThat(prijsregel1).isNotEqualTo(prijsregel2);
    }

    @Test
    void heeftPrijsafspraakTest() throws Exception {
        Prijsregel prijsregel = getPrijsregelRandomSampleGenerator();
        Prijsafspraak prijsafspraakBack = getPrijsafspraakRandomSampleGenerator();

        prijsregel.setHeeftPrijsafspraak(prijsafspraakBack);
        assertThat(prijsregel.getHeeftPrijsafspraak()).isEqualTo(prijsafspraakBack);

        prijsregel.heeftPrijsafspraak(null);
        assertThat(prijsregel.getHeeftPrijsafspraak()).isNull();
    }
}
