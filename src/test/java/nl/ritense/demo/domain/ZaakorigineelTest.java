package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ZaakorigineelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZaakorigineelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zaakorigineel.class);
        Zaakorigineel zaakorigineel1 = getZaakorigineelSample1();
        Zaakorigineel zaakorigineel2 = new Zaakorigineel();
        assertThat(zaakorigineel1).isNotEqualTo(zaakorigineel2);

        zaakorigineel2.setId(zaakorigineel1.getId());
        assertThat(zaakorigineel1).isEqualTo(zaakorigineel2);

        zaakorigineel2 = getZaakorigineelSample2();
        assertThat(zaakorigineel1).isNotEqualTo(zaakorigineel2);
    }
}
