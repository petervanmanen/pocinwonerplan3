package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Kunstwerk.
 */
@Entity
@Table(name = "kunstwerk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kunstwerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "antigraffitivoorziening")
    private Boolean antigraffitivoorziening;

    @Column(name = "bereikbaarheid")
    private String bereikbaarheid;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "constructietype")
    private String constructietype;

    @Column(name = "gewicht")
    private String gewicht;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "installateur")
    private String installateur;

    @Column(name = "jaarconserveren")
    private String jaarconserveren;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "jaarrenovatie")
    private String jaarrenovatie;

    @Column(name = "jaarvervanging")
    private String jaarvervanging;

    @Column(name = "kilometreringbegin")
    private String kilometreringbegin;

    @Column(name = "kilometreringeinde")
    private String kilometreringeinde;

    @Column(name = "kleur")
    private String kleur;

    @Column(name = "kunstwerkbereikbaarheidplus")
    private String kunstwerkbereikbaarheidplus;

    @Column(name = "kunstwerkmateriaal")
    private String kunstwerkmateriaal;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "looprichel")
    private Boolean looprichel;

    @Column(name = "minimumconditiescore")
    private String minimumconditiescore;

    @Column(name = "monument")
    private Boolean monument;

    @Size(max = 20)
    @Column(name = "monumentnummer", length = 20)
    private String monumentnummer;

    @Column(name = "eobjectnaam")
    private String eobjectnaam;

    @Size(max = 20)
    @Column(name = "eobjectnummer", length = 20)
    private String eobjectnummer;

    @Column(name = "onderhoudsregime")
    private String onderhoudsregime;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "orientatie")
    private String orientatie;

    @Column(name = "technischelevensduur")
    private String technischelevensduur;

    @Column(name = "typefundering")
    private String typefundering;

    @Column(name = "typemonument")
    private String typemonument;

    @Column(name = "vervangingswaarde")
    private String vervangingswaarde;

    @Size(max = 20)
    @Column(name = "wegnummer", length = 20)
    private String wegnummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kunstwerk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Kunstwerk aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public Boolean getAntigraffitivoorziening() {
        return this.antigraffitivoorziening;
    }

    public Kunstwerk antigraffitivoorziening(Boolean antigraffitivoorziening) {
        this.setAntigraffitivoorziening(antigraffitivoorziening);
        return this;
    }

    public void setAntigraffitivoorziening(Boolean antigraffitivoorziening) {
        this.antigraffitivoorziening = antigraffitivoorziening;
    }

    public String getBereikbaarheid() {
        return this.bereikbaarheid;
    }

    public Kunstwerk bereikbaarheid(String bereikbaarheid) {
        this.setBereikbaarheid(bereikbaarheid);
        return this;
    }

    public void setBereikbaarheid(String bereikbaarheid) {
        this.bereikbaarheid = bereikbaarheid;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Kunstwerk breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getConstructietype() {
        return this.constructietype;
    }

    public Kunstwerk constructietype(String constructietype) {
        this.setConstructietype(constructietype);
        return this;
    }

    public void setConstructietype(String constructietype) {
        this.constructietype = constructietype;
    }

    public String getGewicht() {
        return this.gewicht;
    }

    public Kunstwerk gewicht(String gewicht) {
        this.setGewicht(gewicht);
        return this;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Kunstwerk hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getInstallateur() {
        return this.installateur;
    }

    public Kunstwerk installateur(String installateur) {
        this.setInstallateur(installateur);
        return this;
    }

    public void setInstallateur(String installateur) {
        this.installateur = installateur;
    }

    public String getJaarconserveren() {
        return this.jaarconserveren;
    }

    public Kunstwerk jaarconserveren(String jaarconserveren) {
        this.setJaarconserveren(jaarconserveren);
        return this;
    }

    public void setJaarconserveren(String jaarconserveren) {
        this.jaarconserveren = jaarconserveren;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Kunstwerk jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getJaarrenovatie() {
        return this.jaarrenovatie;
    }

    public Kunstwerk jaarrenovatie(String jaarrenovatie) {
        this.setJaarrenovatie(jaarrenovatie);
        return this;
    }

    public void setJaarrenovatie(String jaarrenovatie) {
        this.jaarrenovatie = jaarrenovatie;
    }

    public String getJaarvervanging() {
        return this.jaarvervanging;
    }

    public Kunstwerk jaarvervanging(String jaarvervanging) {
        this.setJaarvervanging(jaarvervanging);
        return this;
    }

    public void setJaarvervanging(String jaarvervanging) {
        this.jaarvervanging = jaarvervanging;
    }

    public String getKilometreringbegin() {
        return this.kilometreringbegin;
    }

    public Kunstwerk kilometreringbegin(String kilometreringbegin) {
        this.setKilometreringbegin(kilometreringbegin);
        return this;
    }

    public void setKilometreringbegin(String kilometreringbegin) {
        this.kilometreringbegin = kilometreringbegin;
    }

    public String getKilometreringeinde() {
        return this.kilometreringeinde;
    }

    public Kunstwerk kilometreringeinde(String kilometreringeinde) {
        this.setKilometreringeinde(kilometreringeinde);
        return this;
    }

    public void setKilometreringeinde(String kilometreringeinde) {
        this.kilometreringeinde = kilometreringeinde;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Kunstwerk kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getKunstwerkbereikbaarheidplus() {
        return this.kunstwerkbereikbaarheidplus;
    }

    public Kunstwerk kunstwerkbereikbaarheidplus(String kunstwerkbereikbaarheidplus) {
        this.setKunstwerkbereikbaarheidplus(kunstwerkbereikbaarheidplus);
        return this;
    }

    public void setKunstwerkbereikbaarheidplus(String kunstwerkbereikbaarheidplus) {
        this.kunstwerkbereikbaarheidplus = kunstwerkbereikbaarheidplus;
    }

    public String getKunstwerkmateriaal() {
        return this.kunstwerkmateriaal;
    }

    public Kunstwerk kunstwerkmateriaal(String kunstwerkmateriaal) {
        this.setKunstwerkmateriaal(kunstwerkmateriaal);
        return this;
    }

    public void setKunstwerkmateriaal(String kunstwerkmateriaal) {
        this.kunstwerkmateriaal = kunstwerkmateriaal;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Kunstwerk kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Kunstwerk kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Kunstwerk lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Kunstwerk leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public Boolean getLooprichel() {
        return this.looprichel;
    }

    public Kunstwerk looprichel(Boolean looprichel) {
        this.setLooprichel(looprichel);
        return this;
    }

    public void setLooprichel(Boolean looprichel) {
        this.looprichel = looprichel;
    }

    public String getMinimumconditiescore() {
        return this.minimumconditiescore;
    }

    public Kunstwerk minimumconditiescore(String minimumconditiescore) {
        this.setMinimumconditiescore(minimumconditiescore);
        return this;
    }

    public void setMinimumconditiescore(String minimumconditiescore) {
        this.minimumconditiescore = minimumconditiescore;
    }

    public Boolean getMonument() {
        return this.monument;
    }

    public Kunstwerk monument(Boolean monument) {
        this.setMonument(monument);
        return this;
    }

    public void setMonument(Boolean monument) {
        this.monument = monument;
    }

    public String getMonumentnummer() {
        return this.monumentnummer;
    }

    public Kunstwerk monumentnummer(String monumentnummer) {
        this.setMonumentnummer(monumentnummer);
        return this;
    }

    public void setMonumentnummer(String monumentnummer) {
        this.monumentnummer = monumentnummer;
    }

    public String getEobjectnaam() {
        return this.eobjectnaam;
    }

    public Kunstwerk eobjectnaam(String eobjectnaam) {
        this.setEobjectnaam(eobjectnaam);
        return this;
    }

    public void setEobjectnaam(String eobjectnaam) {
        this.eobjectnaam = eobjectnaam;
    }

    public String getEobjectnummer() {
        return this.eobjectnummer;
    }

    public Kunstwerk eobjectnummer(String eobjectnummer) {
        this.setEobjectnummer(eobjectnummer);
        return this;
    }

    public void setEobjectnummer(String eobjectnummer) {
        this.eobjectnummer = eobjectnummer;
    }

    public String getOnderhoudsregime() {
        return this.onderhoudsregime;
    }

    public Kunstwerk onderhoudsregime(String onderhoudsregime) {
        this.setOnderhoudsregime(onderhoudsregime);
        return this;
    }

    public void setOnderhoudsregime(String onderhoudsregime) {
        this.onderhoudsregime = onderhoudsregime;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Kunstwerk oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOrientatie() {
        return this.orientatie;
    }

    public Kunstwerk orientatie(String orientatie) {
        this.setOrientatie(orientatie);
        return this;
    }

    public void setOrientatie(String orientatie) {
        this.orientatie = orientatie;
    }

    public String getTechnischelevensduur() {
        return this.technischelevensduur;
    }

    public Kunstwerk technischelevensduur(String technischelevensduur) {
        this.setTechnischelevensduur(technischelevensduur);
        return this;
    }

    public void setTechnischelevensduur(String technischelevensduur) {
        this.technischelevensduur = technischelevensduur;
    }

    public String getTypefundering() {
        return this.typefundering;
    }

    public Kunstwerk typefundering(String typefundering) {
        this.setTypefundering(typefundering);
        return this;
    }

    public void setTypefundering(String typefundering) {
        this.typefundering = typefundering;
    }

    public String getTypemonument() {
        return this.typemonument;
    }

    public Kunstwerk typemonument(String typemonument) {
        this.setTypemonument(typemonument);
        return this;
    }

    public void setTypemonument(String typemonument) {
        this.typemonument = typemonument;
    }

    public String getVervangingswaarde() {
        return this.vervangingswaarde;
    }

    public Kunstwerk vervangingswaarde(String vervangingswaarde) {
        this.setVervangingswaarde(vervangingswaarde);
        return this;
    }

    public void setVervangingswaarde(String vervangingswaarde) {
        this.vervangingswaarde = vervangingswaarde;
    }

    public String getWegnummer() {
        return this.wegnummer;
    }

    public Kunstwerk wegnummer(String wegnummer) {
        this.setWegnummer(wegnummer);
        return this;
    }

    public void setWegnummer(String wegnummer) {
        this.wegnummer = wegnummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kunstwerk)) {
            return false;
        }
        return getId() != null && getId().equals(((Kunstwerk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kunstwerk{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", antigraffitivoorziening='" + getAntigraffitivoorziening() + "'" +
            ", bereikbaarheid='" + getBereikbaarheid() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", constructietype='" + getConstructietype() + "'" +
            ", gewicht='" + getGewicht() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", installateur='" + getInstallateur() + "'" +
            ", jaarconserveren='" + getJaarconserveren() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", jaarrenovatie='" + getJaarrenovatie() + "'" +
            ", jaarvervanging='" + getJaarvervanging() + "'" +
            ", kilometreringbegin='" + getKilometreringbegin() + "'" +
            ", kilometreringeinde='" + getKilometreringeinde() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", kunstwerkbereikbaarheidplus='" + getKunstwerkbereikbaarheidplus() + "'" +
            ", kunstwerkmateriaal='" + getKunstwerkmateriaal() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", looprichel='" + getLooprichel() + "'" +
            ", minimumconditiescore='" + getMinimumconditiescore() + "'" +
            ", monument='" + getMonument() + "'" +
            ", monumentnummer='" + getMonumentnummer() + "'" +
            ", eobjectnaam='" + getEobjectnaam() + "'" +
            ", eobjectnummer='" + getEobjectnummer() + "'" +
            ", onderhoudsregime='" + getOnderhoudsregime() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", orientatie='" + getOrientatie() + "'" +
            ", technischelevensduur='" + getTechnischelevensduur() + "'" +
            ", typefundering='" + getTypefundering() + "'" +
            ", typemonument='" + getTypemonument() + "'" +
            ", vervangingswaarde='" + getVervangingswaarde() + "'" +
            ", wegnummer='" + getWegnummer() + "'" +
            "}";
    }
}
