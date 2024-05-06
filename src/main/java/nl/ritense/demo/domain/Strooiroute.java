package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Strooiroute.
 */
@Entity
@Table(name = "strooiroute")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Strooiroute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eroute")
    private String eroute;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Strooiroute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEroute() {
        return this.eroute;
    }

    public Strooiroute eroute(String eroute) {
        this.setEroute(eroute);
        return this;
    }

    public void setEroute(String eroute) {
        this.eroute = eroute;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strooiroute)) {
            return false;
        }
        return getId() != null && getId().equals(((Strooiroute) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strooiroute{" +
            "id=" + getId() +
            ", eroute='" + getEroute() + "'" +
            "}";
    }
}
