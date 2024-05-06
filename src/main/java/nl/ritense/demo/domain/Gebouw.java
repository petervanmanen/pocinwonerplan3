package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Gebouw.
 */
@Entity
@Table(name = "gebouw")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebouw implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "aantaladressen")
    private String aantaladressen;

    @Column(name = "aantalkamers")
    private String aantalkamers;

    @Column(name = "aardgasloos")
    private Boolean aardgasloos;

    @Column(name = "duurzaam")
    private Boolean duurzaam;

    @Column(name = "energielabel")
    private String energielabel;

    @Column(name = "natuurinclusief")
    private Boolean natuurinclusief;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "regenwater")
    private Boolean regenwater;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "bestaatuitGebouws",
            "binnenprogrammaProgramma",
            "isprojectleidervanProjectleider",
            "betrekkingopOmgevingsvergunnings",
            "heeftProjectontwikkelaars",
        },
        allowSetters = true
    )
    private Plan bestaatuitPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebouw id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Gebouw aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getAantaladressen() {
        return this.aantaladressen;
    }

    public Gebouw aantaladressen(String aantaladressen) {
        this.setAantaladressen(aantaladressen);
        return this;
    }

    public void setAantaladressen(String aantaladressen) {
        this.aantaladressen = aantaladressen;
    }

    public String getAantalkamers() {
        return this.aantalkamers;
    }

    public Gebouw aantalkamers(String aantalkamers) {
        this.setAantalkamers(aantalkamers);
        return this;
    }

    public void setAantalkamers(String aantalkamers) {
        this.aantalkamers = aantalkamers;
    }

    public Boolean getAardgasloos() {
        return this.aardgasloos;
    }

    public Gebouw aardgasloos(Boolean aardgasloos) {
        this.setAardgasloos(aardgasloos);
        return this;
    }

    public void setAardgasloos(Boolean aardgasloos) {
        this.aardgasloos = aardgasloos;
    }

    public Boolean getDuurzaam() {
        return this.duurzaam;
    }

    public Gebouw duurzaam(Boolean duurzaam) {
        this.setDuurzaam(duurzaam);
        return this;
    }

    public void setDuurzaam(Boolean duurzaam) {
        this.duurzaam = duurzaam;
    }

    public String getEnergielabel() {
        return this.energielabel;
    }

    public Gebouw energielabel(String energielabel) {
        this.setEnergielabel(energielabel);
        return this;
    }

    public void setEnergielabel(String energielabel) {
        this.energielabel = energielabel;
    }

    public Boolean getNatuurinclusief() {
        return this.natuurinclusief;
    }

    public Gebouw natuurinclusief(Boolean natuurinclusief) {
        this.setNatuurinclusief(natuurinclusief);
        return this;
    }

    public void setNatuurinclusief(Boolean natuurinclusief) {
        this.natuurinclusief = natuurinclusief;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Gebouw oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public Boolean getRegenwater() {
        return this.regenwater;
    }

    public Gebouw regenwater(Boolean regenwater) {
        this.setRegenwater(regenwater);
        return this;
    }

    public void setRegenwater(Boolean regenwater) {
        this.regenwater = regenwater;
    }

    public Plan getBestaatuitPlan() {
        return this.bestaatuitPlan;
    }

    public void setBestaatuitPlan(Plan plan) {
        this.bestaatuitPlan = plan;
    }

    public Gebouw bestaatuitPlan(Plan plan) {
        this.setBestaatuitPlan(plan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebouw)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebouw) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebouw{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", aantaladressen='" + getAantaladressen() + "'" +
            ", aantalkamers='" + getAantalkamers() + "'" +
            ", aardgasloos='" + getAardgasloos() + "'" +
            ", duurzaam='" + getDuurzaam() + "'" +
            ", energielabel='" + getEnergielabel() + "'" +
            ", natuurinclusief='" + getNatuurinclusief() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", regenwater='" + getRegenwater() + "'" +
            "}";
    }
}
