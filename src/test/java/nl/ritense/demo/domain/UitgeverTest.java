package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.UitgeverTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitgeverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitgever.class);
        Uitgever uitgever1 = getUitgeverSample1();
        Uitgever uitgever2 = new Uitgever();
        assertThat(uitgever1).isNotEqualTo(uitgever2);

        uitgever2.setId(uitgever1.getId());
        assertThat(uitgever1).isEqualTo(uitgever2);

        uitgever2 = getUitgeverSample2();
        assertThat(uitgever1).isNotEqualTo(uitgever2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Uitgever uitgever = new Uitgever();
        assertThat(uitgever.hashCode()).isZero();

        Uitgever uitgever1 = getUitgeverSample1();
        uitgever.setId(uitgever1.getId());
        assertThat(uitgever).hasSameHashCodeAs(uitgever1);
    }

    @Test
    void heeftArchiefstukTest() throws Exception {
        Uitgever uitgever = getUitgeverRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        uitgever.addHeeftArchiefstuk(archiefstukBack);
        assertThat(uitgever.getHeeftArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getHeeftUitgever()).isEqualTo(uitgever);

        uitgever.removeHeeftArchiefstuk(archiefstukBack);
        assertThat(uitgever.getHeeftArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getHeeftUitgever()).isNull();

        uitgever.heeftArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(uitgever.getHeeftArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getHeeftUitgever()).isEqualTo(uitgever);

        uitgever.setHeeftArchiefstuks(new HashSet<>());
        assertThat(uitgever.getHeeftArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getHeeftUitgever()).isNull();
    }
}
