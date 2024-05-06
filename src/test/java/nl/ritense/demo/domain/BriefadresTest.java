package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BriefadresTestSamples.*;
import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BriefadresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Briefadres.class);
        Briefadres briefadres1 = getBriefadresSample1();
        Briefadres briefadres2 = new Briefadres();
        assertThat(briefadres1).isNotEqualTo(briefadres2);

        briefadres2.setId(briefadres1.getId());
        assertThat(briefadres1).isEqualTo(briefadres2);

        briefadres2 = getBriefadresSample2();
        assertThat(briefadres1).isNotEqualTo(briefadres2);
    }

    @Test
    void emptyNummeraanduidingTest() throws Exception {
        Briefadres briefadres = getBriefadresRandomSampleGenerator();
        Nummeraanduiding nummeraanduidingBack = getNummeraanduidingRandomSampleGenerator();

        briefadres.setEmptyNummeraanduiding(nummeraanduidingBack);
        assertThat(briefadres.getEmptyNummeraanduiding()).isEqualTo(nummeraanduidingBack);

        briefadres.emptyNummeraanduiding(null);
        assertThat(briefadres.getEmptyNummeraanduiding()).isNull();
    }
}
