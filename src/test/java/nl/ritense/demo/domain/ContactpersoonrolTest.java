package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContactpersoonrolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactpersoonrolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contactpersoonrol.class);
        Contactpersoonrol contactpersoonrol1 = getContactpersoonrolSample1();
        Contactpersoonrol contactpersoonrol2 = new Contactpersoonrol();
        assertThat(contactpersoonrol1).isNotEqualTo(contactpersoonrol2);

        contactpersoonrol2.setId(contactpersoonrol1.getId());
        assertThat(contactpersoonrol1).isEqualTo(contactpersoonrol2);

        contactpersoonrol2 = getContactpersoonrolSample2();
        assertThat(contactpersoonrol1).isNotEqualTo(contactpersoonrol2);
    }
}
