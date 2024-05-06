package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DienstTestSamples.*;
import static nl.ritense.demo.domain.OnderwerpTestSamples.*;
import static nl.ritense.demo.domain.OnderwerpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderwerpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderwerp.class);
        Onderwerp onderwerp1 = getOnderwerpSample1();
        Onderwerp onderwerp2 = new Onderwerp();
        assertThat(onderwerp1).isNotEqualTo(onderwerp2);

        onderwerp2.setId(onderwerp1.getId());
        assertThat(onderwerp1).isEqualTo(onderwerp2);

        onderwerp2 = getOnderwerpSample2();
        assertThat(onderwerp1).isNotEqualTo(onderwerp2);
    }

    @Test
    void hoofdonderwerpOnderwerpTest() throws Exception {
        Onderwerp onderwerp = getOnderwerpRandomSampleGenerator();
        Onderwerp onderwerpBack = getOnderwerpRandomSampleGenerator();

        onderwerp.setHoofdonderwerpOnderwerp(onderwerpBack);
        assertThat(onderwerp.getHoofdonderwerpOnderwerp()).isEqualTo(onderwerpBack);

        onderwerp.hoofdonderwerpOnderwerp(null);
        assertThat(onderwerp.getHoofdonderwerpOnderwerp()).isNull();
    }

    @Test
    void heeftDienstTest() throws Exception {
        Onderwerp onderwerp = getOnderwerpRandomSampleGenerator();
        Dienst dienstBack = getDienstRandomSampleGenerator();

        onderwerp.addHeeftDienst(dienstBack);
        assertThat(onderwerp.getHeeftDiensts()).containsOnly(dienstBack);
        assertThat(dienstBack.getHeeftOnderwerp()).isEqualTo(onderwerp);

        onderwerp.removeHeeftDienst(dienstBack);
        assertThat(onderwerp.getHeeftDiensts()).doesNotContain(dienstBack);
        assertThat(dienstBack.getHeeftOnderwerp()).isNull();

        onderwerp.heeftDiensts(new HashSet<>(Set.of(dienstBack)));
        assertThat(onderwerp.getHeeftDiensts()).containsOnly(dienstBack);
        assertThat(dienstBack.getHeeftOnderwerp()).isEqualTo(onderwerp);

        onderwerp.setHeeftDiensts(new HashSet<>());
        assertThat(onderwerp.getHeeftDiensts()).doesNotContain(dienstBack);
        assertThat(dienstBack.getHeeftOnderwerp()).isNull();
    }

    @Test
    void hoofdonderwerpOnderwerp2Test() throws Exception {
        Onderwerp onderwerp = getOnderwerpRandomSampleGenerator();
        Onderwerp onderwerpBack = getOnderwerpRandomSampleGenerator();

        onderwerp.addHoofdonderwerpOnderwerp2(onderwerpBack);
        assertThat(onderwerp.getHoofdonderwerpOnderwerp2s()).containsOnly(onderwerpBack);
        assertThat(onderwerpBack.getHoofdonderwerpOnderwerp()).isEqualTo(onderwerp);

        onderwerp.removeHoofdonderwerpOnderwerp2(onderwerpBack);
        assertThat(onderwerp.getHoofdonderwerpOnderwerp2s()).doesNotContain(onderwerpBack);
        assertThat(onderwerpBack.getHoofdonderwerpOnderwerp()).isNull();

        onderwerp.hoofdonderwerpOnderwerp2s(new HashSet<>(Set.of(onderwerpBack)));
        assertThat(onderwerp.getHoofdonderwerpOnderwerp2s()).containsOnly(onderwerpBack);
        assertThat(onderwerpBack.getHoofdonderwerpOnderwerp()).isEqualTo(onderwerp);

        onderwerp.setHoofdonderwerpOnderwerp2s(new HashSet<>());
        assertThat(onderwerp.getHoofdonderwerpOnderwerp2s()).doesNotContain(onderwerpBack);
        assertThat(onderwerpBack.getHoofdonderwerpOnderwerp()).isNull();
    }
}
