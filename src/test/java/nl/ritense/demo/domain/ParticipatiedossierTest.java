package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.ParticipatiedossierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParticipatiedossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participatiedossier.class);
        Participatiedossier participatiedossier1 = getParticipatiedossierSample1();
        Participatiedossier participatiedossier2 = new Participatiedossier();
        assertThat(participatiedossier1).isNotEqualTo(participatiedossier2);

        participatiedossier2.setId(participatiedossier1.getId());
        assertThat(participatiedossier1).isEqualTo(participatiedossier2);

        participatiedossier2 = getParticipatiedossierSample2();
        assertThat(participatiedossier1).isNotEqualTo(participatiedossier2);
    }

    @Test
    void emptyClientbegeleiderTest() throws Exception {
        Participatiedossier participatiedossier = getParticipatiedossierRandomSampleGenerator();
        Clientbegeleider clientbegeleiderBack = getClientbegeleiderRandomSampleGenerator();

        participatiedossier.setEmptyClientbegeleider(clientbegeleiderBack);
        assertThat(participatiedossier.getEmptyClientbegeleider()).isEqualTo(clientbegeleiderBack);

        participatiedossier.emptyClientbegeleider(null);
        assertThat(participatiedossier.getEmptyClientbegeleider()).isNull();
    }

    @Test
    void heeftClientTest() throws Exception {
        Participatiedossier participatiedossier = getParticipatiedossierRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        participatiedossier.setHeeftClient(clientBack);
        assertThat(participatiedossier.getHeeftClient()).isEqualTo(clientBack);
        assertThat(clientBack.getHeeftParticipatiedossier()).isEqualTo(participatiedossier);

        participatiedossier.heeftClient(null);
        assertThat(participatiedossier.getHeeftClient()).isNull();
        assertThat(clientBack.getHeeftParticipatiedossier()).isNull();
    }
}
