package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwdeelTestSamples.*;
import static nl.ritense.demo.domain.InspectieTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.PandTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static nl.ritense.demo.domain.VerblijfsobjectTestSamples.*;
import static nl.ritense.demo.domain.WerkbonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VastgoedobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vastgoedobject.class);
        Vastgoedobject vastgoedobject1 = getVastgoedobjectSample1();
        Vastgoedobject vastgoedobject2 = new Vastgoedobject();
        assertThat(vastgoedobject1).isNotEqualTo(vastgoedobject2);

        vastgoedobject2.setId(vastgoedobject1.getId());
        assertThat(vastgoedobject1).isEqualTo(vastgoedobject2);

        vastgoedobject2 = getVastgoedobjectSample2();
        assertThat(vastgoedobject1).isNotEqualTo(vastgoedobject2);
    }

    @Test
    void betreftPandTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Pand pandBack = getPandRandomSampleGenerator();

        vastgoedobject.setBetreftPand(pandBack);
        assertThat(vastgoedobject.getBetreftPand()).isEqualTo(pandBack);

        vastgoedobject.betreftPand(null);
        assertThat(vastgoedobject.getBetreftPand()).isNull();
    }

    @Test
    void bestaatuitBouwdeelTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Bouwdeel bouwdeelBack = getBouwdeelRandomSampleGenerator();

        vastgoedobject.addBestaatuitBouwdeel(bouwdeelBack);
        assertThat(vastgoedobject.getBestaatuitBouwdeels()).containsOnly(bouwdeelBack);
        assertThat(bouwdeelBack.getBestaatuitVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.removeBestaatuitBouwdeel(bouwdeelBack);
        assertThat(vastgoedobject.getBestaatuitBouwdeels()).doesNotContain(bouwdeelBack);
        assertThat(bouwdeelBack.getBestaatuitVastgoedobject()).isNull();

        vastgoedobject.bestaatuitBouwdeels(new HashSet<>(Set.of(bouwdeelBack)));
        assertThat(vastgoedobject.getBestaatuitBouwdeels()).containsOnly(bouwdeelBack);
        assertThat(bouwdeelBack.getBestaatuitVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.setBestaatuitBouwdeels(new HashSet<>());
        assertThat(vastgoedobject.getBestaatuitBouwdeels()).doesNotContain(bouwdeelBack);
        assertThat(bouwdeelBack.getBestaatuitVastgoedobject()).isNull();
    }

    @Test
    void betreftInspectieTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Inspectie inspectieBack = getInspectieRandomSampleGenerator();

        vastgoedobject.addBetreftInspectie(inspectieBack);
        assertThat(vastgoedobject.getBetreftInspecties()).containsOnly(inspectieBack);
        assertThat(inspectieBack.getBetreftVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.removeBetreftInspectie(inspectieBack);
        assertThat(vastgoedobject.getBetreftInspecties()).doesNotContain(inspectieBack);
        assertThat(inspectieBack.getBetreftVastgoedobject()).isNull();

        vastgoedobject.betreftInspecties(new HashSet<>(Set.of(inspectieBack)));
        assertThat(vastgoedobject.getBetreftInspecties()).containsOnly(inspectieBack);
        assertThat(inspectieBack.getBetreftVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.setBetreftInspecties(new HashSet<>());
        assertThat(vastgoedobject.getBetreftInspecties()).doesNotContain(inspectieBack);
        assertThat(inspectieBack.getBetreftVastgoedobject()).isNull();
    }

    @Test
    void heeftVerblijfsobjectTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Verblijfsobject verblijfsobjectBack = getVerblijfsobjectRandomSampleGenerator();

        vastgoedobject.setHeeftVerblijfsobject(verblijfsobjectBack);
        assertThat(vastgoedobject.getHeeftVerblijfsobject()).isEqualTo(verblijfsobjectBack);
        assertThat(verblijfsobjectBack.getHeeftVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.heeftVerblijfsobject(null);
        assertThat(vastgoedobject.getHeeftVerblijfsobject()).isNull();
        assertThat(verblijfsobjectBack.getHeeftVastgoedobject()).isNull();
    }

    @Test
    void heeftPandTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Pand pandBack = getPandRandomSampleGenerator();

        vastgoedobject.setHeeftPand(pandBack);
        assertThat(vastgoedobject.getHeeftPand()).isEqualTo(pandBack);
        assertThat(pandBack.getHeeftVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.heeftPand(null);
        assertThat(vastgoedobject.getHeeftPand()).isNull();
        assertThat(pandBack.getHeeftVastgoedobject()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        vastgoedobject.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(vastgoedobject.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        vastgoedobject.heeftKostenplaats(null);
        assertThat(vastgoedobject.getHeeftKostenplaats()).isNull();
    }

    @Test
    void betreftWerkbonTest() throws Exception {
        Vastgoedobject vastgoedobject = getVastgoedobjectRandomSampleGenerator();
        Werkbon werkbonBack = getWerkbonRandomSampleGenerator();

        vastgoedobject.addBetreftWerkbon(werkbonBack);
        assertThat(vastgoedobject.getBetreftWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getBetreftVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.removeBetreftWerkbon(werkbonBack);
        assertThat(vastgoedobject.getBetreftWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getBetreftVastgoedobject()).isNull();

        vastgoedobject.betreftWerkbons(new HashSet<>(Set.of(werkbonBack)));
        assertThat(vastgoedobject.getBetreftWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getBetreftVastgoedobject()).isEqualTo(vastgoedobject);

        vastgoedobject.setBetreftWerkbons(new HashSet<>());
        assertThat(vastgoedobject.getBetreftWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getBetreftVastgoedobject()).isNull();
    }
}
