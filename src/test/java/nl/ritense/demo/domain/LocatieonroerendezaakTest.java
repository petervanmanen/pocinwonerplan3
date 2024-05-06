package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatieonroerendezaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocatieonroerendezaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locatieonroerendezaak.class);
        Locatieonroerendezaak locatieonroerendezaak1 = getLocatieonroerendezaakSample1();
        Locatieonroerendezaak locatieonroerendezaak2 = new Locatieonroerendezaak();
        assertThat(locatieonroerendezaak1).isNotEqualTo(locatieonroerendezaak2);

        locatieonroerendezaak2.setId(locatieonroerendezaak1.getId());
        assertThat(locatieonroerendezaak1).isEqualTo(locatieonroerendezaak2);

        locatieonroerendezaak2 = getLocatieonroerendezaakSample2();
        assertThat(locatieonroerendezaak1).isNotEqualTo(locatieonroerendezaak2);
    }
}
