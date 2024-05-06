package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelijningTestSamples.*;
import static nl.ritense.demo.domain.BinnenlocatieTestSamples.*;
import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.SportmateriaalTestSamples.*;
import static nl.ritense.demo.domain.VerblijfsobjectTestSamples.*;
import static nl.ritense.demo.domain.WijkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BinnenlocatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Binnenlocatie.class);
        Binnenlocatie binnenlocatie1 = getBinnenlocatieSample1();
        Binnenlocatie binnenlocatie2 = new Binnenlocatie();
        assertThat(binnenlocatie1).isNotEqualTo(binnenlocatie2);

        binnenlocatie2.setId(binnenlocatie1.getId());
        assertThat(binnenlocatie1).isEqualTo(binnenlocatie2);

        binnenlocatie2 = getBinnenlocatieSample2();
        assertThat(binnenlocatie1).isNotEqualTo(binnenlocatie2);
    }

    @Test
    void inspectierapportDocumentTest() throws Exception {
        Binnenlocatie binnenlocatie = getBinnenlocatieRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        binnenlocatie.addInspectierapportDocument(documentBack);
        assertThat(binnenlocatie.getInspectierapportDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getInspectierapportBinnenlocatie()).isEqualTo(binnenlocatie);

        binnenlocatie.removeInspectierapportDocument(documentBack);
        assertThat(binnenlocatie.getInspectierapportDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getInspectierapportBinnenlocatie()).isNull();

        binnenlocatie.inspectierapportDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(binnenlocatie.getInspectierapportDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getInspectierapportBinnenlocatie()).isEqualTo(binnenlocatie);

        binnenlocatie.setInspectierapportDocuments(new HashSet<>());
        assertThat(binnenlocatie.getInspectierapportDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getInspectierapportBinnenlocatie()).isNull();
    }

    @Test
    void isgevestigdinVerblijfsobjectTest() throws Exception {
        Binnenlocatie binnenlocatie = getBinnenlocatieRandomSampleGenerator();
        Verblijfsobject verblijfsobjectBack = getVerblijfsobjectRandomSampleGenerator();

        binnenlocatie.setIsgevestigdinVerblijfsobject(verblijfsobjectBack);
        assertThat(binnenlocatie.getIsgevestigdinVerblijfsobject()).isEqualTo(verblijfsobjectBack);

        binnenlocatie.isgevestigdinVerblijfsobject(null);
        assertThat(binnenlocatie.getIsgevestigdinVerblijfsobject()).isNull();
    }

    @Test
    void bedientWijkTest() throws Exception {
        Binnenlocatie binnenlocatie = getBinnenlocatieRandomSampleGenerator();
        Wijk wijkBack = getWijkRandomSampleGenerator();

        binnenlocatie.setBedientWijk(wijkBack);
        assertThat(binnenlocatie.getBedientWijk()).isEqualTo(wijkBack);

        binnenlocatie.bedientWijk(null);
        assertThat(binnenlocatie.getBedientWijk()).isNull();
    }

    @Test
    void heeftBelijningTest() throws Exception {
        Binnenlocatie binnenlocatie = getBinnenlocatieRandomSampleGenerator();
        Belijning belijningBack = getBelijningRandomSampleGenerator();

        binnenlocatie.addHeeftBelijning(belijningBack);
        assertThat(binnenlocatie.getHeeftBelijnings()).containsOnly(belijningBack);

        binnenlocatie.removeHeeftBelijning(belijningBack);
        assertThat(binnenlocatie.getHeeftBelijnings()).doesNotContain(belijningBack);

        binnenlocatie.heeftBelijnings(new HashSet<>(Set.of(belijningBack)));
        assertThat(binnenlocatie.getHeeftBelijnings()).containsOnly(belijningBack);

        binnenlocatie.setHeeftBelijnings(new HashSet<>());
        assertThat(binnenlocatie.getHeeftBelijnings()).doesNotContain(belijningBack);
    }

    @Test
    void heeftSportmateriaalTest() throws Exception {
        Binnenlocatie binnenlocatie = getBinnenlocatieRandomSampleGenerator();
        Sportmateriaal sportmateriaalBack = getSportmateriaalRandomSampleGenerator();

        binnenlocatie.addHeeftSportmateriaal(sportmateriaalBack);
        assertThat(binnenlocatie.getHeeftSportmateriaals()).containsOnly(sportmateriaalBack);

        binnenlocatie.removeHeeftSportmateriaal(sportmateriaalBack);
        assertThat(binnenlocatie.getHeeftSportmateriaals()).doesNotContain(sportmateriaalBack);

        binnenlocatie.heeftSportmateriaals(new HashSet<>(Set.of(sportmateriaalBack)));
        assertThat(binnenlocatie.getHeeftSportmateriaals()).containsOnly(sportmateriaalBack);

        binnenlocatie.setHeeftSportmateriaals(new HashSet<>());
        assertThat(binnenlocatie.getHeeftSportmateriaals()).doesNotContain(sportmateriaalBack);
    }
}
