package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VorderingregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VorderingregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vorderingregel.class);
        Vorderingregel vorderingregel1 = getVorderingregelSample1();
        Vorderingregel vorderingregel2 = new Vorderingregel();
        assertThat(vorderingregel1).isNotEqualTo(vorderingregel2);

        vorderingregel2.setId(vorderingregel1.getId());
        assertThat(vorderingregel1).isEqualTo(vorderingregel2);

        vorderingregel2 = getVorderingregelSample2();
        assertThat(vorderingregel1).isNotEqualTo(vorderingregel2);
    }
}
