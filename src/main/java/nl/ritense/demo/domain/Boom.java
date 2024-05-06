package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Boom.
 */
@Entity
@Table(name = "boom")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Boom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beleidsstatus")
    private String beleidsstatus;

    @Column(name = "beoogdeomlooptijd")
    private String beoogdeomlooptijd;

    @Column(name = "boombeeld")
    private String boombeeld;

    @Column(name = "boombeschermer")
    private String boombeschermer;

    @Column(name = "boomgroep")
    private String boomgroep;

    @Column(name = "boomhoogteactueel")
    private String boomhoogteactueel;

    @Column(name = "boomhoogteklasseactueel")
    private String boomhoogteklasseactueel;

    @Column(name = "boomhoogteklasseeindebeeld")
    private String boomhoogteklasseeindebeeld;

    @Column(name = "boomspiegel")
    private String boomspiegel;

    @Column(name = "boomtypebeschermingsstatusplus")
    private String boomtypebeschermingsstatusplus;

    @Column(name = "boomvoorziening")
    private String boomvoorziening;

    @Column(name = "controlefrequentie")
    private String controlefrequentie;

    @Column(name = "feestverlichting")
    private String feestverlichting;

    @Column(name = "groeifase")
    private String groeifase;

    @Column(name = "groeiplaatsinrichting")
    private String groeiplaatsinrichting;

    @Column(name = "herplantplicht")
    private Boolean herplantplicht;

    @Column(name = "kiemjaar")
    private String kiemjaar;

    @Column(name = "kroondiameterklasseactueel")
    private String kroondiameterklasseactueel;

    @Column(name = "kroondiameterklasseeindebeeld")
    private String kroondiameterklasseeindebeeld;

    @Column(name = "kroonvolume")
    private String kroonvolume;

    @Column(name = "leeftijd")
    private String leeftijd;

    @Column(name = "meerstammig")
    private Boolean meerstammig;

    @Column(name = "monetaireboomwaarde")
    private String monetaireboomwaarde;

    @Column(name = "snoeifase")
    private String snoeifase;

    @Column(name = "stamdiameter")
    private String stamdiameter;

    @Column(name = "stamdiameterklasse")
    private String stamdiameterklasse;

    @Column(name = "takvrijeruimtetotgebouw")
    private String takvrijeruimtetotgebouw;

    @Column(name = "takvrijestam")
    private String takvrijestam;

    @Column(name = "takvrijezoneprimair")
    private String takvrijezoneprimair;

    @Column(name = "takvrijezonesecundair")
    private String takvrijezonesecundair;

    @Column(name = "transponder")
    private String transponder;

    @Column(name = "type")
    private String type;

    @Column(name = "typebeschermingsstatus")
    private String typebeschermingsstatus;

    @Column(name = "typeomgevingsrisicoklasse")
    private String typeomgevingsrisicoklasse;

    @Column(name = "typeplus")
    private String typeplus;

    @Column(name = "typevermeerderingsvorm")
    private String typevermeerderingsvorm;

    @Column(name = "veiligheidsklasseboom")
    private String veiligheidsklasseboom;

    @Column(name = "verplant")
    private Boolean verplant;

    @Column(name = "verplantbaar")
    private Boolean verplantbaar;

    @Column(name = "vrijedoorrijhoogte")
    private String vrijedoorrijhoogte;

    @Column(name = "vrijedoorrijhoogteprimair")
    private String vrijedoorrijhoogteprimair;

    @Column(name = "vrijedoorrijhoogtesecundair")
    private String vrijedoorrijhoogtesecundair;

    @Column(name = "vrijetakval")
    private String vrijetakval;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Boom id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeleidsstatus() {
        return this.beleidsstatus;
    }

    public Boom beleidsstatus(String beleidsstatus) {
        this.setBeleidsstatus(beleidsstatus);
        return this;
    }

    public void setBeleidsstatus(String beleidsstatus) {
        this.beleidsstatus = beleidsstatus;
    }

    public String getBeoogdeomlooptijd() {
        return this.beoogdeomlooptijd;
    }

    public Boom beoogdeomlooptijd(String beoogdeomlooptijd) {
        this.setBeoogdeomlooptijd(beoogdeomlooptijd);
        return this;
    }

    public void setBeoogdeomlooptijd(String beoogdeomlooptijd) {
        this.beoogdeomlooptijd = beoogdeomlooptijd;
    }

    public String getBoombeeld() {
        return this.boombeeld;
    }

    public Boom boombeeld(String boombeeld) {
        this.setBoombeeld(boombeeld);
        return this;
    }

    public void setBoombeeld(String boombeeld) {
        this.boombeeld = boombeeld;
    }

    public String getBoombeschermer() {
        return this.boombeschermer;
    }

    public Boom boombeschermer(String boombeschermer) {
        this.setBoombeschermer(boombeschermer);
        return this;
    }

    public void setBoombeschermer(String boombeschermer) {
        this.boombeschermer = boombeschermer;
    }

    public String getBoomgroep() {
        return this.boomgroep;
    }

    public Boom boomgroep(String boomgroep) {
        this.setBoomgroep(boomgroep);
        return this;
    }

    public void setBoomgroep(String boomgroep) {
        this.boomgroep = boomgroep;
    }

    public String getBoomhoogteactueel() {
        return this.boomhoogteactueel;
    }

    public Boom boomhoogteactueel(String boomhoogteactueel) {
        this.setBoomhoogteactueel(boomhoogteactueel);
        return this;
    }

    public void setBoomhoogteactueel(String boomhoogteactueel) {
        this.boomhoogteactueel = boomhoogteactueel;
    }

    public String getBoomhoogteklasseactueel() {
        return this.boomhoogteklasseactueel;
    }

    public Boom boomhoogteklasseactueel(String boomhoogteklasseactueel) {
        this.setBoomhoogteklasseactueel(boomhoogteklasseactueel);
        return this;
    }

    public void setBoomhoogteklasseactueel(String boomhoogteklasseactueel) {
        this.boomhoogteklasseactueel = boomhoogteklasseactueel;
    }

    public String getBoomhoogteklasseeindebeeld() {
        return this.boomhoogteklasseeindebeeld;
    }

    public Boom boomhoogteklasseeindebeeld(String boomhoogteklasseeindebeeld) {
        this.setBoomhoogteklasseeindebeeld(boomhoogteklasseeindebeeld);
        return this;
    }

    public void setBoomhoogteklasseeindebeeld(String boomhoogteklasseeindebeeld) {
        this.boomhoogteklasseeindebeeld = boomhoogteklasseeindebeeld;
    }

    public String getBoomspiegel() {
        return this.boomspiegel;
    }

    public Boom boomspiegel(String boomspiegel) {
        this.setBoomspiegel(boomspiegel);
        return this;
    }

    public void setBoomspiegel(String boomspiegel) {
        this.boomspiegel = boomspiegel;
    }

    public String getBoomtypebeschermingsstatusplus() {
        return this.boomtypebeschermingsstatusplus;
    }

    public Boom boomtypebeschermingsstatusplus(String boomtypebeschermingsstatusplus) {
        this.setBoomtypebeschermingsstatusplus(boomtypebeschermingsstatusplus);
        return this;
    }

    public void setBoomtypebeschermingsstatusplus(String boomtypebeschermingsstatusplus) {
        this.boomtypebeschermingsstatusplus = boomtypebeschermingsstatusplus;
    }

    public String getBoomvoorziening() {
        return this.boomvoorziening;
    }

    public Boom boomvoorziening(String boomvoorziening) {
        this.setBoomvoorziening(boomvoorziening);
        return this;
    }

    public void setBoomvoorziening(String boomvoorziening) {
        this.boomvoorziening = boomvoorziening;
    }

    public String getControlefrequentie() {
        return this.controlefrequentie;
    }

    public Boom controlefrequentie(String controlefrequentie) {
        this.setControlefrequentie(controlefrequentie);
        return this;
    }

    public void setControlefrequentie(String controlefrequentie) {
        this.controlefrequentie = controlefrequentie;
    }

    public String getFeestverlichting() {
        return this.feestverlichting;
    }

    public Boom feestverlichting(String feestverlichting) {
        this.setFeestverlichting(feestverlichting);
        return this;
    }

    public void setFeestverlichting(String feestverlichting) {
        this.feestverlichting = feestverlichting;
    }

    public String getGroeifase() {
        return this.groeifase;
    }

    public Boom groeifase(String groeifase) {
        this.setGroeifase(groeifase);
        return this;
    }

    public void setGroeifase(String groeifase) {
        this.groeifase = groeifase;
    }

    public String getGroeiplaatsinrichting() {
        return this.groeiplaatsinrichting;
    }

    public Boom groeiplaatsinrichting(String groeiplaatsinrichting) {
        this.setGroeiplaatsinrichting(groeiplaatsinrichting);
        return this;
    }

    public void setGroeiplaatsinrichting(String groeiplaatsinrichting) {
        this.groeiplaatsinrichting = groeiplaatsinrichting;
    }

    public Boolean getHerplantplicht() {
        return this.herplantplicht;
    }

    public Boom herplantplicht(Boolean herplantplicht) {
        this.setHerplantplicht(herplantplicht);
        return this;
    }

    public void setHerplantplicht(Boolean herplantplicht) {
        this.herplantplicht = herplantplicht;
    }

    public String getKiemjaar() {
        return this.kiemjaar;
    }

    public Boom kiemjaar(String kiemjaar) {
        this.setKiemjaar(kiemjaar);
        return this;
    }

    public void setKiemjaar(String kiemjaar) {
        this.kiemjaar = kiemjaar;
    }

    public String getKroondiameterklasseactueel() {
        return this.kroondiameterklasseactueel;
    }

    public Boom kroondiameterklasseactueel(String kroondiameterklasseactueel) {
        this.setKroondiameterklasseactueel(kroondiameterklasseactueel);
        return this;
    }

    public void setKroondiameterklasseactueel(String kroondiameterklasseactueel) {
        this.kroondiameterklasseactueel = kroondiameterklasseactueel;
    }

    public String getKroondiameterklasseeindebeeld() {
        return this.kroondiameterklasseeindebeeld;
    }

    public Boom kroondiameterklasseeindebeeld(String kroondiameterklasseeindebeeld) {
        this.setKroondiameterklasseeindebeeld(kroondiameterklasseeindebeeld);
        return this;
    }

    public void setKroondiameterklasseeindebeeld(String kroondiameterklasseeindebeeld) {
        this.kroondiameterklasseeindebeeld = kroondiameterklasseeindebeeld;
    }

    public String getKroonvolume() {
        return this.kroonvolume;
    }

    public Boom kroonvolume(String kroonvolume) {
        this.setKroonvolume(kroonvolume);
        return this;
    }

    public void setKroonvolume(String kroonvolume) {
        this.kroonvolume = kroonvolume;
    }

    public String getLeeftijd() {
        return this.leeftijd;
    }

    public Boom leeftijd(String leeftijd) {
        this.setLeeftijd(leeftijd);
        return this;
    }

    public void setLeeftijd(String leeftijd) {
        this.leeftijd = leeftijd;
    }

    public Boolean getMeerstammig() {
        return this.meerstammig;
    }

    public Boom meerstammig(Boolean meerstammig) {
        this.setMeerstammig(meerstammig);
        return this;
    }

    public void setMeerstammig(Boolean meerstammig) {
        this.meerstammig = meerstammig;
    }

    public String getMonetaireboomwaarde() {
        return this.monetaireboomwaarde;
    }

    public Boom monetaireboomwaarde(String monetaireboomwaarde) {
        this.setMonetaireboomwaarde(monetaireboomwaarde);
        return this;
    }

    public void setMonetaireboomwaarde(String monetaireboomwaarde) {
        this.monetaireboomwaarde = monetaireboomwaarde;
    }

    public String getSnoeifase() {
        return this.snoeifase;
    }

    public Boom snoeifase(String snoeifase) {
        this.setSnoeifase(snoeifase);
        return this;
    }

    public void setSnoeifase(String snoeifase) {
        this.snoeifase = snoeifase;
    }

    public String getStamdiameter() {
        return this.stamdiameter;
    }

    public Boom stamdiameter(String stamdiameter) {
        this.setStamdiameter(stamdiameter);
        return this;
    }

    public void setStamdiameter(String stamdiameter) {
        this.stamdiameter = stamdiameter;
    }

    public String getStamdiameterklasse() {
        return this.stamdiameterklasse;
    }

    public Boom stamdiameterklasse(String stamdiameterklasse) {
        this.setStamdiameterklasse(stamdiameterklasse);
        return this;
    }

    public void setStamdiameterklasse(String stamdiameterklasse) {
        this.stamdiameterklasse = stamdiameterklasse;
    }

    public String getTakvrijeruimtetotgebouw() {
        return this.takvrijeruimtetotgebouw;
    }

    public Boom takvrijeruimtetotgebouw(String takvrijeruimtetotgebouw) {
        this.setTakvrijeruimtetotgebouw(takvrijeruimtetotgebouw);
        return this;
    }

    public void setTakvrijeruimtetotgebouw(String takvrijeruimtetotgebouw) {
        this.takvrijeruimtetotgebouw = takvrijeruimtetotgebouw;
    }

    public String getTakvrijestam() {
        return this.takvrijestam;
    }

    public Boom takvrijestam(String takvrijestam) {
        this.setTakvrijestam(takvrijestam);
        return this;
    }

    public void setTakvrijestam(String takvrijestam) {
        this.takvrijestam = takvrijestam;
    }

    public String getTakvrijezoneprimair() {
        return this.takvrijezoneprimair;
    }

    public Boom takvrijezoneprimair(String takvrijezoneprimair) {
        this.setTakvrijezoneprimair(takvrijezoneprimair);
        return this;
    }

    public void setTakvrijezoneprimair(String takvrijezoneprimair) {
        this.takvrijezoneprimair = takvrijezoneprimair;
    }

    public String getTakvrijezonesecundair() {
        return this.takvrijezonesecundair;
    }

    public Boom takvrijezonesecundair(String takvrijezonesecundair) {
        this.setTakvrijezonesecundair(takvrijezonesecundair);
        return this;
    }

    public void setTakvrijezonesecundair(String takvrijezonesecundair) {
        this.takvrijezonesecundair = takvrijezonesecundair;
    }

    public String getTransponder() {
        return this.transponder;
    }

    public Boom transponder(String transponder) {
        this.setTransponder(transponder);
        return this;
    }

    public void setTransponder(String transponder) {
        this.transponder = transponder;
    }

    public String getType() {
        return this.type;
    }

    public Boom type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypebeschermingsstatus() {
        return this.typebeschermingsstatus;
    }

    public Boom typebeschermingsstatus(String typebeschermingsstatus) {
        this.setTypebeschermingsstatus(typebeschermingsstatus);
        return this;
    }

    public void setTypebeschermingsstatus(String typebeschermingsstatus) {
        this.typebeschermingsstatus = typebeschermingsstatus;
    }

    public String getTypeomgevingsrisicoklasse() {
        return this.typeomgevingsrisicoklasse;
    }

    public Boom typeomgevingsrisicoklasse(String typeomgevingsrisicoklasse) {
        this.setTypeomgevingsrisicoklasse(typeomgevingsrisicoklasse);
        return this;
    }

    public void setTypeomgevingsrisicoklasse(String typeomgevingsrisicoklasse) {
        this.typeomgevingsrisicoklasse = typeomgevingsrisicoklasse;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Boom typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypevermeerderingsvorm() {
        return this.typevermeerderingsvorm;
    }

    public Boom typevermeerderingsvorm(String typevermeerderingsvorm) {
        this.setTypevermeerderingsvorm(typevermeerderingsvorm);
        return this;
    }

    public void setTypevermeerderingsvorm(String typevermeerderingsvorm) {
        this.typevermeerderingsvorm = typevermeerderingsvorm;
    }

    public String getVeiligheidsklasseboom() {
        return this.veiligheidsklasseboom;
    }

    public Boom veiligheidsklasseboom(String veiligheidsklasseboom) {
        this.setVeiligheidsklasseboom(veiligheidsklasseboom);
        return this;
    }

    public void setVeiligheidsklasseboom(String veiligheidsklasseboom) {
        this.veiligheidsklasseboom = veiligheidsklasseboom;
    }

    public Boolean getVerplant() {
        return this.verplant;
    }

    public Boom verplant(Boolean verplant) {
        this.setVerplant(verplant);
        return this;
    }

    public void setVerplant(Boolean verplant) {
        this.verplant = verplant;
    }

    public Boolean getVerplantbaar() {
        return this.verplantbaar;
    }

    public Boom verplantbaar(Boolean verplantbaar) {
        this.setVerplantbaar(verplantbaar);
        return this;
    }

    public void setVerplantbaar(Boolean verplantbaar) {
        this.verplantbaar = verplantbaar;
    }

    public String getVrijedoorrijhoogte() {
        return this.vrijedoorrijhoogte;
    }

    public Boom vrijedoorrijhoogte(String vrijedoorrijhoogte) {
        this.setVrijedoorrijhoogte(vrijedoorrijhoogte);
        return this;
    }

    public void setVrijedoorrijhoogte(String vrijedoorrijhoogte) {
        this.vrijedoorrijhoogte = vrijedoorrijhoogte;
    }

    public String getVrijedoorrijhoogteprimair() {
        return this.vrijedoorrijhoogteprimair;
    }

    public Boom vrijedoorrijhoogteprimair(String vrijedoorrijhoogteprimair) {
        this.setVrijedoorrijhoogteprimair(vrijedoorrijhoogteprimair);
        return this;
    }

    public void setVrijedoorrijhoogteprimair(String vrijedoorrijhoogteprimair) {
        this.vrijedoorrijhoogteprimair = vrijedoorrijhoogteprimair;
    }

    public String getVrijedoorrijhoogtesecundair() {
        return this.vrijedoorrijhoogtesecundair;
    }

    public Boom vrijedoorrijhoogtesecundair(String vrijedoorrijhoogtesecundair) {
        this.setVrijedoorrijhoogtesecundair(vrijedoorrijhoogtesecundair);
        return this;
    }

    public void setVrijedoorrijhoogtesecundair(String vrijedoorrijhoogtesecundair) {
        this.vrijedoorrijhoogtesecundair = vrijedoorrijhoogtesecundair;
    }

    public String getVrijetakval() {
        return this.vrijetakval;
    }

    public Boom vrijetakval(String vrijetakval) {
        this.setVrijetakval(vrijetakval);
        return this;
    }

    public void setVrijetakval(String vrijetakval) {
        this.vrijetakval = vrijetakval;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boom)) {
            return false;
        }
        return getId() != null && getId().equals(((Boom) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Boom{" +
            "id=" + getId() +
            ", beleidsstatus='" + getBeleidsstatus() + "'" +
            ", beoogdeomlooptijd='" + getBeoogdeomlooptijd() + "'" +
            ", boombeeld='" + getBoombeeld() + "'" +
            ", boombeschermer='" + getBoombeschermer() + "'" +
            ", boomgroep='" + getBoomgroep() + "'" +
            ", boomhoogteactueel='" + getBoomhoogteactueel() + "'" +
            ", boomhoogteklasseactueel='" + getBoomhoogteklasseactueel() + "'" +
            ", boomhoogteklasseeindebeeld='" + getBoomhoogteklasseeindebeeld() + "'" +
            ", boomspiegel='" + getBoomspiegel() + "'" +
            ", boomtypebeschermingsstatusplus='" + getBoomtypebeschermingsstatusplus() + "'" +
            ", boomvoorziening='" + getBoomvoorziening() + "'" +
            ", controlefrequentie='" + getControlefrequentie() + "'" +
            ", feestverlichting='" + getFeestverlichting() + "'" +
            ", groeifase='" + getGroeifase() + "'" +
            ", groeiplaatsinrichting='" + getGroeiplaatsinrichting() + "'" +
            ", herplantplicht='" + getHerplantplicht() + "'" +
            ", kiemjaar='" + getKiemjaar() + "'" +
            ", kroondiameterklasseactueel='" + getKroondiameterklasseactueel() + "'" +
            ", kroondiameterklasseeindebeeld='" + getKroondiameterklasseeindebeeld() + "'" +
            ", kroonvolume='" + getKroonvolume() + "'" +
            ", leeftijd='" + getLeeftijd() + "'" +
            ", meerstammig='" + getMeerstammig() + "'" +
            ", monetaireboomwaarde='" + getMonetaireboomwaarde() + "'" +
            ", snoeifase='" + getSnoeifase() + "'" +
            ", stamdiameter='" + getStamdiameter() + "'" +
            ", stamdiameterklasse='" + getStamdiameterklasse() + "'" +
            ", takvrijeruimtetotgebouw='" + getTakvrijeruimtetotgebouw() + "'" +
            ", takvrijestam='" + getTakvrijestam() + "'" +
            ", takvrijezoneprimair='" + getTakvrijezoneprimair() + "'" +
            ", takvrijezonesecundair='" + getTakvrijezonesecundair() + "'" +
            ", transponder='" + getTransponder() + "'" +
            ", type='" + getType() + "'" +
            ", typebeschermingsstatus='" + getTypebeschermingsstatus() + "'" +
            ", typeomgevingsrisicoklasse='" + getTypeomgevingsrisicoklasse() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typevermeerderingsvorm='" + getTypevermeerderingsvorm() + "'" +
            ", veiligheidsklasseboom='" + getVeiligheidsklasseboom() + "'" +
            ", verplant='" + getVerplant() + "'" +
            ", verplantbaar='" + getVerplantbaar() + "'" +
            ", vrijedoorrijhoogte='" + getVrijedoorrijhoogte() + "'" +
            ", vrijedoorrijhoogteprimair='" + getVrijedoorrijhoogteprimair() + "'" +
            ", vrijedoorrijhoogtesecundair='" + getVrijedoorrijhoogtesecundair() + "'" +
            ", vrijetakval='" + getVrijetakval() + "'" +
            "}";
    }
}
