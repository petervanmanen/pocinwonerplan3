package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefTestSamples.*;
import static nl.ritense.demo.domain.RechthebbendeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RechthebbendeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rechthebbende.class);
        Rechthebbende rechthebbende1 = getRechthebbendeSample1();
        Rechthebbende rechthebbende2 = new Rechthebbende();
        assertThat(rechthebbende1).isNotEqualTo(rechthebbende2);

        rechthebbende2.setId(rechthebbende1.getId());
        assertThat(rechthebbende1).isEqualTo(rechthebbende2);

        rechthebbende2 = getRechthebbendeSample2();
        assertThat(rechthebbende1).isNotEqualTo(rechthebbende2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Rechthebbende rechthebbende = new Rechthebbende();
        assertThat(rechthebbende.hashCode()).isZero();

        Rechthebbende rechthebbende1 = getRechthebbendeSample1();
        rechthebbende.setId(rechthebbende1.getId());
        assertThat(rechthebbende).hasSameHashCodeAs(rechthebbende1);
    }

    @Test
    void heeftArchiefTest() throws Exception {
        Rechthebbende rechthebbende = getRechthebbendeRandomSampleGenerator();
        Archief archiefBack = getArchiefRandomSampleGenerator();

        rechthebbende.addHeeftArchief(archiefBack);
        assertThat(rechthebbende.getHeeftArchiefs()).containsOnly(archiefBack);
        assertThat(archiefBack.getHeeftRechthebbende()).isEqualTo(rechthebbende);

        rechthebbende.removeHeeftArchief(archiefBack);
        assertThat(rechthebbende.getHeeftArchiefs()).doesNotContain(archiefBack);
        assertThat(archiefBack.getHeeftRechthebbende()).isNull();

        rechthebbende.heeftArchiefs(new HashSet<>(Set.of(archiefBack)));
        assertThat(rechthebbende.getHeeftArchiefs()).containsOnly(archiefBack);
        assertThat(archiefBack.getHeeftRechthebbende()).isEqualTo(rechthebbende);

        rechthebbende.setHeeftArchiefs(new HashSet<>());
        assertThat(rechthebbende.getHeeftArchiefs()).doesNotContain(archiefBack);
        assertThat(archiefBack.getHeeftRechthebbende()).isNull();
    }
}
