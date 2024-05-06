package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BedrijfsprocesTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedrijfsprocesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bedrijfsproces.class);
        Bedrijfsproces bedrijfsproces1 = getBedrijfsprocesSample1();
        Bedrijfsproces bedrijfsproces2 = new Bedrijfsproces();
        assertThat(bedrijfsproces1).isNotEqualTo(bedrijfsproces2);

        bedrijfsproces2.setId(bedrijfsproces1.getId());
        assertThat(bedrijfsproces1).isEqualTo(bedrijfsproces2);

        bedrijfsproces2 = getBedrijfsprocesSample2();
        assertThat(bedrijfsproces1).isNotEqualTo(bedrijfsproces2);
    }

    @Test
    void uitgevoerdbinnenZaakTest() throws Exception {
        Bedrijfsproces bedrijfsproces = getBedrijfsprocesRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        bedrijfsproces.addUitgevoerdbinnenZaak(zaakBack);
        assertThat(bedrijfsproces.getUitgevoerdbinnenZaaks()).containsOnly(zaakBack);

        bedrijfsproces.removeUitgevoerdbinnenZaak(zaakBack);
        assertThat(bedrijfsproces.getUitgevoerdbinnenZaaks()).doesNotContain(zaakBack);

        bedrijfsproces.uitgevoerdbinnenZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(bedrijfsproces.getUitgevoerdbinnenZaaks()).containsOnly(zaakBack);

        bedrijfsproces.setUitgevoerdbinnenZaaks(new HashSet<>());
        assertThat(bedrijfsproces.getUitgevoerdbinnenZaaks()).doesNotContain(zaakBack);
    }
}
