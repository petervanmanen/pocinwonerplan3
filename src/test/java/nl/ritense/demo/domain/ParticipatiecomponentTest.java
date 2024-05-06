package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParticipatiecomponentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParticipatiecomponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participatiecomponent.class);
        Participatiecomponent participatiecomponent1 = getParticipatiecomponentSample1();
        Participatiecomponent participatiecomponent2 = new Participatiecomponent();
        assertThat(participatiecomponent1).isNotEqualTo(participatiecomponent2);

        participatiecomponent2.setId(participatiecomponent1.getId());
        assertThat(participatiecomponent1).isEqualTo(participatiecomponent2);

        participatiecomponent2 = getParticipatiecomponentSample2();
        assertThat(participatiecomponent1).isNotEqualTo(participatiecomponent2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Participatiecomponent participatiecomponent = new Participatiecomponent();
        assertThat(participatiecomponent.hashCode()).isZero();

        Participatiecomponent participatiecomponent1 = getParticipatiecomponentSample1();
        participatiecomponent.setId(participatiecomponent1.getId());
        assertThat(participatiecomponent).hasSameHashCodeAs(participatiecomponent1);
    }
}
