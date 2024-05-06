package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.DocumenttypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumenttypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documenttype.class);
        Documenttype documenttype1 = getDocumenttypeSample1();
        Documenttype documenttype2 = new Documenttype();
        assertThat(documenttype1).isNotEqualTo(documenttype2);

        documenttype2.setId(documenttype1.getId());
        assertThat(documenttype1).isEqualTo(documenttype2);

        documenttype2 = getDocumenttypeSample2();
        assertThat(documenttype1).isNotEqualTo(documenttype2);
    }

    @Test
    void isvanDocumentTest() throws Exception {
        Documenttype documenttype = getDocumenttypeRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        documenttype.addIsvanDocument(documentBack);
        assertThat(documenttype.getIsvanDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getIsvanDocumenttype()).isEqualTo(documenttype);

        documenttype.removeIsvanDocument(documentBack);
        assertThat(documenttype.getIsvanDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getIsvanDocumenttype()).isNull();

        documenttype.isvanDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(documenttype.getIsvanDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getIsvanDocumenttype()).isEqualTo(documenttype);

        documenttype.setIsvanDocuments(new HashSet<>());
        assertThat(documenttype.getIsvanDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getIsvanDocumenttype()).isNull();
    }
}
