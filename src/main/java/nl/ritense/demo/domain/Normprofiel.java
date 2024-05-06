package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Normprofiel.
 */
@Entity
@Table(name = "normprofiel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Normprofiel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "code", length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "omschrijving", length = 100)
    private String omschrijving;

    @Size(max = 10)
    @Column(name = "schaal", length = 10)
    private String schaal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Normprofiel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Normprofiel code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Normprofiel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getSchaal() {
        return this.schaal;
    }

    public Normprofiel schaal(String schaal) {
        this.setSchaal(schaal);
        return this;
    }

    public void setSchaal(String schaal) {
        this.schaal = schaal;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Normprofiel)) {
            return false;
        }
        return getId() != null && getId().equals(((Normprofiel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Normprofiel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", schaal='" + getSchaal() + "'" +
            "}";
    }
}
