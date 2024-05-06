package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Verblijfsobject.
 */
@Entity
@Table(name = "verblijfsobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verblijfsobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalkamers")
    private String aantalkamers;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "documentdatum")
    private LocalDate documentdatum;

    @Column(name = "documentnr")
    private String documentnr;

    @Column(name = "gebruiksdoel")
    private String gebruiksdoel;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "hoogstebouwlaagverblijfsobject")
    private String hoogstebouwlaagverblijfsobject;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "laagstebouwlaagverblijfsobject")
    private String laagstebouwlaagverblijfsobject;

    @Column(name = "ontsluitingverdieping")
    private String ontsluitingverdieping;

    @Column(name = "soortwoonobject")
    private String soortwoonobject;

    @Column(name = "status")
    private String status;

    @Column(name = "toegangbouwlaagverblijfsobject")
    private String toegangbouwlaagverblijfsobject;

    @Column(name = "versie")
    private String versie;

    @JsonIgnoreProperties(
        value = {
            "betreftPand",
            "bestaatuitBouwdeels",
            "betreftInspecties",
            "heeftVerblijfsobject",
            "heeftPand",
            "heeftKostenplaats",
            "betreftWerkbons",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Vastgoedobject heeftVastgoedobject;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_verblijfsobject__maaktdeeluitvan_pand",
        joinColumns = @JoinColumn(name = "verblijfsobject_id"),
        inverseJoinColumns = @JoinColumn(name = "maaktdeeluitvan_pand_id")
    )
    @JsonIgnoreProperties(
        value = { "heeftVastgoedobject", "zonderverblijfsobjectligtinBuurt", "betreftVastgoedobject", "maaktdeeluitvanVerblijfsobjects" },
        allowSetters = true
    )
    private Set<Pand> maaktdeeluitvanPands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isgevestigdinVerblijfsobject")
    @JsonIgnoreProperties(
        value = { "inspectierapportDocuments", "isgevestigdinVerblijfsobject", "bedientWijk", "heeftBelijnings", "heeftSportmateriaals" },
        allowSetters = true
    )
    private Set<Binnenlocatie> isgevestigdinBinnenlocaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verblijfsobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalkamers() {
        return this.aantalkamers;
    }

    public Verblijfsobject aantalkamers(String aantalkamers) {
        this.setAantalkamers(aantalkamers);
        return this;
    }

    public void setAantalkamers(String aantalkamers) {
        this.aantalkamers = aantalkamers;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Verblijfsobject datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Verblijfsobject datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Verblijfsobject datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Verblijfsobject datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public LocalDate getDocumentdatum() {
        return this.documentdatum;
    }

    public Verblijfsobject documentdatum(LocalDate documentdatum) {
        this.setDocumentdatum(documentdatum);
        return this;
    }

    public void setDocumentdatum(LocalDate documentdatum) {
        this.documentdatum = documentdatum;
    }

    public String getDocumentnr() {
        return this.documentnr;
    }

    public Verblijfsobject documentnr(String documentnr) {
        this.setDocumentnr(documentnr);
        return this;
    }

    public void setDocumentnr(String documentnr) {
        this.documentnr = documentnr;
    }

    public String getGebruiksdoel() {
        return this.gebruiksdoel;
    }

    public Verblijfsobject gebruiksdoel(String gebruiksdoel) {
        this.setGebruiksdoel(gebruiksdoel);
        return this;
    }

    public void setGebruiksdoel(String gebruiksdoel) {
        this.gebruiksdoel = gebruiksdoel;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Verblijfsobject geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Verblijfsobject geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getHoogstebouwlaagverblijfsobject() {
        return this.hoogstebouwlaagverblijfsobject;
    }

    public Verblijfsobject hoogstebouwlaagverblijfsobject(String hoogstebouwlaagverblijfsobject) {
        this.setHoogstebouwlaagverblijfsobject(hoogstebouwlaagverblijfsobject);
        return this;
    }

    public void setHoogstebouwlaagverblijfsobject(String hoogstebouwlaagverblijfsobject) {
        this.hoogstebouwlaagverblijfsobject = hoogstebouwlaagverblijfsobject;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Verblijfsobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Verblijfsobject inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getLaagstebouwlaagverblijfsobject() {
        return this.laagstebouwlaagverblijfsobject;
    }

    public Verblijfsobject laagstebouwlaagverblijfsobject(String laagstebouwlaagverblijfsobject) {
        this.setLaagstebouwlaagverblijfsobject(laagstebouwlaagverblijfsobject);
        return this;
    }

    public void setLaagstebouwlaagverblijfsobject(String laagstebouwlaagverblijfsobject) {
        this.laagstebouwlaagverblijfsobject = laagstebouwlaagverblijfsobject;
    }

    public String getOntsluitingverdieping() {
        return this.ontsluitingverdieping;
    }

    public Verblijfsobject ontsluitingverdieping(String ontsluitingverdieping) {
        this.setOntsluitingverdieping(ontsluitingverdieping);
        return this;
    }

    public void setOntsluitingverdieping(String ontsluitingverdieping) {
        this.ontsluitingverdieping = ontsluitingverdieping;
    }

    public String getSoortwoonobject() {
        return this.soortwoonobject;
    }

    public Verblijfsobject soortwoonobject(String soortwoonobject) {
        this.setSoortwoonobject(soortwoonobject);
        return this;
    }

    public void setSoortwoonobject(String soortwoonobject) {
        this.soortwoonobject = soortwoonobject;
    }

    public String getStatus() {
        return this.status;
    }

    public Verblijfsobject status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToegangbouwlaagverblijfsobject() {
        return this.toegangbouwlaagverblijfsobject;
    }

    public Verblijfsobject toegangbouwlaagverblijfsobject(String toegangbouwlaagverblijfsobject) {
        this.setToegangbouwlaagverblijfsobject(toegangbouwlaagverblijfsobject);
        return this;
    }

    public void setToegangbouwlaagverblijfsobject(String toegangbouwlaagverblijfsobject) {
        this.toegangbouwlaagverblijfsobject = toegangbouwlaagverblijfsobject;
    }

    public String getVersie() {
        return this.versie;
    }

    public Verblijfsobject versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public Vastgoedobject getHeeftVastgoedobject() {
        return this.heeftVastgoedobject;
    }

    public void setHeeftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.heeftVastgoedobject = vastgoedobject;
    }

    public Verblijfsobject heeftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.setHeeftVastgoedobject(vastgoedobject);
        return this;
    }

    public Set<Pand> getMaaktdeeluitvanPands() {
        return this.maaktdeeluitvanPands;
    }

    public void setMaaktdeeluitvanPands(Set<Pand> pands) {
        this.maaktdeeluitvanPands = pands;
    }

    public Verblijfsobject maaktdeeluitvanPands(Set<Pand> pands) {
        this.setMaaktdeeluitvanPands(pands);
        return this;
    }

    public Verblijfsobject addMaaktdeeluitvanPand(Pand pand) {
        this.maaktdeeluitvanPands.add(pand);
        return this;
    }

    public Verblijfsobject removeMaaktdeeluitvanPand(Pand pand) {
        this.maaktdeeluitvanPands.remove(pand);
        return this;
    }

    public Set<Binnenlocatie> getIsgevestigdinBinnenlocaties() {
        return this.isgevestigdinBinnenlocaties;
    }

    public void setIsgevestigdinBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        if (this.isgevestigdinBinnenlocaties != null) {
            this.isgevestigdinBinnenlocaties.forEach(i -> i.setIsgevestigdinVerblijfsobject(null));
        }
        if (binnenlocaties != null) {
            binnenlocaties.forEach(i -> i.setIsgevestigdinVerblijfsobject(this));
        }
        this.isgevestigdinBinnenlocaties = binnenlocaties;
    }

    public Verblijfsobject isgevestigdinBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        this.setIsgevestigdinBinnenlocaties(binnenlocaties);
        return this;
    }

    public Verblijfsobject addIsgevestigdinBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.isgevestigdinBinnenlocaties.add(binnenlocatie);
        binnenlocatie.setIsgevestigdinVerblijfsobject(this);
        return this;
    }

    public Verblijfsobject removeIsgevestigdinBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.isgevestigdinBinnenlocaties.remove(binnenlocatie);
        binnenlocatie.setIsgevestigdinVerblijfsobject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verblijfsobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Verblijfsobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verblijfsobject{" +
            "id=" + getId() +
            ", aantalkamers='" + getAantalkamers() + "'" +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", documentdatum='" + getDocumentdatum() + "'" +
            ", documentnr='" + getDocumentnr() + "'" +
            ", gebruiksdoel='" + getGebruiksdoel() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", hoogstebouwlaagverblijfsobject='" + getHoogstebouwlaagverblijfsobject() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", laagstebouwlaagverblijfsobject='" + getLaagstebouwlaagverblijfsobject() + "'" +
            ", ontsluitingverdieping='" + getOntsluitingverdieping() + "'" +
            ", soortwoonobject='" + getSoortwoonobject() + "'" +
            ", status='" + getStatus() + "'" +
            ", toegangbouwlaagverblijfsobject='" + getToegangbouwlaagverblijfsobject() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
