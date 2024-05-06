package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.AankondigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AankondigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aankondiging.class);
        Aankondiging aankondiging1 = getAankondigingSample1();
        Aankondiging aankondiging2 = new Aankondiging();
        assertThat(aankondiging1).isNotEqualTo(aankondiging2);

        aankondiging2.setId(aankondiging1.getId());
        assertThat(aankondiging1).isEqualTo(aankondiging2);

        aankondiging2 = getAankondigingSample2();
        assertThat(aankondiging1).isNotEqualTo(aankondiging2);
    }

    @Test
    void mondtuitAanbestedingTest() throws Exception {
        Aankondiging aankondiging = getAankondigingRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        aankondiging.setMondtuitAanbesteding(aanbestedingBack);
        assertThat(aankondiging.getMondtuitAanbesteding()).isEqualTo(aanbestedingBack);

        aankondiging.mondtuitAanbesteding(null);
        assertThat(aankondiging.getMondtuitAanbesteding()).isNull();
    }
}
