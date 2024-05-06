package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Milieustraat.
 */
@Entity
@Table(name = "milieustraat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Milieustraat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresaanduiding")
    private String adresaanduiding;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_milieustraat__inzamelpuntvan_fractie",
        joinColumns = @JoinColumn(name = "milieustraat_id"),
        inverseJoinColumns = @JoinColumn(name = "inzamelpuntvan_fractie_id")
    )
    @JsonIgnoreProperties(
        value = { "geschiktvoorContainers", "betreftMeldings", "inzamelpuntvanMilieustraats", "fractieStortings" },
        allowSetters = true
    )
    private Set<Fractie> inzamelpuntvanFracties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bijMilieustraat")
    @JsonIgnoreProperties(value = { "bijMilieustraat", "fractieFracties", "uitgevoerdestortingPas" }, allowSetters = true)
    private Set<Storting> bijStortings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "geldigvoorMilieustraats")
    @JsonIgnoreProperties(value = { "uitgevoerdestortingStortings", "geldigvoorMilieustraats" }, allowSetters = true)
    private Set<Pas> geldigvoorPas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Milieustraat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresaanduiding() {
        return this.adresaanduiding;
    }

    public Milieustraat adresaanduiding(String adresaanduiding) {
        this.setAdresaanduiding(adresaanduiding);
        return this;
    }

    public void setAdresaanduiding(String adresaanduiding) {
        this.adresaanduiding = adresaanduiding;
    }

    public String getNaam() {
        return this.naam;
    }

    public Milieustraat naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Milieustraat omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Fractie> getInzamelpuntvanFracties() {
        return this.inzamelpuntvanFracties;
    }

    public void setInzamelpuntvanFracties(Set<Fractie> fracties) {
        this.inzamelpuntvanFracties = fracties;
    }

    public Milieustraat inzamelpuntvanFracties(Set<Fractie> fracties) {
        this.setInzamelpuntvanFracties(fracties);
        return this;
    }

    public Milieustraat addInzamelpuntvanFractie(Fractie fractie) {
        this.inzamelpuntvanFracties.add(fractie);
        return this;
    }

    public Milieustraat removeInzamelpuntvanFractie(Fractie fractie) {
        this.inzamelpuntvanFracties.remove(fractie);
        return this;
    }

    public Set<Storting> getBijStortings() {
        return this.bijStortings;
    }

    public void setBijStortings(Set<Storting> stortings) {
        if (this.bijStortings != null) {
            this.bijStortings.forEach(i -> i.setBijMilieustraat(null));
        }
        if (stortings != null) {
            stortings.forEach(i -> i.setBijMilieustraat(this));
        }
        this.bijStortings = stortings;
    }

    public Milieustraat bijStortings(Set<Storting> stortings) {
        this.setBijStortings(stortings);
        return this;
    }

    public Milieustraat addBijStorting(Storting storting) {
        this.bijStortings.add(storting);
        storting.setBijMilieustraat(this);
        return this;
    }

    public Milieustraat removeBijStorting(Storting storting) {
        this.bijStortings.remove(storting);
        storting.setBijMilieustraat(null);
        return this;
    }

    public Set<Pas> getGeldigvoorPas() {
        return this.geldigvoorPas;
    }

    public void setGeldigvoorPas(Set<Pas> pas) {
        if (this.geldigvoorPas != null) {
            this.geldigvoorPas.forEach(i -> i.removeGeldigvoorMilieustraat(this));
        }
        if (pas != null) {
            pas.forEach(i -> i.addGeldigvoorMilieustraat(this));
        }
        this.geldigvoorPas = pas;
    }

    public Milieustraat geldigvoorPas(Set<Pas> pas) {
        this.setGeldigvoorPas(pas);
        return this;
    }

    public Milieustraat addGeldigvoorPas(Pas pas) {
        this.geldigvoorPas.add(pas);
        pas.getGeldigvoorMilieustraats().add(this);
        return this;
    }

    public Milieustraat removeGeldigvoorPas(Pas pas) {
        this.geldigvoorPas.remove(pas);
        pas.getGeldigvoorMilieustraats().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Milieustraat)) {
            return false;
        }
        return getId() != null && getId().equals(((Milieustraat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Milieustraat{" +
            "id=" + getId() +
            ", adresaanduiding='" + getAdresaanduiding() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
