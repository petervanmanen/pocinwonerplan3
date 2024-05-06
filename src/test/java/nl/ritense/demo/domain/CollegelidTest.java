package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CollegelidTestSamples.*;
import static nl.ritense.demo.domain.IndienerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollegelidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collegelid.class);
        Collegelid collegelid1 = getCollegelidSample1();
        Collegelid collegelid2 = new Collegelid();
        assertThat(collegelid1).isNotEqualTo(collegelid2);

        collegelid2.setId(collegelid1.getId());
        assertThat(collegelid1).isEqualTo(collegelid2);

        collegelid2 = getCollegelidSample2();
        assertThat(collegelid1).isNotEqualTo(collegelid2);
    }

    @Test
    void isIndienerTest() throws Exception {
        Collegelid collegelid = getCollegelidRandomSampleGenerator();
        Indiener indienerBack = getIndienerRandomSampleGenerator();

        collegelid.setIsIndiener(indienerBack);
        assertThat(collegelid.getIsIndiener()).isEqualTo(indienerBack);
        assertThat(indienerBack.getIsCollegelid()).isEqualTo(collegelid);

        collegelid.isIndiener(null);
        assertThat(collegelid.getIsIndiener()).isNull();
        assertThat(indienerBack.getIsCollegelid()).isNull();
    }
}
