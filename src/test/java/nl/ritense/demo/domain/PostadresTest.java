package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PostadresTestSamples.*;
import static nl.ritense.demo.domain.WoonplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostadresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Postadres.class);
        Postadres postadres1 = getPostadresSample1();
        Postadres postadres2 = new Postadres();
        assertThat(postadres1).isNotEqualTo(postadres2);

        postadres2.setId(postadres1.getId());
        assertThat(postadres1).isEqualTo(postadres2);

        postadres2 = getPostadresSample2();
        assertThat(postadres1).isNotEqualTo(postadres2);
    }

    @Test
    void heeftalscorrespondentieadrespostadresinWoonplaatsTest() throws Exception {
        Postadres postadres = getPostadresRandomSampleGenerator();
        Woonplaats woonplaatsBack = getWoonplaatsRandomSampleGenerator();

        postadres.setHeeftalscorrespondentieadrespostadresinWoonplaats(woonplaatsBack);
        assertThat(postadres.getHeeftalscorrespondentieadrespostadresinWoonplaats()).isEqualTo(woonplaatsBack);

        postadres.heeftalscorrespondentieadrespostadresinWoonplaats(null);
        assertThat(postadres.getHeeftalscorrespondentieadrespostadresinWoonplaats()).isNull();
    }
}
