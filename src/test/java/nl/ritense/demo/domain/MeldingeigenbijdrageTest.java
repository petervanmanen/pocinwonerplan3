package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MeldingeigenbijdrageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeldingeigenbijdrageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meldingeigenbijdrage.class);
        Meldingeigenbijdrage meldingeigenbijdrage1 = getMeldingeigenbijdrageSample1();
        Meldingeigenbijdrage meldingeigenbijdrage2 = new Meldingeigenbijdrage();
        assertThat(meldingeigenbijdrage1).isNotEqualTo(meldingeigenbijdrage2);

        meldingeigenbijdrage2.setId(meldingeigenbijdrage1.getId());
        assertThat(meldingeigenbijdrage1).isEqualTo(meldingeigenbijdrage2);

        meldingeigenbijdrage2 = getMeldingeigenbijdrageSample2();
        assertThat(meldingeigenbijdrage1).isNotEqualTo(meldingeigenbijdrage2);
    }
}
