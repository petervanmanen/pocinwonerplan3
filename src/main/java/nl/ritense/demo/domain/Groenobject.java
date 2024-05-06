package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Groenobject.
 */
@Entity
@Table(name = "groenobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Groenobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalobstakels")
    private String aantalobstakels;

    @Column(name = "aantalzijden")
    private String aantalzijden;

    @Column(name = "afvoeren")
    private Boolean afvoeren;

    @Column(name = "bereikbaarheid")
    private String bereikbaarheid;

    @Column(name = "bergendvermogen")
    private String bergendvermogen;

    @Column(name = "bewerkingspercentage")
    private String bewerkingspercentage;

    @Column(name = "bgtfysiekvoorkomen")
    private String bgtfysiekvoorkomen;

    @Column(name = "bollen")
    private Boolean bollen;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "breedteklassehaag")
    private String breedteklassehaag;

    @Column(name = "bvc")
    private Boolean bvc;

    @Column(name = "cultuurhistorischwaardevol")
    private String cultuurhistorischwaardevol;

    @Column(name = "draagkrachtig")
    private Boolean draagkrachtig;

    @Column(name = "ecologischbeheer")
    private Boolean ecologischbeheer;

    @Column(name = "fysiekvoorkomenimgeo")
    private String fysiekvoorkomenimgeo;

    @Column(name = "gewenstsluitingspercentage")
    private String gewenstsluitingspercentage;

    @Column(name = "groenobjectbereikbaarheidplus")
    private String groenobjectbereikbaarheidplus;

    @Column(name = "groenobjectconstructielaag")
    private String groenobjectconstructielaag;

    @Column(name = "groenobjectrand")
    private String groenobjectrand;

    @Column(name = "groenobjectsoortnaam")
    private String groenobjectsoortnaam;

    @Column(name = "haagvoetlengte")
    private String haagvoetlengte;

    @Column(name = "haagvoetoppervlakte")
    private String haagvoetoppervlakte;

    @Column(name = "herplantplicht")
    private Boolean herplantplicht;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "hoogteklassehaag")
    private String hoogteklassehaag;

    @Column(name = "knipfrequentie")
    private String knipfrequentie;

    @Column(name = "knipoppervlakte")
    private String knipoppervlakte;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "maaifrequentie")
    private String maaifrequentie;

    @Column(name = "maximalevalhoogte")
    private String maximalevalhoogte;

    @Size(max = 20)
    @Column(name = "eobjectnummer", length = 20)
    private String eobjectnummer;

    @Column(name = "obstakels")
    private Boolean obstakels;

    @Column(name = "omtrek")
    private String omtrek;

    @Column(name = "ondergroei")
    private String ondergroei;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "optalud")
    private String optalud;

    @Column(name = "taludsteilte")
    private String taludsteilte;

    @Column(name = "type")
    private String type;

    @Column(name = "typebewerking")
    private String typebewerking;

    @Column(name = "typeomgevingsrisicoklasse")
    private String typeomgevingsrisicoklasse;

    @Column(name = "typeplus")
    private String typeplus;

    @Size(max = 100)
    @Column(name = "typeplus_2", length = 100)
    private String typeplus2;

    @Column(name = "veiligheidsklasseboom")
    private String veiligheidsklasseboom;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Groenobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalobstakels() {
        return this.aantalobstakels;
    }

    public Groenobject aantalobstakels(String aantalobstakels) {
        this.setAantalobstakels(aantalobstakels);
        return this;
    }

    public void setAantalobstakels(String aantalobstakels) {
        this.aantalobstakels = aantalobstakels;
    }

    public String getAantalzijden() {
        return this.aantalzijden;
    }

    public Groenobject aantalzijden(String aantalzijden) {
        this.setAantalzijden(aantalzijden);
        return this;
    }

    public void setAantalzijden(String aantalzijden) {
        this.aantalzijden = aantalzijden;
    }

    public Boolean getAfvoeren() {
        return this.afvoeren;
    }

    public Groenobject afvoeren(Boolean afvoeren) {
        this.setAfvoeren(afvoeren);
        return this;
    }

    public void setAfvoeren(Boolean afvoeren) {
        this.afvoeren = afvoeren;
    }

    public String getBereikbaarheid() {
        return this.bereikbaarheid;
    }

    public Groenobject bereikbaarheid(String bereikbaarheid) {
        this.setBereikbaarheid(bereikbaarheid);
        return this;
    }

    public void setBereikbaarheid(String bereikbaarheid) {
        this.bereikbaarheid = bereikbaarheid;
    }

    public String getBergendvermogen() {
        return this.bergendvermogen;
    }

    public Groenobject bergendvermogen(String bergendvermogen) {
        this.setBergendvermogen(bergendvermogen);
        return this;
    }

    public void setBergendvermogen(String bergendvermogen) {
        this.bergendvermogen = bergendvermogen;
    }

    public String getBewerkingspercentage() {
        return this.bewerkingspercentage;
    }

    public Groenobject bewerkingspercentage(String bewerkingspercentage) {
        this.setBewerkingspercentage(bewerkingspercentage);
        return this;
    }

    public void setBewerkingspercentage(String bewerkingspercentage) {
        this.bewerkingspercentage = bewerkingspercentage;
    }

    public String getBgtfysiekvoorkomen() {
        return this.bgtfysiekvoorkomen;
    }

    public Groenobject bgtfysiekvoorkomen(String bgtfysiekvoorkomen) {
        this.setBgtfysiekvoorkomen(bgtfysiekvoorkomen);
        return this;
    }

    public void setBgtfysiekvoorkomen(String bgtfysiekvoorkomen) {
        this.bgtfysiekvoorkomen = bgtfysiekvoorkomen;
    }

    public Boolean getBollen() {
        return this.bollen;
    }

    public Groenobject bollen(Boolean bollen) {
        this.setBollen(bollen);
        return this;
    }

    public void setBollen(Boolean bollen) {
        this.bollen = bollen;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Groenobject breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getBreedteklassehaag() {
        return this.breedteklassehaag;
    }

    public Groenobject breedteklassehaag(String breedteklassehaag) {
        this.setBreedteklassehaag(breedteklassehaag);
        return this;
    }

    public void setBreedteklassehaag(String breedteklassehaag) {
        this.breedteklassehaag = breedteklassehaag;
    }

    public Boolean getBvc() {
        return this.bvc;
    }

    public Groenobject bvc(Boolean bvc) {
        this.setBvc(bvc);
        return this;
    }

    public void setBvc(Boolean bvc) {
        this.bvc = bvc;
    }

    public String getCultuurhistorischwaardevol() {
        return this.cultuurhistorischwaardevol;
    }

    public Groenobject cultuurhistorischwaardevol(String cultuurhistorischwaardevol) {
        this.setCultuurhistorischwaardevol(cultuurhistorischwaardevol);
        return this;
    }

    public void setCultuurhistorischwaardevol(String cultuurhistorischwaardevol) {
        this.cultuurhistorischwaardevol = cultuurhistorischwaardevol;
    }

    public Boolean getDraagkrachtig() {
        return this.draagkrachtig;
    }

    public Groenobject draagkrachtig(Boolean draagkrachtig) {
        this.setDraagkrachtig(draagkrachtig);
        return this;
    }

    public void setDraagkrachtig(Boolean draagkrachtig) {
        this.draagkrachtig = draagkrachtig;
    }

    public Boolean getEcologischbeheer() {
        return this.ecologischbeheer;
    }

    public Groenobject ecologischbeheer(Boolean ecologischbeheer) {
        this.setEcologischbeheer(ecologischbeheer);
        return this;
    }

    public void setEcologischbeheer(Boolean ecologischbeheer) {
        this.ecologischbeheer = ecologischbeheer;
    }

    public String getFysiekvoorkomenimgeo() {
        return this.fysiekvoorkomenimgeo;
    }

    public Groenobject fysiekvoorkomenimgeo(String fysiekvoorkomenimgeo) {
        this.setFysiekvoorkomenimgeo(fysiekvoorkomenimgeo);
        return this;
    }

    public void setFysiekvoorkomenimgeo(String fysiekvoorkomenimgeo) {
        this.fysiekvoorkomenimgeo = fysiekvoorkomenimgeo;
    }

    public String getGewenstsluitingspercentage() {
        return this.gewenstsluitingspercentage;
    }

    public Groenobject gewenstsluitingspercentage(String gewenstsluitingspercentage) {
        this.setGewenstsluitingspercentage(gewenstsluitingspercentage);
        return this;
    }

    public void setGewenstsluitingspercentage(String gewenstsluitingspercentage) {
        this.gewenstsluitingspercentage = gewenstsluitingspercentage;
    }

    public String getGroenobjectbereikbaarheidplus() {
        return this.groenobjectbereikbaarheidplus;
    }

    public Groenobject groenobjectbereikbaarheidplus(String groenobjectbereikbaarheidplus) {
        this.setGroenobjectbereikbaarheidplus(groenobjectbereikbaarheidplus);
        return this;
    }

    public void setGroenobjectbereikbaarheidplus(String groenobjectbereikbaarheidplus) {
        this.groenobjectbereikbaarheidplus = groenobjectbereikbaarheidplus;
    }

    public String getGroenobjectconstructielaag() {
        return this.groenobjectconstructielaag;
    }

    public Groenobject groenobjectconstructielaag(String groenobjectconstructielaag) {
        this.setGroenobjectconstructielaag(groenobjectconstructielaag);
        return this;
    }

    public void setGroenobjectconstructielaag(String groenobjectconstructielaag) {
        this.groenobjectconstructielaag = groenobjectconstructielaag;
    }

    public String getGroenobjectrand() {
        return this.groenobjectrand;
    }

    public Groenobject groenobjectrand(String groenobjectrand) {
        this.setGroenobjectrand(groenobjectrand);
        return this;
    }

    public void setGroenobjectrand(String groenobjectrand) {
        this.groenobjectrand = groenobjectrand;
    }

    public String getGroenobjectsoortnaam() {
        return this.groenobjectsoortnaam;
    }

    public Groenobject groenobjectsoortnaam(String groenobjectsoortnaam) {
        this.setGroenobjectsoortnaam(groenobjectsoortnaam);
        return this;
    }

    public void setGroenobjectsoortnaam(String groenobjectsoortnaam) {
        this.groenobjectsoortnaam = groenobjectsoortnaam;
    }

    public String getHaagvoetlengte() {
        return this.haagvoetlengte;
    }

    public Groenobject haagvoetlengte(String haagvoetlengte) {
        this.setHaagvoetlengte(haagvoetlengte);
        return this;
    }

    public void setHaagvoetlengte(String haagvoetlengte) {
        this.haagvoetlengte = haagvoetlengte;
    }

    public String getHaagvoetoppervlakte() {
        return this.haagvoetoppervlakte;
    }

    public Groenobject haagvoetoppervlakte(String haagvoetoppervlakte) {
        this.setHaagvoetoppervlakte(haagvoetoppervlakte);
        return this;
    }

    public void setHaagvoetoppervlakte(String haagvoetoppervlakte) {
        this.haagvoetoppervlakte = haagvoetoppervlakte;
    }

    public Boolean getHerplantplicht() {
        return this.herplantplicht;
    }

    public Groenobject herplantplicht(Boolean herplantplicht) {
        this.setHerplantplicht(herplantplicht);
        return this;
    }

    public void setHerplantplicht(Boolean herplantplicht) {
        this.herplantplicht = herplantplicht;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Groenobject hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getHoogteklassehaag() {
        return this.hoogteklassehaag;
    }

    public Groenobject hoogteklassehaag(String hoogteklassehaag) {
        this.setHoogteklassehaag(hoogteklassehaag);
        return this;
    }

    public void setHoogteklassehaag(String hoogteklassehaag) {
        this.hoogteklassehaag = hoogteklassehaag;
    }

    public String getKnipfrequentie() {
        return this.knipfrequentie;
    }

    public Groenobject knipfrequentie(String knipfrequentie) {
        this.setKnipfrequentie(knipfrequentie);
        return this;
    }

    public void setKnipfrequentie(String knipfrequentie) {
        this.knipfrequentie = knipfrequentie;
    }

    public String getKnipoppervlakte() {
        return this.knipoppervlakte;
    }

    public Groenobject knipoppervlakte(String knipoppervlakte) {
        this.setKnipoppervlakte(knipoppervlakte);
        return this;
    }

    public void setKnipoppervlakte(String knipoppervlakte) {
        this.knipoppervlakte = knipoppervlakte;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Groenobject kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Groenobject kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Groenobject lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Groenobject leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getMaaifrequentie() {
        return this.maaifrequentie;
    }

    public Groenobject maaifrequentie(String maaifrequentie) {
        this.setMaaifrequentie(maaifrequentie);
        return this;
    }

    public void setMaaifrequentie(String maaifrequentie) {
        this.maaifrequentie = maaifrequentie;
    }

    public String getMaximalevalhoogte() {
        return this.maximalevalhoogte;
    }

    public Groenobject maximalevalhoogte(String maximalevalhoogte) {
        this.setMaximalevalhoogte(maximalevalhoogte);
        return this;
    }

    public void setMaximalevalhoogte(String maximalevalhoogte) {
        this.maximalevalhoogte = maximalevalhoogte;
    }

    public String getEobjectnummer() {
        return this.eobjectnummer;
    }

    public Groenobject eobjectnummer(String eobjectnummer) {
        this.setEobjectnummer(eobjectnummer);
        return this;
    }

    public void setEobjectnummer(String eobjectnummer) {
        this.eobjectnummer = eobjectnummer;
    }

    public Boolean getObstakels() {
        return this.obstakels;
    }

    public Groenobject obstakels(Boolean obstakels) {
        this.setObstakels(obstakels);
        return this;
    }

    public void setObstakels(Boolean obstakels) {
        this.obstakels = obstakels;
    }

    public String getOmtrek() {
        return this.omtrek;
    }

    public Groenobject omtrek(String omtrek) {
        this.setOmtrek(omtrek);
        return this;
    }

    public void setOmtrek(String omtrek) {
        this.omtrek = omtrek;
    }

    public String getOndergroei() {
        return this.ondergroei;
    }

    public Groenobject ondergroei(String ondergroei) {
        this.setOndergroei(ondergroei);
        return this;
    }

    public void setOndergroei(String ondergroei) {
        this.ondergroei = ondergroei;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Groenobject oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOptalud() {
        return this.optalud;
    }

    public Groenobject optalud(String optalud) {
        this.setOptalud(optalud);
        return this;
    }

    public void setOptalud(String optalud) {
        this.optalud = optalud;
    }

    public String getTaludsteilte() {
        return this.taludsteilte;
    }

    public Groenobject taludsteilte(String taludsteilte) {
        this.setTaludsteilte(taludsteilte);
        return this;
    }

    public void setTaludsteilte(String taludsteilte) {
        this.taludsteilte = taludsteilte;
    }

    public String getType() {
        return this.type;
    }

    public Groenobject type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypebewerking() {
        return this.typebewerking;
    }

    public Groenobject typebewerking(String typebewerking) {
        this.setTypebewerking(typebewerking);
        return this;
    }

    public void setTypebewerking(String typebewerking) {
        this.typebewerking = typebewerking;
    }

    public String getTypeomgevingsrisicoklasse() {
        return this.typeomgevingsrisicoklasse;
    }

    public Groenobject typeomgevingsrisicoklasse(String typeomgevingsrisicoklasse) {
        this.setTypeomgevingsrisicoklasse(typeomgevingsrisicoklasse);
        return this;
    }

    public void setTypeomgevingsrisicoklasse(String typeomgevingsrisicoklasse) {
        this.typeomgevingsrisicoklasse = typeomgevingsrisicoklasse;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Groenobject typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypeplus2() {
        return this.typeplus2;
    }

    public Groenobject typeplus2(String typeplus2) {
        this.setTypeplus2(typeplus2);
        return this;
    }

    public void setTypeplus2(String typeplus2) {
        this.typeplus2 = typeplus2;
    }

    public String getVeiligheidsklasseboom() {
        return this.veiligheidsklasseboom;
    }

    public Groenobject veiligheidsklasseboom(String veiligheidsklasseboom) {
        this.setVeiligheidsklasseboom(veiligheidsklasseboom);
        return this;
    }

    public void setVeiligheidsklasseboom(String veiligheidsklasseboom) {
        this.veiligheidsklasseboom = veiligheidsklasseboom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Groenobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Groenobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Groenobject{" +
            "id=" + getId() +
            ", aantalobstakels='" + getAantalobstakels() + "'" +
            ", aantalzijden='" + getAantalzijden() + "'" +
            ", afvoeren='" + getAfvoeren() + "'" +
            ", bereikbaarheid='" + getBereikbaarheid() + "'" +
            ", bergendvermogen='" + getBergendvermogen() + "'" +
            ", bewerkingspercentage='" + getBewerkingspercentage() + "'" +
            ", bgtfysiekvoorkomen='" + getBgtfysiekvoorkomen() + "'" +
            ", bollen='" + getBollen() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", breedteklassehaag='" + getBreedteklassehaag() + "'" +
            ", bvc='" + getBvc() + "'" +
            ", cultuurhistorischwaardevol='" + getCultuurhistorischwaardevol() + "'" +
            ", draagkrachtig='" + getDraagkrachtig() + "'" +
            ", ecologischbeheer='" + getEcologischbeheer() + "'" +
            ", fysiekvoorkomenimgeo='" + getFysiekvoorkomenimgeo() + "'" +
            ", gewenstsluitingspercentage='" + getGewenstsluitingspercentage() + "'" +
            ", groenobjectbereikbaarheidplus='" + getGroenobjectbereikbaarheidplus() + "'" +
            ", groenobjectconstructielaag='" + getGroenobjectconstructielaag() + "'" +
            ", groenobjectrand='" + getGroenobjectrand() + "'" +
            ", groenobjectsoortnaam='" + getGroenobjectsoortnaam() + "'" +
            ", haagvoetlengte='" + getHaagvoetlengte() + "'" +
            ", haagvoetoppervlakte='" + getHaagvoetoppervlakte() + "'" +
            ", herplantplicht='" + getHerplantplicht() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", hoogteklassehaag='" + getHoogteklassehaag() + "'" +
            ", knipfrequentie='" + getKnipfrequentie() + "'" +
            ", knipoppervlakte='" + getKnipoppervlakte() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", maaifrequentie='" + getMaaifrequentie() + "'" +
            ", maximalevalhoogte='" + getMaximalevalhoogte() + "'" +
            ", eobjectnummer='" + getEobjectnummer() + "'" +
            ", obstakels='" + getObstakels() + "'" +
            ", omtrek='" + getOmtrek() + "'" +
            ", ondergroei='" + getOndergroei() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", optalud='" + getOptalud() + "'" +
            ", taludsteilte='" + getTaludsteilte() + "'" +
            ", type='" + getType() + "'" +
            ", typebewerking='" + getTypebewerking() + "'" +
            ", typeomgevingsrisicoklasse='" + getTypeomgevingsrisicoklasse() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typeplus2='" + getTypeplus2() + "'" +
            ", veiligheidsklasseboom='" + getVeiligheidsklasseboom() + "'" +
            "}";
    }
}
