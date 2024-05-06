package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KadastraleonroerendezaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KadastraleonroerendezaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kadastraleonroerendezaak.class);
        Kadastraleonroerendezaak kadastraleonroerendezaak1 = getKadastraleonroerendezaakSample1();
        Kadastraleonroerendezaak kadastraleonroerendezaak2 = new Kadastraleonroerendezaak();
        assertThat(kadastraleonroerendezaak1).isNotEqualTo(kadastraleonroerendezaak2);

        kadastraleonroerendezaak2.setId(kadastraleonroerendezaak1.getId());
        assertThat(kadastraleonroerendezaak1).isEqualTo(kadastraleonroerendezaak2);

        kadastraleonroerendezaak2 = getKadastraleonroerendezaakSample2();
        assertThat(kadastraleonroerendezaak1).isNotEqualTo(kadastraleonroerendezaak2);
    }
}
