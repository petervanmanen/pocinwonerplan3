package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SportterreinTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportterreinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sportterrein.class);
        Sportterrein sportterrein1 = getSportterreinSample1();
        Sportterrein sportterrein2 = new Sportterrein();
        assertThat(sportterrein1).isNotEqualTo(sportterrein2);

        sportterrein2.setId(sportterrein1.getId());
        assertThat(sportterrein1).isEqualTo(sportterrein2);

        sportterrein2 = getSportterreinSample2();
        assertThat(sportterrein1).isNotEqualTo(sportterrein2);
    }
}
