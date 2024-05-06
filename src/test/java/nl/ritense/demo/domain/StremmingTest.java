package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.StremmingTestSamples.*;
import static nl.ritense.demo.domain.WegdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StremmingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stremming.class);
        Stremming stremming1 = getStremmingSample1();
        Stremming stremming2 = new Stremming();
        assertThat(stremming1).isNotEqualTo(stremming2);

        stremming2.setId(stremming1.getId());
        assertThat(stremming1).isEqualTo(stremming2);

        stremming2 = getStremmingSample2();
        assertThat(stremming1).isNotEqualTo(stremming2);
    }

    @Test
    void betreftWegdeelTest() throws Exception {
        Stremming stremming = getStremmingRandomSampleGenerator();
        Wegdeel wegdeelBack = getWegdeelRandomSampleGenerator();

        stremming.addBetreftWegdeel(wegdeelBack);
        assertThat(stremming.getBetreftWegdeels()).containsOnly(wegdeelBack);

        stremming.removeBetreftWegdeel(wegdeelBack);
        assertThat(stremming.getBetreftWegdeels()).doesNotContain(wegdeelBack);

        stremming.betreftWegdeels(new HashSet<>(Set.of(wegdeelBack)));
        assertThat(stremming.getBetreftWegdeels()).containsOnly(wegdeelBack);

        stremming.setBetreftWegdeels(new HashSet<>());
        assertThat(stremming.getBetreftWegdeels()).doesNotContain(wegdeelBack);
    }

    @Test
    void ingevoerddoorMedewerkerTest() throws Exception {
        Stremming stremming = getStremmingRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        stremming.setIngevoerddoorMedewerker(medewerkerBack);
        assertThat(stremming.getIngevoerddoorMedewerker()).isEqualTo(medewerkerBack);

        stremming.ingevoerddoorMedewerker(null);
        assertThat(stremming.getIngevoerddoorMedewerker()).isNull();
    }

    @Test
    void gewijzigddoorMedewerkerTest() throws Exception {
        Stremming stremming = getStremmingRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        stremming.setGewijzigddoorMedewerker(medewerkerBack);
        assertThat(stremming.getGewijzigddoorMedewerker()).isEqualTo(medewerkerBack);

        stremming.gewijzigddoorMedewerker(null);
        assertThat(stremming.getGewijzigddoorMedewerker()).isNull();
    }
}
