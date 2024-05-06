package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Verkeersbesluit.
 */
@Entity
@Table(name = "verkeersbesluit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verkeersbesluit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbesluit")
    private LocalDate datumbesluit;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "huisnummer")
    private String huisnummer;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "referentienummer")
    private String referentienummer;

    @Column(name = "straat")
    private String straat;

    @Column(name = "titel")
    private String titel;

    @JsonIgnoreProperties(
        value = {
            "heeftkenmerkIdentificatiekenmerk",
            "isvanDocumenttype",
            "isvastgelegdinVerkeersbesluit",
            "isvastgelegdinBesluit",
            "inspectierapportBinnenlocatie",
            "heeftRapportagemoment",
            "heeftSubsidies",
            "heeftdocumentenApplicaties",
            "kanvastgelegdzijnalsBesluits",
            "kentZaaks",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Document isvastgelegdinDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verkeersbesluit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbesluit() {
        return this.datumbesluit;
    }

    public Verkeersbesluit datumbesluit(LocalDate datumbesluit) {
        this.setDatumbesluit(datumbesluit);
        return this;
    }

    public void setDatumbesluit(LocalDate datumbesluit) {
        this.datumbesluit = datumbesluit;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Verkeersbesluit datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Verkeersbesluit datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getHuisnummer() {
        return this.huisnummer;
    }

    public Verkeersbesluit huisnummer(String huisnummer) {
        this.setHuisnummer(huisnummer);
        return this;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Verkeersbesluit postcode(String postcode) {
        this.setPostcode(postcode);
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getReferentienummer() {
        return this.referentienummer;
    }

    public Verkeersbesluit referentienummer(String referentienummer) {
        this.setReferentienummer(referentienummer);
        return this;
    }

    public void setReferentienummer(String referentienummer) {
        this.referentienummer = referentienummer;
    }

    public String getStraat() {
        return this.straat;
    }

    public Verkeersbesluit straat(String straat) {
        this.setStraat(straat);
        return this;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getTitel() {
        return this.titel;
    }

    public Verkeersbesluit titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Document getIsvastgelegdinDocument() {
        return this.isvastgelegdinDocument;
    }

    public void setIsvastgelegdinDocument(Document document) {
        this.isvastgelegdinDocument = document;
    }

    public Verkeersbesluit isvastgelegdinDocument(Document document) {
        this.setIsvastgelegdinDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verkeersbesluit)) {
            return false;
        }
        return getId() != null && getId().equals(((Verkeersbesluit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verkeersbesluit{" +
            "id=" + getId() +
            ", datumbesluit='" + getDatumbesluit() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", huisnummer='" + getHuisnummer() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", referentienummer='" + getReferentienummer() + "'" +
            ", straat='" + getStraat() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
