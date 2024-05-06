package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KpbetrokkenbijTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KpbetrokkenbijTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kpbetrokkenbij.class);
        Kpbetrokkenbij kpbetrokkenbij1 = getKpbetrokkenbijSample1();
        Kpbetrokkenbij kpbetrokkenbij2 = new Kpbetrokkenbij();
        assertThat(kpbetrokkenbij1).isNotEqualTo(kpbetrokkenbij2);

        kpbetrokkenbij2.setId(kpbetrokkenbij1.getId());
        assertThat(kpbetrokkenbij1).isEqualTo(kpbetrokkenbij2);

        kpbetrokkenbij2 = getKpbetrokkenbijSample2();
        assertThat(kpbetrokkenbij1).isNotEqualTo(kpbetrokkenbij2);
    }
}
