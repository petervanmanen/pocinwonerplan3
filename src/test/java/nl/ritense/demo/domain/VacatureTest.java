package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FunctieTestSamples.*;
import static nl.ritense.demo.domain.SollicitatieTestSamples.*;
import static nl.ritense.demo.domain.VacatureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VacatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vacature.class);
        Vacature vacature1 = getVacatureSample1();
        Vacature vacature2 = new Vacature();
        assertThat(vacature1).isNotEqualTo(vacature2);

        vacature2.setId(vacature1.getId());
        assertThat(vacature1).isEqualTo(vacature2);

        vacature2 = getVacatureSample2();
        assertThat(vacature1).isNotEqualTo(vacature2);
    }

    @Test
    void vacaturebijfunctieFunctieTest() throws Exception {
        Vacature vacature = getVacatureRandomSampleGenerator();
        Functie functieBack = getFunctieRandomSampleGenerator();

        vacature.setVacaturebijfunctieFunctie(functieBack);
        assertThat(vacature.getVacaturebijfunctieFunctie()).isEqualTo(functieBack);

        vacature.vacaturebijfunctieFunctie(null);
        assertThat(vacature.getVacaturebijfunctieFunctie()).isNull();
    }

    @Test
    void opvacatureSollicitatieTest() throws Exception {
        Vacature vacature = getVacatureRandomSampleGenerator();
        Sollicitatie sollicitatieBack = getSollicitatieRandomSampleGenerator();

        vacature.addOpvacatureSollicitatie(sollicitatieBack);
        assertThat(vacature.getOpvacatureSollicitaties()).containsOnly(sollicitatieBack);
        assertThat(sollicitatieBack.getOpvacatureVacature()).isEqualTo(vacature);

        vacature.removeOpvacatureSollicitatie(sollicitatieBack);
        assertThat(vacature.getOpvacatureSollicitaties()).doesNotContain(sollicitatieBack);
        assertThat(sollicitatieBack.getOpvacatureVacature()).isNull();

        vacature.opvacatureSollicitaties(new HashSet<>(Set.of(sollicitatieBack)));
        assertThat(vacature.getOpvacatureSollicitaties()).containsOnly(sollicitatieBack);
        assertThat(sollicitatieBack.getOpvacatureVacature()).isEqualTo(vacature);

        vacature.setOpvacatureSollicitaties(new HashSet<>());
        assertThat(vacature.getOpvacatureSollicitaties()).doesNotContain(sollicitatieBack);
        assertThat(sollicitatieBack.getOpvacatureVacature()).isNull();
    }
}
