package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VoorzieningTestSamples.*;
import static nl.ritense.demo.domain.VoorzieningsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoorzieningsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voorzieningsoort.class);
        Voorzieningsoort voorzieningsoort1 = getVoorzieningsoortSample1();
        Voorzieningsoort voorzieningsoort2 = new Voorzieningsoort();
        assertThat(voorzieningsoort1).isNotEqualTo(voorzieningsoort2);

        voorzieningsoort2.setId(voorzieningsoort1.getId());
        assertThat(voorzieningsoort1).isEqualTo(voorzieningsoort2);

        voorzieningsoort2 = getVoorzieningsoortSample2();
        assertThat(voorzieningsoort1).isNotEqualTo(voorzieningsoort2);
    }

    @Test
    void valtbinnenVoorzieningTest() throws Exception {
        Voorzieningsoort voorzieningsoort = getVoorzieningsoortRandomSampleGenerator();
        Voorziening voorzieningBack = getVoorzieningRandomSampleGenerator();

        voorzieningsoort.addValtbinnenVoorziening(voorzieningBack);
        assertThat(voorzieningsoort.getValtbinnenVoorzienings()).containsOnly(voorzieningBack);
        assertThat(voorzieningBack.getValtbinnenVoorzieningsoort()).isEqualTo(voorzieningsoort);

        voorzieningsoort.removeValtbinnenVoorziening(voorzieningBack);
        assertThat(voorzieningsoort.getValtbinnenVoorzienings()).doesNotContain(voorzieningBack);
        assertThat(voorzieningBack.getValtbinnenVoorzieningsoort()).isNull();

        voorzieningsoort.valtbinnenVoorzienings(new HashSet<>(Set.of(voorzieningBack)));
        assertThat(voorzieningsoort.getValtbinnenVoorzienings()).containsOnly(voorzieningBack);
        assertThat(voorzieningBack.getValtbinnenVoorzieningsoort()).isEqualTo(voorzieningsoort);

        voorzieningsoort.setValtbinnenVoorzienings(new HashSet<>());
        assertThat(voorzieningsoort.getValtbinnenVoorzienings()).doesNotContain(voorzieningBack);
        assertThat(voorzieningBack.getValtbinnenVoorzieningsoort()).isNull();
    }
}
