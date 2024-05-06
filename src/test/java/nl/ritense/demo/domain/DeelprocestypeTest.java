package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BedrijfsprocestypeTestSamples.*;
import static nl.ritense.demo.domain.DeelprocesTestSamples.*;
import static nl.ritense.demo.domain.DeelprocestypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeelprocestypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deelprocestype.class);
        Deelprocestype deelprocestype1 = getDeelprocestypeSample1();
        Deelprocestype deelprocestype2 = new Deelprocestype();
        assertThat(deelprocestype1).isNotEqualTo(deelprocestype2);

        deelprocestype2.setId(deelprocestype1.getId());
        assertThat(deelprocestype1).isEqualTo(deelprocestype2);

        deelprocestype2 = getDeelprocestypeSample2();
        assertThat(deelprocestype1).isNotEqualTo(deelprocestype2);
    }

    @Test
    void isdeelvanBedrijfsprocestypeTest() throws Exception {
        Deelprocestype deelprocestype = getDeelprocestypeRandomSampleGenerator();
        Bedrijfsprocestype bedrijfsprocestypeBack = getBedrijfsprocestypeRandomSampleGenerator();

        deelprocestype.setIsdeelvanBedrijfsprocestype(bedrijfsprocestypeBack);
        assertThat(deelprocestype.getIsdeelvanBedrijfsprocestype()).isEqualTo(bedrijfsprocestypeBack);

        deelprocestype.isdeelvanBedrijfsprocestype(null);
        assertThat(deelprocestype.getIsdeelvanBedrijfsprocestype()).isNull();
    }

    @Test
    void isvanDeelprocesTest() throws Exception {
        Deelprocestype deelprocestype = getDeelprocestypeRandomSampleGenerator();
        Deelproces deelprocesBack = getDeelprocesRandomSampleGenerator();

        deelprocestype.setIsvanDeelproces(deelprocesBack);
        assertThat(deelprocestype.getIsvanDeelproces()).isEqualTo(deelprocesBack);
        assertThat(deelprocesBack.getIsvanDeelprocestype()).isEqualTo(deelprocestype);

        deelprocestype.isvanDeelproces(null);
        assertThat(deelprocestype.getIsvanDeelproces()).isNull();
        assertThat(deelprocesBack.getIsvanDeelprocestype()).isNull();
    }
}
