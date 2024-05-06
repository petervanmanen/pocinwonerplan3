package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KenmerkenzaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KenmerkenzaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kenmerkenzaak.class);
        Kenmerkenzaak kenmerkenzaak1 = getKenmerkenzaakSample1();
        Kenmerkenzaak kenmerkenzaak2 = new Kenmerkenzaak();
        assertThat(kenmerkenzaak1).isNotEqualTo(kenmerkenzaak2);

        kenmerkenzaak2.setId(kenmerkenzaak1.getId());
        assertThat(kenmerkenzaak1).isEqualTo(kenmerkenzaak2);

        kenmerkenzaak2 = getKenmerkenzaakSample2();
        assertThat(kenmerkenzaak1).isNotEqualTo(kenmerkenzaak2);
    }
}
