package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HandelsnamenmaatschappelijkeactiviteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HandelsnamenmaatschappelijkeactiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Handelsnamenmaatschappelijkeactiviteit.class);
        Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit1 = getHandelsnamenmaatschappelijkeactiviteitSample1();
        Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit2 = new Handelsnamenmaatschappelijkeactiviteit();
        assertThat(handelsnamenmaatschappelijkeactiviteit1).isNotEqualTo(handelsnamenmaatschappelijkeactiviteit2);

        handelsnamenmaatschappelijkeactiviteit2.setId(handelsnamenmaatschappelijkeactiviteit1.getId());
        assertThat(handelsnamenmaatschappelijkeactiviteit1).isEqualTo(handelsnamenmaatschappelijkeactiviteit2);

        handelsnamenmaatschappelijkeactiviteit2 = getHandelsnamenmaatschappelijkeactiviteitSample2();
        assertThat(handelsnamenmaatschappelijkeactiviteit1).isNotEqualTo(handelsnamenmaatschappelijkeactiviteit2);
    }
}
