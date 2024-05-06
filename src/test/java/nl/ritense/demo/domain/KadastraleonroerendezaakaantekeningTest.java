package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KadastraleonroerendezaakaantekeningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KadastraleonroerendezaakaantekeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kadastraleonroerendezaakaantekening.class);
        Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening1 = getKadastraleonroerendezaakaantekeningSample1();
        Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening2 = new Kadastraleonroerendezaakaantekening();
        assertThat(kadastraleonroerendezaakaantekening1).isNotEqualTo(kadastraleonroerendezaakaantekening2);

        kadastraleonroerendezaakaantekening2.setId(kadastraleonroerendezaakaantekening1.getId());
        assertThat(kadastraleonroerendezaakaantekening1).isEqualTo(kadastraleonroerendezaakaantekening2);

        kadastraleonroerendezaakaantekening2 = getKadastraleonroerendezaakaantekeningSample2();
        assertThat(kadastraleonroerendezaakaantekening1).isNotEqualTo(kadastraleonroerendezaakaantekening2);
    }
}
