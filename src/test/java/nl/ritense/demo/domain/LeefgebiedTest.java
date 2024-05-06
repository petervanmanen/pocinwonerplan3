package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeefgebiedTestSamples.*;
import static nl.ritense.demo.domain.ScoreTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeefgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leefgebied.class);
        Leefgebied leefgebied1 = getLeefgebiedSample1();
        Leefgebied leefgebied2 = new Leefgebied();
        assertThat(leefgebied1).isNotEqualTo(leefgebied2);

        leefgebied2.setId(leefgebied1.getId());
        assertThat(leefgebied1).isEqualTo(leefgebied2);

        leefgebied2 = getLeefgebiedSample2();
        assertThat(leefgebied1).isNotEqualTo(leefgebied2);
    }

    @Test
    void scorebijleeggebiedScoreTest() throws Exception {
        Leefgebied leefgebied = getLeefgebiedRandomSampleGenerator();
        Score scoreBack = getScoreRandomSampleGenerator();

        leefgebied.addScorebijleeggebiedScore(scoreBack);
        assertThat(leefgebied.getScorebijleeggebiedScores()).containsOnly(scoreBack);
        assertThat(scoreBack.getScorebijleeggebiedLeefgebied()).isEqualTo(leefgebied);

        leefgebied.removeScorebijleeggebiedScore(scoreBack);
        assertThat(leefgebied.getScorebijleeggebiedScores()).doesNotContain(scoreBack);
        assertThat(scoreBack.getScorebijleeggebiedLeefgebied()).isNull();

        leefgebied.scorebijleeggebiedScores(new HashSet<>(Set.of(scoreBack)));
        assertThat(leefgebied.getScorebijleeggebiedScores()).containsOnly(scoreBack);
        assertThat(scoreBack.getScorebijleeggebiedLeefgebied()).isEqualTo(leefgebied);

        leefgebied.setScorebijleeggebiedScores(new HashSet<>());
        assertThat(leefgebied.getScorebijleeggebiedScores()).doesNotContain(scoreBack);
        assertThat(scoreBack.getScorebijleeggebiedLeefgebied()).isNull();
    }
}
