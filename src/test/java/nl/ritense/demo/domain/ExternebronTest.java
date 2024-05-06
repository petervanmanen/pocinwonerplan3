package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ExternebronTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExternebronTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Externebron.class);
        Externebron externebron1 = getExternebronSample1();
        Externebron externebron2 = new Externebron();
        assertThat(externebron1).isNotEqualTo(externebron2);

        externebron2.setId(externebron1.getId());
        assertThat(externebron1).isEqualTo(externebron2);

        externebron2 = getExternebronSample2();
        assertThat(externebron1).isNotEqualTo(externebron2);
    }
}
