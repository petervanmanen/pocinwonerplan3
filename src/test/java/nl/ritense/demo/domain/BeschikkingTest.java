package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeperkingTestSamples.*;
import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieregelTestSamples.*;
import static nl.ritense.demo.domain.LeveringTestSamples.*;
import static nl.ritense.demo.domain.ToewijzingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeschikkingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beschikking.class);
        Beschikking beschikking1 = getBeschikkingSample1();
        Beschikking beschikking2 = new Beschikking();
        assertThat(beschikking1).isNotEqualTo(beschikking2);

        beschikking2.setId(beschikking1.getId());
        assertThat(beschikking1).isEqualTo(beschikking2);

        beschikking2 = getBeschikkingSample2();
        assertThat(beschikking1).isNotEqualTo(beschikking2);
    }

    @Test
    void toewijzingToewijzingTest() throws Exception {
        Beschikking beschikking = getBeschikkingRandomSampleGenerator();
        Toewijzing toewijzingBack = getToewijzingRandomSampleGenerator();

        beschikking.addToewijzingToewijzing(toewijzingBack);
        assertThat(beschikking.getToewijzingToewijzings()).containsOnly(toewijzingBack);
        assertThat(toewijzingBack.getToewijzingBeschikking()).isEqualTo(beschikking);

        beschikking.removeToewijzingToewijzing(toewijzingBack);
        assertThat(beschikking.getToewijzingToewijzings()).doesNotContain(toewijzingBack);
        assertThat(toewijzingBack.getToewijzingBeschikking()).isNull();

        beschikking.toewijzingToewijzings(new HashSet<>(Set.of(toewijzingBack)));
        assertThat(beschikking.getToewijzingToewijzings()).containsOnly(toewijzingBack);
        assertThat(toewijzingBack.getToewijzingBeschikking()).isEqualTo(beschikking);

        beschikking.setToewijzingToewijzings(new HashSet<>());
        assertThat(beschikking.getToewijzingToewijzings()).doesNotContain(toewijzingBack);
        assertThat(toewijzingBack.getToewijzingBeschikking()).isNull();
    }

    @Test
    void emptyClientTest() throws Exception {
        Beschikking beschikking = getBeschikkingRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        beschikking.setEmptyClient(clientBack);
        assertThat(beschikking.getEmptyClient()).isEqualTo(clientBack);

        beschikking.emptyClient(null);
        assertThat(beschikking.getEmptyClient()).isNull();
    }

    @Test
    void geeftafClientbegeleiderTest() throws Exception {
        Beschikking beschikking = getBeschikkingRandomSampleGenerator();
        Clientbegeleider clientbegeleiderBack = getClientbegeleiderRandomSampleGenerator();

        beschikking.setGeeftafClientbegeleider(clientbegeleiderBack);
        assertThat(beschikking.getGeeftafClientbegeleider()).isEqualTo(clientbegeleiderBack);

        beschikking.geeftafClientbegeleider(null);
        assertThat(beschikking.getGeeftafClientbegeleider()).isNull();
    }

    @Test
    void isgebaseerdopBeperkingTest() throws Exception {
        Beschikking beschikking = getBeschikkingRandomSampleGenerator();
        Beperking beperkingBack = getBeperkingRandomSampleGenerator();

        beschikking.addIsgebaseerdopBeperking(beperkingBack);
        assertThat(beschikking.getIsgebaseerdopBeperkings()).containsOnly(beperkingBack);
        assertThat(beperkingBack.getIsgebaseerdopBeschikking()).isEqualTo(beschikking);

        beschikking.removeIsgebaseerdopBeperking(beperkingBack);
        assertThat(beschikking.getIsgebaseerdopBeperkings()).doesNotContain(beperkingBack);
        assertThat(beperkingBack.getIsgebaseerdopBeschikking()).isNull();

        beschikking.isgebaseerdopBeperkings(new HashSet<>(Set.of(beperkingBack)));
        assertThat(beschikking.getIsgebaseerdopBeperkings()).containsOnly(beperkingBack);
        assertThat(beperkingBack.getIsgebaseerdopBeschikking()).isEqualTo(beschikking);

        beschikking.setIsgebaseerdopBeperkings(new HashSet<>());
        assertThat(beschikking.getIsgebaseerdopBeperkings()).doesNotContain(beperkingBack);
        assertThat(beperkingBack.getIsgebaseerdopBeschikking()).isNull();
    }

    @Test
    void geleverdeprestatieLeveringTest() throws Exception {
        Beschikking beschikking = getBeschikkingRandomSampleGenerator();
        Levering leveringBack = getLeveringRandomSampleGenerator();

        beschikking.addGeleverdeprestatieLevering(leveringBack);
        assertThat(beschikking.getGeleverdeprestatieLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getGeleverdeprestatieBeschikking()).isEqualTo(beschikking);

        beschikking.removeGeleverdeprestatieLevering(leveringBack);
        assertThat(beschikking.getGeleverdeprestatieLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getGeleverdeprestatieBeschikking()).isNull();

        beschikking.geleverdeprestatieLeverings(new HashSet<>(Set.of(leveringBack)));
        assertThat(beschikking.getGeleverdeprestatieLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getGeleverdeprestatieBeschikking()).isEqualTo(beschikking);

        beschikking.setGeleverdeprestatieLeverings(new HashSet<>());
        assertThat(beschikking.getGeleverdeprestatieLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getGeleverdeprestatieBeschikking()).isNull();
    }

    @Test
    void isvoorDeclaratieregelTest() throws Exception {
        Beschikking beschikking = getBeschikkingRandomSampleGenerator();
        Declaratieregel declaratieregelBack = getDeclaratieregelRandomSampleGenerator();

        beschikking.addIsvoorDeclaratieregel(declaratieregelBack);
        assertThat(beschikking.getIsvoorDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getIsvoorBeschikking()).isEqualTo(beschikking);

        beschikking.removeIsvoorDeclaratieregel(declaratieregelBack);
        assertThat(beschikking.getIsvoorDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getIsvoorBeschikking()).isNull();

        beschikking.isvoorDeclaratieregels(new HashSet<>(Set.of(declaratieregelBack)));
        assertThat(beschikking.getIsvoorDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getIsvoorBeschikking()).isEqualTo(beschikking);

        beschikking.setIsvoorDeclaratieregels(new HashSet<>());
        assertThat(beschikking.getIsvoorDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getIsvoorBeschikking()).isNull();
    }
}
