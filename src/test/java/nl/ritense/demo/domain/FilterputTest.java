package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FilterputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filterput.class);
        Filterput filterput1 = getFilterputSample1();
        Filterput filterput2 = new Filterput();
        assertThat(filterput1).isNotEqualTo(filterput2);

        filterput2.setId(filterput1.getId());
        assertThat(filterput1).isEqualTo(filterput2);

        filterput2 = getFilterputSample2();
        assertThat(filterput1).isNotEqualTo(filterput2);
    }
}
