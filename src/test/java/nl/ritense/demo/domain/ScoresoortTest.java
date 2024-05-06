package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ScoreTestSamples.*;
import static nl.ritense.demo.domain.ScoresoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScoresoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scoresoort.class);
        Scoresoort scoresoort1 = getScoresoortSample1();
        Scoresoort scoresoort2 = new Scoresoort();
        assertThat(scoresoort1).isNotEqualTo(scoresoort2);

        scoresoort2.setId(scoresoort1.getId());
        assertThat(scoresoort1).isEqualTo(scoresoort2);

        scoresoort2 = getScoresoortSample2();
        assertThat(scoresoort1).isNotEqualTo(scoresoort2);
    }

    @Test
    void hoogtescoreScoreTest() throws Exception {
        Scoresoort scoresoort = getScoresoortRandomSampleGenerator();
        Score scoreBack = getScoreRandomSampleGenerator();

        scoresoort.addHoogtescoreScore(scoreBack);
        assertThat(scoresoort.getHoogtescoreScores()).containsOnly(scoreBack);
        assertThat(scoreBack.getHoogtescoreScoresoort()).isEqualTo(scoresoort);

        scoresoort.removeHoogtescoreScore(scoreBack);
        assertThat(scoresoort.getHoogtescoreScores()).doesNotContain(scoreBack);
        assertThat(scoreBack.getHoogtescoreScoresoort()).isNull();

        scoresoort.hoogtescoreScores(new HashSet<>(Set.of(scoreBack)));
        assertThat(scoresoort.getHoogtescoreScores()).containsOnly(scoreBack);
        assertThat(scoreBack.getHoogtescoreScoresoort()).isEqualTo(scoresoort);

        scoresoort.setHoogtescoreScores(new HashSet<>());
        assertThat(scoresoort.getHoogtescoreScores()).doesNotContain(scoreBack);
        assertThat(scoreBack.getHoogtescoreScoresoort()).isNull();
    }
}
