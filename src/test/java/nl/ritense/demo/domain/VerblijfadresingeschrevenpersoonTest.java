package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfadresingeschrevenpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfadresingeschrevenpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfadresingeschrevenpersoon.class);
        Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon1 = getVerblijfadresingeschrevenpersoonSample1();
        Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon2 = new Verblijfadresingeschrevenpersoon();
        assertThat(verblijfadresingeschrevenpersoon1).isNotEqualTo(verblijfadresingeschrevenpersoon2);

        verblijfadresingeschrevenpersoon2.setId(verblijfadresingeschrevenpersoon1.getId());
        assertThat(verblijfadresingeschrevenpersoon1).isEqualTo(verblijfadresingeschrevenpersoon2);

        verblijfadresingeschrevenpersoon2 = getVerblijfadresingeschrevenpersoonSample2();
        assertThat(verblijfadresingeschrevenpersoon1).isNotEqualTo(verblijfadresingeschrevenpersoon2);
    }
}
