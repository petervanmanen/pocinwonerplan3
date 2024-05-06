package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HistorischpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistorischpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historischpersoon.class);
        Historischpersoon historischpersoon1 = getHistorischpersoonSample1();
        Historischpersoon historischpersoon2 = new Historischpersoon();
        assertThat(historischpersoon1).isNotEqualTo(historischpersoon2);

        historischpersoon2.setId(historischpersoon1.getId());
        assertThat(historischpersoon1).isEqualTo(historischpersoon2);

        historischpersoon2 = getHistorischpersoonSample2();
        assertThat(historischpersoon1).isNotEqualTo(historischpersoon2);
    }
}
