package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Parkeerzone.
 */
@Entity
@Table(name = "parkeerzone")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parkeerzone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalparkeervlakken")
    private String aantalparkeervlakken;

    @Column(name = "alleendagtarief")
    private Boolean alleendagtarief;

    @Column(name = "dagtarief", precision = 21, scale = 2)
    private BigDecimal dagtarief;

    @Size(max = 20)
    @Column(name = "eindedag", length = 20)
    private String eindedag;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Column(name = "gebruik")
    private String gebruik;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "ipmcode")
    private String ipmcode;

    @Column(name = "ipmnaam")
    private String ipmnaam;

    @Column(name = "naam")
    private String naam;

    @Column(name = "parkeergarage")
    private Boolean parkeergarage;

    @Column(name = "sectorcode")
    private String sectorcode;

    @Column(name = "soortcode")
    private String soortcode;

    @Size(max = 20)
    @Column(name = "startdag", length = 20)
    private String startdag;

    @Column(name = "starttarief", precision = 21, scale = 2)
    private BigDecimal starttarief;

    @Column(name = "starttijd")
    private String starttijd;

    @Column(name = "typecode")
    private String typecode;

    @Column(name = "typenaam")
    private String typenaam;

    @Column(name = "uurtarief", precision = 21, scale = 2)
    private BigDecimal uurtarief;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parkeerzone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalparkeervlakken() {
        return this.aantalparkeervlakken;
    }

    public Parkeerzone aantalparkeervlakken(String aantalparkeervlakken) {
        this.setAantalparkeervlakken(aantalparkeervlakken);
        return this;
    }

    public void setAantalparkeervlakken(String aantalparkeervlakken) {
        this.aantalparkeervlakken = aantalparkeervlakken;
    }

    public Boolean getAlleendagtarief() {
        return this.alleendagtarief;
    }

    public Parkeerzone alleendagtarief(Boolean alleendagtarief) {
        this.setAlleendagtarief(alleendagtarief);
        return this;
    }

    public void setAlleendagtarief(Boolean alleendagtarief) {
        this.alleendagtarief = alleendagtarief;
    }

    public BigDecimal getDagtarief() {
        return this.dagtarief;
    }

    public Parkeerzone dagtarief(BigDecimal dagtarief) {
        this.setDagtarief(dagtarief);
        return this;
    }

    public void setDagtarief(BigDecimal dagtarief) {
        this.dagtarief = dagtarief;
    }

    public String getEindedag() {
        return this.eindedag;
    }

    public Parkeerzone eindedag(String eindedag) {
        this.setEindedag(eindedag);
        return this;
    }

    public void setEindedag(String eindedag) {
        this.eindedag = eindedag;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Parkeerzone eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public String getGebruik() {
        return this.gebruik;
    }

    public Parkeerzone gebruik(String gebruik) {
        this.setGebruik(gebruik);
        return this;
    }

    public void setGebruik(String gebruik) {
        this.gebruik = gebruik;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Parkeerzone geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getIpmcode() {
        return this.ipmcode;
    }

    public Parkeerzone ipmcode(String ipmcode) {
        this.setIpmcode(ipmcode);
        return this;
    }

    public void setIpmcode(String ipmcode) {
        this.ipmcode = ipmcode;
    }

    public String getIpmnaam() {
        return this.ipmnaam;
    }

    public Parkeerzone ipmnaam(String ipmnaam) {
        this.setIpmnaam(ipmnaam);
        return this;
    }

    public void setIpmnaam(String ipmnaam) {
        this.ipmnaam = ipmnaam;
    }

    public String getNaam() {
        return this.naam;
    }

    public Parkeerzone naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Boolean getParkeergarage() {
        return this.parkeergarage;
    }

    public Parkeerzone parkeergarage(Boolean parkeergarage) {
        this.setParkeergarage(parkeergarage);
        return this;
    }

    public void setParkeergarage(Boolean parkeergarage) {
        this.parkeergarage = parkeergarage;
    }

    public String getSectorcode() {
        return this.sectorcode;
    }

    public Parkeerzone sectorcode(String sectorcode) {
        this.setSectorcode(sectorcode);
        return this;
    }

    public void setSectorcode(String sectorcode) {
        this.sectorcode = sectorcode;
    }

    public String getSoortcode() {
        return this.soortcode;
    }

    public Parkeerzone soortcode(String soortcode) {
        this.setSoortcode(soortcode);
        return this;
    }

    public void setSoortcode(String soortcode) {
        this.soortcode = soortcode;
    }

    public String getStartdag() {
        return this.startdag;
    }

    public Parkeerzone startdag(String startdag) {
        this.setStartdag(startdag);
        return this;
    }

    public void setStartdag(String startdag) {
        this.startdag = startdag;
    }

    public BigDecimal getStarttarief() {
        return this.starttarief;
    }

    public Parkeerzone starttarief(BigDecimal starttarief) {
        this.setStarttarief(starttarief);
        return this;
    }

    public void setStarttarief(BigDecimal starttarief) {
        this.starttarief = starttarief;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Parkeerzone starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public String getTypecode() {
        return this.typecode;
    }

    public Parkeerzone typecode(String typecode) {
        this.setTypecode(typecode);
        return this;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getTypenaam() {
        return this.typenaam;
    }

    public Parkeerzone typenaam(String typenaam) {
        this.setTypenaam(typenaam);
        return this;
    }

    public void setTypenaam(String typenaam) {
        this.typenaam = typenaam;
    }

    public BigDecimal getUurtarief() {
        return this.uurtarief;
    }

    public Parkeerzone uurtarief(BigDecimal uurtarief) {
        this.setUurtarief(uurtarief);
        return this;
    }

    public void setUurtarief(BigDecimal uurtarief) {
        this.uurtarief = uurtarief;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parkeerzone)) {
            return false;
        }
        return getId() != null && getId().equals(((Parkeerzone) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parkeerzone{" +
            "id=" + getId() +
            ", aantalparkeervlakken='" + getAantalparkeervlakken() + "'" +
            ", alleendagtarief='" + getAlleendagtarief() + "'" +
            ", dagtarief=" + getDagtarief() +
            ", eindedag='" + getEindedag() + "'" +
            ", eindtijd='" + getEindtijd() + "'" +
            ", gebruik='" + getGebruik() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", ipmcode='" + getIpmcode() + "'" +
            ", ipmnaam='" + getIpmnaam() + "'" +
            ", naam='" + getNaam() + "'" +
            ", parkeergarage='" + getParkeergarage() + "'" +
            ", sectorcode='" + getSectorcode() + "'" +
            ", soortcode='" + getSoortcode() + "'" +
            ", startdag='" + getStartdag() + "'" +
            ", starttarief=" + getStarttarief() +
            ", starttijd='" + getStarttijd() + "'" +
            ", typecode='" + getTypecode() + "'" +
            ", typenaam='" + getTypenaam() + "'" +
            ", uurtarief=" + getUurtarief() +
            "}";
    }
}
