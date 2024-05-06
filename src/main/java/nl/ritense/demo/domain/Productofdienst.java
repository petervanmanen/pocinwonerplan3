package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Productofdienst.
 */
@Entity
@Table(name = "productofdienst")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Productofdienst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afhandeltijd")
    private String afhandeltijd;

    @Column(name = "ingebruik")
    private String ingebruik;

    @Column(name = "naam")
    private String naam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Productofdienst id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfhandeltijd() {
        return this.afhandeltijd;
    }

    public Productofdienst afhandeltijd(String afhandeltijd) {
        this.setAfhandeltijd(afhandeltijd);
        return this;
    }

    public void setAfhandeltijd(String afhandeltijd) {
        this.afhandeltijd = afhandeltijd;
    }

    public String getIngebruik() {
        return this.ingebruik;
    }

    public Productofdienst ingebruik(String ingebruik) {
        this.setIngebruik(ingebruik);
        return this;
    }

    public void setIngebruik(String ingebruik) {
        this.ingebruik = ingebruik;
    }

    public String getNaam() {
        return this.naam;
    }

    public Productofdienst naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Productofdienst)) {
            return false;
        }
        return getId() != null && getId().equals(((Productofdienst) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Productofdienst{" +
            "id=" + getId() +
            ", afhandeltijd='" + getAfhandeltijd() + "'" +
            ", ingebruik='" + getIngebruik() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
