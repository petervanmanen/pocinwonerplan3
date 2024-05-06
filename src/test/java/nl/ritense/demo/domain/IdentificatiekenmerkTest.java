package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.IdentificatiekenmerkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdentificatiekenmerkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Identificatiekenmerk.class);
        Identificatiekenmerk identificatiekenmerk1 = getIdentificatiekenmerkSample1();
        Identificatiekenmerk identificatiekenmerk2 = new Identificatiekenmerk();
        assertThat(identificatiekenmerk1).isNotEqualTo(identificatiekenmerk2);

        identificatiekenmerk2.setId(identificatiekenmerk1.getId());
        assertThat(identificatiekenmerk1).isEqualTo(identificatiekenmerk2);

        identificatiekenmerk2 = getIdentificatiekenmerkSample2();
        assertThat(identificatiekenmerk1).isNotEqualTo(identificatiekenmerk2);
    }

    @Test
    void heeftkenmerkDocumentTest() throws Exception {
        Identificatiekenmerk identificatiekenmerk = getIdentificatiekenmerkRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        identificatiekenmerk.addHeeftkenmerkDocument(documentBack);
        assertThat(identificatiekenmerk.getHeeftkenmerkDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getHeeftkenmerkIdentificatiekenmerk()).isEqualTo(identificatiekenmerk);

        identificatiekenmerk.removeHeeftkenmerkDocument(documentBack);
        assertThat(identificatiekenmerk.getHeeftkenmerkDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getHeeftkenmerkIdentificatiekenmerk()).isNull();

        identificatiekenmerk.heeftkenmerkDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(identificatiekenmerk.getHeeftkenmerkDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getHeeftkenmerkIdentificatiekenmerk()).isEqualTo(identificatiekenmerk);

        identificatiekenmerk.setHeeftkenmerkDocuments(new HashSet<>());
        assertThat(identificatiekenmerk.getHeeftkenmerkDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getHeeftkenmerkIdentificatiekenmerk()).isNull();
    }
}
