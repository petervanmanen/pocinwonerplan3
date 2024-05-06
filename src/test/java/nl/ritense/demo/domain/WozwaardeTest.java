package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WozwaardeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WozwaardeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wozwaarde.class);
        Wozwaarde wozwaarde1 = getWozwaardeSample1();
        Wozwaarde wozwaarde2 = new Wozwaarde();
        assertThat(wozwaarde1).isNotEqualTo(wozwaarde2);

        wozwaarde2.setId(wozwaarde1.getId());
        assertThat(wozwaarde1).isEqualTo(wozwaarde2);

        wozwaarde2 = getWozwaardeSample2();
        assertThat(wozwaarde1).isNotEqualTo(wozwaarde2);
    }
}
