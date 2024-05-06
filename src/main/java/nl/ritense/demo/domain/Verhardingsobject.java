package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Verhardingsobject.
 */
@Entity
@Table(name = "verhardingsobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verhardingsobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "aanofvrijliggend")
    private String aanofvrijliggend;

    @Column(name = "aantaldeklagen")
    private String aantaldeklagen;

    @Column(name = "aantalonderlagen")
    private String aantalonderlagen;

    @Column(name = "aantaltussenlagen")
    private String aantaltussenlagen;

    @Column(name = "afmeting")
    private String afmeting;

    @Column(name = "belasting")
    private String belasting;

    @Column(name = "bergendvermogen")
    private String bergendvermogen;

    @Column(name = "bgtfysiekvoorkomen")
    private String bgtfysiekvoorkomen;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "dikteconstructie")
    private String dikteconstructie;

    @Column(name = "draagkrachtig")
    private Boolean draagkrachtig;

    @Column(name = "formaat")
    private String formaat;

    @Column(name = "fysiekvoorkomenimgeo")
    private String fysiekvoorkomenimgeo;

    @Column(name = "geluidsreducerend")
    private Boolean geluidsreducerend;

    @Column(name = "jaarconserveren")
    private String jaarconserveren;

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

    @Column(name = "lengtekunstgras")
    private String lengtekunstgras;

    @Column(name = "lengtevoegen")
    private String lengtevoegen;

    @Column(name = "levensduur")
    private String levensduur;

    @Column(name = "materiaal")
    private String materiaal;

    @Column(name = "maximalevalhoogte")
    private String maximalevalhoogte;

    @Column(name = "omtrek")
    private String omtrek;

    @Size(max = 10)
    @Column(name = "ondergrondcode", length = 10)
    private String ondergrondcode;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "optalud")
    private String optalud;

    @Column(name = "plaatsorientatie")
    private String plaatsorientatie;

    @Column(name = "prijsaanschaf")
    private String prijsaanschaf;

    @Column(name = "rijstrook")
    private String rijstrook;

    @Column(name = "soortvoeg")
    private String soortvoeg;

    @Column(name = "toelichtinggemengdebestrating")
    private String toelichtinggemengdebestrating;

    @Column(name = "type")
    private String type;

    @Column(name = "typeconstructie")
    private String typeconstructie;

    @Column(name = "typefundering")
    private String typefundering;

    @Column(name = "typeplus")
    private String typeplus;

    @Size(max = 100)
    @Column(name = "typeplus_2", length = 100)
    private String typeplus2;

    @Column(name = "typerijstrook")
    private String typerijstrook;

    @Column(name = "typevoeg")
    private String typevoeg;

    @Column(name = "typevoegvulling")
    private String typevoegvulling;

    @Column(name = "vegen")
    private String vegen;

    @Column(name = "verhardingsobjectconstructielaag")
    private String verhardingsobjectconstructielaag;

    @Column(name = "verhardingsobjectmodaliteit")
    private String verhardingsobjectmodaliteit;

    @Column(name = "verhardingsobjectrand")
    private String verhardingsobjectrand;

    @Column(name = "verhardingsobjectwegfunctie")
    private String verhardingsobjectwegfunctie;

    @Column(name = "verhoogdeligging")
    private Boolean verhoogdeligging;

    @Column(name = "vulmateriaalkunstgras")
    private String vulmateriaalkunstgras;

    @Column(name = "waterdoorlatendheid")
    private String waterdoorlatendheid;

    @Column(name = "wegas")
    private String wegas;

    @Column(name = "wegcategoriedv")
    private String wegcategoriedv;

    @Column(name = "wegcategoriedvplus")
    private String wegcategoriedvplus;

    @Size(max = 20)
    @Column(name = "wegnummer", length = 20)
    private String wegnummer;

    @Column(name = "wegtypebestaand")
    private String wegtypebestaand;

    @Column(name = "wegvak")
    private String wegvak;

    @Size(max = 20)
    @Column(name = "wegvaknummer", length = 20)
    private String wegvaknummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verhardingsobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Verhardingsobject aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getAanofvrijliggend() {
        return this.aanofvrijliggend;
    }

    public Verhardingsobject aanofvrijliggend(String aanofvrijliggend) {
        this.setAanofvrijliggend(aanofvrijliggend);
        return this;
    }

    public void setAanofvrijliggend(String aanofvrijliggend) {
        this.aanofvrijliggend = aanofvrijliggend;
    }

    public String getAantaldeklagen() {
        return this.aantaldeklagen;
    }

    public Verhardingsobject aantaldeklagen(String aantaldeklagen) {
        this.setAantaldeklagen(aantaldeklagen);
        return this;
    }

    public void setAantaldeklagen(String aantaldeklagen) {
        this.aantaldeklagen = aantaldeklagen;
    }

    public String getAantalonderlagen() {
        return this.aantalonderlagen;
    }

    public Verhardingsobject aantalonderlagen(String aantalonderlagen) {
        this.setAantalonderlagen(aantalonderlagen);
        return this;
    }

    public void setAantalonderlagen(String aantalonderlagen) {
        this.aantalonderlagen = aantalonderlagen;
    }

    public String getAantaltussenlagen() {
        return this.aantaltussenlagen;
    }

    public Verhardingsobject aantaltussenlagen(String aantaltussenlagen) {
        this.setAantaltussenlagen(aantaltussenlagen);
        return this;
    }

    public void setAantaltussenlagen(String aantaltussenlagen) {
        this.aantaltussenlagen = aantaltussenlagen;
    }

    public String getAfmeting() {
        return this.afmeting;
    }

    public Verhardingsobject afmeting(String afmeting) {
        this.setAfmeting(afmeting);
        return this;
    }

    public void setAfmeting(String afmeting) {
        this.afmeting = afmeting;
    }

    public String getBelasting() {
        return this.belasting;
    }

    public Verhardingsobject belasting(String belasting) {
        this.setBelasting(belasting);
        return this;
    }

    public void setBelasting(String belasting) {
        this.belasting = belasting;
    }

    public String getBergendvermogen() {
        return this.bergendvermogen;
    }

    public Verhardingsobject bergendvermogen(String bergendvermogen) {
        this.setBergendvermogen(bergendvermogen);
        return this;
    }

    public void setBergendvermogen(String bergendvermogen) {
        this.bergendvermogen = bergendvermogen;
    }

    public String getBgtfysiekvoorkomen() {
        return this.bgtfysiekvoorkomen;
    }

    public Verhardingsobject bgtfysiekvoorkomen(String bgtfysiekvoorkomen) {
        this.setBgtfysiekvoorkomen(bgtfysiekvoorkomen);
        return this;
    }

    public void setBgtfysiekvoorkomen(String bgtfysiekvoorkomen) {
        this.bgtfysiekvoorkomen = bgtfysiekvoorkomen;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Verhardingsobject breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getDikteconstructie() {
        return this.dikteconstructie;
    }

    public Verhardingsobject dikteconstructie(String dikteconstructie) {
        this.setDikteconstructie(dikteconstructie);
        return this;
    }

    public void setDikteconstructie(String dikteconstructie) {
        this.dikteconstructie = dikteconstructie;
    }

    public Boolean getDraagkrachtig() {
        return this.draagkrachtig;
    }

    public Verhardingsobject draagkrachtig(Boolean draagkrachtig) {
        this.setDraagkrachtig(draagkrachtig);
        return this;
    }

    public void setDraagkrachtig(Boolean draagkrachtig) {
        this.draagkrachtig = draagkrachtig;
    }

    public String getFormaat() {
        return this.formaat;
    }

    public Verhardingsobject formaat(String formaat) {
        this.setFormaat(formaat);
        return this;
    }

    public void setFormaat(String formaat) {
        this.formaat = formaat;
    }

    public String getFysiekvoorkomenimgeo() {
        return this.fysiekvoorkomenimgeo;
    }

    public Verhardingsobject fysiekvoorkomenimgeo(String fysiekvoorkomenimgeo) {
        this.setFysiekvoorkomenimgeo(fysiekvoorkomenimgeo);
        return this;
    }

    public void setFysiekvoorkomenimgeo(String fysiekvoorkomenimgeo) {
        this.fysiekvoorkomenimgeo = fysiekvoorkomenimgeo;
    }

    public Boolean getGeluidsreducerend() {
        return this.geluidsreducerend;
    }

    public Verhardingsobject geluidsreducerend(Boolean geluidsreducerend) {
        this.setGeluidsreducerend(geluidsreducerend);
        return this;
    }

    public void setGeluidsreducerend(Boolean geluidsreducerend) {
        this.geluidsreducerend = geluidsreducerend;
    }

    public String getJaarconserveren() {
        return this.jaarconserveren;
    }

    public Verhardingsobject jaarconserveren(String jaarconserveren) {
        this.setJaarconserveren(jaarconserveren);
        return this;
    }

    public void setJaarconserveren(String jaarconserveren) {
        this.jaarconserveren = jaarconserveren;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Verhardingsobject jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getJaarpraktischeinde() {
        return this.jaarpraktischeinde;
    }

    public Verhardingsobject jaarpraktischeinde(String jaarpraktischeinde) {
        this.setJaarpraktischeinde(jaarpraktischeinde);
        return this;
    }

    public void setJaarpraktischeinde(String jaarpraktischeinde) {
        this.jaarpraktischeinde = jaarpraktischeinde;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Verhardingsobject kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Verhardingsobject kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Verhardingsobject kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Verhardingsobject lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLengtekunstgras() {
        return this.lengtekunstgras;
    }

    public Verhardingsobject lengtekunstgras(String lengtekunstgras) {
        this.setLengtekunstgras(lengtekunstgras);
        return this;
    }

    public void setLengtekunstgras(String lengtekunstgras) {
        this.lengtekunstgras = lengtekunstgras;
    }

    public String getLengtevoegen() {
        return this.lengtevoegen;
    }

    public Verhardingsobject lengtevoegen(String lengtevoegen) {
        this.setLengtevoegen(lengtevoegen);
        return this;
    }

    public void setLengtevoegen(String lengtevoegen) {
        this.lengtevoegen = lengtevoegen;
    }

    public String getLevensduur() {
        return this.levensduur;
    }

    public Verhardingsobject levensduur(String levensduur) {
        this.setLevensduur(levensduur);
        return this;
    }

    public void setLevensduur(String levensduur) {
        this.levensduur = levensduur;
    }

    public String getMateriaal() {
        return this.materiaal;
    }

    public Verhardingsobject materiaal(String materiaal) {
        this.setMateriaal(materiaal);
        return this;
    }

    public void setMateriaal(String materiaal) {
        this.materiaal = materiaal;
    }

    public String getMaximalevalhoogte() {
        return this.maximalevalhoogte;
    }

    public Verhardingsobject maximalevalhoogte(String maximalevalhoogte) {
        this.setMaximalevalhoogte(maximalevalhoogte);
        return this;
    }

    public void setMaximalevalhoogte(String maximalevalhoogte) {
        this.maximalevalhoogte = maximalevalhoogte;
    }

    public String getOmtrek() {
        return this.omtrek;
    }

    public Verhardingsobject omtrek(String omtrek) {
        this.setOmtrek(omtrek);
        return this;
    }

    public void setOmtrek(String omtrek) {
        this.omtrek = omtrek;
    }

    public String getOndergrondcode() {
        return this.ondergrondcode;
    }

    public Verhardingsobject ondergrondcode(String ondergrondcode) {
        this.setOndergrondcode(ondergrondcode);
        return this;
    }

    public void setOndergrondcode(String ondergrondcode) {
        this.ondergrondcode = ondergrondcode;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Verhardingsobject oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOptalud() {
        return this.optalud;
    }

    public Verhardingsobject optalud(String optalud) {
        this.setOptalud(optalud);
        return this;
    }

    public void setOptalud(String optalud) {
        this.optalud = optalud;
    }

    public String getPlaatsorientatie() {
        return this.plaatsorientatie;
    }

    public Verhardingsobject plaatsorientatie(String plaatsorientatie) {
        this.setPlaatsorientatie(plaatsorientatie);
        return this;
    }

    public void setPlaatsorientatie(String plaatsorientatie) {
        this.plaatsorientatie = plaatsorientatie;
    }

    public String getPrijsaanschaf() {
        return this.prijsaanschaf;
    }

    public Verhardingsobject prijsaanschaf(String prijsaanschaf) {
        this.setPrijsaanschaf(prijsaanschaf);
        return this;
    }

    public void setPrijsaanschaf(String prijsaanschaf) {
        this.prijsaanschaf = prijsaanschaf;
    }

    public String getRijstrook() {
        return this.rijstrook;
    }

    public Verhardingsobject rijstrook(String rijstrook) {
        this.setRijstrook(rijstrook);
        return this;
    }

    public void setRijstrook(String rijstrook) {
        this.rijstrook = rijstrook;
    }

    public String getSoortvoeg() {
        return this.soortvoeg;
    }

    public Verhardingsobject soortvoeg(String soortvoeg) {
        this.setSoortvoeg(soortvoeg);
        return this;
    }

    public void setSoortvoeg(String soortvoeg) {
        this.soortvoeg = soortvoeg;
    }

    public String getToelichtinggemengdebestrating() {
        return this.toelichtinggemengdebestrating;
    }

    public Verhardingsobject toelichtinggemengdebestrating(String toelichtinggemengdebestrating) {
        this.setToelichtinggemengdebestrating(toelichtinggemengdebestrating);
        return this;
    }

    public void setToelichtinggemengdebestrating(String toelichtinggemengdebestrating) {
        this.toelichtinggemengdebestrating = toelichtinggemengdebestrating;
    }

    public String getType() {
        return this.type;
    }

    public Verhardingsobject type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeconstructie() {
        return this.typeconstructie;
    }

    public Verhardingsobject typeconstructie(String typeconstructie) {
        this.setTypeconstructie(typeconstructie);
        return this;
    }

    public void setTypeconstructie(String typeconstructie) {
        this.typeconstructie = typeconstructie;
    }

    public String getTypefundering() {
        return this.typefundering;
    }

    public Verhardingsobject typefundering(String typefundering) {
        this.setTypefundering(typefundering);
        return this;
    }

    public void setTypefundering(String typefundering) {
        this.typefundering = typefundering;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Verhardingsobject typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypeplus2() {
        return this.typeplus2;
    }

    public Verhardingsobject typeplus2(String typeplus2) {
        this.setTypeplus2(typeplus2);
        return this;
    }

    public void setTypeplus2(String typeplus2) {
        this.typeplus2 = typeplus2;
    }

    public String getTyperijstrook() {
        return this.typerijstrook;
    }

    public Verhardingsobject typerijstrook(String typerijstrook) {
        this.setTyperijstrook(typerijstrook);
        return this;
    }

    public void setTyperijstrook(String typerijstrook) {
        this.typerijstrook = typerijstrook;
    }

    public String getTypevoeg() {
        return this.typevoeg;
    }

    public Verhardingsobject typevoeg(String typevoeg) {
        this.setTypevoeg(typevoeg);
        return this;
    }

    public void setTypevoeg(String typevoeg) {
        this.typevoeg = typevoeg;
    }

    public String getTypevoegvulling() {
        return this.typevoegvulling;
    }

    public Verhardingsobject typevoegvulling(String typevoegvulling) {
        this.setTypevoegvulling(typevoegvulling);
        return this;
    }

    public void setTypevoegvulling(String typevoegvulling) {
        this.typevoegvulling = typevoegvulling;
    }

    public String getVegen() {
        return this.vegen;
    }

    public Verhardingsobject vegen(String vegen) {
        this.setVegen(vegen);
        return this;
    }

    public void setVegen(String vegen) {
        this.vegen = vegen;
    }

    public String getVerhardingsobjectconstructielaag() {
        return this.verhardingsobjectconstructielaag;
    }

    public Verhardingsobject verhardingsobjectconstructielaag(String verhardingsobjectconstructielaag) {
        this.setVerhardingsobjectconstructielaag(verhardingsobjectconstructielaag);
        return this;
    }

    public void setVerhardingsobjectconstructielaag(String verhardingsobjectconstructielaag) {
        this.verhardingsobjectconstructielaag = verhardingsobjectconstructielaag;
    }

    public String getVerhardingsobjectmodaliteit() {
        return this.verhardingsobjectmodaliteit;
    }

    public Verhardingsobject verhardingsobjectmodaliteit(String verhardingsobjectmodaliteit) {
        this.setVerhardingsobjectmodaliteit(verhardingsobjectmodaliteit);
        return this;
    }

    public void setVerhardingsobjectmodaliteit(String verhardingsobjectmodaliteit) {
        this.verhardingsobjectmodaliteit = verhardingsobjectmodaliteit;
    }

    public String getVerhardingsobjectrand() {
        return this.verhardingsobjectrand;
    }

    public Verhardingsobject verhardingsobjectrand(String verhardingsobjectrand) {
        this.setVerhardingsobjectrand(verhardingsobjectrand);
        return this;
    }

    public void setVerhardingsobjectrand(String verhardingsobjectrand) {
        this.verhardingsobjectrand = verhardingsobjectrand;
    }

    public String getVerhardingsobjectwegfunctie() {
        return this.verhardingsobjectwegfunctie;
    }

    public Verhardingsobject verhardingsobjectwegfunctie(String verhardingsobjectwegfunctie) {
        this.setVerhardingsobjectwegfunctie(verhardingsobjectwegfunctie);
        return this;
    }

    public void setVerhardingsobjectwegfunctie(String verhardingsobjectwegfunctie) {
        this.verhardingsobjectwegfunctie = verhardingsobjectwegfunctie;
    }

    public Boolean getVerhoogdeligging() {
        return this.verhoogdeligging;
    }

    public Verhardingsobject verhoogdeligging(Boolean verhoogdeligging) {
        this.setVerhoogdeligging(verhoogdeligging);
        return this;
    }

    public void setVerhoogdeligging(Boolean verhoogdeligging) {
        this.verhoogdeligging = verhoogdeligging;
    }

    public String getVulmateriaalkunstgras() {
        return this.vulmateriaalkunstgras;
    }

    public Verhardingsobject vulmateriaalkunstgras(String vulmateriaalkunstgras) {
        this.setVulmateriaalkunstgras(vulmateriaalkunstgras);
        return this;
    }

    public void setVulmateriaalkunstgras(String vulmateriaalkunstgras) {
        this.vulmateriaalkunstgras = vulmateriaalkunstgras;
    }

    public String getWaterdoorlatendheid() {
        return this.waterdoorlatendheid;
    }

    public Verhardingsobject waterdoorlatendheid(String waterdoorlatendheid) {
        this.setWaterdoorlatendheid(waterdoorlatendheid);
        return this;
    }

    public void setWaterdoorlatendheid(String waterdoorlatendheid) {
        this.waterdoorlatendheid = waterdoorlatendheid;
    }

    public String getWegas() {
        return this.wegas;
    }

    public Verhardingsobject wegas(String wegas) {
        this.setWegas(wegas);
        return this;
    }

    public void setWegas(String wegas) {
        this.wegas = wegas;
    }

    public String getWegcategoriedv() {
        return this.wegcategoriedv;
    }

    public Verhardingsobject wegcategoriedv(String wegcategoriedv) {
        this.setWegcategoriedv(wegcategoriedv);
        return this;
    }

    public void setWegcategoriedv(String wegcategoriedv) {
        this.wegcategoriedv = wegcategoriedv;
    }

    public String getWegcategoriedvplus() {
        return this.wegcategoriedvplus;
    }

    public Verhardingsobject wegcategoriedvplus(String wegcategoriedvplus) {
        this.setWegcategoriedvplus(wegcategoriedvplus);
        return this;
    }

    public void setWegcategoriedvplus(String wegcategoriedvplus) {
        this.wegcategoriedvplus = wegcategoriedvplus;
    }

    public String getWegnummer() {
        return this.wegnummer;
    }

    public Verhardingsobject wegnummer(String wegnummer) {
        this.setWegnummer(wegnummer);
        return this;
    }

    public void setWegnummer(String wegnummer) {
        this.wegnummer = wegnummer;
    }

    public String getWegtypebestaand() {
        return this.wegtypebestaand;
    }

    public Verhardingsobject wegtypebestaand(String wegtypebestaand) {
        this.setWegtypebestaand(wegtypebestaand);
        return this;
    }

    public void setWegtypebestaand(String wegtypebestaand) {
        this.wegtypebestaand = wegtypebestaand;
    }

    public String getWegvak() {
        return this.wegvak;
    }

    public Verhardingsobject wegvak(String wegvak) {
        this.setWegvak(wegvak);
        return this;
    }

    public void setWegvak(String wegvak) {
        this.wegvak = wegvak;
    }

    public String getWegvaknummer() {
        return this.wegvaknummer;
    }

    public Verhardingsobject wegvaknummer(String wegvaknummer) {
        this.setWegvaknummer(wegvaknummer);
        return this;
    }

    public void setWegvaknummer(String wegvaknummer) {
        this.wegvaknummer = wegvaknummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verhardingsobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Verhardingsobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verhardingsobject{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", aanofvrijliggend='" + getAanofvrijliggend() + "'" +
            ", aantaldeklagen='" + getAantaldeklagen() + "'" +
            ", aantalonderlagen='" + getAantalonderlagen() + "'" +
            ", aantaltussenlagen='" + getAantaltussenlagen() + "'" +
            ", afmeting='" + getAfmeting() + "'" +
            ", belasting='" + getBelasting() + "'" +
            ", bergendvermogen='" + getBergendvermogen() + "'" +
            ", bgtfysiekvoorkomen='" + getBgtfysiekvoorkomen() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", dikteconstructie='" + getDikteconstructie() + "'" +
            ", draagkrachtig='" + getDraagkrachtig() + "'" +
            ", formaat='" + getFormaat() + "'" +
            ", fysiekvoorkomenimgeo='" + getFysiekvoorkomenimgeo() + "'" +
            ", geluidsreducerend='" + getGeluidsreducerend() + "'" +
            ", jaarconserveren='" + getJaarconserveren() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", jaarpraktischeinde='" + getJaarpraktischeinde() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", lengtekunstgras='" + getLengtekunstgras() + "'" +
            ", lengtevoegen='" + getLengtevoegen() + "'" +
            ", levensduur='" + getLevensduur() + "'" +
            ", materiaal='" + getMateriaal() + "'" +
            ", maximalevalhoogte='" + getMaximalevalhoogte() + "'" +
            ", omtrek='" + getOmtrek() + "'" +
            ", ondergrondcode='" + getOndergrondcode() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", optalud='" + getOptalud() + "'" +
            ", plaatsorientatie='" + getPlaatsorientatie() + "'" +
            ", prijsaanschaf='" + getPrijsaanschaf() + "'" +
            ", rijstrook='" + getRijstrook() + "'" +
            ", soortvoeg='" + getSoortvoeg() + "'" +
            ", toelichtinggemengdebestrating='" + getToelichtinggemengdebestrating() + "'" +
            ", type='" + getType() + "'" +
            ", typeconstructie='" + getTypeconstructie() + "'" +
            ", typefundering='" + getTypefundering() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typeplus2='" + getTypeplus2() + "'" +
            ", typerijstrook='" + getTyperijstrook() + "'" +
            ", typevoeg='" + getTypevoeg() + "'" +
            ", typevoegvulling='" + getTypevoegvulling() + "'" +
            ", vegen='" + getVegen() + "'" +
            ", verhardingsobjectconstructielaag='" + getVerhardingsobjectconstructielaag() + "'" +
            ", verhardingsobjectmodaliteit='" + getVerhardingsobjectmodaliteit() + "'" +
            ", verhardingsobjectrand='" + getVerhardingsobjectrand() + "'" +
            ", verhardingsobjectwegfunctie='" + getVerhardingsobjectwegfunctie() + "'" +
            ", verhoogdeligging='" + getVerhoogdeligging() + "'" +
            ", vulmateriaalkunstgras='" + getVulmateriaalkunstgras() + "'" +
            ", waterdoorlatendheid='" + getWaterdoorlatendheid() + "'" +
            ", wegas='" + getWegas() + "'" +
            ", wegcategoriedv='" + getWegcategoriedv() + "'" +
            ", wegcategoriedvplus='" + getWegcategoriedvplus() + "'" +
            ", wegnummer='" + getWegnummer() + "'" +
            ", wegtypebestaand='" + getWegtypebestaand() + "'" +
            ", wegvak='" + getWegvak() + "'" +
            ", wegvaknummer='" + getWegvaknummer() + "'" +
            "}";
    }
}
