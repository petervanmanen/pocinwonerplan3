package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeslissingTestSamples.*;
import static nl.ritense.demo.domain.InschrijvingTestSamples.*;
import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.OnderwijsloopbaanTestSamples.*;
import static nl.ritense.demo.domain.StartkwalificatieTestSamples.*;
import static nl.ritense.demo.domain.UitschrijvingTestSamples.*;
import static nl.ritense.demo.domain.VerzuimmeldingTestSamples.*;
import static nl.ritense.demo.domain.VrijstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeerlingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leerling.class);
        Leerling leerling1 = getLeerlingSample1();
        Leerling leerling2 = new Leerling();
        assertThat(leerling1).isNotEqualTo(leerling2);

        leerling2.setId(leerling1.getId());
        assertThat(leerling1).isEqualTo(leerling2);

        leerling2 = getLeerlingSample2();
        assertThat(leerling1).isNotEqualTo(leerling2);
    }

    @Test
    void heeftStartkwalificatieTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Startkwalificatie startkwalificatieBack = getStartkwalificatieRandomSampleGenerator();

        leerling.setHeeftStartkwalificatie(startkwalificatieBack);
        assertThat(leerling.getHeeftStartkwalificatie()).isEqualTo(startkwalificatieBack);

        leerling.heeftStartkwalificatie(null);
        assertThat(leerling.getHeeftStartkwalificatie()).isNull();
    }

    @Test
    void heeftVerzuimmeldingTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Verzuimmelding verzuimmeldingBack = getVerzuimmeldingRandomSampleGenerator();

        leerling.addHeeftVerzuimmelding(verzuimmeldingBack);
        assertThat(leerling.getHeeftVerzuimmeldings()).containsOnly(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.removeHeeftVerzuimmelding(verzuimmeldingBack);
        assertThat(leerling.getHeeftVerzuimmeldings()).doesNotContain(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftLeerling()).isNull();

        leerling.heeftVerzuimmeldings(new HashSet<>(Set.of(verzuimmeldingBack)));
        assertThat(leerling.getHeeftVerzuimmeldings()).containsOnly(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.setHeeftVerzuimmeldings(new HashSet<>());
        assertThat(leerling.getHeeftVerzuimmeldings()).doesNotContain(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftLeerling()).isNull();
    }

    @Test
    void heeftVrijstellingTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Vrijstelling vrijstellingBack = getVrijstellingRandomSampleGenerator();

        leerling.addHeeftVrijstelling(vrijstellingBack);
        assertThat(leerling.getHeeftVrijstellings()).containsOnly(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.removeHeeftVrijstelling(vrijstellingBack);
        assertThat(leerling.getHeeftVrijstellings()).doesNotContain(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftLeerling()).isNull();

        leerling.heeftVrijstellings(new HashSet<>(Set.of(vrijstellingBack)));
        assertThat(leerling.getHeeftVrijstellings()).containsOnly(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.setHeeftVrijstellings(new HashSet<>());
        assertThat(leerling.getHeeftVrijstellings()).doesNotContain(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftLeerling()).isNull();
    }

    @Test
    void heeftInschrijvingTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Inschrijving inschrijvingBack = getInschrijvingRandomSampleGenerator();

        leerling.addHeeftInschrijving(inschrijvingBack);
        assertThat(leerling.getHeeftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.removeHeeftInschrijving(inschrijvingBack);
        assertThat(leerling.getHeeftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeerling()).isNull();

        leerling.heeftInschrijvings(new HashSet<>(Set.of(inschrijvingBack)));
        assertThat(leerling.getHeeftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.setHeeftInschrijvings(new HashSet<>());
        assertThat(leerling.getHeeftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeerling()).isNull();
    }

    @Test
    void heeftOnderwijsloopbaanTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Onderwijsloopbaan onderwijsloopbaanBack = getOnderwijsloopbaanRandomSampleGenerator();

        leerling.addHeeftOnderwijsloopbaan(onderwijsloopbaanBack);
        assertThat(leerling.getHeeftOnderwijsloopbaans()).containsOnly(onderwijsloopbaanBack);
        assertThat(onderwijsloopbaanBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.removeHeeftOnderwijsloopbaan(onderwijsloopbaanBack);
        assertThat(leerling.getHeeftOnderwijsloopbaans()).doesNotContain(onderwijsloopbaanBack);
        assertThat(onderwijsloopbaanBack.getHeeftLeerling()).isNull();

        leerling.heeftOnderwijsloopbaans(new HashSet<>(Set.of(onderwijsloopbaanBack)));
        assertThat(leerling.getHeeftOnderwijsloopbaans()).containsOnly(onderwijsloopbaanBack);
        assertThat(onderwijsloopbaanBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.setHeeftOnderwijsloopbaans(new HashSet<>());
        assertThat(leerling.getHeeftOnderwijsloopbaans()).doesNotContain(onderwijsloopbaanBack);
        assertThat(onderwijsloopbaanBack.getHeeftLeerling()).isNull();
    }

    @Test
    void heeftUitschrijvingTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Uitschrijving uitschrijvingBack = getUitschrijvingRandomSampleGenerator();

        leerling.addHeeftUitschrijving(uitschrijvingBack);
        assertThat(leerling.getHeeftUitschrijvings()).containsOnly(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.removeHeeftUitschrijving(uitschrijvingBack);
        assertThat(leerling.getHeeftUitschrijvings()).doesNotContain(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftLeerling()).isNull();

        leerling.heeftUitschrijvings(new HashSet<>(Set.of(uitschrijvingBack)));
        assertThat(leerling.getHeeftUitschrijvings()).containsOnly(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftLeerling()).isEqualTo(leerling);

        leerling.setHeeftUitschrijvings(new HashSet<>());
        assertThat(leerling.getHeeftUitschrijvings()).doesNotContain(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftLeerling()).isNull();
    }

    @Test
    void betreftBeslissingTest() throws Exception {
        Leerling leerling = getLeerlingRandomSampleGenerator();
        Beslissing beslissingBack = getBeslissingRandomSampleGenerator();

        leerling.addBetreftBeslissing(beslissingBack);
        assertThat(leerling.getBetreftBeslissings()).containsOnly(beslissingBack);
        assertThat(beslissingBack.getBetreftLeerling()).isEqualTo(leerling);

        leerling.removeBetreftBeslissing(beslissingBack);
        assertThat(leerling.getBetreftBeslissings()).doesNotContain(beslissingBack);
        assertThat(beslissingBack.getBetreftLeerling()).isNull();

        leerling.betreftBeslissings(new HashSet<>(Set.of(beslissingBack)));
        assertThat(leerling.getBetreftBeslissings()).containsOnly(beslissingBack);
        assertThat(beslissingBack.getBetreftLeerling()).isEqualTo(leerling);

        leerling.setBetreftBeslissings(new HashSet<>());
        assertThat(leerling.getBetreftBeslissings()).doesNotContain(beslissingBack);
        assertThat(beslissingBack.getBetreftLeerling()).isNull();
    }
}
