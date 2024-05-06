package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Collegelid.
 */
@Entity
@Table(name = "collegelid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Collegelid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "datumaanstelling")
    private LocalDate datumaanstelling;

    @Column(name = "datumuittreding")
    private LocalDate datumuittreding;

    @Column(name = "fractie")
    private String fractie;

    @Column(name = "portefeuille")
    private String portefeuille;

    @Column(name = "titel")
    private String titel;

    @Column(name = "voornaam")
    private String voornaam;

    @JsonIgnoreProperties(value = { "isCollegelid", "isRaadslid", "isRechtspersoon", "heeftRaadsstuks" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isCollegelid")
    private Indiener isIndiener;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Collegelid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchternaam() {
        return this.achternaam;
    }

    public Collegelid achternaam(String achternaam) {
        this.setAchternaam(achternaam);
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getDatumaanstelling() {
        return this.datumaanstelling;
    }

    public Collegelid datumaanstelling(LocalDate datumaanstelling) {
        this.setDatumaanstelling(datumaanstelling);
        return this;
    }

    public void setDatumaanstelling(LocalDate datumaanstelling) {
        this.datumaanstelling = datumaanstelling;
    }

    public LocalDate getDatumuittreding() {
        return this.datumuittreding;
    }

    public Collegelid datumuittreding(LocalDate datumuittreding) {
        this.setDatumuittreding(datumuittreding);
        return this;
    }

    public void setDatumuittreding(LocalDate datumuittreding) {
        this.datumuittreding = datumuittreding;
    }

    public String getFractie() {
        return this.fractie;
    }

    public Collegelid fractie(String fractie) {
        this.setFractie(fractie);
        return this;
    }

    public void setFractie(String fractie) {
        this.fractie = fractie;
    }

    public String getPortefeuille() {
        return this.portefeuille;
    }

    public Collegelid portefeuille(String portefeuille) {
        this.setPortefeuille(portefeuille);
        return this;
    }

    public void setPortefeuille(String portefeuille) {
        this.portefeuille = portefeuille;
    }

    public String getTitel() {
        return this.titel;
    }

    public Collegelid titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public Collegelid voornaam(String voornaam) {
        this.setVoornaam(voornaam);
        return this;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public Indiener getIsIndiener() {
        return this.isIndiener;
    }

    public void setIsIndiener(Indiener indiener) {
        if (this.isIndiener != null) {
            this.isIndiener.setIsCollegelid(null);
        }
        if (indiener != null) {
            indiener.setIsCollegelid(this);
        }
        this.isIndiener = indiener;
    }

    public Collegelid isIndiener(Indiener indiener) {
        this.setIsIndiener(indiener);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collegelid)) {
            return false;
        }
        return getId() != null && getId().equals(((Collegelid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collegelid{" +
            "id=" + getId() +
            ", achternaam='" + getAchternaam() + "'" +
            ", datumaanstelling='" + getDatumaanstelling() + "'" +
            ", datumuittreding='" + getDatumuittreding() + "'" +
            ", fractie='" + getFractie() + "'" +
            ", portefeuille='" + getPortefeuille() + "'" +
            ", titel='" + getTitel() + "'" +
            ", voornaam='" + getVoornaam() + "'" +
            "}";
    }
}
