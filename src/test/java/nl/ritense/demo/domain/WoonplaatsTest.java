package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static nl.ritense.demo.domain.PostadresTestSamples.*;
import static nl.ritense.demo.domain.WoonplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WoonplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Woonplaats.class);
        Woonplaats woonplaats1 = getWoonplaatsSample1();
        Woonplaats woonplaats2 = new Woonplaats();
        assertThat(woonplaats1).isNotEqualTo(woonplaats2);

        woonplaats2.setId(woonplaats1.getId());
        assertThat(woonplaats1).isEqualTo(woonplaats2);

        woonplaats2 = getWoonplaatsSample2();
        assertThat(woonplaats1).isNotEqualTo(woonplaats2);
    }

    @Test
    void ligtinNummeraanduidingTest() throws Exception {
        Woonplaats woonplaats = getWoonplaatsRandomSampleGenerator();
        Nummeraanduiding nummeraanduidingBack = getNummeraanduidingRandomSampleGenerator();

        woonplaats.addLigtinNummeraanduiding(nummeraanduidingBack);
        assertThat(woonplaats.getLigtinNummeraanduidings()).containsOnly(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinWoonplaats()).isEqualTo(woonplaats);

        woonplaats.removeLigtinNummeraanduiding(nummeraanduidingBack);
        assertThat(woonplaats.getLigtinNummeraanduidings()).doesNotContain(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinWoonplaats()).isNull();

        woonplaats.ligtinNummeraanduidings(new HashSet<>(Set.of(nummeraanduidingBack)));
        assertThat(woonplaats.getLigtinNummeraanduidings()).containsOnly(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinWoonplaats()).isEqualTo(woonplaats);

        woonplaats.setLigtinNummeraanduidings(new HashSet<>());
        assertThat(woonplaats.getLigtinNummeraanduidings()).doesNotContain(nummeraanduidingBack);
        assertThat(nummeraanduidingBack.getLigtinWoonplaats()).isNull();
    }

    @Test
    void heeftalscorrespondentieadrespostadresinPostadresTest() throws Exception {
        Woonplaats woonplaats = getWoonplaatsRandomSampleGenerator();
        Postadres postadresBack = getPostadresRandomSampleGenerator();

        woonplaats.addHeeftalscorrespondentieadrespostadresinPostadres(postadresBack);
        assertThat(woonplaats.getHeeftalscorrespondentieadrespostadresinPostadres()).containsOnly(postadresBack);
        assertThat(postadresBack.getHeeftalscorrespondentieadrespostadresinWoonplaats()).isEqualTo(woonplaats);

        woonplaats.removeHeeftalscorrespondentieadrespostadresinPostadres(postadresBack);
        assertThat(woonplaats.getHeeftalscorrespondentieadrespostadresinPostadres()).doesNotContain(postadresBack);
        assertThat(postadresBack.getHeeftalscorrespondentieadrespostadresinWoonplaats()).isNull();

        woonplaats.heeftalscorrespondentieadrespostadresinPostadres(new HashSet<>(Set.of(postadresBack)));
        assertThat(woonplaats.getHeeftalscorrespondentieadrespostadresinPostadres()).containsOnly(postadresBack);
        assertThat(postadresBack.getHeeftalscorrespondentieadrespostadresinWoonplaats()).isEqualTo(woonplaats);

        woonplaats.setHeeftalscorrespondentieadrespostadresinPostadres(new HashSet<>());
        assertThat(woonplaats.getHeeftalscorrespondentieadrespostadresinPostadres()).doesNotContain(postadresBack);
        assertThat(postadresBack.getHeeftalscorrespondentieadrespostadresinWoonplaats()).isNull();
    }
}
