package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeperkingscoreTestSamples.*;
import static nl.ritense.demo.domain.BeperkingscoresoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeperkingscoresoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beperkingscoresoort.class);
        Beperkingscoresoort beperkingscoresoort1 = getBeperkingscoresoortSample1();
        Beperkingscoresoort beperkingscoresoort2 = new Beperkingscoresoort();
        assertThat(beperkingscoresoort1).isNotEqualTo(beperkingscoresoort2);

        beperkingscoresoort2.setId(beperkingscoresoort1.getId());
        assertThat(beperkingscoresoort1).isEqualTo(beperkingscoresoort2);

        beperkingscoresoort2 = getBeperkingscoresoortSample2();
        assertThat(beperkingscoresoort1).isNotEqualTo(beperkingscoresoort2);
    }

    @Test
    void iseenBeperkingscoreTest() throws Exception {
        Beperkingscoresoort beperkingscoresoort = getBeperkingscoresoortRandomSampleGenerator();
        Beperkingscore beperkingscoreBack = getBeperkingscoreRandomSampleGenerator();

        beperkingscoresoort.addIseenBeperkingscore(beperkingscoreBack);
        assertThat(beperkingscoresoort.getIseenBeperkingscores()).containsOnly(beperkingscoreBack);
        assertThat(beperkingscoreBack.getIseenBeperkingscoresoort()).isEqualTo(beperkingscoresoort);

        beperkingscoresoort.removeIseenBeperkingscore(beperkingscoreBack);
        assertThat(beperkingscoresoort.getIseenBeperkingscores()).doesNotContain(beperkingscoreBack);
        assertThat(beperkingscoreBack.getIseenBeperkingscoresoort()).isNull();

        beperkingscoresoort.iseenBeperkingscores(new HashSet<>(Set.of(beperkingscoreBack)));
        assertThat(beperkingscoresoort.getIseenBeperkingscores()).containsOnly(beperkingscoreBack);
        assertThat(beperkingscoreBack.getIseenBeperkingscoresoort()).isEqualTo(beperkingscoresoort);

        beperkingscoresoort.setIseenBeperkingscores(new HashSet<>());
        assertThat(beperkingscoresoort.getIseenBeperkingscores()).doesNotContain(beperkingscoreBack);
        assertThat(beperkingscoreBack.getIseenBeperkingscoresoort()).isNull();
    }
}
