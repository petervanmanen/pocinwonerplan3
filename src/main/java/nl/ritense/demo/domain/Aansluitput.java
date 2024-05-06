package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Aansluitput.
 */
@Entity
@Table(name = "aansluitput")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aansluitput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aansluitpunt")
    private String aansluitpunt;

    @Column(name = "risicogebied")
    private String risicogebied;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aansluitput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAansluitpunt() {
        return this.aansluitpunt;
    }

    public Aansluitput aansluitpunt(String aansluitpunt) {
        this.setAansluitpunt(aansluitpunt);
        return this;
    }

    public void setAansluitpunt(String aansluitpunt) {
        this.aansluitpunt = aansluitpunt;
    }

    public String getRisicogebied() {
        return this.risicogebied;
    }

    public Aansluitput risicogebied(String risicogebied) {
        this.setRisicogebied(risicogebied);
        return this;
    }

    public void setRisicogebied(String risicogebied) {
        this.risicogebied = risicogebied;
    }

    public String getType() {
        return this.type;
    }

    public Aansluitput type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aansluitput)) {
            return false;
        }
        return getId() != null && getId().equals(((Aansluitput) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aansluitput{" +
            "id=" + getId() +
            ", aansluitpunt='" + getAansluitpunt() + "'" +
            ", risicogebied='" + getRisicogebied() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
