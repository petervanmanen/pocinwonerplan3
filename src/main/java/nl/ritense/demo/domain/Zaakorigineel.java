package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Zaakorigineel.
 */
@Entity
@Table(name = "zaakorigineel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zaakorigineel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "anderzaakobject")
    private String anderzaakobject;

    @Column(name = "archiefnominatie")
    private String archiefnominatie;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumeindegepland")
    private String datumeindegepland;

    @Column(name = "datumeindeuiterlijkeafdoening")
    private String datumeindeuiterlijkeafdoening;

    @Column(name = "datumlaatstebetaling")
    private String datumlaatstebetaling;

    @Column(name = "datumpublicatie")
    private String datumpublicatie;

    @Column(name = "datumregistratie")
    private String datumregistratie;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumvernietigingdossier")
    private String datumvernietigingdossier;

    @Column(name = "indicatiebetaling")
    private String indicatiebetaling;

    @Column(name = "indicatiedeelzaken")
    private String indicatiedeelzaken;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "omschrijvingresultaat")
    private String omschrijvingresultaat;

    @Column(name = "opschorting")
    private String opschorting;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "toelichtingresultaat")
    private String toelichtingresultaat;

    @Column(name = "verlenging")
    private String verlenging;

    @Column(name = "zaakidentificatie")
    private String zaakidentificatie;

    @Column(name = "zaakniveau")
    private String zaakniveau;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zaakorigineel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnderzaakobject() {
        return this.anderzaakobject;
    }

    public Zaakorigineel anderzaakobject(String anderzaakobject) {
        this.setAnderzaakobject(anderzaakobject);
        return this;
    }

    public void setAnderzaakobject(String anderzaakobject) {
        this.anderzaakobject = anderzaakobject;
    }

    public String getArchiefnominatie() {
        return this.archiefnominatie;
    }

    public Zaakorigineel archiefnominatie(String archiefnominatie) {
        this.setArchiefnominatie(archiefnominatie);
        return this;
    }

    public void setArchiefnominatie(String archiefnominatie) {
        this.archiefnominatie = archiefnominatie;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Zaakorigineel datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumeindegepland() {
        return this.datumeindegepland;
    }

    public Zaakorigineel datumeindegepland(String datumeindegepland) {
        this.setDatumeindegepland(datumeindegepland);
        return this;
    }

    public void setDatumeindegepland(String datumeindegepland) {
        this.datumeindegepland = datumeindegepland;
    }

    public String getDatumeindeuiterlijkeafdoening() {
        return this.datumeindeuiterlijkeafdoening;
    }

    public Zaakorigineel datumeindeuiterlijkeafdoening(String datumeindeuiterlijkeafdoening) {
        this.setDatumeindeuiterlijkeafdoening(datumeindeuiterlijkeafdoening);
        return this;
    }

    public void setDatumeindeuiterlijkeafdoening(String datumeindeuiterlijkeafdoening) {
        this.datumeindeuiterlijkeafdoening = datumeindeuiterlijkeafdoening;
    }

    public String getDatumlaatstebetaling() {
        return this.datumlaatstebetaling;
    }

    public Zaakorigineel datumlaatstebetaling(String datumlaatstebetaling) {
        this.setDatumlaatstebetaling(datumlaatstebetaling);
        return this;
    }

    public void setDatumlaatstebetaling(String datumlaatstebetaling) {
        this.datumlaatstebetaling = datumlaatstebetaling;
    }

    public String getDatumpublicatie() {
        return this.datumpublicatie;
    }

    public Zaakorigineel datumpublicatie(String datumpublicatie) {
        this.setDatumpublicatie(datumpublicatie);
        return this;
    }

    public void setDatumpublicatie(String datumpublicatie) {
        this.datumpublicatie = datumpublicatie;
    }

    public String getDatumregistratie() {
        return this.datumregistratie;
    }

    public Zaakorigineel datumregistratie(String datumregistratie) {
        this.setDatumregistratie(datumregistratie);
        return this;
    }

    public void setDatumregistratie(String datumregistratie) {
        this.datumregistratie = datumregistratie;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Zaakorigineel datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumvernietigingdossier() {
        return this.datumvernietigingdossier;
    }

    public Zaakorigineel datumvernietigingdossier(String datumvernietigingdossier) {
        this.setDatumvernietigingdossier(datumvernietigingdossier);
        return this;
    }

    public void setDatumvernietigingdossier(String datumvernietigingdossier) {
        this.datumvernietigingdossier = datumvernietigingdossier;
    }

    public String getIndicatiebetaling() {
        return this.indicatiebetaling;
    }

    public Zaakorigineel indicatiebetaling(String indicatiebetaling) {
        this.setIndicatiebetaling(indicatiebetaling);
        return this;
    }

    public void setIndicatiebetaling(String indicatiebetaling) {
        this.indicatiebetaling = indicatiebetaling;
    }

    public String getIndicatiedeelzaken() {
        return this.indicatiedeelzaken;
    }

    public Zaakorigineel indicatiedeelzaken(String indicatiedeelzaken) {
        this.setIndicatiedeelzaken(indicatiedeelzaken);
        return this;
    }

    public void setIndicatiedeelzaken(String indicatiedeelzaken) {
        this.indicatiedeelzaken = indicatiedeelzaken;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Zaakorigineel kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Zaakorigineel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijvingresultaat() {
        return this.omschrijvingresultaat;
    }

    public Zaakorigineel omschrijvingresultaat(String omschrijvingresultaat) {
        this.setOmschrijvingresultaat(omschrijvingresultaat);
        return this;
    }

    public void setOmschrijvingresultaat(String omschrijvingresultaat) {
        this.omschrijvingresultaat = omschrijvingresultaat;
    }

    public String getOpschorting() {
        return this.opschorting;
    }

    public Zaakorigineel opschorting(String opschorting) {
        this.setOpschorting(opschorting);
        return this;
    }

    public void setOpschorting(String opschorting) {
        this.opschorting = opschorting;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Zaakorigineel toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getToelichtingresultaat() {
        return this.toelichtingresultaat;
    }

    public Zaakorigineel toelichtingresultaat(String toelichtingresultaat) {
        this.setToelichtingresultaat(toelichtingresultaat);
        return this;
    }

    public void setToelichtingresultaat(String toelichtingresultaat) {
        this.toelichtingresultaat = toelichtingresultaat;
    }

    public String getVerlenging() {
        return this.verlenging;
    }

    public Zaakorigineel verlenging(String verlenging) {
        this.setVerlenging(verlenging);
        return this;
    }

    public void setVerlenging(String verlenging) {
        this.verlenging = verlenging;
    }

    public String getZaakidentificatie() {
        return this.zaakidentificatie;
    }

    public Zaakorigineel zaakidentificatie(String zaakidentificatie) {
        this.setZaakidentificatie(zaakidentificatie);
        return this;
    }

    public void setZaakidentificatie(String zaakidentificatie) {
        this.zaakidentificatie = zaakidentificatie;
    }

    public String getZaakniveau() {
        return this.zaakniveau;
    }

    public Zaakorigineel zaakniveau(String zaakniveau) {
        this.setZaakniveau(zaakniveau);
        return this;
    }

    public void setZaakniveau(String zaakniveau) {
        this.zaakniveau = zaakniveau;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zaakorigineel)) {
            return false;
        }
        return getId() != null && getId().equals(((Zaakorigineel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zaakorigineel{" +
            "id=" + getId() +
            ", anderzaakobject='" + getAnderzaakobject() + "'" +
            ", archiefnominatie='" + getArchiefnominatie() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegepland='" + getDatumeindegepland() + "'" +
            ", datumeindeuiterlijkeafdoening='" + getDatumeindeuiterlijkeafdoening() + "'" +
            ", datumlaatstebetaling='" + getDatumlaatstebetaling() + "'" +
            ", datumpublicatie='" + getDatumpublicatie() + "'" +
            ", datumregistratie='" + getDatumregistratie() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumvernietigingdossier='" + getDatumvernietigingdossier() + "'" +
            ", indicatiebetaling='" + getIndicatiebetaling() + "'" +
            ", indicatiedeelzaken='" + getIndicatiedeelzaken() + "'" +
            ", kenmerk='" + getKenmerk() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", omschrijvingresultaat='" + getOmschrijvingresultaat() + "'" +
            ", opschorting='" + getOpschorting() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", toelichtingresultaat='" + getToelichtingresultaat() + "'" +
            ", verlenging='" + getVerlenging() + "'" +
            ", zaakidentificatie='" + getZaakidentificatie() + "'" +
            ", zaakniveau='" + getZaakniveau() + "'" +
            "}";
    }
}
