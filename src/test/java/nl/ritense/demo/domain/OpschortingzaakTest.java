package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OpschortingzaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpschortingzaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opschortingzaak.class);
        Opschortingzaak opschortingzaak1 = getOpschortingzaakSample1();
        Opschortingzaak opschortingzaak2 = new Opschortingzaak();
        assertThat(opschortingzaak1).isNotEqualTo(opschortingzaak2);

        opschortingzaak2.setId(opschortingzaak1.getId());
        assertThat(opschortingzaak1).isEqualTo(opschortingzaak2);

        opschortingzaak2 = getOpschortingzaakSample2();
        assertThat(opschortingzaak1).isNotEqualTo(opschortingzaak2);
    }
}
