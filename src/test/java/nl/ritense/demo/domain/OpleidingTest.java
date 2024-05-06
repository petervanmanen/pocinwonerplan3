package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderwijsinstituutTestSamples.*;
import static nl.ritense.demo.domain.OpleidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpleidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opleiding.class);
        Opleiding opleiding1 = getOpleidingSample1();
        Opleiding opleiding2 = new Opleiding();
        assertThat(opleiding1).isNotEqualTo(opleiding2);

        opleiding2.setId(opleiding1.getId());
        assertThat(opleiding1).isEqualTo(opleiding2);

        opleiding2 = getOpleidingSample2();
        assertThat(opleiding1).isNotEqualTo(opleiding2);
    }

    @Test
    void wordtgegevendoorOnderwijsinstituutTest() throws Exception {
        Opleiding opleiding = getOpleidingRandomSampleGenerator();
        Onderwijsinstituut onderwijsinstituutBack = getOnderwijsinstituutRandomSampleGenerator();

        opleiding.addWordtgegevendoorOnderwijsinstituut(onderwijsinstituutBack);
        assertThat(opleiding.getWordtgegevendoorOnderwijsinstituuts()).containsOnly(onderwijsinstituutBack);

        opleiding.removeWordtgegevendoorOnderwijsinstituut(onderwijsinstituutBack);
        assertThat(opleiding.getWordtgegevendoorOnderwijsinstituuts()).doesNotContain(onderwijsinstituutBack);

        opleiding.wordtgegevendoorOnderwijsinstituuts(new HashSet<>(Set.of(onderwijsinstituutBack)));
        assertThat(opleiding.getWordtgegevendoorOnderwijsinstituuts()).containsOnly(onderwijsinstituutBack);

        opleiding.setWordtgegevendoorOnderwijsinstituuts(new HashSet<>());
        assertThat(opleiding.getWordtgegevendoorOnderwijsinstituuts()).doesNotContain(onderwijsinstituutBack);
    }
}
