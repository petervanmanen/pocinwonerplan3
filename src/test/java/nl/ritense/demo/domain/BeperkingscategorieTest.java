package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeperkingTestSamples.*;
import static nl.ritense.demo.domain.BeperkingscategorieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeperkingscategorieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beperkingscategorie.class);
        Beperkingscategorie beperkingscategorie1 = getBeperkingscategorieSample1();
        Beperkingscategorie beperkingscategorie2 = new Beperkingscategorie();
        assertThat(beperkingscategorie1).isNotEqualTo(beperkingscategorie2);

        beperkingscategorie2.setId(beperkingscategorie1.getId());
        assertThat(beperkingscategorie1).isEqualTo(beperkingscategorie2);

        beperkingscategorie2 = getBeperkingscategorieSample2();
        assertThat(beperkingscategorie1).isNotEqualTo(beperkingscategorie2);
    }

    @Test
    void iseenBeperkingTest() throws Exception {
        Beperkingscategorie beperkingscategorie = getBeperkingscategorieRandomSampleGenerator();
        Beperking beperkingBack = getBeperkingRandomSampleGenerator();

        beperkingscategorie.addIseenBeperking(beperkingBack);
        assertThat(beperkingscategorie.getIseenBeperkings()).containsOnly(beperkingBack);
        assertThat(beperkingBack.getIseenBeperkingscategorie()).isEqualTo(beperkingscategorie);

        beperkingscategorie.removeIseenBeperking(beperkingBack);
        assertThat(beperkingscategorie.getIseenBeperkings()).doesNotContain(beperkingBack);
        assertThat(beperkingBack.getIseenBeperkingscategorie()).isNull();

        beperkingscategorie.iseenBeperkings(new HashSet<>(Set.of(beperkingBack)));
        assertThat(beperkingscategorie.getIseenBeperkings()).containsOnly(beperkingBack);
        assertThat(beperkingBack.getIseenBeperkingscategorie()).isEqualTo(beperkingscategorie);

        beperkingscategorie.setIseenBeperkings(new HashSet<>());
        assertThat(beperkingscategorie.getIseenBeperkings()).doesNotContain(beperkingBack);
        assertThat(beperkingBack.getIseenBeperkingscategorie()).isNull();
    }
}
