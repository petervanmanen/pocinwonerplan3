package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BuurtTestSamples.*;
import static nl.ritense.demo.domain.GebiedTestSamples.*;
import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebied.class);
        Gebied gebied1 = getGebiedSample1();
        Gebied gebied2 = new Gebied();
        assertThat(gebied1).isNotEqualTo(gebied2);

        gebied2.setId(gebied1.getId());
        assertThat(gebied1).isEqualTo(gebied2);

        gebied2 = getGebiedSample2();
        assertThat(gebied1).isNotEqualTo(gebied2);
    }

    @Test
    void komtovereenBuurtTest() throws Exception {
        Gebied gebied = getGebiedRandomSampleGenerator();
        Buurt buurtBack = getBuurtRandomSampleGenerator();

        gebied.setKomtovereenBuurt(buurtBack);
        assertThat(gebied.getKomtovereenBuurt()).isEqualTo(buurtBack);

        gebied.komtovereenBuurt(null);
        assertThat(gebied.getKomtovereenBuurt()).isNull();
    }

    @Test
    void ligtinNummeraanduidingTest() throws Exception {
        Gebied gebied = getGebiedRandomSampleGenerator();
        Nummeraanduiding nummeraanduidingBack = getNummeraanduidingRandomSampleGenerator();

        gebied.addLigtinNummeraanduiding(nummeraanduidingBack);
        assertThat(gebied.getLigtinNummeraanduidings()).containsOnly(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinGebieds()).containsOnly(gebied);

        gebied.removeLigtinNummeraanduiding(nummeraanduidingBack);
        assertThat(gebied.getLigtinNummeraanduidings()).doesNotContain(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinGebieds()).doesNotContain(gebied);

        gebied.ligtinNummeraanduidings(new HashSet<>(Set.of(nummeraanduidingBack)));
        assertThat(gebied.getLigtinNummeraanduidings()).containsOnly(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinGebieds()).containsOnly(gebied);

        gebied.setLigtinNummeraanduidings(new HashSet<>());
        assertThat(gebied.getLigtinNummeraanduidings()).doesNotContain(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinGebieds()).doesNotContain(gebied);
    }
}
