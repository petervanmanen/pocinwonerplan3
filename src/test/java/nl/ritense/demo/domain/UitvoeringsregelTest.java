package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.UitvoeringsregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitvoeringsregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitvoeringsregel.class);
        Uitvoeringsregel uitvoeringsregel1 = getUitvoeringsregelSample1();
        Uitvoeringsregel uitvoeringsregel2 = new Uitvoeringsregel();
        assertThat(uitvoeringsregel1).isNotEqualTo(uitvoeringsregel2);

        uitvoeringsregel2.setId(uitvoeringsregel1.getId());
        assertThat(uitvoeringsregel1).isEqualTo(uitvoeringsregel2);

        uitvoeringsregel2 = getUitvoeringsregelSample2();
        assertThat(uitvoeringsregel1).isNotEqualTo(uitvoeringsregel2);
    }
}
