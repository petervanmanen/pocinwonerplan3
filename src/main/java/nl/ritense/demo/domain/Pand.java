package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pand.
 */
@Entity
@Table(name = "pand")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "brutoinhoudpand")
    private String brutoinhoudpand;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "geometriebovenaanzicht")
    private String geometriebovenaanzicht;

    @Column(name = "geometriemaaiveld")
    private String geometriemaaiveld;

    @Column(name = "geometriepunt")
    private String geometriepunt;

    @Column(name = "hoogstebouwlaagpand")
    private String hoogstebouwlaagpand;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "laagstebouwlaagpand")
    private String laagstebouwlaagpand;

    @Column(name = "oorspronkelijkbouwjaar")
    private String oorspronkelijkbouwjaar;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "relatievehoogteliggingpand")
    private String relatievehoogteliggingpand;

    @Column(name = "status")
    private String status;

    @Column(name = "statusvoortgangbouw")
    private String statusvoortgangbouw;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "komtovereenGebied", "ligtinNummeraanduidings", "zonderverblijfsobjectligtinPands", "ligtinAreaals" },
        allowSetters = true
    )
    private Buurt zonderverblijfsobjectligtinBuurt;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftPand")
    private Vastgoedobject betreftVastgoedobject;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "maaktdeeluitvanPands")
    @JsonIgnoreProperties(value = { "heeftVastgoedobject", "maaktdeeluitvanPands", "isgevestigdinBinnenlocaties" }, allowSetters = true)
    private Set<Verblijfsobject> maaktdeeluitvanVerblijfsobjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrutoinhoudpand() {
        return this.brutoinhoudpand;
    }

    public Pand brutoinhoudpand(String brutoinhoudpand) {
        this.setBrutoinhoudpand(brutoinhoudpand);
        return this;
    }

    public void setBrutoinhoudpand(String brutoinhoudpand) {
        this.brutoinhoudpand = brutoinhoudpand;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Pand datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Pand datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Pand datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Pand datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Pand geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometriebovenaanzicht() {
        return this.geometriebovenaanzicht;
    }

    public Pand geometriebovenaanzicht(String geometriebovenaanzicht) {
        this.setGeometriebovenaanzicht(geometriebovenaanzicht);
        return this;
    }

    public void setGeometriebovenaanzicht(String geometriebovenaanzicht) {
        this.geometriebovenaanzicht = geometriebovenaanzicht;
    }

    public String getGeometriemaaiveld() {
        return this.geometriemaaiveld;
    }

    public Pand geometriemaaiveld(String geometriemaaiveld) {
        this.setGeometriemaaiveld(geometriemaaiveld);
        return this;
    }

    public void setGeometriemaaiveld(String geometriemaaiveld) {
        this.geometriemaaiveld = geometriemaaiveld;
    }

    public String getGeometriepunt() {
        return this.geometriepunt;
    }

    public Pand geometriepunt(String geometriepunt) {
        this.setGeometriepunt(geometriepunt);
        return this;
    }

    public void setGeometriepunt(String geometriepunt) {
        this.geometriepunt = geometriepunt;
    }

    public String getHoogstebouwlaagpand() {
        return this.hoogstebouwlaagpand;
    }

    public Pand hoogstebouwlaagpand(String hoogstebouwlaagpand) {
        this.setHoogstebouwlaagpand(hoogstebouwlaagpand);
        return this;
    }

    public void setHoogstebouwlaagpand(String hoogstebouwlaagpand) {
        this.hoogstebouwlaagpand = hoogstebouwlaagpand;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Pand identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Pand inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getLaagstebouwlaagpand() {
        return this.laagstebouwlaagpand;
    }

    public Pand laagstebouwlaagpand(String laagstebouwlaagpand) {
        this.setLaagstebouwlaagpand(laagstebouwlaagpand);
        return this;
    }

    public void setLaagstebouwlaagpand(String laagstebouwlaagpand) {
        this.laagstebouwlaagpand = laagstebouwlaagpand;
    }

    public String getOorspronkelijkbouwjaar() {
        return this.oorspronkelijkbouwjaar;
    }

    public Pand oorspronkelijkbouwjaar(String oorspronkelijkbouwjaar) {
        this.setOorspronkelijkbouwjaar(oorspronkelijkbouwjaar);
        return this;
    }

    public void setOorspronkelijkbouwjaar(String oorspronkelijkbouwjaar) {
        this.oorspronkelijkbouwjaar = oorspronkelijkbouwjaar;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Pand oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getRelatievehoogteliggingpand() {
        return this.relatievehoogteliggingpand;
    }

    public Pand relatievehoogteliggingpand(String relatievehoogteliggingpand) {
        this.setRelatievehoogteliggingpand(relatievehoogteliggingpand);
        return this;
    }

    public void setRelatievehoogteliggingpand(String relatievehoogteliggingpand) {
        this.relatievehoogteliggingpand = relatievehoogteliggingpand;
    }

    public String getStatus() {
        return this.status;
    }

    public Pand status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusvoortgangbouw() {
        return this.statusvoortgangbouw;
    }

    public Pand statusvoortgangbouw(String statusvoortgangbouw) {
        this.setStatusvoortgangbouw(statusvoortgangbouw);
        return this;
    }

    public void setStatusvoortgangbouw(String statusvoortgangbouw) {
        this.statusvoortgangbouw = statusvoortgangbouw;
    }

    public String getVersie() {
        return this.versie;
    }

    public Pand versie(String versie) {
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

    public Pand heeftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.setHeeftVastgoedobject(vastgoedobject);
        return this;
    }

    public Buurt getZonderverblijfsobjectligtinBuurt() {
        return this.zonderverblijfsobjectligtinBuurt;
    }

    public void setZonderverblijfsobjectligtinBuurt(Buurt buurt) {
        this.zonderverblijfsobjectligtinBuurt = buurt;
    }

    public Pand zonderverblijfsobjectligtinBuurt(Buurt buurt) {
        this.setZonderverblijfsobjectligtinBuurt(buurt);
        return this;
    }

    public Vastgoedobject getBetreftVastgoedobject() {
        return this.betreftVastgoedobject;
    }

    public void setBetreftVastgoedobject(Vastgoedobject vastgoedobject) {
        if (this.betreftVastgoedobject != null) {
            this.betreftVastgoedobject.setBetreftPand(null);
        }
        if (vastgoedobject != null) {
            vastgoedobject.setBetreftPand(this);
        }
        this.betreftVastgoedobject = vastgoedobject;
    }

    public Pand betreftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.setBetreftVastgoedobject(vastgoedobject);
        return this;
    }

    public Set<Verblijfsobject> getMaaktdeeluitvanVerblijfsobjects() {
        return this.maaktdeeluitvanVerblijfsobjects;
    }

    public void setMaaktdeeluitvanVerblijfsobjects(Set<Verblijfsobject> verblijfsobjects) {
        if (this.maaktdeeluitvanVerblijfsobjects != null) {
            this.maaktdeeluitvanVerblijfsobjects.forEach(i -> i.removeMaaktdeeluitvanPand(this));
        }
        if (verblijfsobjects != null) {
            verblijfsobjects.forEach(i -> i.addMaaktdeeluitvanPand(this));
        }
        this.maaktdeeluitvanVerblijfsobjects = verblijfsobjects;
    }

    public Pand maaktdeeluitvanVerblijfsobjects(Set<Verblijfsobject> verblijfsobjects) {
        this.setMaaktdeeluitvanVerblijfsobjects(verblijfsobjects);
        return this;
    }

    public Pand addMaaktdeeluitvanVerblijfsobject(Verblijfsobject verblijfsobject) {
        this.maaktdeeluitvanVerblijfsobjects.add(verblijfsobject);
        verblijfsobject.getMaaktdeeluitvanPands().add(this);
        return this;
    }

    public Pand removeMaaktdeeluitvanVerblijfsobject(Verblijfsobject verblijfsobject) {
        this.maaktdeeluitvanVerblijfsobjects.remove(verblijfsobject);
        verblijfsobject.getMaaktdeeluitvanPands().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pand)) {
            return false;
        }
        return getId() != null && getId().equals(((Pand) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pand{" +
            "id=" + getId() +
            ", brutoinhoudpand='" + getBrutoinhoudpand() + "'" +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometriebovenaanzicht='" + getGeometriebovenaanzicht() + "'" +
            ", geometriemaaiveld='" + getGeometriemaaiveld() + "'" +
            ", geometriepunt='" + getGeometriepunt() + "'" +
            ", hoogstebouwlaagpand='" + getHoogstebouwlaagpand() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", laagstebouwlaagpand='" + getLaagstebouwlaagpand() + "'" +
            ", oorspronkelijkbouwjaar='" + getOorspronkelijkbouwjaar() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", relatievehoogteliggingpand='" + getRelatievehoogteliggingpand() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusvoortgangbouw='" + getStatusvoortgangbouw() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
