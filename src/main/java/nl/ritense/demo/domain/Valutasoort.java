package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Valutasoort.
 */
@Entity
@Table(name = "valutasoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Valutasoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidvalutasoort")
    private LocalDate datumbegingeldigheidvalutasoort;

    @Column(name = "datumeindegeldigheidvalutasoort")
    private LocalDate datumeindegeldigheidvalutasoort;

    @Column(name = "naamvaluta")
    private String naamvaluta;

    @Column(name = "valutacode")
    private String valutacode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Valutasoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidvalutasoort() {
        return this.datumbegingeldigheidvalutasoort;
    }

    public Valutasoort datumbegingeldigheidvalutasoort(LocalDate datumbegingeldigheidvalutasoort) {
        this.setDatumbegingeldigheidvalutasoort(datumbegingeldigheidvalutasoort);
        return this;
    }

    public void setDatumbegingeldigheidvalutasoort(LocalDate datumbegingeldigheidvalutasoort) {
        this.datumbegingeldigheidvalutasoort = datumbegingeldigheidvalutasoort;
    }

    public LocalDate getDatumeindegeldigheidvalutasoort() {
        return this.datumeindegeldigheidvalutasoort;
    }

    public Valutasoort datumeindegeldigheidvalutasoort(LocalDate datumeindegeldigheidvalutasoort) {
        this.setDatumeindegeldigheidvalutasoort(datumeindegeldigheidvalutasoort);
        return this;
    }

    public void setDatumeindegeldigheidvalutasoort(LocalDate datumeindegeldigheidvalutasoort) {
        this.datumeindegeldigheidvalutasoort = datumeindegeldigheidvalutasoort;
    }

    public String getNaamvaluta() {
        return this.naamvaluta;
    }

    public Valutasoort naamvaluta(String naamvaluta) {
        this.setNaamvaluta(naamvaluta);
        return this;
    }

    public void setNaamvaluta(String naamvaluta) {
        this.naamvaluta = naamvaluta;
    }

    public String getValutacode() {
        return this.valutacode;
    }

    public Valutasoort valutacode(String valutacode) {
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
        if (!(o instanceof Valutasoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Valutasoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Valutasoort{" +
            "id=" + getId() +
            ", datumbegingeldigheidvalutasoort='" + getDatumbegingeldigheidvalutasoort() + "'" +
            ", datumeindegeldigheidvalutasoort='" + getDatumeindegeldigheidvalutasoort() + "'" +
            ", naamvaluta='" + getNaamvaluta() + "'" +
            ", valutacode='" + getValutacode() + "'" +
            "}";
    }
}
