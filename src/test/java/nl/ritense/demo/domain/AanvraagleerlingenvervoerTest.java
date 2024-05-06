package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagleerlingenvervoerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraagleerlingenvervoerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraagleerlingenvervoer.class);
        Aanvraagleerlingenvervoer aanvraagleerlingenvervoer1 = getAanvraagleerlingenvervoerSample1();
        Aanvraagleerlingenvervoer aanvraagleerlingenvervoer2 = new Aanvraagleerlingenvervoer();
        assertThat(aanvraagleerlingenvervoer1).isNotEqualTo(aanvraagleerlingenvervoer2);

        aanvraagleerlingenvervoer2.setId(aanvraagleerlingenvervoer1.getId());
        assertThat(aanvraagleerlingenvervoer1).isEqualTo(aanvraagleerlingenvervoer2);

        aanvraagleerlingenvervoer2 = getAanvraagleerlingenvervoerSample2();
        assertThat(aanvraagleerlingenvervoer1).isNotEqualTo(aanvraagleerlingenvervoer2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Aanvraagleerlingenvervoer aanvraagleerlingenvervoer = new Aanvraagleerlingenvervoer();
        assertThat(aanvraagleerlingenvervoer.hashCode()).isZero();

        Aanvraagleerlingenvervoer aanvraagleerlingenvervoer1 = getAanvraagleerlingenvervoerSample1();
        aanvraagleerlingenvervoer.setId(aanvraagleerlingenvervoer1.getId());
        assertThat(aanvraagleerlingenvervoer).hasSameHashCodeAs(aanvraagleerlingenvervoer1);
    }
}
