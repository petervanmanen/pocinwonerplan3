package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PgbtoekenningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PgbtoekenningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pgbtoekenning.class);
        Pgbtoekenning pgbtoekenning1 = getPgbtoekenningSample1();
        Pgbtoekenning pgbtoekenning2 = new Pgbtoekenning();
        assertThat(pgbtoekenning1).isNotEqualTo(pgbtoekenning2);

        pgbtoekenning2.setId(pgbtoekenning1.getId());
        assertThat(pgbtoekenning1).isEqualTo(pgbtoekenning2);

        pgbtoekenning2 = getPgbtoekenningSample2();
        assertThat(pgbtoekenning1).isNotEqualTo(pgbtoekenning2);
    }
}
