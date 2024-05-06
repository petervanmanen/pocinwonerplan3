package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Valuta.
 */
@Entity
@Table(name = "valuta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Valuta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private String datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private String datumeindegeldigheid;

    @Column(name = "naam")
    private String naam;

    @Column(name = "valutacode")
    private String valutacode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Valuta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Valuta datumbegingeldigheid(String datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(String datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public String getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Valuta datumeindegeldigheid(String datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(String datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getNaam() {
        return this.naam;
    }

    public Valuta naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getValutacode() {
        return this.valutacode;
    }

    public Valuta valutacode(String valutacode) {
        this.setValutacode(valutacode);
        return this;
    }

    public void setValutacode(String valutacode) {
        this.valutacode = valutacode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Valuta)) {
            return false;
        }
        return getId() != null && getId().equals(((Valuta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Valuta{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", naam='" + getNaam() + "'" +
            ", valutacode='" + getValutacode() + "'" +
            "}";
    }
}
