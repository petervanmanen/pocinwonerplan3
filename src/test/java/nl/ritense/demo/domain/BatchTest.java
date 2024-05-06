package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.BatchTestSamples.*;
import static nl.ritense.demo.domain.BatchregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Batch.class);
        Batch batch1 = getBatchSample1();
        Batch batch2 = new Batch();
        assertThat(batch1).isNotEqualTo(batch2);

        batch2.setId(batch1.getId());
        assertThat(batch1).isEqualTo(batch2);

        batch2 = getBatchSample2();
        assertThat(batch1).isNotEqualTo(batch2);
    }

    @Test
    void heeftBatchregelTest() throws Exception {
        Batch batch = getBatchRandomSampleGenerator();
        Batchregel batchregelBack = getBatchregelRandomSampleGenerator();

        batch.addHeeftBatchregel(batchregelBack);
        assertThat(batch.getHeeftBatchregels()).containsOnly(batchregelBack);
        assertThat(batchregelBack.getHeeftBatch()).isEqualTo(batch);

        batch.removeHeeftBatchregel(batchregelBack);
        assertThat(batch.getHeeftBatchregels()).doesNotContain(batchregelBack);
        assertThat(batchregelBack.getHeeftBatch()).isNull();

        batch.heeftBatchregels(new HashSet<>(Set.of(batchregelBack)));
        assertThat(batch.getHeeftBatchregels()).containsOnly(batchregelBack);
        assertThat(batchregelBack.getHeeftBatch()).isEqualTo(batch);

        batch.setHeeftBatchregels(new HashSet<>());
        assertThat(batch.getHeeftBatchregels()).doesNotContain(batchregelBack);
        assertThat(batchregelBack.getHeeftBatch()).isNull();
    }

    @Test
    void heeftherkomstApplicatieTest() throws Exception {
        Batch batch = getBatchRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        batch.setHeeftherkomstApplicatie(applicatieBack);
        assertThat(batch.getHeeftherkomstApplicatie()).isEqualTo(applicatieBack);

        batch.heeftherkomstApplicatie(null);
        assertThat(batch.getHeeftherkomstApplicatie()).isNull();
    }
}
