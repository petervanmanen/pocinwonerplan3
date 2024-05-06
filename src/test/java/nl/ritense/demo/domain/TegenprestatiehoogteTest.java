package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TegenprestatiehoogteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TegenprestatiehoogteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tegenprestatiehoogte.class);
        Tegenprestatiehoogte tegenprestatiehoogte1 = getTegenprestatiehoogteSample1();
        Tegenprestatiehoogte tegenprestatiehoogte2 = new Tegenprestatiehoogte();
        assertThat(tegenprestatiehoogte1).isNotEqualTo(tegenprestatiehoogte2);

        tegenprestatiehoogte2.setId(tegenprestatiehoogte1.getId());
        assertThat(tegenprestatiehoogte1).isEqualTo(tegenprestatiehoogte2);

        tegenprestatiehoogte2 = getTegenprestatiehoogteSample2();
        assertThat(tegenprestatiehoogte1).isNotEqualTo(tegenprestatiehoogte2);
    }
}
