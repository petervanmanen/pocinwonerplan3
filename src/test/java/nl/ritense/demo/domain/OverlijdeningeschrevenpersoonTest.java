package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverlijdeningeschrevenpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverlijdeningeschrevenpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overlijdeningeschrevenpersoon.class);
        Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon1 = getOverlijdeningeschrevenpersoonSample1();
        Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon2 = new Overlijdeningeschrevenpersoon();
        assertThat(overlijdeningeschrevenpersoon1).isNotEqualTo(overlijdeningeschrevenpersoon2);

        overlijdeningeschrevenpersoon2.setId(overlijdeningeschrevenpersoon1.getId());
        assertThat(overlijdeningeschrevenpersoon1).isEqualTo(overlijdeningeschrevenpersoon2);

        overlijdeningeschrevenpersoon2 = getOverlijdeningeschrevenpersoonSample2();
        assertThat(overlijdeningeschrevenpersoon1).isNotEqualTo(overlijdeningeschrevenpersoon2);
    }
}
