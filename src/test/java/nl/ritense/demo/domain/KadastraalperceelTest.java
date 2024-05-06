package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KadastraalperceelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KadastraalperceelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kadastraalperceel.class);
        Kadastraalperceel kadastraalperceel1 = getKadastraalperceelSample1();
        Kadastraalperceel kadastraalperceel2 = new Kadastraalperceel();
        assertThat(kadastraalperceel1).isNotEqualTo(kadastraalperceel2);

        kadastraalperceel2.setId(kadastraalperceel1.getId());
        assertThat(kadastraalperceel1).isEqualTo(kadastraalperceel2);

        kadastraalperceel2 = getKadastraalperceelSample2();
        assertThat(kadastraalperceel1).isNotEqualTo(kadastraalperceel2);
    }
}
