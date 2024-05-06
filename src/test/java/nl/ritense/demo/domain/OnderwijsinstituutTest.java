package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderwijsinstituutTestSamples.*;
import static nl.ritense.demo.domain.OpleidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderwijsinstituutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderwijsinstituut.class);
        Onderwijsinstituut onderwijsinstituut1 = getOnderwijsinstituutSample1();
        Onderwijsinstituut onderwijsinstituut2 = new Onderwijsinstituut();
        assertThat(onderwijsinstituut1).isNotEqualTo(onderwijsinstituut2);

        onderwijsinstituut2.setId(onderwijsinstituut1.getId());
        assertThat(onderwijsinstituut1).isEqualTo(onderwijsinstituut2);

        onderwijsinstituut2 = getOnderwijsinstituutSample2();
        assertThat(onderwijsinstituut1).isNotEqualTo(onderwijsinstituut2);
    }

    @Test
    void wordtgegevendoorOpleidingTest() throws Exception {
        Onderwijsinstituut onderwijsinstituut = getOnderwijsinstituutRandomSampleGenerator();
        Opleiding opleidingBack = getOpleidingRandomSampleGenerator();

        onderwijsinstituut.addWordtgegevendoorOpleiding(opleidingBack);
        assertThat(onderwijsinstituut.getWordtgegevendoorOpleidings()).containsOnly(opleidingBack);
        assertThat(opleidingBack.getWordtgegevendoorOnderwijsinstituuts()).containsOnly(onderwijsinstituut);

        onderwijsinstituut.removeWordtgegevendoorOpleiding(opleidingBack);
        assertThat(onderwijsinstituut.getWordtgegevendoorOpleidings()).doesNotContain(opleidingBack);
        assertThat(opleidingBack.getWordtgegevendoorOnderwijsinstituuts()).doesNotContain(onderwijsinstituut);

        onderwijsinstituut.wordtgegevendoorOpleidings(new HashSet<>(Set.of(opleidingBack)));
        assertThat(onderwijsinstituut.getWordtgegevendoorOpleidings()).containsOnly(opleidingBack);
        assertThat(opleidingBack.getWordtgegevendoorOnderwijsinstituuts()).containsOnly(onderwijsinstituut);

        onderwijsinstituut.setWordtgegevendoorOpleidings(new HashSet<>());
        assertThat(onderwijsinstituut.getWordtgegevendoorOpleidings()).doesNotContain(opleidingBack);
        assertThat(opleidingBack.getWordtgegevendoorOnderwijsinstituuts()).doesNotContain(onderwijsinstituut);
    }
}
