package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DoorgeleidingomTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoorgeleidingomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doorgeleidingom.class);
        Doorgeleidingom doorgeleidingom1 = getDoorgeleidingomSample1();
        Doorgeleidingom doorgeleidingom2 = new Doorgeleidingom();
        assertThat(doorgeleidingom1).isNotEqualTo(doorgeleidingom2);

        doorgeleidingom2.setId(doorgeleidingom1.getId());
        assertThat(doorgeleidingom1).isEqualTo(doorgeleidingom2);

        doorgeleidingom2 = getDoorgeleidingomSample2();
        assertThat(doorgeleidingom1).isNotEqualTo(doorgeleidingom2);
    }
}
