package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwdeelTestSamples.*;
import static nl.ritense.demo.domain.BouwdeelelementTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static nl.ritense.demo.domain.WerkbonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WerkbonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Werkbon.class);
        Werkbon werkbon1 = getWerkbonSample1();
        Werkbon werkbon2 = new Werkbon();
        assertThat(werkbon1).isNotEqualTo(werkbon2);

        werkbon2.setId(werkbon1.getId());
        assertThat(werkbon1).isEqualTo(werkbon2);

        werkbon2 = getWerkbonSample2();
        assertThat(werkbon1).isNotEqualTo(werkbon2);
    }

    @Test
    void betreftVastgoedobjectTest() throws Exception {
        Werkbon werkbon = getWerkbonRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        werkbon.setBetreftVastgoedobject(vastgoedobjectBack);
        assertThat(werkbon.getBetreftVastgoedobject()).isEqualTo(vastgoedobjectBack);

        werkbon.betreftVastgoedobject(null);
        assertThat(werkbon.getBetreftVastgoedobject()).isNull();
    }

    @Test
    void betreftBouwdeelTest() throws Exception {
        Werkbon werkbon = getWerkbonRandomSampleGenerator();
        Bouwdeel bouwdeelBack = getBouwdeelRandomSampleGenerator();

        werkbon.addBetreftBouwdeel(bouwdeelBack);
        assertThat(werkbon.getBetreftBouwdeels()).containsOnly(bouwdeelBack);

        werkbon.removeBetreftBouwdeel(bouwdeelBack);
        assertThat(werkbon.getBetreftBouwdeels()).doesNotContain(bouwdeelBack);

        werkbon.betreftBouwdeels(new HashSet<>(Set.of(bouwdeelBack)));
        assertThat(werkbon.getBetreftBouwdeels()).containsOnly(bouwdeelBack);

        werkbon.setBetreftBouwdeels(new HashSet<>());
        assertThat(werkbon.getBetreftBouwdeels()).doesNotContain(bouwdeelBack);
    }

    @Test
    void betreftBouwdeelelementTest() throws Exception {
        Werkbon werkbon = getWerkbonRandomSampleGenerator();
        Bouwdeelelement bouwdeelelementBack = getBouwdeelelementRandomSampleGenerator();

        werkbon.addBetreftBouwdeelelement(bouwdeelelementBack);
        assertThat(werkbon.getBetreftBouwdeelelements()).containsOnly(bouwdeelelementBack);

        werkbon.removeBetreftBouwdeelelement(bouwdeelelementBack);
        assertThat(werkbon.getBetreftBouwdeelelements()).doesNotContain(bouwdeelelementBack);

        werkbon.betreftBouwdeelelements(new HashSet<>(Set.of(bouwdeelelementBack)));
        assertThat(werkbon.getBetreftBouwdeelelements()).containsOnly(bouwdeelelementBack);

        werkbon.setBetreftBouwdeelelements(new HashSet<>());
        assertThat(werkbon.getBetreftBouwdeelelements()).doesNotContain(bouwdeelelementBack);
    }

    @Test
    void hoortbijInkooporderTest() throws Exception {
        Werkbon werkbon = getWerkbonRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        werkbon.setHoortbijInkooporder(inkooporderBack);
        assertThat(werkbon.getHoortbijInkooporder()).isEqualTo(inkooporderBack);

        werkbon.hoortbijInkooporder(null);
        assertThat(werkbon.getHoortbijInkooporder()).isNull();
    }

    @Test
    void voertwerkuitconformLeverancierTest() throws Exception {
        Werkbon werkbon = getWerkbonRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        werkbon.setVoertwerkuitconformLeverancier(leverancierBack);
        assertThat(werkbon.getVoertwerkuitconformLeverancier()).isEqualTo(leverancierBack);

        werkbon.voertwerkuitconformLeverancier(null);
        assertThat(werkbon.getVoertwerkuitconformLeverancier()).isNull();
    }
}
