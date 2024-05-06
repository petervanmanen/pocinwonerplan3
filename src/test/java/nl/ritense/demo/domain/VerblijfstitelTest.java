package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IngezeteneTestSamples.*;
import static nl.ritense.demo.domain.VerblijfstitelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfstitelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfstitel.class);
        Verblijfstitel verblijfstitel1 = getVerblijfstitelSample1();
        Verblijfstitel verblijfstitel2 = new Verblijfstitel();
        assertThat(verblijfstitel1).isNotEqualTo(verblijfstitel2);

        verblijfstitel2.setId(verblijfstitel1.getId());
        assertThat(verblijfstitel1).isEqualTo(verblijfstitel2);

        verblijfstitel2 = getVerblijfstitelSample2();
        assertThat(verblijfstitel1).isNotEqualTo(verblijfstitel2);
    }

    @Test
    void heeftIngezeteneTest() throws Exception {
        Verblijfstitel verblijfstitel = getVerblijfstitelRandomSampleGenerator();
        Ingezetene ingezeteneBack = getIngezeteneRandomSampleGenerator();

        verblijfstitel.addHeeftIngezetene(ingezeteneBack);
        assertThat(verblijfstitel.getHeeftIngezetenes()).containsOnly(ingezeteneBack);
        assertThat(ingezeteneBack.getHeeftVerblijfstitel()).isEqualTo(verblijfstitel);

        verblijfstitel.removeHeeftIngezetene(ingezeteneBack);
        assertThat(verblijfstitel.getHeeftIngezetenes()).doesNotContain(ingezeteneBack);
        assertThat(ingezeteneBack.getHeeftVerblijfstitel()).isNull();

        verblijfstitel.heeftIngezetenes(new HashSet<>(Set.of(ingezeteneBack)));
        assertThat(verblijfstitel.getHeeftIngezetenes()).containsOnly(ingezeteneBack);
        assertThat(ingezeteneBack.getHeeftVerblijfstitel()).isEqualTo(verblijfstitel);

        verblijfstitel.setHeeftIngezetenes(new HashSet<>());
        assertThat(verblijfstitel.getHeeftIngezetenes()).doesNotContain(ingezeteneBack);
        assertThat(ingezeteneBack.getHeeftVerblijfstitel()).isNull();
    }
}
