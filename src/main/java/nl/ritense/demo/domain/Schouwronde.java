package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Schouwronde.
 */
@Entity
@Table(name = "schouwronde")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Schouwronde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSchouwronde")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> heeftMeldings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "ingevoerddoorStremmings",
            "gewijzigddoorStremmings",
            "voertuitSchouwrondes",
            "aanvragerSubsidies",
            "isverantwoordelijkevoorZaaktypes",
            "geleverdviaLeverancier",
            "isBetrokkene",
            "uitgevoerddoorParkeerscans",
            "melderMeldings",
            "uitvoerderMeldings",
            "auteurNotities",
            "behandelaarSubsidies",
            "procesleiderAanbestedings",
            "inhuurGunnings",
            "metBalieafspraaks",
            "isgevoerddoorKlantcontacts",
            "rollenApplicaties",
            "afhandelendmedewerkerZaaks",
        },
        allowSetters = true
    )
    private Medewerker voertuitMedewerker;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "binnenSchouwrondes")
    @JsonIgnoreProperties(value = { "ligtinBuurts", "valtbinnenWijks", "binnenSchouwrondes" }, allowSetters = true)
    private Set<Areaal> binnenAreaals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Schouwronde id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Melding> getHeeftMeldings() {
        return this.heeftMeldings;
    }

    public void setHeeftMeldings(Set<Melding> meldings) {
        if (this.heeftMeldings != null) {
            this.heeftMeldings.forEach(i -> i.setHeeftSchouwronde(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setHeeftSchouwronde(this));
        }
        this.heeftMeldings = meldings;
    }

    public Schouwronde heeftMeldings(Set<Melding> meldings) {
        this.setHeeftMeldings(meldings);
        return this;
    }

    public Schouwronde addHeeftMelding(Melding melding) {
        this.heeftMeldings.add(melding);
        melding.setHeeftSchouwronde(this);
        return this;
    }

    public Schouwronde removeHeeftMelding(Melding melding) {
        this.heeftMeldings.remove(melding);
        melding.setHeeftSchouwronde(null);
        return this;
    }

    public Medewerker getVoertuitMedewerker() {
        return this.voertuitMedewerker;
    }

    public void setVoertuitMedewerker(Medewerker medewerker) {
        this.voertuitMedewerker = medewerker;
    }

    public Schouwronde voertuitMedewerker(Medewerker medewerker) {
        this.setVoertuitMedewerker(medewerker);
        return this;
    }

    public Set<Areaal> getBinnenAreaals() {
        return this.binnenAreaals;
    }

    public void setBinnenAreaals(Set<Areaal> areaals) {
        if (this.binnenAreaals != null) {
            this.binnenAreaals.forEach(i -> i.removeBinnenSchouwronde(this));
        }
        if (areaals != null) {
            areaals.forEach(i -> i.addBinnenSchouwronde(this));
        }
        this.binnenAreaals = areaals;
    }

    public Schouwronde binnenAreaals(Set<Areaal> areaals) {
        this.setBinnenAreaals(areaals);
        return this;
    }

    public Schouwronde addBinnenAreaal(Areaal areaal) {
        this.binnenAreaals.add(areaal);
        areaal.getBinnenSchouwrondes().add(this);
        return this;
    }

    public Schouwronde removeBinnenAreaal(Areaal areaal) {
        this.binnenAreaals.remove(areaal);
        areaal.getBinnenSchouwrondes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schouwronde)) {
            return false;
        }
        return getId() != null && getId().equals(((Schouwronde) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Schouwronde{" +
            "id=" + getId() +
            "}";
    }
}
