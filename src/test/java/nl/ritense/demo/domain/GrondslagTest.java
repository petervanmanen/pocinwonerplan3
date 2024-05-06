package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GrondslagTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrondslagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grondslag.class);
        Grondslag grondslag1 = getGrondslagSample1();
        Grondslag grondslag2 = new Grondslag();
        assertThat(grondslag1).isNotEqualTo(grondslag2);

        grondslag2.setId(grondslag1.getId());
        assertThat(grondslag1).isEqualTo(grondslag2);

        grondslag2 = getGrondslagSample2();
        assertThat(grondslag1).isNotEqualTo(grondslag2);
    }

    @Test
    void heeftZaakTest() throws Exception {
        Grondslag grondslag = getGrondslagRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        grondslag.addHeeftZaak(zaakBack);
        assertThat(grondslag.getHeeftZaaks()).containsOnly(zaakBack);

        grondslag.removeHeeftZaak(zaakBack);
        assertThat(grondslag.getHeeftZaaks()).doesNotContain(zaakBack);

        grondslag.heeftZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(grondslag.getHeeftZaaks()).containsOnly(zaakBack);

        grondslag.setHeeftZaaks(new HashSet<>());
        assertThat(grondslag.getHeeftZaaks()).doesNotContain(zaakBack);
    }
}
