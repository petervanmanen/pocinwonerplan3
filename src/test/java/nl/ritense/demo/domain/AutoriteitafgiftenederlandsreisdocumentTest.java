package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AutoriteitafgiftenederlandsreisdocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutoriteitafgiftenederlandsreisdocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autoriteitafgiftenederlandsreisdocument.class);
        Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument1 =
            getAutoriteitafgiftenederlandsreisdocumentSample1();
        Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument2 = new Autoriteitafgiftenederlandsreisdocument();
        assertThat(autoriteitafgiftenederlandsreisdocument1).isNotEqualTo(autoriteitafgiftenederlandsreisdocument2);

        autoriteitafgiftenederlandsreisdocument2.setId(autoriteitafgiftenederlandsreisdocument1.getId());
        assertThat(autoriteitafgiftenederlandsreisdocument1).isEqualTo(autoriteitafgiftenederlandsreisdocument2);

        autoriteitafgiftenederlandsreisdocument2 = getAutoriteitafgiftenederlandsreisdocumentSample2();
        assertThat(autoriteitafgiftenederlandsreisdocument1).isNotEqualTo(autoriteitafgiftenederlandsreisdocument2);
    }
}
