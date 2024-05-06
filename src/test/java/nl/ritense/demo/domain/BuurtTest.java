package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AreaalTestSamples.*;
import static nl.ritense.demo.domain.BuurtTestSamples.*;
import static nl.ritense.demo.domain.GebiedTestSamples.*;
import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static nl.ritense.demo.domain.PandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuurtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Buurt.class);
        Buurt buurt1 = getBuurtSample1();
        Buurt buurt2 = new Buurt();
        assertThat(buurt1).isNotEqualTo(buurt2);

        buurt2.setId(buurt1.getId());
        assertThat(buurt1).isEqualTo(buurt2);

        buurt2 = getBuurtSample2();
        assertThat(buurt1).isNotEqualTo(buurt2);
    }

    @Test
    void komtovereenGebiedTest() throws Exception {
        Buurt buurt = getBuurtRandomSampleGenerator();
        Gebied gebiedBack = getGebiedRandomSampleGenerator();

        buurt.setKomtovereenGebied(gebiedBack);
        assertThat(buurt.getKomtovereenGebied()).isEqualTo(gebiedBack);
        assertThat(gebiedBack.getKomtovereenBuurt()).isEqualTo(buurt);

        buurt.komtovereenGebied(null);
        assertThat(buurt.getKomtovereenGebied()).isNull();
        assertThat(gebiedBack.getKomtovereenBuurt()).isNull();
    }

    @Test
    void ligtinNummeraanduidingTest() throws Exception {
        Buurt buurt = getBuurtRandomSampleGenerator();
        Nummeraanduiding nummeraanduidingBack = getNummeraanduidingRandomSampleGenerator();

        buurt.addLigtinNummeraanduiding(nummeraanduidingBack);
        assertThat(buurt.getLigtinNummeraanduidings()).containsOnly(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinBuurt()).isEqualTo(buurt);

        buurt.removeLigtinNummeraanduiding(nummeraanduidingBack);
        assertThat(buurt.getLigtinNummeraanduidings()).doesNotContain(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinBuurt()).isNull();

        buurt.ligtinNummeraanduidings(new HashSet<>(Set.of(nummeraanduidingBack)));
        assertThat(buurt.getLigtinNummeraanduidings()).containsOnly(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinBuurt()).isEqualTo(buurt);

        buurt.setLigtinNummeraanduidings(new HashSet<>());
        assertThat(buurt.getLigtinNummeraanduidings()).doesNotContain(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinBuurt()).isNull();
    }

    @Test
    void zonderverblijfsobjectligtinPandTest() throws Exception {
        Buurt buurt = getBuurtRandomSampleGenerator();
        Pand pandBack = getPandRandomSampleGenerator();

        buurt.addZonderverblijfsobjectligtinPand(pandBack);
        assertThat(buurt.getZonderverblijfsobjectligtinPands()).containsOnly(pandBack);
        assertThat(pandBack.getZonderverblijfsobjectligtinBuurt()).isEqualTo(buurt);

        buurt.removeZonderverblijfsobjectligtinPand(pandBack);
        assertThat(buurt.getZonderverblijfsobjectligtinPands()).doesNotContain(pandBack);
        assertThat(pandBack.getZonderverblijfsobjectligtinBuurt()).isNull();

        buurt.zonderverblijfsobjectligtinPands(new HashSet<>(Set.of(pandBack)));
        assertThat(buurt.getZonderverblijfsobjectligtinPands()).containsOnly(pandBack);
        assertThat(pandBack.getZonderverblijfsobjectligtinBuurt()).isEqualTo(buurt);

        buurt.setZonderverblijfsobjectligtinPands(new HashSet<>());
        assertThat(buurt.getZonderverblijfsobjectligtinPands()).doesNotContain(pandBack);
        assertThat(pandBack.getZonderverblijfsobjectligtinBuurt()).isNull();
    }

    @Test
    void ligtinAreaalTest() throws Exception {
        Buurt buurt = getBuurtRandomSampleGenerator();
        Areaal areaalBack = getAreaalRandomSampleGenerator();

        buurt.addLigtinAreaal(areaalBack);
        assertThat(buurt.getLigtinAreaals()).containsOnly(areaalBack);
        assertThat(areaalBack.getLigtinBuurts()).containsOnly(buurt);

        buurt.removeLigtinAreaal(areaalBack);
        assertThat(buurt.getLigtinAreaals()).doesNotContain(areaalBack);
        assertThat(areaalBack.getLigtinBuurts()).doesNotContain(buurt);

        buurt.ligtinAreaals(new HashSet<>(Set.of(areaalBack)));
        assertThat(buurt.getLigtinAreaals()).containsOnly(areaalBack);
        assertThat(areaalBack.getLigtinBuurts()).containsOnly(buurt);

        buurt.setLigtinAreaals(new HashSet<>());
        assertThat(buurt.getLigtinAreaals()).doesNotContain(areaalBack);
        assertThat(areaalBack.getLigtinBuurts()).doesNotContain(buurt);
    }
}
