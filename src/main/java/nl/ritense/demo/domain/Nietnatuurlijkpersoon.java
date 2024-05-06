package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Nietnatuurlijkpersoon.
 */
@Entity
@Table(name = "nietnatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nietnatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumaanvang")
    private LocalDate datumaanvang;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumuitschrijving")
    private LocalDate datumuitschrijving;

    @Column(name = "datumvoortzetting")
    private LocalDate datumvoortzetting;

    @Size(max = 20)
    @Column(name = "faxnummer", length = 20)
    private String faxnummer;

    @Column(name = "ingeschreven")
    private Boolean ingeschreven;

    @Column(name = "inoprichting")
    private Boolean inoprichting;

    @Size(max = 8)
    @Column(name = "kvknummer", length = 8)
    private String kvknummer;

    @Column(name = "nnpid")
    private String nnpid;

    @Column(name = "rechtsvorm")
    private String rechtsvorm;

    @Size(max = 8)
    @Column(name = "rsinnummer", length = 8)
    private String rsinnummer;

    @Column(name = "statutairenaam")
    private String statutairenaam;

    @Column(name = "statutairezetel")
    private String statutairezetel;

    @Column(name = "websiteurl")
    private String websiteurl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nietnatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumaanvang() {
        return this.datumaanvang;
    }

    public Nietnatuurlijkpersoon datumaanvang(LocalDate datumaanvang) {
        this.setDatumaanvang(datumaanvang);
        return this;
    }

    public void setDatumaanvang(LocalDate datumaanvang) {
        this.datumaanvang = datumaanvang;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Nietnatuurlijkpersoon datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumuitschrijving() {
        return this.datumuitschrijving;
    }

    public Nietnatuurlijkpersoon datumuitschrijving(LocalDate datumuitschrijving) {
        this.setDatumuitschrijving(datumuitschrijving);
        return this;
    }

    public void setDatumuitschrijving(LocalDate datumuitschrijving) {
        this.datumuitschrijving = datumuitschrijving;
    }

    public LocalDate getDatumvoortzetting() {
        return this.datumvoortzetting;
    }

    public Nietnatuurlijkpersoon datumvoortzetting(LocalDate datumvoortzetting) {
        this.setDatumvoortzetting(datumvoortzetting);
        return this;
    }

    public void setDatumvoortzetting(LocalDate datumvoortzetting) {
        this.datumvoortzetting = datumvoortzetting;
    }

    public String getFaxnummer() {
        return this.faxnummer;
    }

    public Nietnatuurlijkpersoon faxnummer(String faxnummer) {
        this.setFaxnummer(faxnummer);
        return this;
    }

    public void setFaxnummer(String faxnummer) {
        this.faxnummer = faxnummer;
    }

    public Boolean getIngeschreven() {
        return this.ingeschreven;
    }

    public Nietnatuurlijkpersoon ingeschreven(Boolean ingeschreven) {
        this.setIngeschreven(ingeschreven);
        return this;
    }

    public void setIngeschreven(Boolean ingeschreven) {
        this.ingeschreven = ingeschreven;
    }

    public Boolean getInoprichting() {
        return this.inoprichting;
    }

    public Nietnatuurlijkpersoon inoprichting(Boolean inoprichting) {
        this.setInoprichting(inoprichting);
        return this;
    }

    public void setInoprichting(Boolean inoprichting) {
        this.inoprichting = inoprichting;
    }

    public String getKvknummer() {
        return this.kvknummer;
    }

    public Nietnatuurlijkpersoon kvknummer(String kvknummer) {
        this.setKvknummer(kvknummer);
        return this;
    }

    public void setKvknummer(String kvknummer) {
        this.kvknummer = kvknummer;
    }

    public String getNnpid() {
        return this.nnpid;
    }

    public Nietnatuurlijkpersoon nnpid(String nnpid) {
        this.setNnpid(nnpid);
        return this;
    }

    public void setNnpid(String nnpid) {
        this.nnpid = nnpid;
    }

    public String getRechtsvorm() {
        return this.rechtsvorm;
    }

    public Nietnatuurlijkpersoon rechtsvorm(String rechtsvorm) {
        this.setRechtsvorm(rechtsvorm);
        return this;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getRsinnummer() {
        return this.rsinnummer;
    }

    public Nietnatuurlijkpersoon rsinnummer(String rsinnummer) {
        this.setRsinnummer(rsinnummer);
        return this;
    }

    public void setRsinnummer(String rsinnummer) {
        this.rsinnummer = rsinnummer;
    }

    public String getStatutairenaam() {
        return this.statutairenaam;
    }

    public Nietnatuurlijkpersoon statutairenaam(String statutairenaam) {
        this.setStatutairenaam(statutairenaam);
        return this;
    }

    public void setStatutairenaam(String statutairenaam) {
        this.statutairenaam = statutairenaam;
    }

    public String getStatutairezetel() {
        return this.statutairezetel;
    }

    public Nietnatuurlijkpersoon statutairezetel(String statutairezetel) {
        this.setStatutairezetel(statutairezetel);
        return this;
    }

    public void setStatutairezetel(String statutairezetel) {
        this.statutairezetel = statutairezetel;
    }

    public String getWebsiteurl() {
        return this.websiteurl;
    }

    public Nietnatuurlijkpersoon websiteurl(String websiteurl) {
        this.setWebsiteurl(websiteurl);
        return this;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nietnatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Nietnatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nietnatuurlijkpersoon{" +
            "id=" + getId() +
            ", datumaanvang='" + getDatumaanvang() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumuitschrijving='" + getDatumuitschrijving() + "'" +
            ", datumvoortzetting='" + getDatumvoortzetting() + "'" +
            ", faxnummer='" + getFaxnummer() + "'" +
            ", ingeschreven='" + getIngeschreven() + "'" +
            ", inoprichting='" + getInoprichting() + "'" +
            ", kvknummer='" + getKvknummer() + "'" +
            ", nnpid='" + getNnpid() + "'" +
            ", rechtsvorm='" + getRechtsvorm() + "'" +
            ", rsinnummer='" + getRsinnummer() + "'" +
            ", statutairenaam='" + getStatutairenaam() + "'" +
            ", statutairezetel='" + getStatutairezetel() + "'" +
            ", websiteurl='" + getWebsiteurl() + "'" +
            "}";
    }
}
