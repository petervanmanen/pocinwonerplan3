package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AreaalTestSamples.*;
import static nl.ritense.demo.domain.BinnenlocatieTestSamples.*;
import static nl.ritense.demo.domain.WijkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WijkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wijk.class);
        Wijk wijk1 = getWijkSample1();
        Wijk wijk2 = new Wijk();
        assertThat(wijk1).isNotEqualTo(wijk2);

        wijk2.setId(wijk1.getId());
        assertThat(wijk1).isEqualTo(wijk2);

        wijk2 = getWijkSample2();
        assertThat(wijk1).isNotEqualTo(wijk2);
    }

    @Test
    void bedientBinnenlocatieTest() throws Exception {
        Wijk wijk = getWijkRandomSampleGenerator();
        Binnenlocatie binnenlocatieBack = getBinnenlocatieRandomSampleGenerator();

        wijk.addBedientBinnenlocatie(binnenlocatieBack);
        assertThat(wijk.getBedientBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getBedientWijk()).isEqualTo(wijk);

        wijk.removeBedientBinnenlocatie(binnenlocatieBack);
        assertThat(wijk.getBedientBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getBedientWijk()).isNull();

        wijk.bedientBinnenlocaties(new HashSet<>(Set.of(binnenlocatieBack)));
        assertThat(wijk.getBedientBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getBedientWijk()).isEqualTo(wijk);

        wijk.setBedientBinnenlocaties(new HashSet<>());
        assertThat(wijk.getBedientBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getBedientWijk()).isNull();
    }

    @Test
    void valtbinnenAreaalTest() throws Exception {
        Wijk wijk = getWijkRandomSampleGenerator();
        Areaal areaalBack = getAreaalRandomSampleGenerator();

        wijk.addValtbinnenAreaal(areaalBack);
        assertThat(wijk.getValtbinnenAreaals()).containsOnly(areaalBack);
        assertThat(areaalBack.getValtbinnenWijks()).containsOnly(wijk);

        wijk.removeValtbinnenAreaal(areaalBack);
        assertThat(wijk.getValtbinnenAreaals()).doesNotContain(areaalBack);
        assertThat(areaalBack.getValtbinnenWijks()).doesNotContain(wijk);

        wijk.valtbinnenAreaals(new HashSet<>(Set.of(areaalBack)));
        assertThat(wijk.getValtbinnenAreaals()).containsOnly(areaalBack);
        assertThat(areaalBack.getValtbinnenWijks()).containsOnly(wijk);

        wijk.setValtbinnenAreaals(new HashSet<>());
        assertThat(wijk.getValtbinnenAreaals()).doesNotContain(areaalBack);
        assertThat(areaalBack.getValtbinnenWijks()).doesNotContain(wijk);
    }
}
