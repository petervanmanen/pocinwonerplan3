package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static nl.ritense.demo.domain.TaakveldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaakveldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taakveld.class);
        Taakveld taakveld1 = getTaakveldSample1();
        Taakveld taakveld2 = new Taakveld();
        assertThat(taakveld1).isNotEqualTo(taakveld2);

        taakveld2.setId(taakveld1.getId());
        assertThat(taakveld1).isEqualTo(taakveld2);

        taakveld2 = getTaakveldSample2();
        assertThat(taakveld1).isNotEqualTo(taakveld2);
    }

    @Test
    void heeftRaadsstukTest() throws Exception {
        Taakveld taakveld = getTaakveldRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        taakveld.addHeeftRaadsstuk(raadsstukBack);
        assertThat(taakveld.getHeeftRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getHeeftTaakveld()).isEqualTo(taakveld);

        taakveld.removeHeeftRaadsstuk(raadsstukBack);
        assertThat(taakveld.getHeeftRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getHeeftTaakveld()).isNull();

        taakveld.heeftRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(taakveld.getHeeftRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getHeeftTaakveld()).isEqualTo(taakveld);

        taakveld.setHeeftRaadsstuks(new HashSet<>());
        assertThat(taakveld.getHeeftRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getHeeftTaakveld()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Taakveld taakveld = getTaakveldRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        taakveld.addHeeftKostenplaats(kostenplaatsBack);
        assertThat(taakveld.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftTaakvelds()).containsOnly(taakveld);

        taakveld.removeHeeftKostenplaats(kostenplaatsBack);
        assertThat(taakveld.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftTaakvelds()).doesNotContain(taakveld);

        taakveld.heeftKostenplaats(new HashSet<>(Set.of(kostenplaatsBack)));
        assertThat(taakveld.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftTaakvelds()).containsOnly(taakveld);

        taakveld.setHeeftKostenplaats(new HashSet<>());
        assertThat(taakveld.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftTaakvelds()).doesNotContain(taakveld);
    }
}
