package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pas.
 */
@Entity
@Table(name = "pas")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresaanduiding")
    private String adresaanduiding;

    @Column(name = "pasnummer")
    private String pasnummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitgevoerdestortingPas")
    @JsonIgnoreProperties(value = { "bijMilieustraat", "fractieFracties", "uitgevoerdestortingPas" }, allowSetters = true)
    private Set<Storting> uitgevoerdestortingStortings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_pas__geldigvoor_milieustraat",
        joinColumns = @JoinColumn(name = "pas_id"),
        inverseJoinColumns = @JoinColumn(name = "geldigvoor_milieustraat_id")
    )
    @JsonIgnoreProperties(value = { "inzamelpuntvanFracties", "bijStortings", "geldigvoorPas" }, allowSetters = true)
    private Set<Milieustraat> geldigvoorMilieustraats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresaanduiding() {
        return this.adresaanduiding;
    }

    public Pas adresaanduiding(String adresaanduiding) {
        this.setAdresaanduiding(adresaanduiding);
        return this;
    }

    public void setAdresaanduiding(String adresaanduiding) {
        this.adresaanduiding = adresaanduiding;
    }

    public String getPasnummer() {
        return this.pasnummer;
    }

    public Pas pasnummer(String pasnummer) {
        this.setPasnummer(pasnummer);
        return this;
    }

    public void setPasnummer(String pasnummer) {
        this.pasnummer = pasnummer;
    }

    public Set<Storting> getUitgevoerdestortingStortings() {
        return this.uitgevoerdestortingStortings;
    }

    public void setUitgevoerdestortingStortings(Set<Storting> stortings) {
        if (this.uitgevoerdestortingStortings != null) {
            this.uitgevoerdestortingStortings.forEach(i -> i.setUitgevoerdestortingPas(null));
        }
        if (stortings != null) {
            stortings.forEach(i -> i.setUitgevoerdestortingPas(this));
        }
        this.uitgevoerdestortingStortings = stortings;
    }

    public Pas uitgevoerdestortingStortings(Set<Storting> stortings) {
        this.setUitgevoerdestortingStortings(stortings);
        return this;
    }

    public Pas addUitgevoerdestortingStorting(Storting storting) {
        this.uitgevoerdestortingStortings.add(storting);
        storting.setUitgevoerdestortingPas(this);
        return this;
    }

    public Pas removeUitgevoerdestortingStorting(Storting storting) {
        this.uitgevoerdestortingStortings.remove(storting);
        storting.setUitgevoerdestortingPas(null);
        return this;
    }

    public Set<Milieustraat> getGeldigvoorMilieustraats() {
        return this.geldigvoorMilieustraats;
    }

    public void setGeldigvoorMilieustraats(Set<Milieustraat> milieustraats) {
        this.geldigvoorMilieustraats = milieustraats;
    }

    public Pas geldigvoorMilieustraats(Set<Milieustraat> milieustraats) {
        this.setGeldigvoorMilieustraats(milieustraats);
        return this;
    }

    public Pas addGeldigvoorMilieustraat(Milieustraat milieustraat) {
        this.geldigvoorMilieustraats.add(milieustraat);
        return this;
    }

    public Pas removeGeldigvoorMilieustraat(Milieustraat milieustraat) {
        this.geldigvoorMilieustraats.remove(milieustraat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pas)) {
            return false;
        }
        return getId() != null && getId().equals(((Pas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pas{" +
            "id=" + getId() +
            ", adresaanduiding='" + getAdresaanduiding() + "'" +
            ", pasnummer='" + getPasnummer() + "'" +
            "}";
    }
}
