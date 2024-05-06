package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortdisciplinairemaatregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortdisciplinairemaatregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortdisciplinairemaatregel.class);
        Soortdisciplinairemaatregel soortdisciplinairemaatregel1 = getSoortdisciplinairemaatregelSample1();
        Soortdisciplinairemaatregel soortdisciplinairemaatregel2 = new Soortdisciplinairemaatregel();
        assertThat(soortdisciplinairemaatregel1).isNotEqualTo(soortdisciplinairemaatregel2);

        soortdisciplinairemaatregel2.setId(soortdisciplinairemaatregel1.getId());
        assertThat(soortdisciplinairemaatregel1).isEqualTo(soortdisciplinairemaatregel2);

        soortdisciplinairemaatregel2 = getSoortdisciplinairemaatregelSample2();
        assertThat(soortdisciplinairemaatregel1).isNotEqualTo(soortdisciplinairemaatregel2);
    }
}
