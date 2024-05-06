package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeperkingTestSamples.*;
import static nl.ritense.demo.domain.BeperkingscategorieTestSamples.*;
import static nl.ritense.demo.domain.BeperkingscoreTestSamples.*;
import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeperkingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beperking.class);
        Beperking beperking1 = getBeperkingSample1();
        Beperking beperking2 = new Beperking();
        assertThat(beperking1).isNotEqualTo(beperking2);

        beperking2.setId(beperking1.getId());
        assertThat(beperking1).isEqualTo(beperking2);

        beperking2 = getBeperkingSample2();
        assertThat(beperking1).isNotEqualTo(beperking2);
    }

    @Test
    void emptyBeperkingscoreTest() throws Exception {
        Beperking beperking = getBeperkingRandomSampleGenerator();
        Beperkingscore beperkingscoreBack = getBeperkingscoreRandomSampleGenerator();

        beperking.addEmptyBeperkingscore(beperkingscoreBack);
        assertThat(beperking.getEmptyBeperkingscores()).containsOnly(beperkingscoreBack);
        assertThat(beperkingscoreBack.getEmptyBeperking()).isEqualTo(beperking);

        beperking.removeEmptyBeperkingscore(beperkingscoreBack);
        assertThat(beperking.getEmptyBeperkingscores()).doesNotContain(beperkingscoreBack);
        assertThat(beperkingscoreBack.getEmptyBeperking()).isNull();

        beperking.emptyBeperkingscores(new HashSet<>(Set.of(beperkingscoreBack)));
        assertThat(beperking.getEmptyBeperkingscores()).containsOnly(beperkingscoreBack);
        assertThat(beperkingscoreBack.getEmptyBeperking()).isEqualTo(beperking);

        beperking.setEmptyBeperkingscores(new HashSet<>());
        assertThat(beperking.getEmptyBeperkingscores()).doesNotContain(beperkingscoreBack);
        assertThat(beperkingscoreBack.getEmptyBeperking()).isNull();
    }

    @Test
    void iseenBeperkingscategorieTest() throws Exception {
        Beperking beperking = getBeperkingRandomSampleGenerator();
        Beperkingscategorie beperkingscategorieBack = getBeperkingscategorieRandomSampleGenerator();

        beperking.setIseenBeperkingscategorie(beperkingscategorieBack);
        assertThat(beperking.getIseenBeperkingscategorie()).isEqualTo(beperkingscategorieBack);

        beperking.iseenBeperkingscategorie(null);
        assertThat(beperking.getIseenBeperkingscategorie()).isNull();
    }

    @Test
    void isgebaseerdopBeschikkingTest() throws Exception {
        Beperking beperking = getBeperkingRandomSampleGenerator();
        Beschikking beschikkingBack = getBeschikkingRandomSampleGenerator();

        beperking.setIsgebaseerdopBeschikking(beschikkingBack);
        assertThat(beperking.getIsgebaseerdopBeschikking()).isEqualTo(beschikkingBack);

        beperking.isgebaseerdopBeschikking(null);
        assertThat(beperking.getIsgebaseerdopBeschikking()).isNull();
    }
}
