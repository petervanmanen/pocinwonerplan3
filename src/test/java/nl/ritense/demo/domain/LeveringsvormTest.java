package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeveringsvormTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeveringsvormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leveringsvorm.class);
        Leveringsvorm leveringsvorm1 = getLeveringsvormSample1();
        Leveringsvorm leveringsvorm2 = new Leveringsvorm();
        assertThat(leveringsvorm1).isNotEqualTo(leveringsvorm2);

        leveringsvorm2.setId(leveringsvorm1.getId());
        assertThat(leveringsvorm1).isEqualTo(leveringsvorm2);

        leveringsvorm2 = getLeveringsvormSample2();
        assertThat(leveringsvorm1).isNotEqualTo(leveringsvorm2);
    }
}
