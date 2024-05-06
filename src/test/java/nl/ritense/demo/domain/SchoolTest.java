package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeslissingTestSamples.*;
import static nl.ritense.demo.domain.InschrijvingTestSamples.*;
import static nl.ritense.demo.domain.OnderwijsloopbaanTestSamples.*;
import static nl.ritense.demo.domain.OnderwijssoortTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static nl.ritense.demo.domain.SportlocatieTestSamples.*;
import static nl.ritense.demo.domain.UitschrijvingTestSamples.*;
import static nl.ritense.demo.domain.VerzuimmeldingTestSamples.*;
import static nl.ritense.demo.domain.VrijstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchoolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = getSchoolSample1();
        School school2 = new School();
        assertThat(school1).isNotEqualTo(school2);

        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);

        school2 = getSchoolSample2();
        assertThat(school1).isNotEqualTo(school2);
    }

    @Test
    void heeftUitschrijvingTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Uitschrijving uitschrijvingBack = getUitschrijvingRandomSampleGenerator();

        school.addHeeftUitschrijving(uitschrijvingBack);
        assertThat(school.getHeeftUitschrijvings()).containsOnly(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftSchool()).isEqualTo(school);

        school.removeHeeftUitschrijving(uitschrijvingBack);
        assertThat(school.getHeeftUitschrijvings()).doesNotContain(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftSchool()).isNull();

        school.heeftUitschrijvings(new HashSet<>(Set.of(uitschrijvingBack)));
        assertThat(school.getHeeftUitschrijvings()).containsOnly(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftSchool()).isEqualTo(school);

        school.setHeeftUitschrijvings(new HashSet<>());
        assertThat(school.getHeeftUitschrijvings()).doesNotContain(uitschrijvingBack);
        assertThat(uitschrijvingBack.getHeeftSchool()).isNull();
    }

    @Test
    void kentOnderwijsloopbaanTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Onderwijsloopbaan onderwijsloopbaanBack = getOnderwijsloopbaanRandomSampleGenerator();

        school.addKentOnderwijsloopbaan(onderwijsloopbaanBack);
        assertThat(school.getKentOnderwijsloopbaans()).containsOnly(onderwijsloopbaanBack);

        school.removeKentOnderwijsloopbaan(onderwijsloopbaanBack);
        assertThat(school.getKentOnderwijsloopbaans()).doesNotContain(onderwijsloopbaanBack);

        school.kentOnderwijsloopbaans(new HashSet<>(Set.of(onderwijsloopbaanBack)));
        assertThat(school.getKentOnderwijsloopbaans()).containsOnly(onderwijsloopbaanBack);

        school.setKentOnderwijsloopbaans(new HashSet<>());
        assertThat(school.getKentOnderwijsloopbaans()).doesNotContain(onderwijsloopbaanBack);
    }

    @Test
    void heeftOnderwijssoortTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Onderwijssoort onderwijssoortBack = getOnderwijssoortRandomSampleGenerator();

        school.addHeeftOnderwijssoort(onderwijssoortBack);
        assertThat(school.getHeeftOnderwijssoorts()).containsOnly(onderwijssoortBack);

        school.removeHeeftOnderwijssoort(onderwijssoortBack);
        assertThat(school.getHeeftOnderwijssoorts()).doesNotContain(onderwijssoortBack);

        school.heeftOnderwijssoorts(new HashSet<>(Set.of(onderwijssoortBack)));
        assertThat(school.getHeeftOnderwijssoorts()).containsOnly(onderwijssoortBack);

        school.setHeeftOnderwijssoorts(new HashSet<>());
        assertThat(school.getHeeftOnderwijssoorts()).doesNotContain(onderwijssoortBack);
    }

    @Test
    void gebruiktSportlocatieTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Sportlocatie sportlocatieBack = getSportlocatieRandomSampleGenerator();

        school.addGebruiktSportlocatie(sportlocatieBack);
        assertThat(school.getGebruiktSportlocaties()).containsOnly(sportlocatieBack);

        school.removeGebruiktSportlocatie(sportlocatieBack);
        assertThat(school.getGebruiktSportlocaties()).doesNotContain(sportlocatieBack);

        school.gebruiktSportlocaties(new HashSet<>(Set.of(sportlocatieBack)));
        assertThat(school.getGebruiktSportlocaties()).containsOnly(sportlocatieBack);

        school.setGebruiktSportlocaties(new HashSet<>());
        assertThat(school.getGebruiktSportlocaties()).doesNotContain(sportlocatieBack);
    }

    @Test
    void betreftBeslissingTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Beslissing beslissingBack = getBeslissingRandomSampleGenerator();

        school.addBetreftBeslissing(beslissingBack);
        assertThat(school.getBetreftBeslissings()).containsOnly(beslissingBack);
        assertThat(beslissingBack.getBetreftSchool()).isEqualTo(school);

        school.removeBetreftBeslissing(beslissingBack);
        assertThat(school.getBetreftBeslissings()).doesNotContain(beslissingBack);
        assertThat(beslissingBack.getBetreftSchool()).isNull();

        school.betreftBeslissings(new HashSet<>(Set.of(beslissingBack)));
        assertThat(school.getBetreftBeslissings()).containsOnly(beslissingBack);
        assertThat(beslissingBack.getBetreftSchool()).isEqualTo(school);

        school.setBetreftBeslissings(new HashSet<>());
        assertThat(school.getBetreftBeslissings()).doesNotContain(beslissingBack);
        assertThat(beslissingBack.getBetreftSchool()).isNull();
    }

    @Test
    void heeftVerzuimmeldingTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Verzuimmelding verzuimmeldingBack = getVerzuimmeldingRandomSampleGenerator();

        school.addHeeftVerzuimmelding(verzuimmeldingBack);
        assertThat(school.getHeeftVerzuimmeldings()).containsOnly(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftSchool()).isEqualTo(school);

        school.removeHeeftVerzuimmelding(verzuimmeldingBack);
        assertThat(school.getHeeftVerzuimmeldings()).doesNotContain(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftSchool()).isNull();

        school.heeftVerzuimmeldings(new HashSet<>(Set.of(verzuimmeldingBack)));
        assertThat(school.getHeeftVerzuimmeldings()).containsOnly(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftSchool()).isEqualTo(school);

        school.setHeeftVerzuimmeldings(new HashSet<>());
        assertThat(school.getHeeftVerzuimmeldings()).doesNotContain(verzuimmeldingBack);
        assertThat(verzuimmeldingBack.getHeeftSchool()).isNull();
    }

    @Test
    void heeftVrijstellingTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Vrijstelling vrijstellingBack = getVrijstellingRandomSampleGenerator();

        school.addHeeftVrijstelling(vrijstellingBack);
        assertThat(school.getHeeftVrijstellings()).containsOnly(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftSchool()).isEqualTo(school);

        school.removeHeeftVrijstelling(vrijstellingBack);
        assertThat(school.getHeeftVrijstellings()).doesNotContain(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftSchool()).isNull();

        school.heeftVrijstellings(new HashSet<>(Set.of(vrijstellingBack)));
        assertThat(school.getHeeftVrijstellings()).containsOnly(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftSchool()).isEqualTo(school);

        school.setHeeftVrijstellings(new HashSet<>());
        assertThat(school.getHeeftVrijstellings()).doesNotContain(vrijstellingBack);
        assertThat(vrijstellingBack.getHeeftSchool()).isNull();
    }

    @Test
    void heeftInschrijvingTest() throws Exception {
        School school = getSchoolRandomSampleGenerator();
        Inschrijving inschrijvingBack = getInschrijvingRandomSampleGenerator();

        school.addHeeftInschrijving(inschrijvingBack);
        assertThat(school.getHeeftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftSchool()).isEqualTo(school);

        school.removeHeeftInschrijving(inschrijvingBack);
        assertThat(school.getHeeftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftSchool()).isNull();

        school.heeftInschrijvings(new HashSet<>(Set.of(inschrijvingBack)));
        assertThat(school.getHeeftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftSchool()).isEqualTo(school);

        school.setHeeftInschrijvings(new HashSet<>());
        assertThat(school.getHeeftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftSchool()).isNull();
    }
}
