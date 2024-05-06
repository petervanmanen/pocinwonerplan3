package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VestigingTestSamples.*;
import static nl.ritense.demo.domain.WerkgelegenheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WerkgelegenheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Werkgelegenheid.class);
        Werkgelegenheid werkgelegenheid1 = getWerkgelegenheidSample1();
        Werkgelegenheid werkgelegenheid2 = new Werkgelegenheid();
        assertThat(werkgelegenheid1).isNotEqualTo(werkgelegenheid2);

        werkgelegenheid2.setId(werkgelegenheid1.getId());
        assertThat(werkgelegenheid1).isEqualTo(werkgelegenheid2);

        werkgelegenheid2 = getWerkgelegenheidSample2();
        assertThat(werkgelegenheid1).isNotEqualTo(werkgelegenheid2);
    }

    @Test
    void heeftVestigingTest() throws Exception {
        Werkgelegenheid werkgelegenheid = getWerkgelegenheidRandomSampleGenerator();
        Vestiging vestigingBack = getVestigingRandomSampleGenerator();

        werkgelegenheid.setHeeftVestiging(vestigingBack);
        assertThat(werkgelegenheid.getHeeftVestiging()).isEqualTo(vestigingBack);
        assertThat(vestigingBack.getHeeftWerkgelegenheid()).isEqualTo(werkgelegenheid);

        werkgelegenheid.heeftVestiging(null);
        assertThat(werkgelegenheid.getHeeftVestiging()).isNull();
        assertThat(vestigingBack.getHeeftWerkgelegenheid()).isNull();
    }
}
