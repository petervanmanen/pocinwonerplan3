package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatiekadastraleonroerendezaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocatiekadastraleonroerendezaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locatiekadastraleonroerendezaak.class);
        Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak1 = getLocatiekadastraleonroerendezaakSample1();
        Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak2 = new Locatiekadastraleonroerendezaak();
        assertThat(locatiekadastraleonroerendezaak1).isNotEqualTo(locatiekadastraleonroerendezaak2);

        locatiekadastraleonroerendezaak2.setId(locatiekadastraleonroerendezaak1.getId());
        assertThat(locatiekadastraleonroerendezaak1).isEqualTo(locatiekadastraleonroerendezaak2);

        locatiekadastraleonroerendezaak2 = getLocatiekadastraleonroerendezaakSample2();
        assertThat(locatiekadastraleonroerendezaak1).isNotEqualTo(locatiekadastraleonroerendezaak2);
    }
}
