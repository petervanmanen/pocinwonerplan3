package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KoopsomkadastraleonroerendezaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KoopsomkadastraleonroerendezaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Koopsomkadastraleonroerendezaak.class);
        Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak1 = getKoopsomkadastraleonroerendezaakSample1();
        Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak2 = new Koopsomkadastraleonroerendezaak();
        assertThat(koopsomkadastraleonroerendezaak1).isNotEqualTo(koopsomkadastraleonroerendezaak2);

        koopsomkadastraleonroerendezaak2.setId(koopsomkadastraleonroerendezaak1.getId());
        assertThat(koopsomkadastraleonroerendezaak1).isEqualTo(koopsomkadastraleonroerendezaak2);

        koopsomkadastraleonroerendezaak2 = getKoopsomkadastraleonroerendezaakSample2();
        assertThat(koopsomkadastraleonroerendezaak1).isNotEqualTo(koopsomkadastraleonroerendezaak2);
    }
}
