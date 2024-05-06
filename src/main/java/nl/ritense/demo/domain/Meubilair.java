package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Meubilair.
 */
@Entity
@Table(name = "meubilair")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Meubilair implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "bouwjaar")
    private String bouwjaar;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "datumaanschaf")
    private LocalDate datumaanschaf;

    @Column(name = "diameter")
    private String diameter;

    @Column(name = "fabrikant")
    private String fabrikant;

    @Column(name = "gewicht")
    private String gewicht;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "installateur")
    private String installateur;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "jaarpraktischeinde")
    private String jaarpraktischeinde;

    @Column(name = "kleur")
    private String kleur;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "meubilairmateriaal")
    private String meubilairmateriaal;

    @Column(name = "model")
    private String model;

    @Column(name = "ondergrond")
    private String ondergrond;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "prijsaanschaf")
    private String prijsaanschaf;

    @Column(name = "serienummer")
    private String serienummer;

    @Column(name = "transponder")
    private String transponder;

    @Column(name = "transponderlocatie")
    private String transponderlocatie;

    @Column(name = "typefundering")
    private String typefundering;

    @Column(name = "typeplaat")
    private Boolean typeplaat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Meubilair id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Meubilair aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getBouwjaar() {
        return this.bouwjaar;
    }

    public Meubilair bouwjaar(String bouwjaar) {
        this.setBouwjaar(bouwjaar);
        return this;
    }

    public void setBouwjaar(String bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Meubilair breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public LocalDate getDatumaanschaf() {
        return this.datumaanschaf;
    }

    public Meubilair datumaanschaf(LocalDate datumaanschaf) {
        this.setDatumaanschaf(datumaanschaf);
        return this;
    }

    public void setDatumaanschaf(LocalDate datumaanschaf) {
        this.datumaanschaf = datumaanschaf;
    }

    public String getDiameter() {
        return this.diameter;
    }

    public Meubilair diameter(String diameter) {
        this.setDiameter(diameter);
        return this;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getFabrikant() {
        return this.fabrikant;
    }

    public Meubilair fabrikant(String fabrikant) {
        this.setFabrikant(fabrikant);
        return this;
    }

    public void setFabrikant(String fabrikant) {
        this.fabrikant = fabrikant;
    }

    public String getGewicht() {
        return this.gewicht;
    }

    public Meubilair gewicht(String gewicht) {
        this.setGewicht(gewicht);
        return this;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Meubilair hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getInstallateur() {
        return this.installateur;
    }

    public Meubilair installateur(String installateur) {
        this.setInstallateur(installateur);
        return this;
    }

    public void setInstallateur(String installateur) {
        this.installateur = installateur;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Meubilair jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getJaarpraktischeinde() {
        return this.jaarpraktischeinde;
    }

    public Meubilair jaarpraktischeinde(String jaarpraktischeinde) {
        this.setJaarpraktischeinde(jaarpraktischeinde);
        return this;
    }

    public void setJaarpraktischeinde(String jaarpraktischeinde) {
        this.jaarpraktischeinde = jaarpraktischeinde;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Meubilair kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Meubilair kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Meubilair kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Meubilair lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Meubilair leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getMeubilairmateriaal() {
        return this.meubilairmateriaal;
    }

    public Meubilair meubilairmateriaal(String meubilairmateriaal) {
        this.setMeubilairmateriaal(meubilairmateriaal);
        return this;
    }

    public void setMeubilairmateriaal(String meubilairmateriaal) {
        this.meubilairmateriaal = meubilairmateriaal;
    }

    public String getModel() {
        return this.model;
    }

    public Meubilair model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOndergrond() {
        return this.ondergrond;
    }

    public Meubilair ondergrond(String ondergrond) {
        this.setOndergrond(ondergrond);
        return this;
    }

    public void setOndergrond(String ondergrond) {
        this.ondergrond = ondergrond;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Meubilair oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getPrijsaanschaf() {
        return this.prijsaanschaf;
    }

    public Meubilair prijsaanschaf(String prijsaanschaf) {
        this.setPrijsaanschaf(prijsaanschaf);
        return this;
    }

    public void setPrijsaanschaf(String prijsaanschaf) {
        this.prijsaanschaf = prijsaanschaf;
    }

    public String getSerienummer() {
        return this.serienummer;
    }

    public Meubilair serienummer(String serienummer) {
        this.setSerienummer(serienummer);
        return this;
    }

    public void setSerienummer(String serienummer) {
        this.serienummer = serienummer;
    }

    public String getTransponder() {
        return this.transponder;
    }

    public Meubilair transponder(String transponder) {
        this.setTransponder(transponder);
        return this;
    }

    public void setTransponder(String transponder) {
        this.transponder = transponder;
    }

    public String getTransponderlocatie() {
        return this.transponderlocatie;
    }

    public Meubilair transponderlocatie(String transponderlocatie) {
        this.setTransponderlocatie(transponderlocatie);
        return this;
    }

    public void setTransponderlocatie(String transponderlocatie) {
        this.transponderlocatie = transponderlocatie;
    }

    public String getTypefundering() {
        return this.typefundering;
    }

    public Meubilair typefundering(String typefundering) {
        this.setTypefundering(typefundering);
        return this;
    }

    public void setTypefundering(String typefundering) {
        this.typefundering = typefundering;
    }

    public Boolean getTypeplaat() {
        return this.typeplaat;
    }

    public Meubilair typeplaat(Boolean typeplaat) {
        this.setTypeplaat(typeplaat);
        return this;
    }

    public void setTypeplaat(Boolean typeplaat) {
        this.typeplaat = typeplaat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meubilair)) {
            return false;
        }
        return getId() != null && getId().equals(((Meubilair) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meubilair{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", bouwjaar='" + getBouwjaar() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", datumaanschaf='" + getDatumaanschaf() + "'" +
            ", diameter='" + getDiameter() + "'" +
            ", fabrikant='" + getFabrikant() + "'" +
            ", gewicht='" + getGewicht() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", installateur='" + getInstallateur() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", jaarpraktischeinde='" + getJaarpraktischeinde() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", meubilairmateriaal='" + getMeubilairmateriaal() + "'" +
            ", model='" + getModel() + "'" +
            ", ondergrond='" + getOndergrond() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", prijsaanschaf='" + getPrijsaanschaf() + "'" +
            ", serienummer='" + getSerienummer() + "'" +
            ", transponder='" + getTransponder() + "'" +
            ", transponderlocatie='" + getTransponderlocatie() + "'" +
            ", typefundering='" + getTypefundering() + "'" +
            ", typeplaat='" + getTypeplaat() + "'" +
            "}";
    }
}
