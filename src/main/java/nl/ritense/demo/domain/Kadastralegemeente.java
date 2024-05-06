package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Kadastralegemeente.
 */
@Entity
@Table(name = "kadastralegemeente")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kadastralegemeente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidkadastralegemeente")
    private LocalDate datumbegingeldigheidkadastralegemeente;

    @Column(name = "datumeindegeldigheidkadastralegemeente")
    private LocalDate datumeindegeldigheidkadastralegemeente;

    @Column(name = "kadastralegemeentecode")
    private String kadastralegemeentecode;

    @Column(name = "naam")
    private String naam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kadastralegemeente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidkadastralegemeente() {
        return this.datumbegingeldigheidkadastralegemeente;
    }

    public Kadastralegemeente datumbegingeldigheidkadastralegemeente(LocalDate datumbegingeldigheidkadastralegemeente) {
        this.setDatumbegingeldigheidkadastralegemeente(datumbegingeldigheidkadastralegemeente);
        return this;
    }

    public void setDatumbegingeldigheidkadastralegemeente(LocalDate datumbegingeldigheidkadastralegemeente) {
        this.datumbegingeldigheidkadastralegemeente = datumbegingeldigheidkadastralegemeente;
    }

    public LocalDate getDatumeindegeldigheidkadastralegemeente() {
        return this.datumeindegeldigheidkadastralegemeente;
    }

    public Kadastralegemeente datumeindegeldigheidkadastralegemeente(LocalDate datumeindegeldigheidkadastralegemeente) {
        this.setDatumeindegeldigheidkadastralegemeente(datumeindegeldigheidkadastralegemeente);
        return this;
    }

    public void setDatumeindegeldigheidkadastralegemeente(LocalDate datumeindegeldigheidkadastralegemeente) {
        this.datumeindegeldigheidkadastralegemeente = datumeindegeldigheidkadastralegemeente;
    }

    public String getKadastralegemeentecode() {
        return this.kadastralegemeentecode;
    }

    public Kadastralegemeente kadastralegemeentecode(String kadastralegemeentecode) {
        this.setKadastralegemeentecode(kadastralegemeentecode);
        return this;
    }

    public void setKadastralegemeentecode(String kadastralegemeentecode) {
        this.kadastralegemeentecode = kadastralegemeentecode;
    }

    public String getNaam() {
        return this.naam;
    }

    public Kadastralegemeente naam(String naam) {
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
        if (!(o instanceof Kadastralegemeente)) {
            return false;
        }
        return getId() != null && getId().equals(((Kadastralegemeente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kadastralegemeente{" +
            "id=" + getId() +
            ", datumbegingeldigheidkadastralegemeente='" + getDatumbegingeldigheidkadastralegemeente() + "'" +
            ", datumeindegeldigheidkadastralegemeente='" + getDatumeindegeldigheidkadastralegemeente() + "'" +
            ", kadastralegemeentecode='" + getKadastralegemeentecode() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
