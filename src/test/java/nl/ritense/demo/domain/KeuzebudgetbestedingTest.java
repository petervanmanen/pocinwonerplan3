package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KeuzebudgetbestedingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KeuzebudgetbestedingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Keuzebudgetbesteding.class);
        Keuzebudgetbesteding keuzebudgetbesteding1 = getKeuzebudgetbestedingSample1();
        Keuzebudgetbesteding keuzebudgetbesteding2 = new Keuzebudgetbesteding();
        assertThat(keuzebudgetbesteding1).isNotEqualTo(keuzebudgetbesteding2);

        keuzebudgetbesteding2.setId(keuzebudgetbesteding1.getId());
        assertThat(keuzebudgetbesteding1).isEqualTo(keuzebudgetbesteding2);

        keuzebudgetbesteding2 = getKeuzebudgetbestedingSample2();
        assertThat(keuzebudgetbesteding1).isNotEqualTo(keuzebudgetbesteding2);
    }
}
