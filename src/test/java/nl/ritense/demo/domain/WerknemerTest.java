package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeoordelingTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieTestSamples.*;
import static nl.ritense.demo.domain.DienstverbandTestSamples.*;
import static nl.ritense.demo.domain.GeweldsincidentTestSamples.*;
import static nl.ritense.demo.domain.RolTestSamples.*;
import static nl.ritense.demo.domain.SollicitatieTestSamples.*;
import static nl.ritense.demo.domain.SollicitatiegesprekTestSamples.*;
import static nl.ritense.demo.domain.VerlofTestSamples.*;
import static nl.ritense.demo.domain.VerzuimTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WerknemerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Werknemer.class);
        Werknemer werknemer1 = getWerknemerSample1();
        Werknemer werknemer2 = new Werknemer();
        assertThat(werknemer1).isNotEqualTo(werknemer2);

        werknemer2.setId(werknemer1.getId());
        assertThat(werknemer1).isEqualTo(werknemer2);

        werknemer2 = getWerknemerSample2();
        assertThat(werknemer1).isNotEqualTo(werknemer2);
    }

    @Test
    void beoordeeltdoorBeoordelingTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Beoordeling beoordelingBack = getBeoordelingRandomSampleGenerator();

        werknemer.addBeoordeeltdoorBeoordeling(beoordelingBack);
        assertThat(werknemer.getBeoordeeltdoorBeoordelings()).containsOnly(beoordelingBack);
        assertThat(beoordelingBack.getBeoordeeltdoorWerknemer()).isEqualTo(werknemer);

        werknemer.removeBeoordeeltdoorBeoordeling(beoordelingBack);
        assertThat(werknemer.getBeoordeeltdoorBeoordelings()).doesNotContain(beoordelingBack);
        assertThat(beoordelingBack.getBeoordeeltdoorWerknemer()).isNull();

        werknemer.beoordeeltdoorBeoordelings(new HashSet<>(Set.of(beoordelingBack)));
        assertThat(werknemer.getBeoordeeltdoorBeoordelings()).containsOnly(beoordelingBack);
        assertThat(beoordelingBack.getBeoordeeltdoorWerknemer()).isEqualTo(werknemer);

        werknemer.setBeoordeeltdoorBeoordelings(new HashSet<>());
        assertThat(werknemer.getBeoordeeltdoorBeoordelings()).doesNotContain(beoordelingBack);
        assertThat(beoordelingBack.getBeoordeeltdoorWerknemer()).isNull();
    }

    @Test
    void beoordelingvanBeoordelingTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Beoordeling beoordelingBack = getBeoordelingRandomSampleGenerator();

        werknemer.addBeoordelingvanBeoordeling(beoordelingBack);
        assertThat(werknemer.getBeoordelingvanBeoordelings()).containsOnly(beoordelingBack);
        assertThat(beoordelingBack.getBeoordelingvanWerknemer()).isEqualTo(werknemer);

        werknemer.removeBeoordelingvanBeoordeling(beoordelingBack);
        assertThat(werknemer.getBeoordelingvanBeoordelings()).doesNotContain(beoordelingBack);
        assertThat(beoordelingBack.getBeoordelingvanWerknemer()).isNull();

        werknemer.beoordelingvanBeoordelings(new HashSet<>(Set.of(beoordelingBack)));
        assertThat(werknemer.getBeoordelingvanBeoordelings()).containsOnly(beoordelingBack);
        assertThat(beoordelingBack.getBeoordelingvanWerknemer()).isEqualTo(werknemer);

        werknemer.setBeoordelingvanBeoordelings(new HashSet<>());
        assertThat(werknemer.getBeoordelingvanBeoordelings()).doesNotContain(beoordelingBack);
        assertThat(beoordelingBack.getBeoordelingvanWerknemer()).isNull();
    }

    @Test
    void dientinDeclaratieTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Declaratie declaratieBack = getDeclaratieRandomSampleGenerator();

        werknemer.addDientinDeclaratie(declaratieBack);
        assertThat(werknemer.getDientinDeclaraties()).containsOnly(declaratieBack);
        assertThat(declaratieBack.getDientinWerknemer()).isEqualTo(werknemer);

        werknemer.removeDientinDeclaratie(declaratieBack);
        assertThat(werknemer.getDientinDeclaraties()).doesNotContain(declaratieBack);
        assertThat(declaratieBack.getDientinWerknemer()).isNull();

        werknemer.dientinDeclaraties(new HashSet<>(Set.of(declaratieBack)));
        assertThat(werknemer.getDientinDeclaraties()).containsOnly(declaratieBack);
        assertThat(declaratieBack.getDientinWerknemer()).isEqualTo(werknemer);

        werknemer.setDientinDeclaraties(new HashSet<>());
        assertThat(werknemer.getDientinDeclaraties()).doesNotContain(declaratieBack);
        assertThat(declaratieBack.getDientinWerknemer()).isNull();
    }

    @Test
    void medewerkerheeftdienstverbandDienstverbandTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Dienstverband dienstverbandBack = getDienstverbandRandomSampleGenerator();

        werknemer.addMedewerkerheeftdienstverbandDienstverband(dienstverbandBack);
        assertThat(werknemer.getMedewerkerheeftdienstverbandDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getMedewerkerheeftdienstverbandWerknemer()).isEqualTo(werknemer);

        werknemer.removeMedewerkerheeftdienstverbandDienstverband(dienstverbandBack);
        assertThat(werknemer.getMedewerkerheeftdienstverbandDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getMedewerkerheeftdienstverbandWerknemer()).isNull();

        werknemer.medewerkerheeftdienstverbandDienstverbands(new HashSet<>(Set.of(dienstverbandBack)));
        assertThat(werknemer.getMedewerkerheeftdienstverbandDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getMedewerkerheeftdienstverbandWerknemer()).isEqualTo(werknemer);

        werknemer.setMedewerkerheeftdienstverbandDienstverbands(new HashSet<>());
        assertThat(werknemer.getMedewerkerheeftdienstverbandDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getMedewerkerheeftdienstverbandWerknemer()).isNull();
    }

    @Test
    void solliciteertSollicitatieTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Sollicitatie sollicitatieBack = getSollicitatieRandomSampleGenerator();

        werknemer.addSolliciteertSollicitatie(sollicitatieBack);
        assertThat(werknemer.getSolliciteertSollicitaties()).containsOnly(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertWerknemer()).isEqualTo(werknemer);

        werknemer.removeSolliciteertSollicitatie(sollicitatieBack);
        assertThat(werknemer.getSolliciteertSollicitaties()).doesNotContain(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertWerknemer()).isNull();

        werknemer.solliciteertSollicitaties(new HashSet<>(Set.of(sollicitatieBack)));
        assertThat(werknemer.getSolliciteertSollicitaties()).containsOnly(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertWerknemer()).isEqualTo(werknemer);

        werknemer.setSolliciteertSollicitaties(new HashSet<>());
        assertThat(werknemer.getSolliciteertSollicitaties()).doesNotContain(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertWerknemer()).isNull();
    }

    @Test
    void heeftverlofVerlofTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Verlof verlofBack = getVerlofRandomSampleGenerator();

        werknemer.addHeeftverlofVerlof(verlofBack);
        assertThat(werknemer.getHeeftverlofVerlofs()).containsOnly(verlofBack);
        assertThat(verlofBack.getHeeftverlofWerknemer()).isEqualTo(werknemer);

        werknemer.removeHeeftverlofVerlof(verlofBack);
        assertThat(werknemer.getHeeftverlofVerlofs()).doesNotContain(verlofBack);
        assertThat(verlofBack.getHeeftverlofWerknemer()).isNull();

        werknemer.heeftverlofVerlofs(new HashSet<>(Set.of(verlofBack)));
        assertThat(werknemer.getHeeftverlofVerlofs()).containsOnly(verlofBack);
        assertThat(verlofBack.getHeeftverlofWerknemer()).isEqualTo(werknemer);

        werknemer.setHeeftverlofVerlofs(new HashSet<>());
        assertThat(werknemer.getHeeftverlofVerlofs()).doesNotContain(verlofBack);
        assertThat(verlofBack.getHeeftverlofWerknemer()).isNull();
    }

    @Test
    void heeftverzuimVerzuimTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Verzuim verzuimBack = getVerzuimRandomSampleGenerator();

        werknemer.addHeeftverzuimVerzuim(verzuimBack);
        assertThat(werknemer.getHeeftverzuimVerzuims()).containsOnly(verzuimBack);
        assertThat(verzuimBack.getHeeftverzuimWerknemer()).isEqualTo(werknemer);

        werknemer.removeHeeftverzuimVerzuim(verzuimBack);
        assertThat(werknemer.getHeeftverzuimVerzuims()).doesNotContain(verzuimBack);
        assertThat(verzuimBack.getHeeftverzuimWerknemer()).isNull();

        werknemer.heeftverzuimVerzuims(new HashSet<>(Set.of(verzuimBack)));
        assertThat(werknemer.getHeeftverzuimVerzuims()).containsOnly(verzuimBack);
        assertThat(verzuimBack.getHeeftverzuimWerknemer()).isEqualTo(werknemer);

        werknemer.setHeeftverzuimVerzuims(new HashSet<>());
        assertThat(werknemer.getHeeftverzuimVerzuims()).doesNotContain(verzuimBack);
        assertThat(verzuimBack.getHeeftverzuimWerknemer()).isNull();
    }

    @Test
    void heeftondergaanGeweldsincidentTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Geweldsincident geweldsincidentBack = getGeweldsincidentRandomSampleGenerator();

        werknemer.setHeeftondergaanGeweldsincident(geweldsincidentBack);
        assertThat(werknemer.getHeeftondergaanGeweldsincident()).isEqualTo(geweldsincidentBack);

        werknemer.heeftondergaanGeweldsincident(null);
        assertThat(werknemer.getHeeftondergaanGeweldsincident()).isNull();
    }

    @Test
    void heeftRolTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Rol rolBack = getRolRandomSampleGenerator();

        werknemer.addHeeftRol(rolBack);
        assertThat(werknemer.getHeeftRols()).containsOnly(rolBack);

        werknemer.removeHeeftRol(rolBack);
        assertThat(werknemer.getHeeftRols()).doesNotContain(rolBack);

        werknemer.heeftRols(new HashSet<>(Set.of(rolBack)));
        assertThat(werknemer.getHeeftRols()).containsOnly(rolBack);

        werknemer.setHeeftRols(new HashSet<>());
        assertThat(werknemer.getHeeftRols()).doesNotContain(rolBack);
    }

    @Test
    void doetsollicitatiegesprekSollicitatiegesprekTest() throws Exception {
        Werknemer werknemer = getWerknemerRandomSampleGenerator();
        Sollicitatiegesprek sollicitatiegesprekBack = getSollicitatiegesprekRandomSampleGenerator();

        werknemer.addDoetsollicitatiegesprekSollicitatiegesprek(sollicitatiegesprekBack);
        assertThat(werknemer.getDoetsollicitatiegesprekSollicitatiegespreks()).containsOnly(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getDoetsollicitatiegesprekWerknemers()).containsOnly(werknemer);

        werknemer.removeDoetsollicitatiegesprekSollicitatiegesprek(sollicitatiegesprekBack);
        assertThat(werknemer.getDoetsollicitatiegesprekSollicitatiegespreks()).doesNotContain(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getDoetsollicitatiegesprekWerknemers()).doesNotContain(werknemer);

        werknemer.doetsollicitatiegesprekSollicitatiegespreks(new HashSet<>(Set.of(sollicitatiegesprekBack)));
        assertThat(werknemer.getDoetsollicitatiegesprekSollicitatiegespreks()).containsOnly(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getDoetsollicitatiegesprekWerknemers()).containsOnly(werknemer);

        werknemer.setDoetsollicitatiegesprekSollicitatiegespreks(new HashSet<>());
        assertThat(werknemer.getDoetsollicitatiegesprekSollicitatiegespreks()).doesNotContain(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getDoetsollicitatiegesprekWerknemers()).doesNotContain(werknemer);
    }
}
