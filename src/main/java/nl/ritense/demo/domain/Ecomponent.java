package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ecomponent.
 */
@Entity
@Table(name = "ecomponent")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ecomponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag")
    private String bedrag;

    @Column(name = "datumbeginbetrekkingop")
    private LocalDate datumbeginbetrekkingop;

    @Column(name = "datumeindebetrekkingop")
    private LocalDate datumeindebetrekkingop;

    @Column(name = "debetcredit")
    private String debetcredit;

    @Column(name = "groep")
    private String groep;

    @Size(max = 20)
    @Column(name = "groepcode", length = 20)
    private String groepcode;

    @Column(name = "grootboekcode")
    private String grootboekcode;

    @Size(max = 100)
    @Column(name = "grootboekomschrijving", length = 100)
    private String grootboekomschrijving;

    @Column(name = "kostenplaats")
    private String kostenplaats;

    @Size(max = 100)
    @Column(name = "omschrijving", length = 100)
    private String omschrijving;

    @Column(name = "rekeningnummer")
    private String rekeningnummer;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ecomponent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedrag() {
        return this.bedrag;
    }

    public Ecomponent bedrag(String bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatumbeginbetrekkingop() {
        return this.datumbeginbetrekkingop;
    }

    public Ecomponent datumbeginbetrekkingop(LocalDate datumbeginbetrekkingop) {
        this.setDatumbeginbetrekkingop(datumbeginbetrekkingop);
        return this;
    }

    public void setDatumbeginbetrekkingop(LocalDate datumbeginbetrekkingop) {
        this.datumbeginbetrekkingop = datumbeginbetrekkingop;
    }

    public LocalDate getDatumeindebetrekkingop() {
        return this.datumeindebetrekkingop;
    }

    public Ecomponent datumeindebetrekkingop(LocalDate datumeindebetrekkingop) {
        this.setDatumeindebetrekkingop(datumeindebetrekkingop);
        return this;
    }

    public void setDatumeindebetrekkingop(LocalDate datumeindebetrekkingop) {
        this.datumeindebetrekkingop = datumeindebetrekkingop;
    }

    public String getDebetcredit() {
        return this.debetcredit;
    }

    public Ecomponent debetcredit(String debetcredit) {
        this.setDebetcredit(debetcredit);
        return this;
    }

    public void setDebetcredit(String debetcredit) {
        this.debetcredit = debetcredit;
    }

    public String getGroep() {
        return this.groep;
    }

    public Ecomponent groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public String getGroepcode() {
        return this.groepcode;
    }

    public Ecomponent groepcode(String groepcode) {
        this.setGroepcode(groepcode);
        return this;
    }

    public void setGroepcode(String groepcode) {
        this.groepcode = groepcode;
    }

    public String getGrootboekcode() {
        return this.grootboekcode;
    }

    public Ecomponent grootboekcode(String grootboekcode) {
        this.setGrootboekcode(grootboekcode);
        return this;
    }

    public void setGrootboekcode(String grootboekcode) {
        this.grootboekcode = grootboekcode;
    }

    public String getGrootboekomschrijving() {
        return this.grootboekomschrijving;
    }

    public Ecomponent grootboekomschrijving(String grootboekomschrijving) {
        this.setGrootboekomschrijving(grootboekomschrijving);
        return this;
    }

    public void setGrootboekomschrijving(String grootboekomschrijving) {
        this.grootboekomschrijving = grootboekomschrijving;
    }

    public String getKostenplaats() {
        return this.kostenplaats;
    }

    public Ecomponent kostenplaats(String kostenplaats) {
        this.setKostenplaats(kostenplaats);
        return this;
    }

    public void setKostenplaats(String kostenplaats) {
        this.kostenplaats = kostenplaats;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Ecomponent omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getRekeningnummer() {
        return this.rekeningnummer;
    }

    public Ecomponent rekeningnummer(String rekeningnummer) {
        this.setRekeningnummer(rekeningnummer);
        return this;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Ecomponent toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ecomponent)) {
            return false;
        }
        return getId() != null && getId().equals(((Ecomponent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecomponent{" +
            "id=" + getId() +
            ", bedrag='" + getBedrag() + "'" +
            ", datumbeginbetrekkingop='" + getDatumbeginbetrekkingop() + "'" +
            ", datumeindebetrekkingop='" + getDatumeindebetrekkingop() + "'" +
            ", debetcredit='" + getDebetcredit() + "'" +
            ", groep='" + getGroep() + "'" +
            ", groepcode='" + getGroepcode() + "'" +
            ", grootboekcode='" + getGrootboekcode() + "'" +
            ", grootboekomschrijving='" + getGrootboekomschrijving() + "'" +
            ", kostenplaats='" + getKostenplaats() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", rekeningnummer='" + getRekeningnummer() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
