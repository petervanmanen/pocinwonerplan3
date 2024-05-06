package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Natuurlijkpersoon.
 */
@Entity
@Table(name = "natuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Natuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "empty")
    private String empty;

    @Column(name = "aanduidingbijzondernederlanderschappersoon")
    private String aanduidingbijzondernederlanderschappersoon;

    @Column(name = "aanduidingnaamgebruik")
    private String aanduidingnaamgebruik;

    @Column(name = "aanhefaanschrijving")
    private String aanhefaanschrijving;

    @Column(name = "academischetitel")
    private String academischetitel;

    @Size(max = 100)
    @Column(name = "achternaam", length = 100)
    private String achternaam;

    @Column(name = "adellijketitelofpredikaat")
    private String adellijketitelofpredikaat;

    @Size(max = 20)
    @Column(name = "anummer", length = 20)
    private String anummer;

    @Column(name = "burgerservicenummer")
    private String burgerservicenummer;

    @Column(name = "datumgeboorte")
    private String datumgeboorte;

    @Column(name = "datumoverlijden")
    private String datumoverlijden;

    @Column(name = "geboorteland")
    private String geboorteland;

    @Column(name = "geboorteplaats")
    private String geboorteplaats;

    @Column(name = "geslachtsaanduiding")
    private String geslachtsaanduiding;

    @Column(name = "geslachtsnaam")
    private String geslachtsnaam;

    @Column(name = "geslachtsnaamaanschrijving")
    private String geslachtsnaamaanschrijving;

    @Column(name = "handlichting")
    private String handlichting;

    @Column(name = "indicatieafschermingpersoonsgegevens")
    private Boolean indicatieafschermingpersoonsgegevens;

    @Column(name = "indicatieoverleden")
    private Boolean indicatieoverleden;

    @Column(name = "landoverlijden")
    private String landoverlijden;

    @Size(max = 100)
    @Column(name = "nationaliteit", length = 100)
    private String nationaliteit;

    @Column(name = "overlijdensplaats")
    private String overlijdensplaats;

    @Size(max = 20)
    @Column(name = "voorlettersaanschrijving", length = 20)
    private String voorlettersaanschrijving;

    @Column(name = "voornamen")
    private String voornamen;

    @Column(name = "voornamenaanschrijving")
    private String voornamenaanschrijving;

    @Column(name = "voorvoegselgeslachtsnaam")
    private String voorvoegselgeslachtsnaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Natuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpty() {
        return this.empty;
    }

    public Natuurlijkpersoon empty(String empty) {
        this.setEmpty(empty);
        return this;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public String getAanduidingbijzondernederlanderschappersoon() {
        return this.aanduidingbijzondernederlanderschappersoon;
    }

    public Natuurlijkpersoon aanduidingbijzondernederlanderschappersoon(String aanduidingbijzondernederlanderschappersoon) {
        this.setAanduidingbijzondernederlanderschappersoon(aanduidingbijzondernederlanderschappersoon);
        return this;
    }

    public void setAanduidingbijzondernederlanderschappersoon(String aanduidingbijzondernederlanderschappersoon) {
        this.aanduidingbijzondernederlanderschappersoon = aanduidingbijzondernederlanderschappersoon;
    }

    public String getAanduidingnaamgebruik() {
        return this.aanduidingnaamgebruik;
    }

    public Natuurlijkpersoon aanduidingnaamgebruik(String aanduidingnaamgebruik) {
        this.setAanduidingnaamgebruik(aanduidingnaamgebruik);
        return this;
    }

    public void setAanduidingnaamgebruik(String aanduidingnaamgebruik) {
        this.aanduidingnaamgebruik = aanduidingnaamgebruik;
    }

    public String getAanhefaanschrijving() {
        return this.aanhefaanschrijving;
    }

    public Natuurlijkpersoon aanhefaanschrijving(String aanhefaanschrijving) {
        this.setAanhefaanschrijving(aanhefaanschrijving);
        return this;
    }

    public void setAanhefaanschrijving(String aanhefaanschrijving) {
        this.aanhefaanschrijving = aanhefaanschrijving;
    }

    public String getAcademischetitel() {
        return this.academischetitel;
    }

    public Natuurlijkpersoon academischetitel(String academischetitel) {
        this.setAcademischetitel(academischetitel);
        return this;
    }

    public void setAcademischetitel(String academischetitel) {
        this.academischetitel = academischetitel;
    }

    public String getAchternaam() {
        return this.achternaam;
    }

    public Natuurlijkpersoon achternaam(String achternaam) {
        this.setAchternaam(achternaam);
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getAdellijketitelofpredikaat() {
        return this.adellijketitelofpredikaat;
    }

    public Natuurlijkpersoon adellijketitelofpredikaat(String adellijketitelofpredikaat) {
        this.setAdellijketitelofpredikaat(adellijketitelofpredikaat);
        return this;
    }

    public void setAdellijketitelofpredikaat(String adellijketitelofpredikaat) {
        this.adellijketitelofpredikaat = adellijketitelofpredikaat;
    }

    public String getAnummer() {
        return this.anummer;
    }

    public Natuurlijkpersoon anummer(String anummer) {
        this.setAnummer(anummer);
        return this;
    }

    public void setAnummer(String anummer) {
        this.anummer = anummer;
    }

    public String getBurgerservicenummer() {
        return this.burgerservicenummer;
    }

    public Natuurlijkpersoon burgerservicenummer(String burgerservicenummer) {
        this.setBurgerservicenummer(burgerservicenummer);
        return this;
    }

    public void setBurgerservicenummer(String burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    public String getDatumgeboorte() {
        return this.datumgeboorte;
    }

    public Natuurlijkpersoon datumgeboorte(String datumgeboorte) {
        this.setDatumgeboorte(datumgeboorte);
        return this;
    }

    public void setDatumgeboorte(String datumgeboorte) {
        this.datumgeboorte = datumgeboorte;
    }

    public String getDatumoverlijden() {
        return this.datumoverlijden;
    }

    public Natuurlijkpersoon datumoverlijden(String datumoverlijden) {
        this.setDatumoverlijden(datumoverlijden);
        return this;
    }

    public void setDatumoverlijden(String datumoverlijden) {
        this.datumoverlijden = datumoverlijden;
    }

    public String getGeboorteland() {
        return this.geboorteland;
    }

    public Natuurlijkpersoon geboorteland(String geboorteland) {
        this.setGeboorteland(geboorteland);
        return this;
    }

    public void setGeboorteland(String geboorteland) {
        this.geboorteland = geboorteland;
    }

    public String getGeboorteplaats() {
        return this.geboorteplaats;
    }

    public Natuurlijkpersoon geboorteplaats(String geboorteplaats) {
        this.setGeboorteplaats(geboorteplaats);
        return this;
    }

    public void setGeboorteplaats(String geboorteplaats) {
        this.geboorteplaats = geboorteplaats;
    }

    public String getGeslachtsaanduiding() {
        return this.geslachtsaanduiding;
    }

    public Natuurlijkpersoon geslachtsaanduiding(String geslachtsaanduiding) {
        this.setGeslachtsaanduiding(geslachtsaanduiding);
        return this;
    }

    public void setGeslachtsaanduiding(String geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    public String getGeslachtsnaam() {
        return this.geslachtsnaam;
    }

    public Natuurlijkpersoon geslachtsnaam(String geslachtsnaam) {
        this.setGeslachtsnaam(geslachtsnaam);
        return this;
    }

    public void setGeslachtsnaam(String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public String getGeslachtsnaamaanschrijving() {
        return this.geslachtsnaamaanschrijving;
    }

    public Natuurlijkpersoon geslachtsnaamaanschrijving(String geslachtsnaamaanschrijving) {
        this.setGeslachtsnaamaanschrijving(geslachtsnaamaanschrijving);
        return this;
    }

    public void setGeslachtsnaamaanschrijving(String geslachtsnaamaanschrijving) {
        this.geslachtsnaamaanschrijving = geslachtsnaamaanschrijving;
    }

    public String getHandlichting() {
        return this.handlichting;
    }

    public Natuurlijkpersoon handlichting(String handlichting) {
        this.setHandlichting(handlichting);
        return this;
    }

    public void setHandlichting(String handlichting) {
        this.handlichting = handlichting;
    }

    public Boolean getIndicatieafschermingpersoonsgegevens() {
        return this.indicatieafschermingpersoonsgegevens;
    }

    public Natuurlijkpersoon indicatieafschermingpersoonsgegevens(Boolean indicatieafschermingpersoonsgegevens) {
        this.setIndicatieafschermingpersoonsgegevens(indicatieafschermingpersoonsgegevens);
        return this;
    }

    public void setIndicatieafschermingpersoonsgegevens(Boolean indicatieafschermingpersoonsgegevens) {
        this.indicatieafschermingpersoonsgegevens = indicatieafschermingpersoonsgegevens;
    }

    public Boolean getIndicatieoverleden() {
        return this.indicatieoverleden;
    }

    public Natuurlijkpersoon indicatieoverleden(Boolean indicatieoverleden) {
        this.setIndicatieoverleden(indicatieoverleden);
        return this;
    }

    public void setIndicatieoverleden(Boolean indicatieoverleden) {
        this.indicatieoverleden = indicatieoverleden;
    }

    public String getLandoverlijden() {
        return this.landoverlijden;
    }

    public Natuurlijkpersoon landoverlijden(String landoverlijden) {
        this.setLandoverlijden(landoverlijden);
        return this;
    }

    public void setLandoverlijden(String landoverlijden) {
        this.landoverlijden = landoverlijden;
    }

    public String getNationaliteit() {
        return this.nationaliteit;
    }

    public Natuurlijkpersoon nationaliteit(String nationaliteit) {
        this.setNationaliteit(nationaliteit);
        return this;
    }

    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public String getOverlijdensplaats() {
        return this.overlijdensplaats;
    }

    public Natuurlijkpersoon overlijdensplaats(String overlijdensplaats) {
        this.setOverlijdensplaats(overlijdensplaats);
        return this;
    }

    public void setOverlijdensplaats(String overlijdensplaats) {
        this.overlijdensplaats = overlijdensplaats;
    }

    public String getVoorlettersaanschrijving() {
        return this.voorlettersaanschrijving;
    }

    public Natuurlijkpersoon voorlettersaanschrijving(String voorlettersaanschrijving) {
        this.setVoorlettersaanschrijving(voorlettersaanschrijving);
        return this;
    }

    public void setVoorlettersaanschrijving(String voorlettersaanschrijving) {
        this.voorlettersaanschrijving = voorlettersaanschrijving;
    }

    public String getVoornamen() {
        return this.voornamen;
    }

    public Natuurlijkpersoon voornamen(String voornamen) {
        this.setVoornamen(voornamen);
        return this;
    }

    public void setVoornamen(String voornamen) {
        this.voornamen = voornamen;
    }

    public String getVoornamenaanschrijving() {
        return this.voornamenaanschrijving;
    }

    public Natuurlijkpersoon voornamenaanschrijving(String voornamenaanschrijving) {
        this.setVoornamenaanschrijving(voornamenaanschrijving);
        return this;
    }

    public void setVoornamenaanschrijving(String voornamenaanschrijving) {
        this.voornamenaanschrijving = voornamenaanschrijving;
    }

    public String getVoorvoegselgeslachtsnaam() {
        return this.voorvoegselgeslachtsnaam;
    }

    public Natuurlijkpersoon voorvoegselgeslachtsnaam(String voorvoegselgeslachtsnaam) {
        this.setVoorvoegselgeslachtsnaam(voorvoegselgeslachtsnaam);
        return this;
    }

    public void setVoorvoegselgeslachtsnaam(String voorvoegselgeslachtsnaam) {
        this.voorvoegselgeslachtsnaam = voorvoegselgeslachtsnaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Natuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Natuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Natuurlijkpersoon{" +
            "id=" + getId() +
            ", empty='" + getEmpty() + "'" +
            ", aanduidingbijzondernederlanderschappersoon='" + getAanduidingbijzondernederlanderschappersoon() + "'" +
            ", aanduidingnaamgebruik='" + getAanduidingnaamgebruik() + "'" +
            ", aanhefaanschrijving='" + getAanhefaanschrijving() + "'" +
            ", academischetitel='" + getAcademischetitel() + "'" +
            ", achternaam='" + getAchternaam() + "'" +
            ", adellijketitelofpredikaat='" + getAdellijketitelofpredikaat() + "'" +
            ", anummer='" + getAnummer() + "'" +
            ", burgerservicenummer='" + getBurgerservicenummer() + "'" +
            ", datumgeboorte='" + getDatumgeboorte() + "'" +
            ", datumoverlijden='" + getDatumoverlijden() + "'" +
            ", geboorteland='" + getGeboorteland() + "'" +
            ", geboorteplaats='" + getGeboorteplaats() + "'" +
            ", geslachtsaanduiding='" + getGeslachtsaanduiding() + "'" +
            ", geslachtsnaam='" + getGeslachtsnaam() + "'" +
            ", geslachtsnaamaanschrijving='" + getGeslachtsnaamaanschrijving() + "'" +
            ", handlichting='" + getHandlichting() + "'" +
            ", indicatieafschermingpersoonsgegevens='" + getIndicatieafschermingpersoonsgegevens() + "'" +
            ", indicatieoverleden='" + getIndicatieoverleden() + "'" +
            ", landoverlijden='" + getLandoverlijden() + "'" +
            ", nationaliteit='" + getNationaliteit() + "'" +
            ", overlijdensplaats='" + getOverlijdensplaats() + "'" +
            ", voorlettersaanschrijving='" + getVoorlettersaanschrijving() + "'" +
            ", voornamen='" + getVoornamen() + "'" +
            ", voornamenaanschrijving='" + getVoornamenaanschrijving() + "'" +
            ", voorvoegselgeslachtsnaam='" + getVoorvoegselgeslachtsnaam() + "'" +
            "}";
    }
}
