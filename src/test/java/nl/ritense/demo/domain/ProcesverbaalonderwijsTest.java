package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProcesverbaalonderwijsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcesverbaalonderwijsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procesverbaalonderwijs.class);
        Procesverbaalonderwijs procesverbaalonderwijs1 = getProcesverbaalonderwijsSample1();
        Procesverbaalonderwijs procesverbaalonderwijs2 = new Procesverbaalonderwijs();
        assertThat(procesverbaalonderwijs1).isNotEqualTo(procesverbaalonderwijs2);

        procesverbaalonderwijs2.setId(procesverbaalonderwijs1.getId());
        assertThat(procesverbaalonderwijs1).isEqualTo(procesverbaalonderwijs2);

        procesverbaalonderwijs2 = getProcesverbaalonderwijsSample2();
        assertThat(procesverbaalonderwijs1).isNotEqualTo(procesverbaalonderwijs2);
    }
}
