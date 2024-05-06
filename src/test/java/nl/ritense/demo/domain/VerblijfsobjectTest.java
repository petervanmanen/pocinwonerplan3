package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BinnenlocatieTestSamples.*;
import static nl.ritense.demo.domain.PandTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static nl.ritense.demo.domain.VerblijfsobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfsobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfsobject.class);
        Verblijfsobject verblijfsobject1 = getVerblijfsobjectSample1();
        Verblijfsobject verblijfsobject2 = new Verblijfsobject();
        assertThat(verblijfsobject1).isNotEqualTo(verblijfsobject2);

        verblijfsobject2.setId(verblijfsobject1.getId());
        assertThat(verblijfsobject1).isEqualTo(verblijfsobject2);

        verblijfsobject2 = getVerblijfsobjectSample2();
        assertThat(verblijfsobject1).isNotEqualTo(verblijfsobject2);
    }

    @Test
    void heeftVastgoedobjectTest() throws Exception {
        Verblijfsobject verblijfsobject = getVerblijfsobjectRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        verblijfsobject.setHeeftVastgoedobject(vastgoedobjectBack);
        assertThat(verblijfsobject.getHeeftVastgoedobject()).isEqualTo(vastgoedobjectBack);

        verblijfsobject.heeftVastgoedobject(null);
        assertThat(verblijfsobject.getHeeftVastgoedobject()).isNull();
    }

    @Test
    void maaktdeeluitvanPandTest() throws Exception {
        Verblijfsobject verblijfsobject = getVerblijfsobjectRandomSampleGenerator();
        Pand pandBack = getPandRandomSampleGenerator();

        verblijfsobject.addMaaktdeeluitvanPand(pandBack);
        assertThat(verblijfsobject.getMaaktdeeluitvanPands()).containsOnly(pandBack);

        verblijfsobject.removeMaaktdeeluitvanPand(pandBack);
        assertThat(verblijfsobject.getMaaktdeeluitvanPands()).doesNotContain(pandBack);

        verblijfsobject.maaktdeeluitvanPands(new HashSet<>(Set.of(pandBack)));
        assertThat(verblijfsobject.getMaaktdeeluitvanPands()).containsOnly(pandBack);

        verblijfsobject.setMaaktdeeluitvanPands(new HashSet<>());
        assertThat(verblijfsobject.getMaaktdeeluitvanPands()).doesNotContain(pandBack);
    }

    @Test
    void isgevestigdinBinnenlocatieTest() throws Exception {
        Verblijfsobject verblijfsobject = getVerblijfsobjectRandomSampleGenerator();
        Binnenlocatie binnenlocatieBack = getBinnenlocatieRandomSampleGenerator();

        verblijfsobject.addIsgevestigdinBinnenlocatie(binnenlocatieBack);
        assertThat(verblijfsobject.getIsgevestigdinBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getIsgevestigdinVerblijfsobject()).isEqualTo(verblijfsobject);

        verblijfsobject.removeIsgevestigdinBinnenlocatie(binnenlocatieBack);
        assertThat(verblijfsobject.getIsgevestigdinBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getIsgevestigdinVerblijfsobject()).isNull();

        verblijfsobject.isgevestigdinBinnenlocaties(new HashSet<>(Set.of(binnenlocatieBack)));
        assertThat(verblijfsobject.getIsgevestigdinBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getIsgevestigdinVerblijfsobject()).isEqualTo(verblijfsobject);

        verblijfsobject.setIsgevestigdinBinnenlocaties(new HashSet<>());
        assertThat(verblijfsobject.getIsgevestigdinBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getIsgevestigdinVerblijfsobject()).isNull();
    }
}
