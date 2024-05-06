package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VloginfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VloginfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vloginfo.class);
        Vloginfo vloginfo1 = getVloginfoSample1();
        Vloginfo vloginfo2 = new Vloginfo();
        assertThat(vloginfo1).isNotEqualTo(vloginfo2);

        vloginfo2.setId(vloginfo1.getId());
        assertThat(vloginfo1).isEqualTo(vloginfo2);

        vloginfo2 = getVloginfoSample2();
        assertThat(vloginfo1).isNotEqualTo(vloginfo2);
    }
}
