package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LoopbaanstapTestSamples.*;
import static nl.ritense.demo.domain.OnderwijsloopbaanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoopbaanstapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loopbaanstap.class);
        Loopbaanstap loopbaanstap1 = getLoopbaanstapSample1();
        Loopbaanstap loopbaanstap2 = new Loopbaanstap();
        assertThat(loopbaanstap1).isNotEqualTo(loopbaanstap2);

        loopbaanstap2.setId(loopbaanstap1.getId());
        assertThat(loopbaanstap1).isEqualTo(loopbaanstap2);

        loopbaanstap2 = getLoopbaanstapSample2();
        assertThat(loopbaanstap1).isNotEqualTo(loopbaanstap2);
    }

    @Test
    void emptyOnderwijsloopbaanTest() throws Exception {
        Loopbaanstap loopbaanstap = getLoopbaanstapRandomSampleGenerator();
        Onderwijsloopbaan onderwijsloopbaanBack = getOnderwijsloopbaanRandomSampleGenerator();

        loopbaanstap.setEmptyOnderwijsloopbaan(onderwijsloopbaanBack);
        assertThat(loopbaanstap.getEmptyOnderwijsloopbaan()).isEqualTo(onderwijsloopbaanBack);

        loopbaanstap.emptyOnderwijsloopbaan(null);
        assertThat(loopbaanstap.getEmptyOnderwijsloopbaan()).isNull();
    }
}
