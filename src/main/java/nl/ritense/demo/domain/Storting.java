package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Storting.
 */
@Entity
@Table(name = "storting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Storting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumtijd")
    private String datumtijd;

    @Column(name = "gewicht")
    private String gewicht;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inzamelpuntvanFracties", "bijStortings", "geldigvoorPas" }, allowSetters = true)
    private Milieustraat bijMilieustraat;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_storting__fractie_fractie",
        joinColumns = @JoinColumn(name = "storting_id"),
        inverseJoinColumns = @JoinColumn(name = "fractie_fractie_id")
    )
    @JsonIgnoreProperties(
        value = { "geschiktvoorContainers", "betreftMeldings", "inzamelpuntvanMilieustraats", "fractieStortings" },
        allowSetters = true
    )
    private Set<Fractie> fractieFracties = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "uitgevoerdestortingStortings", "geldigvoorMilieustraats" }, allowSetters = true)
    private Pas uitgevoerdestortingPas;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Storting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumtijd() {
        return this.datumtijd;
    }

    public Storting datumtijd(String datumtijd) {
        this.setDatumtijd(datumtijd);
        return this;
    }

    public void setDatumtijd(String datumtijd) {
        this.datumtijd = datumtijd;
    }

    public String getGewicht() {
        return this.gewicht;
    }

    public Storting gewicht(String gewicht) {
        this.setGewicht(gewicht);
        return this;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public Milieustraat getBijMilieustraat() {
        return this.bijMilieustraat;
    }

    public void setBijMilieustraat(Milieustraat milieustraat) {
        this.bijMilieustraat = milieustraat;
    }

    public Storting bijMilieustraat(Milieustraat milieustraat) {
        this.setBijMilieustraat(milieustraat);
        return this;
    }

    public Set<Fractie> getFractieFracties() {
        return this.fractieFracties;
    }

    public void setFractieFracties(Set<Fractie> fracties) {
        this.fractieFracties = fracties;
    }

    public Storting fractieFracties(Set<Fractie> fracties) {
        this.setFractieFracties(fracties);
        return this;
    }

    public Storting addFractieFractie(Fractie fractie) {
        this.fractieFracties.add(fractie);
        return this;
    }

    public Storting removeFractieFractie(Fractie fractie) {
        this.fractieFracties.remove(fractie);
        return this;
    }

    public Pas getUitgevoerdestortingPas() {
        return this.uitgevoerdestortingPas;
    }

    public void setUitgevoerdestortingPas(Pas pas) {
        this.uitgevoerdestortingPas = pas;
    }

    public Storting uitgevoerdestortingPas(Pas pas) {
        this.setUitgevoerdestortingPas(pas);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Storting)) {
            return false;
        }
        return getId() != null && getId().equals(((Storting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Storting{" +
            "id=" + getId() +
            ", datumtijd='" + getDatumtijd() + "'" +
            ", gewicht='" + getGewicht() + "'" +
            "}";
    }
}
