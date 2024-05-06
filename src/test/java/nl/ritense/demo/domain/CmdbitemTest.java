package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CmdbitemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CmdbitemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cmdbitem.class);
        Cmdbitem cmdbitem1 = getCmdbitemSample1();
        Cmdbitem cmdbitem2 = new Cmdbitem();
        assertThat(cmdbitem1).isNotEqualTo(cmdbitem2);

        cmdbitem2.setId(cmdbitem1.getId());
        assertThat(cmdbitem1).isEqualTo(cmdbitem2);

        cmdbitem2 = getCmdbitemSample2();
        assertThat(cmdbitem1).isNotEqualTo(cmdbitem2);
    }
}
