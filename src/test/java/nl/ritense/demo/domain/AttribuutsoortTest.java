package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AttribuutsoortTestSamples.*;
import static nl.ritense.demo.domain.DatatypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttribuutsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attribuutsoort.class);
        Attribuutsoort attribuutsoort1 = getAttribuutsoortSample1();
        Attribuutsoort attribuutsoort2 = new Attribuutsoort();
        assertThat(attribuutsoort1).isNotEqualTo(attribuutsoort2);

        attribuutsoort2.setId(attribuutsoort1.getId());
        assertThat(attribuutsoort1).isEqualTo(attribuutsoort2);

        attribuutsoort2 = getAttribuutsoortSample2();
        assertThat(attribuutsoort1).isNotEqualTo(attribuutsoort2);
    }

    @Test
    void heeftDatatypeTest() throws Exception {
        Attribuutsoort attribuutsoort = getAttribuutsoortRandomSampleGenerator();
        Datatype datatypeBack = getDatatypeRandomSampleGenerator();

        attribuutsoort.setHeeftDatatype(datatypeBack);
        assertThat(attribuutsoort.getHeeftDatatype()).isEqualTo(datatypeBack);

        attribuutsoort.heeftDatatype(null);
        assertThat(attribuutsoort.getHeeftDatatype()).isNull();
    }
}
