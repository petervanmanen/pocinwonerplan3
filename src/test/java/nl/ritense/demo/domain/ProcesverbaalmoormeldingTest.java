package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProcesverbaalmoormeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcesverbaalmoormeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procesverbaalmoormelding.class);
        Procesverbaalmoormelding procesverbaalmoormelding1 = getProcesverbaalmoormeldingSample1();
        Procesverbaalmoormelding procesverbaalmoormelding2 = new Procesverbaalmoormelding();
        assertThat(procesverbaalmoormelding1).isNotEqualTo(procesverbaalmoormelding2);

        procesverbaalmoormelding2.setId(procesverbaalmoormelding1.getId());
        assertThat(procesverbaalmoormelding1).isEqualTo(procesverbaalmoormelding2);

        procesverbaalmoormelding2 = getProcesverbaalmoormeldingSample2();
        assertThat(procesverbaalmoormelding1).isNotEqualTo(procesverbaalmoormelding2);
    }
}
