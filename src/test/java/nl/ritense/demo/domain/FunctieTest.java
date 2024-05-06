package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DienstverbandTestSamples.*;
import static nl.ritense.demo.domain.FunctieTestSamples.*;
import static nl.ritense.demo.domain.OpdrachtgeverTestSamples.*;
import static nl.ritense.demo.domain.OpdrachtnemerTestSamples.*;
import static nl.ritense.demo.domain.VacatureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FunctieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functie.class);
        Functie functie1 = getFunctieSample1();
        Functie functie2 = new Functie();
        assertThat(functie1).isNotEqualTo(functie2);

        functie2.setId(functie1.getId());
        assertThat(functie1).isEqualTo(functie2);

        functie2 = getFunctieSample2();
        assertThat(functie1).isNotEqualTo(functie2);
    }

    @Test
    void uitgevoerddoorOpdrachtgeverTest() throws Exception {
        Functie functie = getFunctieRandomSampleGenerator();
        Opdrachtgever opdrachtgeverBack = getOpdrachtgeverRandomSampleGenerator();

        functie.addUitgevoerddoorOpdrachtgever(opdrachtgeverBack);
        assertThat(functie.getUitgevoerddoorOpdrachtgevers()).containsOnly(opdrachtgeverBack);
        assertThat(opdrachtgeverBack.getUitgevoerddoorFunctie()).isEqualTo(functie);

        functie.removeUitgevoerddoorOpdrachtgever(opdrachtgeverBack);
        assertThat(functie.getUitgevoerddoorOpdrachtgevers()).doesNotContain(opdrachtgeverBack);
        assertThat(opdrachtgeverBack.getUitgevoerddoorFunctie()).isNull();

        functie.uitgevoerddoorOpdrachtgevers(new HashSet<>(Set.of(opdrachtgeverBack)));
        assertThat(functie.getUitgevoerddoorOpdrachtgevers()).containsOnly(opdrachtgeverBack);
        assertThat(opdrachtgeverBack.getUitgevoerddoorFunctie()).isEqualTo(functie);

        functie.setUitgevoerddoorOpdrachtgevers(new HashSet<>());
        assertThat(functie.getUitgevoerddoorOpdrachtgevers()).doesNotContain(opdrachtgeverBack);
        assertThat(opdrachtgeverBack.getUitgevoerddoorFunctie()).isNull();
    }

    @Test
    void uitgevoerddoorOpdrachtnemerTest() throws Exception {
        Functie functie = getFunctieRandomSampleGenerator();
        Opdrachtnemer opdrachtnemerBack = getOpdrachtnemerRandomSampleGenerator();

        functie.addUitgevoerddoorOpdrachtnemer(opdrachtnemerBack);
        assertThat(functie.getUitgevoerddoorOpdrachtnemers()).containsOnly(opdrachtnemerBack);
        assertThat(opdrachtnemerBack.getUitgevoerddoorFunctie()).isEqualTo(functie);

        functie.removeUitgevoerddoorOpdrachtnemer(opdrachtnemerBack);
        assertThat(functie.getUitgevoerddoorOpdrachtnemers()).doesNotContain(opdrachtnemerBack);
        assertThat(opdrachtnemerBack.getUitgevoerddoorFunctie()).isNull();

        functie.uitgevoerddoorOpdrachtnemers(new HashSet<>(Set.of(opdrachtnemerBack)));
        assertThat(functie.getUitgevoerddoorOpdrachtnemers()).containsOnly(opdrachtnemerBack);
        assertThat(opdrachtnemerBack.getUitgevoerddoorFunctie()).isEqualTo(functie);

        functie.setUitgevoerddoorOpdrachtnemers(new HashSet<>());
        assertThat(functie.getUitgevoerddoorOpdrachtnemers()).doesNotContain(opdrachtnemerBack);
        assertThat(opdrachtnemerBack.getUitgevoerddoorFunctie()).isNull();
    }

    @Test
    void dienstverbandconformfunctieDienstverbandTest() throws Exception {
        Functie functie = getFunctieRandomSampleGenerator();
        Dienstverband dienstverbandBack = getDienstverbandRandomSampleGenerator();

        functie.addDienstverbandconformfunctieDienstverband(dienstverbandBack);
        assertThat(functie.getDienstverbandconformfunctieDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getDienstverbandconformfunctieFunctie()).isEqualTo(functie);

        functie.removeDienstverbandconformfunctieDienstverband(dienstverbandBack);
        assertThat(functie.getDienstverbandconformfunctieDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getDienstverbandconformfunctieFunctie()).isNull();

        functie.dienstverbandconformfunctieDienstverbands(new HashSet<>(Set.of(dienstverbandBack)));
        assertThat(functie.getDienstverbandconformfunctieDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getDienstverbandconformfunctieFunctie()).isEqualTo(functie);

        functie.setDienstverbandconformfunctieDienstverbands(new HashSet<>());
        assertThat(functie.getDienstverbandconformfunctieDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getDienstverbandconformfunctieFunctie()).isNull();
    }

    @Test
    void vacaturebijfunctieVacatureTest() throws Exception {
        Functie functie = getFunctieRandomSampleGenerator();
        Vacature vacatureBack = getVacatureRandomSampleGenerator();

        functie.addVacaturebijfunctieVacature(vacatureBack);
        assertThat(functie.getVacaturebijfunctieVacatures()).containsOnly(vacatureBack);
        assertThat(vacatureBack.getVacaturebijfunctieFunctie()).isEqualTo(functie);

        functie.removeVacaturebijfunctieVacature(vacatureBack);
        assertThat(functie.getVacaturebijfunctieVacatures()).doesNotContain(vacatureBack);
        assertThat(vacatureBack.getVacaturebijfunctieFunctie()).isNull();

        functie.vacaturebijfunctieVacatures(new HashSet<>(Set.of(vacatureBack)));
        assertThat(functie.getVacaturebijfunctieVacatures()).containsOnly(vacatureBack);
        assertThat(vacatureBack.getVacaturebijfunctieFunctie()).isEqualTo(functie);

        functie.setVacaturebijfunctieVacatures(new HashSet<>());
        assertThat(functie.getVacaturebijfunctieVacatures()).doesNotContain(vacatureBack);
        assertThat(vacatureBack.getVacaturebijfunctieFunctie()).isNull();
    }
}
