package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Voertuig.
 */
@Entity
@Table(name = "voertuig")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Voertuig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kenteken")
    private String kenteken;

    @Column(name = "kleur")
    private String kleur;

    @Column(name = "land")
    private String land;

    @Column(name = "merk")
    private String merk;

    @Column(name = "type")
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftVoertuig")
    @JsonIgnoreProperties(value = { "leverancierBelprovider", "betreftVoertuig", "resulteertParkeervergunning" }, allowSetters = true)
    private Set<Parkeerrecht> betreftParkeerrechts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftVoertuig")
    @JsonIgnoreProperties(
        value = { "komtvoortuitNaheffing", "uitgevoerddoorMedewerker", "betreftVoertuig", "betreftParkeervlak" },
        allowSetters = true
    )
    private Set<Parkeerscan> betreftParkeerscans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Voertuig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKenteken() {
        return this.kenteken;
    }

    public Voertuig kenteken(String kenteken) {
        this.setKenteken(kenteken);
        return this;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Voertuig kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getLand() {
        return this.land;
    }

    public Voertuig land(String land) {
        this.setLand(land);
        return this;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getMerk() {
        return this.merk;
    }

    public Voertuig merk(String merk) {
        this.setMerk(merk);
        return this;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getType() {
        return this.type;
    }

    public Voertuig type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Parkeerrecht> getBetreftParkeerrechts() {
        return this.betreftParkeerrechts;
    }

    public void setBetreftParkeerrechts(Set<Parkeerrecht> parkeerrechts) {
        if (this.betreftParkeerrechts != null) {
            this.betreftParkeerrechts.forEach(i -> i.setBetreftVoertuig(null));
        }
        if (parkeerrechts != null) {
            parkeerrechts.forEach(i -> i.setBetreftVoertuig(this));
        }
        this.betreftParkeerrechts = parkeerrechts;
    }

    public Voertuig betreftParkeerrechts(Set<Parkeerrecht> parkeerrechts) {
        this.setBetreftParkeerrechts(parkeerrechts);
        return this;
    }

    public Voertuig addBetreftParkeerrecht(Parkeerrecht parkeerrecht) {
        this.betreftParkeerrechts.add(parkeerrecht);
        parkeerrecht.setBetreftVoertuig(this);
        return this;
    }

    public Voertuig removeBetreftParkeerrecht(Parkeerrecht parkeerrecht) {
        this.betreftParkeerrechts.remove(parkeerrecht);
        parkeerrecht.setBetreftVoertuig(null);
        return this;
    }

    public Set<Parkeerscan> getBetreftParkeerscans() {
        return this.betreftParkeerscans;
    }

    public void setBetreftParkeerscans(Set<Parkeerscan> parkeerscans) {
        if (this.betreftParkeerscans != null) {
            this.betreftParkeerscans.forEach(i -> i.setBetreftVoertuig(null));
        }
        if (parkeerscans != null) {
            parkeerscans.forEach(i -> i.setBetreftVoertuig(this));
        }
        this.betreftParkeerscans = parkeerscans;
    }

    public Voertuig betreftParkeerscans(Set<Parkeerscan> parkeerscans) {
        this.setBetreftParkeerscans(parkeerscans);
        return this;
    }

    public Voertuig addBetreftParkeerscan(Parkeerscan parkeerscan) {
        this.betreftParkeerscans.add(parkeerscan);
        parkeerscan.setBetreftVoertuig(this);
        return this;
    }

    public Voertuig removeBetreftParkeerscan(Parkeerscan parkeerscan) {
        this.betreftParkeerscans.remove(parkeerscan);
        parkeerscan.setBetreftVoertuig(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voertuig)) {
            return false;
        }
        return getId() != null && getId().equals(((Voertuig) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voertuig{" +
            "id=" + getId() +
            ", kenteken='" + getKenteken() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", land='" + getLand() + "'" +
            ", merk='" + getMerk() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
