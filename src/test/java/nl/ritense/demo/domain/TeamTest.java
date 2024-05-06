package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = getTeamSample1();
        Team team2 = new Team();
        assertThat(team1).isNotEqualTo(team2);

        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);

        team2 = getTeamSample2();
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    void maaktonderdeeluitvanClientbegeleiderTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Clientbegeleider clientbegeleiderBack = getClientbegeleiderRandomSampleGenerator();

        team.addMaaktonderdeeluitvanClientbegeleider(clientbegeleiderBack);
        assertThat(team.getMaaktonderdeeluitvanClientbegeleiders()).containsOnly(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getMaaktonderdeeluitvanTeam()).isEqualTo(team);

        team.removeMaaktonderdeeluitvanClientbegeleider(clientbegeleiderBack);
        assertThat(team.getMaaktonderdeeluitvanClientbegeleiders()).doesNotContain(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getMaaktonderdeeluitvanTeam()).isNull();

        team.maaktonderdeeluitvanClientbegeleiders(new HashSet<>(Set.of(clientbegeleiderBack)));
        assertThat(team.getMaaktonderdeeluitvanClientbegeleiders()).containsOnly(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getMaaktonderdeeluitvanTeam()).isEqualTo(team);

        team.setMaaktonderdeeluitvanClientbegeleiders(new HashSet<>());
        assertThat(team.getMaaktonderdeeluitvanClientbegeleiders()).doesNotContain(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getMaaktonderdeeluitvanTeam()).isNull();
    }
}
