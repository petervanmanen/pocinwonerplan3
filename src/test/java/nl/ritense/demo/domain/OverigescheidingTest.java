package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverigescheidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverigescheidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overigescheiding.class);
        Overigescheiding overigescheiding1 = getOverigescheidingSample1();
        Overigescheiding overigescheiding2 = new Overigescheiding();
        assertThat(overigescheiding1).isNotEqualTo(overigescheiding2);

        overigescheiding2.setId(overigescheiding1.getId());
        assertThat(overigescheiding1).isEqualTo(overigescheiding2);

        overigescheiding2 = getOverigescheidingSample2();
        assertThat(overigescheiding1).isNotEqualTo(overigescheiding2);
    }
}
