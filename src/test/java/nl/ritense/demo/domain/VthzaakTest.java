package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VthzaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VthzaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vthzaak.class);
        Vthzaak vthzaak1 = getVthzaakSample1();
        Vthzaak vthzaak2 = new Vthzaak();
        assertThat(vthzaak1).isNotEqualTo(vthzaak2);

        vthzaak2.setId(vthzaak1.getId());
        assertThat(vthzaak1).isEqualTo(vthzaak2);

        vthzaak2 = getVthzaakSample2();
        assertThat(vthzaak1).isNotEqualTo(vthzaak2);
    }
}
