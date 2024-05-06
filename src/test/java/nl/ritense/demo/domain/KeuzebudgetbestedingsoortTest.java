package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KeuzebudgetbestedingsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KeuzebudgetbestedingsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Keuzebudgetbestedingsoort.class);
        Keuzebudgetbestedingsoort keuzebudgetbestedingsoort1 = getKeuzebudgetbestedingsoortSample1();
        Keuzebudgetbestedingsoort keuzebudgetbestedingsoort2 = new Keuzebudgetbestedingsoort();
        assertThat(keuzebudgetbestedingsoort1).isNotEqualTo(keuzebudgetbestedingsoort2);

        keuzebudgetbestedingsoort2.setId(keuzebudgetbestedingsoort1.getId());
        assertThat(keuzebudgetbestedingsoort1).isEqualTo(keuzebudgetbestedingsoort2);

        keuzebudgetbestedingsoort2 = getKeuzebudgetbestedingsoortSample2();
        assertThat(keuzebudgetbestedingsoort1).isNotEqualTo(keuzebudgetbestedingsoort2);
    }
}
