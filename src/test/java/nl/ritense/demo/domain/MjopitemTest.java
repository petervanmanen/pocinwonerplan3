package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MjopitemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MjopitemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mjopitem.class);
        Mjopitem mjopitem1 = getMjopitemSample1();
        Mjopitem mjopitem2 = new Mjopitem();
        assertThat(mjopitem1).isNotEqualTo(mjopitem2);

        mjopitem2.setId(mjopitem1.getId());
        assertThat(mjopitem1).isEqualTo(mjopitem2);

        mjopitem2 = getMjopitemSample2();
        assertThat(mjopitem1).isNotEqualTo(mjopitem2);
    }
}
