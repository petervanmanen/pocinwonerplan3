package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AreaalTestSamples.*;
import static nl.ritense.demo.domain.BuurtTestSamples.*;
import static nl.ritense.demo.domain.SchouwrondeTestSamples.*;
import static nl.ritense.demo.domain.WijkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Areaal.class);
        Areaal areaal1 = getAreaalSample1();
        Areaal areaal2 = new Areaal();
        assertThat(areaal1).isNotEqualTo(areaal2);

        areaal2.setId(areaal1.getId());
        assertThat(areaal1).isEqualTo(areaal2);

        areaal2 = getAreaalSample2();
        assertThat(areaal1).isNotEqualTo(areaal2);
    }

    @Test
    void ligtinBuurtTest() throws Exception {
        Areaal areaal = getAreaalRandomSampleGenerator();
        Buurt buurtBack = getBuurtRandomSampleGenerator();

        areaal.addLigtinBuurt(buurtBack);
        assertThat(areaal.getLigtinBuurts()).containsOnly(buurtBack);

        areaal.removeLigtinBuurt(buurtBack);
        assertThat(areaal.getLigtinBuurts()).doesNotContain(buurtBack);

        areaal.ligtinBuurts(new HashSet<>(Set.of(buurtBack)));
        assertThat(areaal.getLigtinBuurts()).containsOnly(buurtBack);

        areaal.setLigtinBuurts(new HashSet<>());
        assertThat(areaal.getLigtinBuurts()).doesNotContain(buurtBack);
    }

    @Test
    void valtbinnenWijkTest() throws Exception {
        Areaal areaal = getAreaalRandomSampleGenerator();
        Wijk wijkBack = getWijkRandomSampleGenerator();

        areaal.addValtbinnenWijk(wijkBack);
        assertThat(areaal.getValtbinnenWijks()).containsOnly(wijkBack);

        areaal.removeValtbinnenWijk(wijkBack);
        assertThat(areaal.getValtbinnenWijks()).doesNotContain(wijkBack);

        areaal.valtbinnenWijks(new HashSet<>(Set.of(wijkBack)));
        assertThat(areaal.getValtbinnenWijks()).containsOnly(wijkBack);

        areaal.setValtbinnenWijks(new HashSet<>());
        assertThat(areaal.getValtbinnenWijks()).doesNotContain(wijkBack);
    }

    @Test
    void binnenSchouwrondeTest() throws Exception {
        Areaal areaal = getAreaalRandomSampleGenerator();
        Schouwronde schouwrondeBack = getSchouwrondeRandomSampleGenerator();

        areaal.addBinnenSchouwronde(schouwrondeBack);
        assertThat(areaal.getBinnenSchouwrondes()).containsOnly(schouwrondeBack);

        areaal.removeBinnenSchouwronde(schouwrondeBack);
        assertThat(areaal.getBinnenSchouwrondes()).doesNotContain(schouwrondeBack);

        areaal.binnenSchouwrondes(new HashSet<>(Set.of(schouwrondeBack)));
        assertThat(areaal.getBinnenSchouwrondes()).containsOnly(schouwrondeBack);

        areaal.setBinnenSchouwrondes(new HashSet<>());
        assertThat(areaal.getBinnenSchouwrondes()).doesNotContain(schouwrondeBack);
    }
}
