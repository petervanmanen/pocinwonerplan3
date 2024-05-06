package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.Lstclass1TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Lstclass1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lstclass1.class);
        Lstclass1 lstclass11 = getLstclass1Sample1();
        Lstclass1 lstclass12 = new Lstclass1();
        assertThat(lstclass11).isNotEqualTo(lstclass12);

        lstclass12.setId(lstclass11.getId());
        assertThat(lstclass11).isEqualTo(lstclass12);

        lstclass12 = getLstclass1Sample2();
        assertThat(lstclass11).isNotEqualTo(lstclass12);
    }
}
