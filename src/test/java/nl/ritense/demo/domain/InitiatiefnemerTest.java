package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InitiatiefnemerTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InitiatiefnemerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Initiatiefnemer.class);
        Initiatiefnemer initiatiefnemer1 = getInitiatiefnemerSample1();
        Initiatiefnemer initiatiefnemer2 = new Initiatiefnemer();
        assertThat(initiatiefnemer1).isNotEqualTo(initiatiefnemer2);

        initiatiefnemer2.setId(initiatiefnemer1.getId());
        assertThat(initiatiefnemer1).isEqualTo(initiatiefnemer2);

        initiatiefnemer2 = getInitiatiefnemerSample2();
        assertThat(initiatiefnemer1).isNotEqualTo(initiatiefnemer2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Initiatiefnemer initiatiefnemer = new Initiatiefnemer();
        assertThat(initiatiefnemer.hashCode()).isZero();

        Initiatiefnemer initiatiefnemer1 = getInitiatiefnemerSample1();
        initiatiefnemer.setId(initiatiefnemer1.getId());
        assertThat(initiatiefnemer).hasSameHashCodeAs(initiatiefnemer1);
    }

    @Test
    void heeftalsverantwoordelijkeVerzoekTest() throws Exception {
        Initiatiefnemer initiatiefnemer = getInitiatiefnemerRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        initiatiefnemer.addHeeftalsverantwoordelijkeVerzoek(verzoekBack);
        assertThat(initiatiefnemer.getHeeftalsverantwoordelijkeVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getHeeftalsverantwoordelijkeInitiatiefnemer()).isEqualTo(initiatiefnemer);

        initiatiefnemer.removeHeeftalsverantwoordelijkeVerzoek(verzoekBack);
        assertThat(initiatiefnemer.getHeeftalsverantwoordelijkeVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getHeeftalsverantwoordelijkeInitiatiefnemer()).isNull();

        initiatiefnemer.heeftalsverantwoordelijkeVerzoeks(new HashSet<>(Set.of(verzoekBack)));
        assertThat(initiatiefnemer.getHeeftalsverantwoordelijkeVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getHeeftalsverantwoordelijkeInitiatiefnemer()).isEqualTo(initiatiefnemer);

        initiatiefnemer.setHeeftalsverantwoordelijkeVerzoeks(new HashSet<>());
        assertThat(initiatiefnemer.getHeeftalsverantwoordelijkeVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getHeeftalsverantwoordelijkeInitiatiefnemer()).isNull();
    }
}
