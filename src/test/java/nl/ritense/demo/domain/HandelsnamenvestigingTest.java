package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HandelsnamenvestigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HandelsnamenvestigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Handelsnamenvestiging.class);
        Handelsnamenvestiging handelsnamenvestiging1 = getHandelsnamenvestigingSample1();
        Handelsnamenvestiging handelsnamenvestiging2 = new Handelsnamenvestiging();
        assertThat(handelsnamenvestiging1).isNotEqualTo(handelsnamenvestiging2);

        handelsnamenvestiging2.setId(handelsnamenvestiging1.getId());
        assertThat(handelsnamenvestiging1).isEqualTo(handelsnamenvestiging2);

        handelsnamenvestiging2 = getHandelsnamenvestigingSample2();
        assertThat(handelsnamenvestiging1).isNotEqualTo(handelsnamenvestiging2);
    }
}
