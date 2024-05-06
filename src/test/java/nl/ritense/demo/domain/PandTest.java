package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BuurtTestSamples.*;
import static nl.ritense.demo.domain.PandTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static nl.ritense.demo.domain.VerblijfsobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pand.class);
        Pand pand1 = getPandSample1();
        Pand pand2 = new Pand();
        assertThat(pand1).isNotEqualTo(pand2);

        pand2.setId(pand1.getId());
        assertThat(pand1).isEqualTo(pand2);

        pand2 = getPandSample2();
        assertThat(pand1).isNotEqualTo(pand2);
    }

    @Test
    void heeftVastgoedobjectTest() throws Exception {
        Pand pand = getPandRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        pand.setHeeftVastgoedobject(vastgoedobjectBack);
        assertThat(pand.getHeeftVastgoedobject()).isEqualTo(vastgoedobjectBack);

        pand.heeftVastgoedobject(null);
        assertThat(pand.getHeeftVastgoedobject()).isNull();
    }

    @Test
    void zonderverblijfsobjectligtinBuurtTest() throws Exception {
        Pand pand = getPandRandomSampleGenerator();
        Buurt buurtBack = getBuurtRandomSampleGenerator();

        pand.setZonderverblijfsobjectligtinBuurt(buurtBack);
        assertThat(pand.getZonderverblijfsobjectligtinBuurt()).isEqualTo(buurtBack);

        pand.zonderverblijfsobjectligtinBuurt(null);
        assertThat(pand.getZonderverblijfsobjectligtinBuurt()).isNull();
    }

    @Test
    void betreftVastgoedobjectTest() throws Exception {
        Pand pand = getPandRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        pand.setBetreftVastgoedobject(vastgoedobjectBack);
        assertThat(pand.getBetreftVastgoedobject()).isEqualTo(vastgoedobjectBack);
        assertThat(vastgoedobjectBack.getBetreftPand()).isEqualTo(pand);

        pand.betreftVastgoedobject(null);
        assertThat(pand.getBetreftVastgoedobject()).isNull();
        assertThat(vastgoedobjectBack.getBetreftPand()).isNull();
    }

    @Test
    void maaktdeeluitvanVerblijfsobjectTest() throws Exception {
        Pand pand = getPandRandomSampleGenerator();
        Verblijfsobject verblijfsobjectBack = getVerblijfsobjectRandomSampleGenerator();

        pand.addMaaktdeeluitvanVerblijfsobject(verblijfsobjectBack);
        assertThat(pand.getMaaktdeeluitvanVerblijfsobjects()).containsOnly(verblijfsobjectBack);
        assertThat(verblijfsobjectBack.getMaaktdeeluitvanPands()).containsOnly(pand);

        pand.removeMaaktdeeluitvanVerblijfsobject(verblijfsobjectBack);
        assertThat(pand.getMaaktdeeluitvanVerblijfsobjects()).doesNotContain(verblijfsobjectBack);
        assertThat(verblijfsobjectBack.getMaaktdeeluitvanPands()).doesNotContain(pand);

        pand.maaktdeeluitvanVerblijfsobjects(new HashSet<>(Set.of(verblijfsobjectBack)));
        assertThat(pand.getMaaktdeeluitvanVerblijfsobjects()).containsOnly(verblijfsobjectBack);
        assertThat(verblijfsobjectBack.getMaaktdeeluitvanPands()).containsOnly(pand);

        pand.setMaaktdeeluitvanVerblijfsobjects(new HashSet<>());
        assertThat(pand.getMaaktdeeluitvanVerblijfsobjects()).doesNotContain(verblijfsobjectBack);
        assertThat(verblijfsobjectBack.getMaaktdeeluitvanPands()).doesNotContain(pand);
    }
}
