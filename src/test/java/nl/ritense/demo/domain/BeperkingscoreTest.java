package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeperkingTestSamples.*;
import static nl.ritense.demo.domain.BeperkingscoreTestSamples.*;
import static nl.ritense.demo.domain.BeperkingscoresoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeperkingscoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beperkingscore.class);
        Beperkingscore beperkingscore1 = getBeperkingscoreSample1();
        Beperkingscore beperkingscore2 = new Beperkingscore();
        assertThat(beperkingscore1).isNotEqualTo(beperkingscore2);

        beperkingscore2.setId(beperkingscore1.getId());
        assertThat(beperkingscore1).isEqualTo(beperkingscore2);

        beperkingscore2 = getBeperkingscoreSample2();
        assertThat(beperkingscore1).isNotEqualTo(beperkingscore2);
    }

    @Test
    void iseenBeperkingscoresoortTest() throws Exception {
        Beperkingscore beperkingscore = getBeperkingscoreRandomSampleGenerator();
        Beperkingscoresoort beperkingscoresoortBack = getBeperkingscoresoortRandomSampleGenerator();

        beperkingscore.setIseenBeperkingscoresoort(beperkingscoresoortBack);
        assertThat(beperkingscore.getIseenBeperkingscoresoort()).isEqualTo(beperkingscoresoortBack);

        beperkingscore.iseenBeperkingscoresoort(null);
        assertThat(beperkingscore.getIseenBeperkingscoresoort()).isNull();
    }

    @Test
    void emptyBeperkingTest() throws Exception {
        Beperkingscore beperkingscore = getBeperkingscoreRandomSampleGenerator();
        Beperking beperkingBack = getBeperkingRandomSampleGenerator();

        beperkingscore.setEmptyBeperking(beperkingBack);
        assertThat(beperkingscore.getEmptyBeperking()).isEqualTo(beperkingBack);

        beperkingscore.emptyBeperking(null);
        assertThat(beperkingscore.getEmptyBeperking()).isNull();
    }
}
