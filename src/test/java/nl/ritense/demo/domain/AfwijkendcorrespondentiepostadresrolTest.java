package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AfwijkendcorrespondentiepostadresrolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AfwijkendcorrespondentiepostadresrolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Afwijkendcorrespondentiepostadresrol.class);
        Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol1 = getAfwijkendcorrespondentiepostadresrolSample1();
        Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol2 = new Afwijkendcorrespondentiepostadresrol();
        assertThat(afwijkendcorrespondentiepostadresrol1).isNotEqualTo(afwijkendcorrespondentiepostadresrol2);

        afwijkendcorrespondentiepostadresrol2.setId(afwijkendcorrespondentiepostadresrol1.getId());
        assertThat(afwijkendcorrespondentiepostadresrol1).isEqualTo(afwijkendcorrespondentiepostadresrol2);

        afwijkendcorrespondentiepostadresrol2 = getAfwijkendcorrespondentiepostadresrolSample2();
        assertThat(afwijkendcorrespondentiepostadresrol1).isNotEqualTo(afwijkendcorrespondentiepostadresrol2);
    }
}
