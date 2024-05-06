package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StudentenwoningenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentenwoningenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Studentenwoningen.class);
        Studentenwoningen studentenwoningen1 = getStudentenwoningenSample1();
        Studentenwoningen studentenwoningen2 = new Studentenwoningen();
        assertThat(studentenwoningen1).isNotEqualTo(studentenwoningen2);

        studentenwoningen2.setId(studentenwoningen1.getId());
        assertThat(studentenwoningen1).isEqualTo(studentenwoningen2);

        studentenwoningen2 = getStudentenwoningenSample2();
        assertThat(studentenwoningen1).isNotEqualTo(studentenwoningen2);
    }
}
