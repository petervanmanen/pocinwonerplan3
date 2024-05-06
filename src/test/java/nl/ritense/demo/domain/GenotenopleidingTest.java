package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GenotenopleidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenotenopleidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Genotenopleiding.class);
        Genotenopleiding genotenopleiding1 = getGenotenopleidingSample1();
        Genotenopleiding genotenopleiding2 = new Genotenopleiding();
        assertThat(genotenopleiding1).isNotEqualTo(genotenopleiding2);

        genotenopleiding2.setId(genotenopleiding1.getId());
        assertThat(genotenopleiding1).isEqualTo(genotenopleiding2);

        genotenopleiding2 = getGenotenopleidingSample2();
        assertThat(genotenopleiding1).isNotEqualTo(genotenopleiding2);
    }
}
