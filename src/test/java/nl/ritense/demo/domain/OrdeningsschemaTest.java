package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.OrdeningsschemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdeningsschemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordeningsschema.class);
        Ordeningsschema ordeningsschema1 = getOrdeningsschemaSample1();
        Ordeningsschema ordeningsschema2 = new Ordeningsschema();
        assertThat(ordeningsschema1).isNotEqualTo(ordeningsschema2);

        ordeningsschema2.setId(ordeningsschema1.getId());
        assertThat(ordeningsschema1).isEqualTo(ordeningsschema2);

        ordeningsschema2 = getOrdeningsschemaSample2();
        assertThat(ordeningsschema1).isNotEqualTo(ordeningsschema2);
    }

    @Test
    void heeftArchiefstukTest() throws Exception {
        Ordeningsschema ordeningsschema = getOrdeningsschemaRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        ordeningsschema.addHeeftArchiefstuk(archiefstukBack);
        assertThat(ordeningsschema.getHeeftArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getHeeftOrdeningsschemas()).containsOnly(ordeningsschema);

        ordeningsschema.removeHeeftArchiefstuk(archiefstukBack);
        assertThat(ordeningsschema.getHeeftArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getHeeftOrdeningsschemas()).doesNotContain(ordeningsschema);

        ordeningsschema.heeftArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(ordeningsschema.getHeeftArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getHeeftOrdeningsschemas()).containsOnly(ordeningsschema);

        ordeningsschema.setHeeftArchiefstuks(new HashSet<>());
        assertThat(ordeningsschema.getHeeftArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getHeeftOrdeningsschemas()).doesNotContain(ordeningsschema);
    }
}
