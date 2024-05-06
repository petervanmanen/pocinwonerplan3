package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Beheerobject.
 */
@Entity
@Table(name = "beheerobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beheerobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Column(name = "begingarantieperiode")
    private LocalDate begingarantieperiode;

    @Column(name = "beheergebied")
    private String beheergebied;

    @Column(name = "beheerobjectbeheervak")
    private String beheerobjectbeheervak;

    @Column(name = "beheerobjectgebruiksfunctie")
    private String beheerobjectgebruiksfunctie;

    @Column(name = "beheerobjectmemo")
    private String beheerobjectmemo;

    @Column(name = "beschermdefloraenfauna")
    private Boolean beschermdefloraenfauna;

    @Column(name = "buurt")
    private String buurt;

    @Column(name = "conversieid")
    private String conversieid;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Column(name = "datumoplevering")
    private LocalDate datumoplevering;

    @Column(name = "datumpublicatielv")
    private LocalDate datumpublicatielv;

    @Column(name = "datumverwijdering")
    private LocalDate datumverwijdering;

    @Column(name = "eindegarantieperiode")
    private LocalDate eindegarantieperiode;

    @Column(name = "gebiedstype")
    private String gebiedstype;

    @Column(name = "gemeente")
    private String gemeente;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "gewijzigddoor")
    private String gewijzigddoor;

    @Column(name = "grondsoort")
    private String grondsoort;

    @Column(name = "grondsoortplus")
    private String grondsoortplus;

    @Column(name = "identificatieimbor")
    private String identificatieimbor;

    @Column(name = "identificatieimgeo")
    private String identificatieimgeo;

    @Column(name = "jaarvanaanleg")
    private String jaarvanaanleg;

    @Column(name = "eobjectbegintijd")
    private String eobjectbegintijd;

    @Column(name = "eobjecteindtijd")
    private String eobjecteindtijd;

    @Column(name = "onderhoudsplichtige")
    private String onderhoudsplichtige;

    @Column(name = "openbareruimte")
    private String openbareruimte;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "relatievehoogteligging")
    private String relatievehoogteligging;

    @Column(name = "stadsdeel")
    private String stadsdeel;

    @Column(name = "status")
    private String status;

    @Column(name = "theoretischeindejaar")
    private String theoretischeindejaar;

    @Column(name = "tijdstipregistratie")
    private String tijdstipregistratie;

    @Column(name = "typebeheerder")
    private String typebeheerder;

    @Column(name = "typebeheerderplus")
    private String typebeheerderplus;

    @Column(name = "typeeigenaar")
    private String typeeigenaar;

    @Column(name = "typeeigenaarplus")
    private String typeeigenaarplus;

    @Column(name = "typeligging")
    private String typeligging;

    @Column(name = "waterschap")
    private String waterschap;

    @Column(name = "wijk")
    private String wijk;

    @Column(name = "woonplaats")
    private String woonplaats;

    @Column(name = "zettingsgevoeligheid")
    private String zettingsgevoeligheid;

    @Column(name = "zettingsgevoeligheidplus")
    private String zettingsgevoeligheidplus;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftBeheerobjects")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> betreftMeldings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beheerobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Beheerobject aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public LocalDate getBegingarantieperiode() {
        return this.begingarantieperiode;
    }

    public Beheerobject begingarantieperiode(LocalDate begingarantieperiode) {
        this.setBegingarantieperiode(begingarantieperiode);
        return this;
    }

    public void setBegingarantieperiode(LocalDate begingarantieperiode) {
        this.begingarantieperiode = begingarantieperiode;
    }

    public String getBeheergebied() {
        return this.beheergebied;
    }

    public Beheerobject beheergebied(String beheergebied) {
        this.setBeheergebied(beheergebied);
        return this;
    }

    public void setBeheergebied(String beheergebied) {
        this.beheergebied = beheergebied;
    }

    public String getBeheerobjectbeheervak() {
        return this.beheerobjectbeheervak;
    }

    public Beheerobject beheerobjectbeheervak(String beheerobjectbeheervak) {
        this.setBeheerobjectbeheervak(beheerobjectbeheervak);
        return this;
    }

    public void setBeheerobjectbeheervak(String beheerobjectbeheervak) {
        this.beheerobjectbeheervak = beheerobjectbeheervak;
    }

    public String getBeheerobjectgebruiksfunctie() {
        return this.beheerobjectgebruiksfunctie;
    }

    public Beheerobject beheerobjectgebruiksfunctie(String beheerobjectgebruiksfunctie) {
        this.setBeheerobjectgebruiksfunctie(beheerobjectgebruiksfunctie);
        return this;
    }

    public void setBeheerobjectgebruiksfunctie(String beheerobjectgebruiksfunctie) {
        this.beheerobjectgebruiksfunctie = beheerobjectgebruiksfunctie;
    }

    public String getBeheerobjectmemo() {
        return this.beheerobjectmemo;
    }

    public Beheerobject beheerobjectmemo(String beheerobjectmemo) {
        this.setBeheerobjectmemo(beheerobjectmemo);
        return this;
    }

    public void setBeheerobjectmemo(String beheerobjectmemo) {
        this.beheerobjectmemo = beheerobjectmemo;
    }

    public Boolean getBeschermdefloraenfauna() {
        return this.beschermdefloraenfauna;
    }

    public Beheerobject beschermdefloraenfauna(Boolean beschermdefloraenfauna) {
        this.setBeschermdefloraenfauna(beschermdefloraenfauna);
        return this;
    }

    public void setBeschermdefloraenfauna(Boolean beschermdefloraenfauna) {
        this.beschermdefloraenfauna = beschermdefloraenfauna;
    }

    public String getBuurt() {
        return this.buurt;
    }

    public Beheerobject buurt(String buurt) {
        this.setBuurt(buurt);
        return this;
    }

    public void setBuurt(String buurt) {
        this.buurt = buurt;
    }

    public String getConversieid() {
        return this.conversieid;
    }

    public Beheerobject conversieid(String conversieid) {
        this.setConversieid(conversieid);
        return this;
    }

    public void setConversieid(String conversieid) {
        this.conversieid = conversieid;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Beheerobject datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public LocalDate getDatumoplevering() {
        return this.datumoplevering;
    }

    public Beheerobject datumoplevering(LocalDate datumoplevering) {
        this.setDatumoplevering(datumoplevering);
        return this;
    }

    public void setDatumoplevering(LocalDate datumoplevering) {
        this.datumoplevering = datumoplevering;
    }

    public LocalDate getDatumpublicatielv() {
        return this.datumpublicatielv;
    }

    public Beheerobject datumpublicatielv(LocalDate datumpublicatielv) {
        this.setDatumpublicatielv(datumpublicatielv);
        return this;
    }

    public void setDatumpublicatielv(LocalDate datumpublicatielv) {
        this.datumpublicatielv = datumpublicatielv;
    }

    public LocalDate getDatumverwijdering() {
        return this.datumverwijdering;
    }

    public Beheerobject datumverwijdering(LocalDate datumverwijdering) {
        this.setDatumverwijdering(datumverwijdering);
        return this;
    }

    public void setDatumverwijdering(LocalDate datumverwijdering) {
        this.datumverwijdering = datumverwijdering;
    }

    public LocalDate getEindegarantieperiode() {
        return this.eindegarantieperiode;
    }

    public Beheerobject eindegarantieperiode(LocalDate eindegarantieperiode) {
        this.setEindegarantieperiode(eindegarantieperiode);
        return this;
    }

    public void setEindegarantieperiode(LocalDate eindegarantieperiode) {
        this.eindegarantieperiode = eindegarantieperiode;
    }

    public String getGebiedstype() {
        return this.gebiedstype;
    }

    public Beheerobject gebiedstype(String gebiedstype) {
        this.setGebiedstype(gebiedstype);
        return this;
    }

    public void setGebiedstype(String gebiedstype) {
        this.gebiedstype = gebiedstype;
    }

    public String getGemeente() {
        return this.gemeente;
    }

    public Beheerobject gemeente(String gemeente) {
        this.setGemeente(gemeente);
        return this;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Beheerobject geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getGewijzigddoor() {
        return this.gewijzigddoor;
    }

    public Beheerobject gewijzigddoor(String gewijzigddoor) {
        this.setGewijzigddoor(gewijzigddoor);
        return this;
    }

    public void setGewijzigddoor(String gewijzigddoor) {
        this.gewijzigddoor = gewijzigddoor;
    }

    public String getGrondsoort() {
        return this.grondsoort;
    }

    public Beheerobject grondsoort(String grondsoort) {
        this.setGrondsoort(grondsoort);
        return this;
    }

    public void setGrondsoort(String grondsoort) {
        this.grondsoort = grondsoort;
    }

    public String getGrondsoortplus() {
        return this.grondsoortplus;
    }

    public Beheerobject grondsoortplus(String grondsoortplus) {
        this.setGrondsoortplus(grondsoortplus);
        return this;
    }

    public void setGrondsoortplus(String grondsoortplus) {
        this.grondsoortplus = grondsoortplus;
    }

    public String getIdentificatieimbor() {
        return this.identificatieimbor;
    }

    public Beheerobject identificatieimbor(String identificatieimbor) {
        this.setIdentificatieimbor(identificatieimbor);
        return this;
    }

    public void setIdentificatieimbor(String identificatieimbor) {
        this.identificatieimbor = identificatieimbor;
    }

    public String getIdentificatieimgeo() {
        return this.identificatieimgeo;
    }

    public Beheerobject identificatieimgeo(String identificatieimgeo) {
        this.setIdentificatieimgeo(identificatieimgeo);
        return this;
    }

    public void setIdentificatieimgeo(String identificatieimgeo) {
        this.identificatieimgeo = identificatieimgeo;
    }

    public String getJaarvanaanleg() {
        return this.jaarvanaanleg;
    }

    public Beheerobject jaarvanaanleg(String jaarvanaanleg) {
        this.setJaarvanaanleg(jaarvanaanleg);
        return this;
    }

    public void setJaarvanaanleg(String jaarvanaanleg) {
        this.jaarvanaanleg = jaarvanaanleg;
    }

    public String getEobjectbegintijd() {
        return this.eobjectbegintijd;
    }

    public Beheerobject eobjectbegintijd(String eobjectbegintijd) {
        this.setEobjectbegintijd(eobjectbegintijd);
        return this;
    }

    public void setEobjectbegintijd(String eobjectbegintijd) {
        this.eobjectbegintijd = eobjectbegintijd;
    }

    public String getEobjecteindtijd() {
        return this.eobjecteindtijd;
    }

    public Beheerobject eobjecteindtijd(String eobjecteindtijd) {
        this.setEobjecteindtijd(eobjecteindtijd);
        return this;
    }

    public void setEobjecteindtijd(String eobjecteindtijd) {
        this.eobjecteindtijd = eobjecteindtijd;
    }

    public String getOnderhoudsplichtige() {
        return this.onderhoudsplichtige;
    }

    public Beheerobject onderhoudsplichtige(String onderhoudsplichtige) {
        this.setOnderhoudsplichtige(onderhoudsplichtige);
        return this;
    }

    public void setOnderhoudsplichtige(String onderhoudsplichtige) {
        this.onderhoudsplichtige = onderhoudsplichtige;
    }

    public String getOpenbareruimte() {
        return this.openbareruimte;
    }

    public Beheerobject openbareruimte(String openbareruimte) {
        this.setOpenbareruimte(openbareruimte);
        return this;
    }

    public void setOpenbareruimte(String openbareruimte) {
        this.openbareruimte = openbareruimte;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Beheerobject postcode(String postcode) {
        this.setPostcode(postcode);
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRelatievehoogteligging() {
        return this.relatievehoogteligging;
    }

    public Beheerobject relatievehoogteligging(String relatievehoogteligging) {
        this.setRelatievehoogteligging(relatievehoogteligging);
        return this;
    }

    public void setRelatievehoogteligging(String relatievehoogteligging) {
        this.relatievehoogteligging = relatievehoogteligging;
    }

    public String getStadsdeel() {
        return this.stadsdeel;
    }

    public Beheerobject stadsdeel(String stadsdeel) {
        this.setStadsdeel(stadsdeel);
        return this;
    }

    public void setStadsdeel(String stadsdeel) {
        this.stadsdeel = stadsdeel;
    }

    public String getStatus() {
        return this.status;
    }

    public Beheerobject status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTheoretischeindejaar() {
        return this.theoretischeindejaar;
    }

    public Beheerobject theoretischeindejaar(String theoretischeindejaar) {
        this.setTheoretischeindejaar(theoretischeindejaar);
        return this;
    }

    public void setTheoretischeindejaar(String theoretischeindejaar) {
        this.theoretischeindejaar = theoretischeindejaar;
    }

    public String getTijdstipregistratie() {
        return this.tijdstipregistratie;
    }

    public Beheerobject tijdstipregistratie(String tijdstipregistratie) {
        this.setTijdstipregistratie(tijdstipregistratie);
        return this;
    }

    public void setTijdstipregistratie(String tijdstipregistratie) {
        this.tijdstipregistratie = tijdstipregistratie;
    }

    public String getTypebeheerder() {
        return this.typebeheerder;
    }

    public Beheerobject typebeheerder(String typebeheerder) {
        this.setTypebeheerder(typebeheerder);
        return this;
    }

    public void setTypebeheerder(String typebeheerder) {
        this.typebeheerder = typebeheerder;
    }

    public String getTypebeheerderplus() {
        return this.typebeheerderplus;
    }

    public Beheerobject typebeheerderplus(String typebeheerderplus) {
        this.setTypebeheerderplus(typebeheerderplus);
        return this;
    }

    public void setTypebeheerderplus(String typebeheerderplus) {
        this.typebeheerderplus = typebeheerderplus;
    }

    public String getTypeeigenaar() {
        return this.typeeigenaar;
    }

    public Beheerobject typeeigenaar(String typeeigenaar) {
        this.setTypeeigenaar(typeeigenaar);
        return this;
    }

    public void setTypeeigenaar(String typeeigenaar) {
        this.typeeigenaar = typeeigenaar;
    }

    public String getTypeeigenaarplus() {
        return this.typeeigenaarplus;
    }

    public Beheerobject typeeigenaarplus(String typeeigenaarplus) {
        this.setTypeeigenaarplus(typeeigenaarplus);
        return this;
    }

    public void setTypeeigenaarplus(String typeeigenaarplus) {
        this.typeeigenaarplus = typeeigenaarplus;
    }

    public String getTypeligging() {
        return this.typeligging;
    }

    public Beheerobject typeligging(String typeligging) {
        this.setTypeligging(typeligging);
        return this;
    }

    public void setTypeligging(String typeligging) {
        this.typeligging = typeligging;
    }

    public String getWaterschap() {
        return this.waterschap;
    }

    public Beheerobject waterschap(String waterschap) {
        this.setWaterschap(waterschap);
        return this;
    }

    public void setWaterschap(String waterschap) {
        this.waterschap = waterschap;
    }

    public String getWijk() {
        return this.wijk;
    }

    public Beheerobject wijk(String wijk) {
        this.setWijk(wijk);
        return this;
    }

    public void setWijk(String wijk) {
        this.wijk = wijk;
    }

    public String getWoonplaats() {
        return this.woonplaats;
    }

    public Beheerobject woonplaats(String woonplaats) {
        this.setWoonplaats(woonplaats);
        return this;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getZettingsgevoeligheid() {
        return this.zettingsgevoeligheid;
    }

    public Beheerobject zettingsgevoeligheid(String zettingsgevoeligheid) {
        this.setZettingsgevoeligheid(zettingsgevoeligheid);
        return this;
    }

    public void setZettingsgevoeligheid(String zettingsgevoeligheid) {
        this.zettingsgevoeligheid = zettingsgevoeligheid;
    }

    public String getZettingsgevoeligheidplus() {
        return this.zettingsgevoeligheidplus;
    }

    public Beheerobject zettingsgevoeligheidplus(String zettingsgevoeligheidplus) {
        this.setZettingsgevoeligheidplus(zettingsgevoeligheidplus);
        return this;
    }

    public void setZettingsgevoeligheidplus(String zettingsgevoeligheidplus) {
        this.zettingsgevoeligheidplus = zettingsgevoeligheidplus;
    }

    public Set<Melding> getBetreftMeldings() {
        return this.betreftMeldings;
    }

    public void setBetreftMeldings(Set<Melding> meldings) {
        if (this.betreftMeldings != null) {
            this.betreftMeldings.forEach(i -> i.removeBetreftBeheerobject(this));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.addBetreftBeheerobject(this));
        }
        this.betreftMeldings = meldings;
    }

    public Beheerobject betreftMeldings(Set<Melding> meldings) {
        this.setBetreftMeldings(meldings);
        return this;
    }

    public Beheerobject addBetreftMelding(Melding melding) {
        this.betreftMeldings.add(melding);
        melding.getBetreftBeheerobjects().add(this);
        return this;
    }

    public Beheerobject removeBetreftMelding(Melding melding) {
        this.betreftMeldings.remove(melding);
        melding.getBetreftBeheerobjects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beheerobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Beheerobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beheerobject{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", begingarantieperiode='" + getBegingarantieperiode() + "'" +
            ", beheergebied='" + getBeheergebied() + "'" +
            ", beheerobjectbeheervak='" + getBeheerobjectbeheervak() + "'" +
            ", beheerobjectgebruiksfunctie='" + getBeheerobjectgebruiksfunctie() + "'" +
            ", beheerobjectmemo='" + getBeheerobjectmemo() + "'" +
            ", beschermdefloraenfauna='" + getBeschermdefloraenfauna() + "'" +
            ", buurt='" + getBuurt() + "'" +
            ", conversieid='" + getConversieid() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", datumoplevering='" + getDatumoplevering() + "'" +
            ", datumpublicatielv='" + getDatumpublicatielv() + "'" +
            ", datumverwijdering='" + getDatumverwijdering() + "'" +
            ", eindegarantieperiode='" + getEindegarantieperiode() + "'" +
            ", gebiedstype='" + getGebiedstype() + "'" +
            ", gemeente='" + getGemeente() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", gewijzigddoor='" + getGewijzigddoor() + "'" +
            ", grondsoort='" + getGrondsoort() + "'" +
            ", grondsoortplus='" + getGrondsoortplus() + "'" +
            ", identificatieimbor='" + getIdentificatieimbor() + "'" +
            ", identificatieimgeo='" + getIdentificatieimgeo() + "'" +
            ", jaarvanaanleg='" + getJaarvanaanleg() + "'" +
            ", eobjectbegintijd='" + getEobjectbegintijd() + "'" +
            ", eobjecteindtijd='" + getEobjecteindtijd() + "'" +
            ", onderhoudsplichtige='" + getOnderhoudsplichtige() + "'" +
            ", openbareruimte='" + getOpenbareruimte() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", relatievehoogteligging='" + getRelatievehoogteligging() + "'" +
            ", stadsdeel='" + getStadsdeel() + "'" +
            ", status='" + getStatus() + "'" +
            ", theoretischeindejaar='" + getTheoretischeindejaar() + "'" +
            ", tijdstipregistratie='" + getTijdstipregistratie() + "'" +
            ", typebeheerder='" + getTypebeheerder() + "'" +
            ", typebeheerderplus='" + getTypebeheerderplus() + "'" +
            ", typeeigenaar='" + getTypeeigenaar() + "'" +
            ", typeeigenaarplus='" + getTypeeigenaarplus() + "'" +
            ", typeligging='" + getTypeligging() + "'" +
            ", waterschap='" + getWaterschap() + "'" +
            ", wijk='" + getWijk() + "'" +
            ", woonplaats='" + getWoonplaats() + "'" +
            ", zettingsgevoeligheid='" + getZettingsgevoeligheid() + "'" +
            ", zettingsgevoeligheidplus='" + getZettingsgevoeligheidplus() + "'" +
            "}";
    }
}
