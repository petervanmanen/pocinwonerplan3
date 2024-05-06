package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Elog.
 */
@Entity
@Table(name = "elog")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Elog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "korteomschrijving")
    private String korteomschrijving;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "tijd")
    private String tijd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Elog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorteomschrijving() {
        return this.korteomschrijving;
    }

    public Elog korteomschrijving(String korteomschrijving) {
        this.setKorteomschrijving(korteomschrijving);
        return this;
    }

    public void setKorteomschrijving(String korteomschrijving) {
        this.korteomschrijving = korteomschrijving;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Elog omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTijd() {
        return this.tijd;
    }

    public Elog tijd(String tijd) {
        this.setTijd(tijd);
        return this;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Elog)) {
            return false;
        }
        return getId() != null && getId().equals(((Elog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Elog{" +
            "id=" + getId() +
            ", korteomschrijving='" + getKorteomschrijving() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", tijd='" + getTijd() + "'" +
            "}";
    }
}
