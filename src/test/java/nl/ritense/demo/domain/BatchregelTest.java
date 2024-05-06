package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BatchTestSamples.*;
import static nl.ritense.demo.domain.BatchregelTestSamples.*;
import static nl.ritense.demo.domain.MutatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatchregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Batchregel.class);
        Batchregel batchregel1 = getBatchregelSample1();
        Batchregel batchregel2 = new Batchregel();
        assertThat(batchregel1).isNotEqualTo(batchregel2);

        batchregel2.setId(batchregel1.getId());
        assertThat(batchregel1).isEqualTo(batchregel2);

        batchregel2 = getBatchregelSample2();
        assertThat(batchregel1).isNotEqualTo(batchregel2);
    }

    @Test
    void leidttotMutatieTest() throws Exception {
        Batchregel batchregel = getBatchregelRandomSampleGenerator();
        Mutatie mutatieBack = getMutatieRandomSampleGenerator();

        batchregel.setLeidttotMutatie(mutatieBack);
        assertThat(batchregel.getLeidttotMutatie()).isEqualTo(mutatieBack);

        batchregel.leidttotMutatie(null);
        assertThat(batchregel.getLeidttotMutatie()).isNull();
    }

    @Test
    void heeftBatchTest() throws Exception {
        Batchregel batchregel = getBatchregelRandomSampleGenerator();
        Batch batchBack = getBatchRandomSampleGenerator();

        batchregel.setHeeftBatch(batchBack);
        assertThat(batchregel.getHeeftBatch()).isEqualTo(batchBack);

        batchregel.heeftBatch(null);
        assertThat(batchregel.getHeeftBatch()).isNull();
    }
}
