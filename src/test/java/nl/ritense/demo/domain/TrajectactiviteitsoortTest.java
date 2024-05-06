package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TrajectactiviteitsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrajectactiviteitsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trajectactiviteitsoort.class);
        Trajectactiviteitsoort trajectactiviteitsoort1 = getTrajectactiviteitsoortSample1();
        Trajectactiviteitsoort trajectactiviteitsoort2 = new Trajectactiviteitsoort();
        assertThat(trajectactiviteitsoort1).isNotEqualTo(trajectactiviteitsoort2);

        trajectactiviteitsoort2.setId(trajectactiviteitsoort1.getId());
        assertThat(trajectactiviteitsoort1).isEqualTo(trajectactiviteitsoort2);

        trajectactiviteitsoort2 = getTrajectactiviteitsoortSample2();
        assertThat(trajectactiviteitsoort1).isNotEqualTo(trajectactiviteitsoort2);
    }
}
