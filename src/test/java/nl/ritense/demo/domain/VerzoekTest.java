package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.InitiatiefnemerTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.ProjectactiviteitTestSamples.*;
import static nl.ritense.demo.domain.ProjectlocatieTestSamples.*;
import static nl.ritense.demo.domain.SpecificatieTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerzoekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verzoek.class);
        Verzoek verzoek1 = getVerzoekSample1();
        Verzoek verzoek2 = new Verzoek();
        assertThat(verzoek1).isNotEqualTo(verzoek2);

        verzoek2.setId(verzoek1.getId());
        assertThat(verzoek1).isEqualTo(verzoek2);

        verzoek2 = getVerzoekSample2();
        assertThat(verzoek1).isNotEqualTo(verzoek2);
    }

    @Test
    void leidttotZaakTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        verzoek.setLeidttotZaak(zaakBack);
        assertThat(verzoek.getLeidttotZaak()).isEqualTo(zaakBack);

        verzoek.leidttotZaak(null);
        assertThat(verzoek.getLeidttotZaak()).isNull();
    }

    @Test
    void bevatSpecificatieTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Specificatie specificatieBack = getSpecificatieRandomSampleGenerator();

        verzoek.addBevatSpecificatie(specificatieBack);
        assertThat(verzoek.getBevatSpecificaties()).containsOnly(specificatieBack);
        assertThat(specificatieBack.getBevatVerzoek()).isEqualTo(verzoek);

        verzoek.removeBevatSpecificatie(specificatieBack);
        assertThat(verzoek.getBevatSpecificaties()).doesNotContain(specificatieBack);
        assertThat(specificatieBack.getBevatVerzoek()).isNull();

        verzoek.bevatSpecificaties(new HashSet<>(Set.of(specificatieBack)));
        assertThat(verzoek.getBevatSpecificaties()).containsOnly(specificatieBack);
        assertThat(specificatieBack.getBevatVerzoek()).isEqualTo(verzoek);

        verzoek.setBevatSpecificaties(new HashSet<>());
        assertThat(verzoek.getBevatSpecificaties()).doesNotContain(specificatieBack);
        assertThat(specificatieBack.getBevatVerzoek()).isNull();
    }

    @Test
    void betrefteerderverzoekVerzoekTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        verzoek.setBetrefteerderverzoekVerzoek(verzoekBack);
        assertThat(verzoek.getBetrefteerderverzoekVerzoek()).isEqualTo(verzoekBack);

        verzoek.betrefteerderverzoekVerzoek(null);
        assertThat(verzoek.getBetrefteerderverzoekVerzoek()).isNull();
    }

    @Test
    void betreftProjectactiviteitTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Projectactiviteit projectactiviteitBack = getProjectactiviteitRandomSampleGenerator();

        verzoek.addBetreftProjectactiviteit(projectactiviteitBack);
        assertThat(verzoek.getBetreftProjectactiviteits()).containsOnly(projectactiviteitBack);

        verzoek.removeBetreftProjectactiviteit(projectactiviteitBack);
        assertThat(verzoek.getBetreftProjectactiviteits()).doesNotContain(projectactiviteitBack);

        verzoek.betreftProjectactiviteits(new HashSet<>(Set.of(projectactiviteitBack)));
        assertThat(verzoek.getBetreftProjectactiviteits()).containsOnly(projectactiviteitBack);

        verzoek.setBetreftProjectactiviteits(new HashSet<>());
        assertThat(verzoek.getBetreftProjectactiviteits()).doesNotContain(projectactiviteitBack);
    }

    @Test
    void betreftProjectlocatieTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Projectlocatie projectlocatieBack = getProjectlocatieRandomSampleGenerator();

        verzoek.addBetreftProjectlocatie(projectlocatieBack);
        assertThat(verzoek.getBetreftProjectlocaties()).containsOnly(projectlocatieBack);

        verzoek.removeBetreftProjectlocatie(projectlocatieBack);
        assertThat(verzoek.getBetreftProjectlocaties()).doesNotContain(projectlocatieBack);

        verzoek.betreftProjectlocaties(new HashSet<>(Set.of(projectlocatieBack)));
        assertThat(verzoek.getBetreftProjectlocaties()).containsOnly(projectlocatieBack);

        verzoek.setBetreftProjectlocaties(new HashSet<>());
        assertThat(verzoek.getBetreftProjectlocaties()).doesNotContain(projectlocatieBack);
    }

    @Test
    void betreftActiviteitTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        verzoek.addBetreftActiviteit(activiteitBack);
        assertThat(verzoek.getBetreftActiviteits()).containsOnly(activiteitBack);

        verzoek.removeBetreftActiviteit(activiteitBack);
        assertThat(verzoek.getBetreftActiviteits()).doesNotContain(activiteitBack);

        verzoek.betreftActiviteits(new HashSet<>(Set.of(activiteitBack)));
        assertThat(verzoek.getBetreftActiviteits()).containsOnly(activiteitBack);

        verzoek.setBetreftActiviteits(new HashSet<>());
        assertThat(verzoek.getBetreftActiviteits()).doesNotContain(activiteitBack);
    }

    @Test
    void betreftLocatieTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        verzoek.addBetreftLocatie(locatieBack);
        assertThat(verzoek.getBetreftLocaties()).containsOnly(locatieBack);

        verzoek.removeBetreftLocatie(locatieBack);
        assertThat(verzoek.getBetreftLocaties()).doesNotContain(locatieBack);

        verzoek.betreftLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(verzoek.getBetreftLocaties()).containsOnly(locatieBack);

        verzoek.setBetreftLocaties(new HashSet<>());
        assertThat(verzoek.getBetreftLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void heeftalsverantwoordelijkeInitiatiefnemerTest() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Initiatiefnemer initiatiefnemerBack = getInitiatiefnemerRandomSampleGenerator();

        verzoek.setHeeftalsverantwoordelijkeInitiatiefnemer(initiatiefnemerBack);
        assertThat(verzoek.getHeeftalsverantwoordelijkeInitiatiefnemer()).isEqualTo(initiatiefnemerBack);

        verzoek.heeftalsverantwoordelijkeInitiatiefnemer(null);
        assertThat(verzoek.getHeeftalsverantwoordelijkeInitiatiefnemer()).isNull();
    }

    @Test
    void betrefteerderverzoekVerzoek2Test() throws Exception {
        Verzoek verzoek = getVerzoekRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        verzoek.addBetrefteerderverzoekVerzoek2(verzoekBack);
        assertThat(verzoek.getBetrefteerderverzoekVerzoek2s()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetrefteerderverzoekVerzoek()).isEqualTo(verzoek);

        verzoek.removeBetrefteerderverzoekVerzoek2(verzoekBack);
        assertThat(verzoek.getBetrefteerderverzoekVerzoek2s()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetrefteerderverzoekVerzoek()).isNull();

        verzoek.betrefteerderverzoekVerzoek2s(new HashSet<>(Set.of(verzoekBack)));
        assertThat(verzoek.getBetrefteerderverzoekVerzoek2s()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetrefteerderverzoekVerzoek()).isEqualTo(verzoek);

        verzoek.setBetrefteerderverzoekVerzoek2s(new HashSet<>());
        assertThat(verzoek.getBetrefteerderverzoekVerzoek2s()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetrefteerderverzoekVerzoek()).isNull();
    }
}
