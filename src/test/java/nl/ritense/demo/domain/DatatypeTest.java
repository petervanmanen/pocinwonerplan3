package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AttribuutsoortTestSamples.*;
import static nl.ritense.demo.domain.DatatypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatatypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Datatype.class);
        Datatype datatype1 = getDatatypeSample1();
        Datatype datatype2 = new Datatype();
        assertThat(datatype1).isNotEqualTo(datatype2);

        datatype2.setId(datatype1.getId());
        assertThat(datatype1).isEqualTo(datatype2);

        datatype2 = getDatatypeSample2();
        assertThat(datatype1).isNotEqualTo(datatype2);
    }

    @Test
    void heeftAttribuutsoortTest() throws Exception {
        Datatype datatype = getDatatypeRandomSampleGenerator();
        Attribuutsoort attribuutsoortBack = getAttribuutsoortRandomSampleGenerator();

        datatype.addHeeftAttribuutsoort(attribuutsoortBack);
        assertThat(datatype.getHeeftAttribuutsoorts()).containsOnly(attribuutsoortBack);
        assertThat(attribuutsoortBack.getHeeftDatatype()).isEqualTo(datatype);

        datatype.removeHeeftAttribuutsoort(attribuutsoortBack);
        assertThat(datatype.getHeeftAttribuutsoorts()).doesNotContain(attribuutsoortBack);
        assertThat(attribuutsoortBack.getHeeftDatatype()).isNull();

        datatype.heeftAttribuutsoorts(new HashSet<>(Set.of(attribuutsoortBack)));
        assertThat(datatype.getHeeftAttribuutsoorts()).containsOnly(attribuutsoortBack);
        assertThat(attribuutsoortBack.getHeeftDatatype()).isEqualTo(datatype);

        datatype.setHeeftAttribuutsoorts(new HashSet<>());
        assertThat(datatype.getHeeftAttribuutsoorts()).doesNotContain(attribuutsoortBack);
        assertThat(attribuutsoortBack.getHeeftDatatype()).isNull();
    }
}
