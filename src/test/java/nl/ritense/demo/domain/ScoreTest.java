package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.LeefgebiedTestSamples.*;
import static nl.ritense.demo.domain.ScoreTestSamples.*;
import static nl.ritense.demo.domain.ScoresoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Score.class);
        Score score1 = getScoreSample1();
        Score score2 = new Score();
        assertThat(score1).isNotEqualTo(score2);

        score2.setId(score1.getId());
        assertThat(score1).isEqualTo(score2);

        score2 = getScoreSample2();
        assertThat(score1).isNotEqualTo(score2);
    }

    @Test
    void scorebijleeggebiedLeefgebiedTest() throws Exception {
        Score score = getScoreRandomSampleGenerator();
        Leefgebied leefgebiedBack = getLeefgebiedRandomSampleGenerator();

        score.setScorebijleeggebiedLeefgebied(leefgebiedBack);
        assertThat(score.getScorebijleeggebiedLeefgebied()).isEqualTo(leefgebiedBack);

        score.scorebijleeggebiedLeefgebied(null);
        assertThat(score.getScorebijleeggebiedLeefgebied()).isNull();
    }

    @Test
    void hoogtescoreScoresoortTest() throws Exception {
        Score score = getScoreRandomSampleGenerator();
        Scoresoort scoresoortBack = getScoresoortRandomSampleGenerator();

        score.setHoogtescoreScoresoort(scoresoortBack);
        assertThat(score.getHoogtescoreScoresoort()).isEqualTo(scoresoortBack);

        score.hoogtescoreScoresoort(null);
        assertThat(score.getHoogtescoreScoresoort()).isNull();
    }

    @Test
    void heeftClientTest() throws Exception {
        Score score = getScoreRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        score.setHeeftClient(clientBack);
        assertThat(score.getHeeftClient()).isEqualTo(clientBack);

        score.heeftClient(null);
        assertThat(score.getHeeftClient()).isNull();
    }
}
