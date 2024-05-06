package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WozbelangTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WozbelangTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wozbelang.class);
        Wozbelang wozbelang1 = getWozbelangSample1();
        Wozbelang wozbelang2 = new Wozbelang();
        assertThat(wozbelang1).isNotEqualTo(wozbelang2);

        wozbelang2.setId(wozbelang1.getId());
        assertThat(wozbelang1).isEqualTo(wozbelang2);

        wozbelang2 = getWozbelangSample2();
        assertThat(wozbelang1).isNotEqualTo(wozbelang2);
    }
}
