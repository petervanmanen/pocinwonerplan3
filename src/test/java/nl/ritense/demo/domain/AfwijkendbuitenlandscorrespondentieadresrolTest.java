package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AfwijkendbuitenlandscorrespondentieadresrolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AfwijkendbuitenlandscorrespondentieadresrolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Afwijkendbuitenlandscorrespondentieadresrol.class);
        Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol1 =
            getAfwijkendbuitenlandscorrespondentieadresrolSample1();
        Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol2 =
            new Afwijkendbuitenlandscorrespondentieadresrol();
        assertThat(afwijkendbuitenlandscorrespondentieadresrol1).isNotEqualTo(afwijkendbuitenlandscorrespondentieadresrol2);

        afwijkendbuitenlandscorrespondentieadresrol2.setId(afwijkendbuitenlandscorrespondentieadresrol1.getId());
        assertThat(afwijkendbuitenlandscorrespondentieadresrol1).isEqualTo(afwijkendbuitenlandscorrespondentieadresrol2);

        afwijkendbuitenlandscorrespondentieadresrol2 = getAfwijkendbuitenlandscorrespondentieadresrolSample2();
        assertThat(afwijkendbuitenlandscorrespondentieadresrol1).isNotEqualTo(afwijkendbuitenlandscorrespondentieadresrol2);
    }
}
