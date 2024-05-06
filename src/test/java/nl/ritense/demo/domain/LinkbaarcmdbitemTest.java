package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LinkbaarcmdbitemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkbaarcmdbitemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Linkbaarcmdbitem.class);
        Linkbaarcmdbitem linkbaarcmdbitem1 = getLinkbaarcmdbitemSample1();
        Linkbaarcmdbitem linkbaarcmdbitem2 = new Linkbaarcmdbitem();
        assertThat(linkbaarcmdbitem1).isNotEqualTo(linkbaarcmdbitem2);

        linkbaarcmdbitem2.setId(linkbaarcmdbitem1.getId());
        assertThat(linkbaarcmdbitem1).isEqualTo(linkbaarcmdbitem2);

        linkbaarcmdbitem2 = getLinkbaarcmdbitemSample2();
        assertThat(linkbaarcmdbitem1).isNotEqualTo(linkbaarcmdbitem2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Linkbaarcmdbitem linkbaarcmdbitem = new Linkbaarcmdbitem();
        assertThat(linkbaarcmdbitem.hashCode()).isZero();

        Linkbaarcmdbitem linkbaarcmdbitem1 = getLinkbaarcmdbitemSample1();
        linkbaarcmdbitem.setId(linkbaarcmdbitem1.getId());
        assertThat(linkbaarcmdbitem).hasSameHashCodeAs(linkbaarcmdbitem1);
    }
}
