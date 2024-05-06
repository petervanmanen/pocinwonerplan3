package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KadastralegemeenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KadastralegemeenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kadastralegemeente.class);
        Kadastralegemeente kadastralegemeente1 = getKadastralegemeenteSample1();
        Kadastralegemeente kadastralegemeente2 = new Kadastralegemeente();
        assertThat(kadastralegemeente1).isNotEqualTo(kadastralegemeente2);

        kadastralegemeente2.setId(kadastralegemeente1.getId());
        assertThat(kadastralegemeente1).isEqualTo(kadastralegemeente2);

        kadastralegemeente2 = getKadastralegemeenteSample2();
        assertThat(kadastralegemeente1).isNotEqualTo(kadastralegemeente2);
    }
}
