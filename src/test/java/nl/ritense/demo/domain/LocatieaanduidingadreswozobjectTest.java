package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatieaanduidingadreswozobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocatieaanduidingadreswozobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locatieaanduidingadreswozobject.class);
        Locatieaanduidingadreswozobject locatieaanduidingadreswozobject1 = getLocatieaanduidingadreswozobjectSample1();
        Locatieaanduidingadreswozobject locatieaanduidingadreswozobject2 = new Locatieaanduidingadreswozobject();
        assertThat(locatieaanduidingadreswozobject1).isNotEqualTo(locatieaanduidingadreswozobject2);

        locatieaanduidingadreswozobject2.setId(locatieaanduidingadreswozobject1.getId());
        assertThat(locatieaanduidingadreswozobject1).isEqualTo(locatieaanduidingadreswozobject2);

        locatieaanduidingadreswozobject2 = getLocatieaanduidingadreswozobjectSample2();
        assertThat(locatieaanduidingadreswozobject1).isNotEqualTo(locatieaanduidingadreswozobject2);
    }
}
