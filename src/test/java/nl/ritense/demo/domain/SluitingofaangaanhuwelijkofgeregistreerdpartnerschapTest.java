package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SluitingofaangaanhuwelijkofgeregistreerdpartnerschapTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SluitingofaangaanhuwelijkofgeregistreerdpartnerschapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.class);
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap1 =
            getSluitingofaangaanhuwelijkofgeregistreerdpartnerschapSample1();
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap2 =
            new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap();
        assertThat(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap1).isNotEqualTo(
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap2
        );

        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap2.setId(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap1.getId());
        assertThat(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap1).isEqualTo(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap2);

        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap2 = getSluitingofaangaanhuwelijkofgeregistreerdpartnerschapSample2();
        assertThat(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap1).isNotEqualTo(
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap2
        );
    }
}
