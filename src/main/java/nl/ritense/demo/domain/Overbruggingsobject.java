package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overbruggingsobject.
 */
@Entity
@Table(name = "overbruggingsobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overbruggingsobject implements Serializable {

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

    @Column(name = "kleur")
    private String kleur;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "looprichel")
    private Boolean looprichel;

    @Column(name = "minimumconditiescore")
    private String minimumconditiescore;

    @Column(name = "onderhoudsregime")
    private String onderhoudsregime;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "overbruggingsobjectmateriaal")
    private String overbruggingsobjectmateriaal;

    @Column(name = "overbruggingsobjectmodaliteit")
    private String overbruggingsobjectmodaliteit;

    @Column(name = "technischelevensduur")
    private String technischelevensduur;

    @Column(name = "typefundering")
    private String typefundering;

    @Column(name = "vervangingswaarde")
    private String vervangingswaarde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overbruggingsobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Overbruggingsobject aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public Boolean getAntigraffitivoorziening() {
        return this.antigraffitivoorziening;
    }

    public Overbruggingsobject antigraffitivoorziening(Boolean antigraffitivoorziening) {
        this.setAntigraffitivoorziening(antigraffitivoorziening);
        return this;
    }

    public void setAntigraffitivoorziening(Boolean antigraffitivoorziening) {
        this.antigraffitivoorziening = antigraffitivoorziening;
    }

    public String getBereikbaarheid() {
        return this.bereikbaarheid;
    }

    public Overbruggingsobject bereikbaarheid(String bereikbaarheid) {
        this.setBereikbaarheid(bereikbaarheid);
        return this;
    }

    public void setBereikbaarheid(String bereikbaarheid) {
        this.bereikbaarheid = bereikbaarheid;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Overbruggingsobject breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Overbruggingsobject hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getInstallateur() {
        return this.installateur;
    }

    public Overbruggingsobject installateur(String installateur) {
        this.setInstallateur(installateur);
        return this;
    }

    public void setInstallateur(String installateur) {
        this.installateur = installateur;
    }

    public String getJaarconserveren() {
        return this.jaarconserveren;
    }

    public Overbruggingsobject jaarconserveren(String jaarconserveren) {
        this.setJaarconserveren(jaarconserveren);
        return this;
    }

    public void setJaarconserveren(String jaarconserveren) {
        this.jaarconserveren = jaarconserveren;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Overbruggingsobject jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getJaarrenovatie() {
        return this.jaarrenovatie;
    }

    public Overbruggingsobject jaarrenovatie(String jaarrenovatie) {
        this.setJaarrenovatie(jaarrenovatie);
        return this;
    }

    public void setJaarrenovatie(String jaarrenovatie) {
        this.jaarrenovatie = jaarrenovatie;
    }

    public String getJaarvervanging() {
        return this.jaarvervanging;
    }

    public Overbruggingsobject jaarvervanging(String jaarvervanging) {
        this.setJaarvervanging(jaarvervanging);
        return this;
    }

    public void setJaarvervanging(String jaarvervanging) {
        this.jaarvervanging = jaarvervanging;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Overbruggingsobject kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Overbruggingsobject kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Overbruggingsobject kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Overbruggingsobject lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public Boolean getLooprichel() {
        return this.looprichel;
    }

    public Overbruggingsobject looprichel(Boolean looprichel) {
        this.setLooprichel(looprichel);
        return this;
    }

    public void setLooprichel(Boolean looprichel) {
        this.looprichel = looprichel;
    }

    public String getMinimumconditiescore() {
        return this.minimumconditiescore;
    }

    public Overbruggingsobject minimumconditiescore(String minimumconditiescore) {
        this.setMinimumconditiescore(minimumconditiescore);
        return this;
    }

    public void setMinimumconditiescore(String minimumconditiescore) {
        this.minimumconditiescore = minimumconditiescore;
    }

    public String getOnderhoudsregime() {
        return this.onderhoudsregime;
    }

    public Overbruggingsobject onderhoudsregime(String onderhoudsregime) {
        this.setOnderhoudsregime(onderhoudsregime);
        return this;
    }

    public void setOnderhoudsregime(String onderhoudsregime) {
        this.onderhoudsregime = onderhoudsregime;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Overbruggingsobject oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOverbruggingsobjectmateriaal() {
        return this.overbruggingsobjectmateriaal;
    }

    public Overbruggingsobject overbruggingsobjectmateriaal(String overbruggingsobjectmateriaal) {
        this.setOverbruggingsobjectmateriaal(overbruggingsobjectmateriaal);
        return this;
    }

    public void setOverbruggingsobjectmateriaal(String overbruggingsobjectmateriaal) {
        this.overbruggingsobjectmateriaal = overbruggingsobjectmateriaal;
    }

    public String getOverbruggingsobjectmodaliteit() {
        return this.overbruggingsobjectmodaliteit;
    }

    public Overbruggingsobject overbruggingsobjectmodaliteit(String overbruggingsobjectmodaliteit) {
        this.setOverbruggingsobjectmodaliteit(overbruggingsobjectmodaliteit);
        return this;
    }

    public void setOverbruggingsobjectmodaliteit(String overbruggingsobjectmodaliteit) {
        this.overbruggingsobjectmodaliteit = overbruggingsobjectmodaliteit;
    }

    public String getTechnischelevensduur() {
        return this.technischelevensduur;
    }

    public Overbruggingsobject technischelevensduur(String technischelevensduur) {
        this.setTechnischelevensduur(technischelevensduur);
        return this;
    }

    public void setTechnischelevensduur(String technischelevensduur) {
        this.technischelevensduur = technischelevensduur;
    }

    public String getTypefundering() {
        return this.typefundering;
    }

    public Overbruggingsobject typefundering(String typefundering) {
        this.setTypefundering(typefundering);
        return this;
    }

    public void setTypefundering(String typefundering) {
        this.typefundering = typefundering;
    }

    public String getVervangingswaarde() {
        return this.vervangingswaarde;
    }

    public Overbruggingsobject vervangingswaarde(String vervangingswaarde) {
        this.setVervangingswaarde(vervangingswaarde);
        return this;
    }

    public void setVervangingswaarde(String vervangingswaarde) {
        this.vervangingswaarde = vervangingswaarde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overbruggingsobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Overbruggingsobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overbruggingsobject{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", antigraffitivoorziening='" + getAntigraffitivoorziening() + "'" +
            ", bereikbaarheid='" + getBereikbaarheid() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", installateur='" + getInstallateur() + "'" +
            ", jaarconserveren='" + getJaarconserveren() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", jaarrenovatie='" + getJaarrenovatie() + "'" +
            ", jaarvervanging='" + getJaarvervanging() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", looprichel='" + getLooprichel() + "'" +
            ", minimumconditiescore='" + getMinimumconditiescore() + "'" +
            ", onderhoudsregime='" + getOnderhoudsregime() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", overbruggingsobjectmateriaal='" + getOverbruggingsobjectmateriaal() + "'" +
            ", overbruggingsobjectmodaliteit='" + getOverbruggingsobjectmodaliteit() + "'" +
            ", technischelevensduur='" + getTechnischelevensduur() + "'" +
            ", typefundering='" + getTypefundering() + "'" +
            ", vervangingswaarde='" + getVervangingswaarde() + "'" +
            "}";
    }
}
