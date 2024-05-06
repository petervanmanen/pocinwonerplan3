package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelangtypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BelangtypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Belangtype.class);
        Belangtype belangtype1 = getBelangtypeSample1();
        Belangtype belangtype2 = new Belangtype();
        assertThat(belangtype1).isNotEqualTo(belangtype2);

        belangtype2.setId(belangtype1.getId());
        assertThat(belangtype1).isEqualTo(belangtype2);

        belangtype2 = getBelangtypeSample2();
        assertThat(belangtype1).isNotEqualTo(belangtype2);
    }
}
