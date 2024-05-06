package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MaatschappelijkeactiviteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaatschappelijkeactiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maatschappelijkeactiviteit.class);
        Maatschappelijkeactiviteit maatschappelijkeactiviteit1 = getMaatschappelijkeactiviteitSample1();
        Maatschappelijkeactiviteit maatschappelijkeactiviteit2 = new Maatschappelijkeactiviteit();
        assertThat(maatschappelijkeactiviteit1).isNotEqualTo(maatschappelijkeactiviteit2);

        maatschappelijkeactiviteit2.setId(maatschappelijkeactiviteit1.getId());
        assertThat(maatschappelijkeactiviteit1).isEqualTo(maatschappelijkeactiviteit2);

        maatschappelijkeactiviteit2 = getMaatschappelijkeactiviteitSample2();
        assertThat(maatschappelijkeactiviteit1).isNotEqualTo(maatschappelijkeactiviteit2);
    }
}
