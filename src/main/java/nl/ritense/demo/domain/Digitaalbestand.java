package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Digitaalbestand.
 */
@Entity
@Table(name = "digitaalbestand")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Digitaalbestand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "blob")
    private String blob;

    @Size(max = 20)
    @Column(name = "mimetype", length = 20)
    private String mimetype;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Digitaalbestand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlob() {
        return this.blob;
    }

    public Digitaalbestand blob(String blob) {
        this.setBlob(blob);
        return this;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public String getMimetype() {
        return this.mimetype;
    }

    public Digitaalbestand mimetype(String mimetype) {
        this.setMimetype(mimetype);
        return this;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getNaam() {
        return this.naam;
    }

    public Digitaalbestand naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Digitaalbestand omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Digitaalbestand)) {
            return false;
        }
        return getId() != null && getId().equals(((Digitaalbestand) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Digitaalbestand{" +
            "id=" + getId() +
            ", blob='" + getBlob() + "'" +
            ", mimetype='" + getMimetype() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
