package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SubproducttypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubproducttypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subproducttype.class);
        Subproducttype subproducttype1 = getSubproducttypeSample1();
        Subproducttype subproducttype2 = new Subproducttype();
        assertThat(subproducttype1).isNotEqualTo(subproducttype2);

        subproducttype2.setId(subproducttype1.getId());
        assertThat(subproducttype1).isEqualTo(subproducttype2);

        subproducttype2 = getSubproducttypeSample2();
        assertThat(subproducttype1).isNotEqualTo(subproducttype2);
    }
}
