package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TrajectactiviteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrajectactiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trajectactiviteit.class);
        Trajectactiviteit trajectactiviteit1 = getTrajectactiviteitSample1();
        Trajectactiviteit trajectactiviteit2 = new Trajectactiviteit();
        assertThat(trajectactiviteit1).isNotEqualTo(trajectactiviteit2);

        trajectactiviteit2.setId(trajectactiviteit1.getId());
        assertThat(trajectactiviteit1).isEqualTo(trajectactiviteit2);

        trajectactiviteit2 = getTrajectactiviteitSample2();
        assertThat(trajectactiviteit1).isNotEqualTo(trajectactiviteit2);
    }
}
