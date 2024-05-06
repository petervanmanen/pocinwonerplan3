package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IngezeteneTestSamples.*;
import static nl.ritense.demo.domain.VerblijfstitelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngezeteneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingezetene.class);
        Ingezetene ingezetene1 = getIngezeteneSample1();
        Ingezetene ingezetene2 = new Ingezetene();
        assertThat(ingezetene1).isNotEqualTo(ingezetene2);

        ingezetene2.setId(ingezetene1.getId());
        assertThat(ingezetene1).isEqualTo(ingezetene2);

        ingezetene2 = getIngezeteneSample2();
        assertThat(ingezetene1).isNotEqualTo(ingezetene2);
    }

    @Test
    void heeftVerblijfstitelTest() throws Exception {
        Ingezetene ingezetene = getIngezeteneRandomSampleGenerator();
        Verblijfstitel verblijfstitelBack = getVerblijfstitelRandomSampleGenerator();

        ingezetene.setHeeftVerblijfstitel(verblijfstitelBack);
        assertThat(ingezetene.getHeeftVerblijfstitel()).isEqualTo(verblijfstitelBack);

        ingezetene.heeftVerblijfstitel(null);
        assertThat(ingezetene.getHeeftVerblijfstitel()).isNull();
    }
}
