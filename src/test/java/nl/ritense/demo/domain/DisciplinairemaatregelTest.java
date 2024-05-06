package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DisciplinairemaatregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisciplinairemaatregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disciplinairemaatregel.class);
        Disciplinairemaatregel disciplinairemaatregel1 = getDisciplinairemaatregelSample1();
        Disciplinairemaatregel disciplinairemaatregel2 = new Disciplinairemaatregel();
        assertThat(disciplinairemaatregel1).isNotEqualTo(disciplinairemaatregel2);

        disciplinairemaatregel2.setId(disciplinairemaatregel1.getId());
        assertThat(disciplinairemaatregel1).isEqualTo(disciplinairemaatregel2);

        disciplinairemaatregel2 = getDisciplinairemaatregelSample2();
        assertThat(disciplinairemaatregel1).isNotEqualTo(disciplinairemaatregel2);
    }
}
