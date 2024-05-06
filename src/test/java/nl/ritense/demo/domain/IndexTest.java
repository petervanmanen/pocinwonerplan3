package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IndexTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndexTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Index.class);
        Index index1 = getIndexSample1();
        Index index2 = new Index();
        assertThat(index1).isNotEqualTo(index2);

        index2.setId(index1.getId());
        assertThat(index1).isEqualTo(index2);

        index2 = getIndexSample2();
        assertThat(index1).isNotEqualTo(index2);
    }
}
