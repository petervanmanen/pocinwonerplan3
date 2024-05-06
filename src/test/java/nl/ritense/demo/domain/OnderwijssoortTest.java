package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderwijssoortTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderwijssoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderwijssoort.class);
        Onderwijssoort onderwijssoort1 = getOnderwijssoortSample1();
        Onderwijssoort onderwijssoort2 = new Onderwijssoort();
        assertThat(onderwijssoort1).isNotEqualTo(onderwijssoort2);

        onderwijssoort2.setId(onderwijssoort1.getId());
        assertThat(onderwijssoort1).isEqualTo(onderwijssoort2);

        onderwijssoort2 = getOnderwijssoortSample2();
        assertThat(onderwijssoort1).isNotEqualTo(onderwijssoort2);
    }

    @Test
    void heeftSchoolTest() throws Exception {
        Onderwijssoort onderwijssoort = getOnderwijssoortRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        onderwijssoort.addHeeftSchool(schoolBack);
        assertThat(onderwijssoort.getHeeftSchools()).containsOnly(schoolBack);
        assertThat(schoolBack.getHeeftOnderwijssoorts()).containsOnly(onderwijssoort);

        onderwijssoort.removeHeeftSchool(schoolBack);
        assertThat(onderwijssoort.getHeeftSchools()).doesNotContain(schoolBack);
        assertThat(schoolBack.getHeeftOnderwijssoorts()).doesNotContain(onderwijssoort);

        onderwijssoort.heeftSchools(new HashSet<>(Set.of(schoolBack)));
        assertThat(onderwijssoort.getHeeftSchools()).containsOnly(schoolBack);
        assertThat(schoolBack.getHeeftOnderwijssoorts()).containsOnly(onderwijssoort);

        onderwijssoort.setHeeftSchools(new HashSet<>());
        assertThat(onderwijssoort.getHeeftSchools()).doesNotContain(schoolBack);
        assertThat(schoolBack.getHeeftOnderwijssoorts()).doesNotContain(onderwijssoort);
    }
}
