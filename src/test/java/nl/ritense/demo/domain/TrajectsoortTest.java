package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static nl.ritense.demo.domain.TrajectsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrajectsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trajectsoort.class);
        Trajectsoort trajectsoort1 = getTrajectsoortSample1();
        Trajectsoort trajectsoort2 = new Trajectsoort();
        assertThat(trajectsoort1).isNotEqualTo(trajectsoort2);

        trajectsoort2.setId(trajectsoort1.getId());
        assertThat(trajectsoort1).isEqualTo(trajectsoort2);

        trajectsoort2 = getTrajectsoortSample2();
        assertThat(trajectsoort1).isNotEqualTo(trajectsoort2);
    }

    @Test
    void istrajectsoortTrajectTest() throws Exception {
        Trajectsoort trajectsoort = getTrajectsoortRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        trajectsoort.addIstrajectsoortTraject(trajectBack);
        assertThat(trajectsoort.getIstrajectsoortTrajects()).containsOnly(trajectBack);
        assertThat(trajectBack.getIstrajectsoortTrajectsoort()).isEqualTo(trajectsoort);

        trajectsoort.removeIstrajectsoortTraject(trajectBack);
        assertThat(trajectsoort.getIstrajectsoortTrajects()).doesNotContain(trajectBack);
        assertThat(trajectBack.getIstrajectsoortTrajectsoort()).isNull();

        trajectsoort.istrajectsoortTrajects(new HashSet<>(Set.of(trajectBack)));
        assertThat(trajectsoort.getIstrajectsoortTrajects()).containsOnly(trajectBack);
        assertThat(trajectBack.getIstrajectsoortTrajectsoort()).isEqualTo(trajectsoort);

        trajectsoort.setIstrajectsoortTrajects(new HashSet<>());
        assertThat(trajectsoort.getIstrajectsoortTrajects()).doesNotContain(trajectBack);
        assertThat(trajectBack.getIstrajectsoortTrajectsoort()).isNull();
    }
}
