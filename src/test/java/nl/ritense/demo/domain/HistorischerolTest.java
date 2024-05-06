package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HistorischerolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistorischerolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historischerol.class);
        Historischerol historischerol1 = getHistorischerolSample1();
        Historischerol historischerol2 = new Historischerol();
        assertThat(historischerol1).isNotEqualTo(historischerol2);

        historischerol2.setId(historischerol1.getId());
        assertThat(historischerol1).isEqualTo(historischerol2);

        historischerol2 = getHistorischerolSample2();
        assertThat(historischerol1).isNotEqualTo(historischerol2);
    }
}
