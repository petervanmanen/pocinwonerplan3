package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnbestemdadresTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnbestemdadresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onbestemdadres.class);
        Onbestemdadres onbestemdadres1 = getOnbestemdadresSample1();
        Onbestemdadres onbestemdadres2 = new Onbestemdadres();
        assertThat(onbestemdadres1).isNotEqualTo(onbestemdadres2);

        onbestemdadres2.setId(onbestemdadres1.getId());
        assertThat(onbestemdadres1).isEqualTo(onbestemdadres2);

        onbestemdadres2 = getOnbestemdadresSample2();
        assertThat(onbestemdadres1).isNotEqualTo(onbestemdadres2);
    }
}
