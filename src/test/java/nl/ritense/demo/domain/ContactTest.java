package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContactTestSamples.*;
import static nl.ritense.demo.domain.VestigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contact.class);
        Contact contact1 = getContactSample1();
        Contact contact2 = new Contact();
        assertThat(contact1).isNotEqualTo(contact2);

        contact2.setId(contact1.getId());
        assertThat(contact1).isEqualTo(contact2);

        contact2 = getContactSample2();
        assertThat(contact1).isNotEqualTo(contact2);
    }

    @Test
    void bijVestigingTest() throws Exception {
        Contact contact = getContactRandomSampleGenerator();
        Vestiging vestigingBack = getVestigingRandomSampleGenerator();

        contact.setBijVestiging(vestigingBack);
        assertThat(contact.getBijVestiging()).isEqualTo(vestigingBack);

        contact.bijVestiging(null);
        assertThat(contact.getBijVestiging()).isNull();
    }
}
