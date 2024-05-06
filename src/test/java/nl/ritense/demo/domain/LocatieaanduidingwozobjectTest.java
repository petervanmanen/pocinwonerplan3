package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatieaanduidingwozobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocatieaanduidingwozobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locatieaanduidingwozobject.class);
        Locatieaanduidingwozobject locatieaanduidingwozobject1 = getLocatieaanduidingwozobjectSample1();
        Locatieaanduidingwozobject locatieaanduidingwozobject2 = new Locatieaanduidingwozobject();
        assertThat(locatieaanduidingwozobject1).isNotEqualTo(locatieaanduidingwozobject2);

        locatieaanduidingwozobject2.setId(locatieaanduidingwozobject1.getId());
        assertThat(locatieaanduidingwozobject1).isEqualTo(locatieaanduidingwozobject2);

        locatieaanduidingwozobject2 = getLocatieaanduidingwozobjectSample2();
        assertThat(locatieaanduidingwozobject1).isNotEqualTo(locatieaanduidingwozobject2);
    }
}
