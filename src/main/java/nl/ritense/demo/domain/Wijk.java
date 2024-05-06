package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wijk.
 */
@Entity
@Table(name = "wijk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wijk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "status")
    private String status;

    @Column(name = "versie")
    private String versie;

    @Column(name = "wijkcode")
    private String wijkcode;

    @Column(name = "wijknaam")
    private String wijknaam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bedientWijk")
    @JsonIgnoreProperties(
        value = { "inspectierapportDocuments", "isgevestigdinVerblijfsobject", "bedientWijk", "heeftBelijnings", "heeftSportmateriaals" },
        allowSetters = true
    )
    private Set<Binnenlocatie> bedientBinnenlocaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenWijks")
    @JsonIgnoreProperties(value = { "ligtinBuurts", "valtbinnenWijks", "binnenSchouwrondes" }, allowSetters = true)
    private Set<Areaal> valtbinnenAreaals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wijk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Wijk datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Wijk datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Wijk datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Wijk datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Wijk geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Wijk geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Wijk identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Wijk inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getStatus() {
        return this.status;
    }

    public Wijk status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersie() {
        return this.versie;
    }

    public Wijk versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public String getWijkcode() {
        return this.wijkcode;
    }

    public Wijk wijkcode(String wijkcode) {
        this.setWijkcode(wijkcode);
        return this;
    }

    public void setWijkcode(String wijkcode) {
        this.wijkcode = wijkcode;
    }

    public String getWijknaam() {
        return this.wijknaam;
    }

    public Wijk wijknaam(String wijknaam) {
        this.setWijknaam(wijknaam);
        return this;
    }

    public void setWijknaam(String wijknaam) {
        this.wijknaam = wijknaam;
    }

    public Set<Binnenlocatie> getBedientBinnenlocaties() {
        return this.bedientBinnenlocaties;
    }

    public void setBedientBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        if (this.bedientBinnenlocaties != null) {
            this.bedientBinnenlocaties.forEach(i -> i.setBedientWijk(null));
        }
        if (binnenlocaties != null) {
            binnenlocaties.forEach(i -> i.setBedientWijk(this));
        }
        this.bedientBinnenlocaties = binnenlocaties;
    }

    public Wijk bedientBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        this.setBedientBinnenlocaties(binnenlocaties);
        return this;
    }

    public Wijk addBedientBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.bedientBinnenlocaties.add(binnenlocatie);
        binnenlocatie.setBedientWijk(this);
        return this;
    }

    public Wijk removeBedientBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.bedientBinnenlocaties.remove(binnenlocatie);
        binnenlocatie.setBedientWijk(null);
        return this;
    }

    public Set<Areaal> getValtbinnenAreaals() {
        return this.valtbinnenAreaals;
    }

    public void setValtbinnenAreaals(Set<Areaal> areaals) {
        if (this.valtbinnenAreaals != null) {
            this.valtbinnenAreaals.forEach(i -> i.removeValtbinnenWijk(this));
        }
        if (areaals != null) {
            areaals.forEach(i -> i.addValtbinnenWijk(this));
        }
        this.valtbinnenAreaals = areaals;
    }

    public Wijk valtbinnenAreaals(Set<Areaal> areaals) {
        this.setValtbinnenAreaals(areaals);
        return this;
    }

    public Wijk addValtbinnenAreaal(Areaal areaal) {
        this.valtbinnenAreaals.add(areaal);
        areaal.getValtbinnenWijks().add(this);
        return this;
    }

    public Wijk removeValtbinnenAreaal(Areaal areaal) {
        this.valtbinnenAreaals.remove(areaal);
        areaal.getValtbinnenWijks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wijk)) {
            return false;
        }
        return getId() != null && getId().equals(((Wijk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wijk{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", status='" + getStatus() + "'" +
            ", versie='" + getVersie() + "'" +
            ", wijkcode='" + getWijkcode() + "'" +
            ", wijknaam='" + getWijknaam() + "'" +
            "}";
    }
}
