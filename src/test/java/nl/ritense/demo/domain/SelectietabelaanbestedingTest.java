package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SelectietabelaanbestedingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SelectietabelaanbestedingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Selectietabelaanbesteding.class);
        Selectietabelaanbesteding selectietabelaanbesteding1 = getSelectietabelaanbestedingSample1();
        Selectietabelaanbesteding selectietabelaanbesteding2 = new Selectietabelaanbesteding();
        assertThat(selectietabelaanbesteding1).isNotEqualTo(selectietabelaanbesteding2);

        selectietabelaanbesteding2.setId(selectietabelaanbesteding1.getId());
        assertThat(selectietabelaanbesteding1).isEqualTo(selectietabelaanbesteding2);

        selectietabelaanbesteding2 = getSelectietabelaanbestedingSample2();
        assertThat(selectietabelaanbesteding1).isNotEqualTo(selectietabelaanbesteding2);
    }
}
