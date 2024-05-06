package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Parkeervlak.
 */
@Entity
@Table(name = "parkeervlak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parkeervlak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "coordinaten")
    private String coordinaten;

    @Column(name = "doelgroep")
    private String doelgroep;

    @Column(name = "fiscaal")
    private Boolean fiscaal;

    @Column(name = "plaats")
    private String plaats;

    @Column(name = "vlakid")
    private String vlakid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bevatParkeervlaks" }, allowSetters = true)
    private Straatsectie bevatStraatsectie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftParkeervlak")
    @JsonIgnoreProperties(
        value = { "komtvoortuitNaheffing", "uitgevoerddoorMedewerker", "betreftVoertuig", "betreftParkeervlak" },
        allowSetters = true
    )
    private Set<Parkeerscan> betreftParkeerscans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parkeervlak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Parkeervlak aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getCoordinaten() {
        return this.coordinaten;
    }

    public Parkeervlak coordinaten(String coordinaten) {
        this.setCoordinaten(coordinaten);
        return this;
    }

    public void setCoordinaten(String coordinaten) {
        this.coordinaten = coordinaten;
    }

    public String getDoelgroep() {
        return this.doelgroep;
    }

    public Parkeervlak doelgroep(String doelgroep) {
        this.setDoelgroep(doelgroep);
        return this;
    }

    public void setDoelgroep(String doelgroep) {
        this.doelgroep = doelgroep;
    }

    public Boolean getFiscaal() {
        return this.fiscaal;
    }

    public Parkeervlak fiscaal(Boolean fiscaal) {
        this.setFiscaal(fiscaal);
        return this;
    }

    public void setFiscaal(Boolean fiscaal) {
        this.fiscaal = fiscaal;
    }

    public String getPlaats() {
        return this.plaats;
    }

    public Parkeervlak plaats(String plaats) {
        this.setPlaats(plaats);
        return this;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getVlakid() {
        return this.vlakid;
    }

    public Parkeervlak vlakid(String vlakid) {
        this.setVlakid(vlakid);
        return this;
    }

    public void setVlakid(String vlakid) {
        this.vlakid = vlakid;
    }

    public Straatsectie getBevatStraatsectie() {
        return this.bevatStraatsectie;
    }

    public void setBevatStraatsectie(Straatsectie straatsectie) {
        this.bevatStraatsectie = straatsectie;
    }

    public Parkeervlak bevatStraatsectie(Straatsectie straatsectie) {
        this.setBevatStraatsectie(straatsectie);
        return this;
    }

    public Set<Parkeerscan> getBetreftParkeerscans() {
        return this.betreftParkeerscans;
    }

    public void setBetreftParkeerscans(Set<Parkeerscan> parkeerscans) {
        if (this.betreftParkeerscans != null) {
            this.betreftParkeerscans.forEach(i -> i.setBetreftParkeervlak(null));
        }
        if (parkeerscans != null) {
            parkeerscans.forEach(i -> i.setBetreftParkeervlak(this));
        }
        this.betreftParkeerscans = parkeerscans;
    }

    public Parkeervlak betreftParkeerscans(Set<Parkeerscan> parkeerscans) {
        this.setBetreftParkeerscans(parkeerscans);
        return this;
    }

    public Parkeervlak addBetreftParkeerscan(Parkeerscan parkeerscan) {
        this.betreftParkeerscans.add(parkeerscan);
        parkeerscan.setBetreftParkeervlak(this);
        return this;
    }

    public Parkeervlak removeBetreftParkeerscan(Parkeerscan parkeerscan) {
        this.betreftParkeerscans.remove(parkeerscan);
        parkeerscan.setBetreftParkeervlak(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parkeervlak)) {
            return false;
        }
        return getId() != null && getId().equals(((Parkeervlak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parkeervlak{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", coordinaten='" + getCoordinaten() + "'" +
            ", doelgroep='" + getDoelgroep() + "'" +
            ", fiscaal='" + getFiscaal() + "'" +
            ", plaats='" + getPlaats() + "'" +
            ", vlakid='" + getVlakid() + "'" +
            "}";
    }
}
