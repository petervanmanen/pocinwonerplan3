package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ingeschrevenpersoon.
 */
@Entity
@Table(name = "ingeschrevenpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ingeschrevenpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresherkomst")
    private String adresherkomst;

    @Column(name = "anummer")
    private String anummer;

    @Column(name = "beschrijvinglocatie")
    private String beschrijvinglocatie;

    @Column(name = "buitenlandsreisdocument")
    private String buitenlandsreisdocument;

    @Column(name = "burgerlijkestaat")
    private String burgerlijkestaat;

    @Column(name = "datumbegingeldigheidverblijfplaats")
    private String datumbegingeldigheidverblijfplaats;

    @Column(name = "datumeindegeldigheidverblijfsplaats")
    private LocalDate datumeindegeldigheidverblijfsplaats;

    @Column(name = "datuminschrijvinggemeente")
    private String datuminschrijvinggemeente;

    @Column(name = "datumopschortingbijhouding")
    private String datumopschortingbijhouding;

    @Column(name = "datumvertrekuitnederland")
    private String datumvertrekuitnederland;

    @Column(name = "datumvestigingnederland")
    private String datumvestigingnederland;

    @Column(name = "gemeentevaninschrijving")
    private String gemeentevaninschrijving;

    @Column(name = "gezinsrelatie")
    private String gezinsrelatie;

    @Column(name = "indicatiegeheim")
    private String indicatiegeheim;

    @Column(name = "ingezetene")
    private String ingezetene;

    @Column(name = "landwaarnaarvertrokken")
    private String landwaarnaarvertrokken;

    @Column(name = "landwaarvandaaningeschreven")
    private String landwaarvandaaningeschreven;

    @Column(name = "ouder_1")
    private String ouder1;

    @Column(name = "ouder_2")
    private String ouder2;

    @Column(name = "partnerid")
    private String partnerid;

    @Column(name = "redeneindebewoning")
    private String redeneindebewoning;

    @Column(name = "redenopschortingbijhouding")
    private String redenopschortingbijhouding;

    @Column(name = "signaleringreisdocument")
    private String signaleringreisdocument;

    @Column(name = "verblijfstitel")
    private String verblijfstitel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ingeschrevenpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresherkomst() {
        return this.adresherkomst;
    }

    public Ingeschrevenpersoon adresherkomst(String adresherkomst) {
        this.setAdresherkomst(adresherkomst);
        return this;
    }

    public void setAdresherkomst(String adresherkomst) {
        this.adresherkomst = adresherkomst;
    }

    public String getAnummer() {
        return this.anummer;
    }

    public Ingeschrevenpersoon anummer(String anummer) {
        this.setAnummer(anummer);
        return this;
    }

    public void setAnummer(String anummer) {
        this.anummer = anummer;
    }

    public String getBeschrijvinglocatie() {
        return this.beschrijvinglocatie;
    }

    public Ingeschrevenpersoon beschrijvinglocatie(String beschrijvinglocatie) {
        this.setBeschrijvinglocatie(beschrijvinglocatie);
        return this;
    }

    public void setBeschrijvinglocatie(String beschrijvinglocatie) {
        this.beschrijvinglocatie = beschrijvinglocatie;
    }

    public String getBuitenlandsreisdocument() {
        return this.buitenlandsreisdocument;
    }

    public Ingeschrevenpersoon buitenlandsreisdocument(String buitenlandsreisdocument) {
        this.setBuitenlandsreisdocument(buitenlandsreisdocument);
        return this;
    }

    public void setBuitenlandsreisdocument(String buitenlandsreisdocument) {
        this.buitenlandsreisdocument = buitenlandsreisdocument;
    }

    public String getBurgerlijkestaat() {
        return this.burgerlijkestaat;
    }

    public Ingeschrevenpersoon burgerlijkestaat(String burgerlijkestaat) {
        this.setBurgerlijkestaat(burgerlijkestaat);
        return this;
    }

    public void setBurgerlijkestaat(String burgerlijkestaat) {
        this.burgerlijkestaat = burgerlijkestaat;
    }

    public String getDatumbegingeldigheidverblijfplaats() {
        return this.datumbegingeldigheidverblijfplaats;
    }

    public Ingeschrevenpersoon datumbegingeldigheidverblijfplaats(String datumbegingeldigheidverblijfplaats) {
        this.setDatumbegingeldigheidverblijfplaats(datumbegingeldigheidverblijfplaats);
        return this;
    }

    public void setDatumbegingeldigheidverblijfplaats(String datumbegingeldigheidverblijfplaats) {
        this.datumbegingeldigheidverblijfplaats = datumbegingeldigheidverblijfplaats;
    }

    public LocalDate getDatumeindegeldigheidverblijfsplaats() {
        return this.datumeindegeldigheidverblijfsplaats;
    }

    public Ingeschrevenpersoon datumeindegeldigheidverblijfsplaats(LocalDate datumeindegeldigheidverblijfsplaats) {
        this.setDatumeindegeldigheidverblijfsplaats(datumeindegeldigheidverblijfsplaats);
        return this;
    }

    public void setDatumeindegeldigheidverblijfsplaats(LocalDate datumeindegeldigheidverblijfsplaats) {
        this.datumeindegeldigheidverblijfsplaats = datumeindegeldigheidverblijfsplaats;
    }

    public String getDatuminschrijvinggemeente() {
        return this.datuminschrijvinggemeente;
    }

    public Ingeschrevenpersoon datuminschrijvinggemeente(String datuminschrijvinggemeente) {
        this.setDatuminschrijvinggemeente(datuminschrijvinggemeente);
        return this;
    }

    public void setDatuminschrijvinggemeente(String datuminschrijvinggemeente) {
        this.datuminschrijvinggemeente = datuminschrijvinggemeente;
    }

    public String getDatumopschortingbijhouding() {
        return this.datumopschortingbijhouding;
    }

    public Ingeschrevenpersoon datumopschortingbijhouding(String datumopschortingbijhouding) {
        this.setDatumopschortingbijhouding(datumopschortingbijhouding);
        return this;
    }

    public void setDatumopschortingbijhouding(String datumopschortingbijhouding) {
        this.datumopschortingbijhouding = datumopschortingbijhouding;
    }

    public String getDatumvertrekuitnederland() {
        return this.datumvertrekuitnederland;
    }

    public Ingeschrevenpersoon datumvertrekuitnederland(String datumvertrekuitnederland) {
        this.setDatumvertrekuitnederland(datumvertrekuitnederland);
        return this;
    }

    public void setDatumvertrekuitnederland(String datumvertrekuitnederland) {
        this.datumvertrekuitnederland = datumvertrekuitnederland;
    }

    public String getDatumvestigingnederland() {
        return this.datumvestigingnederland;
    }

    public Ingeschrevenpersoon datumvestigingnederland(String datumvestigingnederland) {
        this.setDatumvestigingnederland(datumvestigingnederland);
        return this;
    }

    public void setDatumvestigingnederland(String datumvestigingnederland) {
        this.datumvestigingnederland = datumvestigingnederland;
    }

    public String getGemeentevaninschrijving() {
        return this.gemeentevaninschrijving;
    }

    public Ingeschrevenpersoon gemeentevaninschrijving(String gemeentevaninschrijving) {
        this.setGemeentevaninschrijving(gemeentevaninschrijving);
        return this;
    }

    public void setGemeentevaninschrijving(String gemeentevaninschrijving) {
        this.gemeentevaninschrijving = gemeentevaninschrijving;
    }

    public String getGezinsrelatie() {
        return this.gezinsrelatie;
    }

    public Ingeschrevenpersoon gezinsrelatie(String gezinsrelatie) {
        this.setGezinsrelatie(gezinsrelatie);
        return this;
    }

    public void setGezinsrelatie(String gezinsrelatie) {
        this.gezinsrelatie = gezinsrelatie;
    }

    public String getIndicatiegeheim() {
        return this.indicatiegeheim;
    }

    public Ingeschrevenpersoon indicatiegeheim(String indicatiegeheim) {
        this.setIndicatiegeheim(indicatiegeheim);
        return this;
    }

    public void setIndicatiegeheim(String indicatiegeheim) {
        this.indicatiegeheim = indicatiegeheim;
    }

    public String getIngezetene() {
        return this.ingezetene;
    }

    public Ingeschrevenpersoon ingezetene(String ingezetene) {
        this.setIngezetene(ingezetene);
        return this;
    }

    public void setIngezetene(String ingezetene) {
        this.ingezetene = ingezetene;
    }

    public String getLandwaarnaarvertrokken() {
        return this.landwaarnaarvertrokken;
    }

    public Ingeschrevenpersoon landwaarnaarvertrokken(String landwaarnaarvertrokken) {
        this.setLandwaarnaarvertrokken(landwaarnaarvertrokken);
        return this;
    }

    public void setLandwaarnaarvertrokken(String landwaarnaarvertrokken) {
        this.landwaarnaarvertrokken = landwaarnaarvertrokken;
    }

    public String getLandwaarvandaaningeschreven() {
        return this.landwaarvandaaningeschreven;
    }

    public Ingeschrevenpersoon landwaarvandaaningeschreven(String landwaarvandaaningeschreven) {
        this.setLandwaarvandaaningeschreven(landwaarvandaaningeschreven);
        return this;
    }

    public void setLandwaarvandaaningeschreven(String landwaarvandaaningeschreven) {
        this.landwaarvandaaningeschreven = landwaarvandaaningeschreven;
    }

    public String getOuder1() {
        return this.ouder1;
    }

    public Ingeschrevenpersoon ouder1(String ouder1) {
        this.setOuder1(ouder1);
        return this;
    }

    public void setOuder1(String ouder1) {
        this.ouder1 = ouder1;
    }

    public String getOuder2() {
        return this.ouder2;
    }

    public Ingeschrevenpersoon ouder2(String ouder2) {
        this.setOuder2(ouder2);
        return this;
    }

    public void setOuder2(String ouder2) {
        this.ouder2 = ouder2;
    }

    public String getPartnerid() {
        return this.partnerid;
    }

    public Ingeschrevenpersoon partnerid(String partnerid) {
        this.setPartnerid(partnerid);
        return this;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getRedeneindebewoning() {
        return this.redeneindebewoning;
    }

    public Ingeschrevenpersoon redeneindebewoning(String redeneindebewoning) {
        this.setRedeneindebewoning(redeneindebewoning);
        return this;
    }

    public void setRedeneindebewoning(String redeneindebewoning) {
        this.redeneindebewoning = redeneindebewoning;
    }

    public String getRedenopschortingbijhouding() {
        return this.redenopschortingbijhouding;
    }

    public Ingeschrevenpersoon redenopschortingbijhouding(String redenopschortingbijhouding) {
        this.setRedenopschortingbijhouding(redenopschortingbijhouding);
        return this;
    }

    public void setRedenopschortingbijhouding(String redenopschortingbijhouding) {
        this.redenopschortingbijhouding = redenopschortingbijhouding;
    }

    public String getSignaleringreisdocument() {
        return this.signaleringreisdocument;
    }

    public Ingeschrevenpersoon signaleringreisdocument(String signaleringreisdocument) {
        this.setSignaleringreisdocument(signaleringreisdocument);
        return this;
    }

    public void setSignaleringreisdocument(String signaleringreisdocument) {
        this.signaleringreisdocument = signaleringreisdocument;
    }

    public String getVerblijfstitel() {
        return this.verblijfstitel;
    }

    public Ingeschrevenpersoon verblijfstitel(String verblijfstitel) {
        this.setVerblijfstitel(verblijfstitel);
        return this;
    }

    public void setVerblijfstitel(String verblijfstitel) {
        this.verblijfstitel = verblijfstitel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingeschrevenpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Ingeschrevenpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ingeschrevenpersoon{" +
            "id=" + getId() +
            ", adresherkomst='" + getAdresherkomst() + "'" +
            ", anummer='" + getAnummer() + "'" +
            ", beschrijvinglocatie='" + getBeschrijvinglocatie() + "'" +
            ", buitenlandsreisdocument='" + getBuitenlandsreisdocument() + "'" +
            ", burgerlijkestaat='" + getBurgerlijkestaat() + "'" +
            ", datumbegingeldigheidverblijfplaats='" + getDatumbegingeldigheidverblijfplaats() + "'" +
            ", datumeindegeldigheidverblijfsplaats='" + getDatumeindegeldigheidverblijfsplaats() + "'" +
            ", datuminschrijvinggemeente='" + getDatuminschrijvinggemeente() + "'" +
            ", datumopschortingbijhouding='" + getDatumopschortingbijhouding() + "'" +
            ", datumvertrekuitnederland='" + getDatumvertrekuitnederland() + "'" +
            ", datumvestigingnederland='" + getDatumvestigingnederland() + "'" +
            ", gemeentevaninschrijving='" + getGemeentevaninschrijving() + "'" +
            ", gezinsrelatie='" + getGezinsrelatie() + "'" +
            ", indicatiegeheim='" + getIndicatiegeheim() + "'" +
            ", ingezetene='" + getIngezetene() + "'" +
            ", landwaarnaarvertrokken='" + getLandwaarnaarvertrokken() + "'" +
            ", landwaarvandaaningeschreven='" + getLandwaarvandaaningeschreven() + "'" +
            ", ouder1='" + getOuder1() + "'" +
            ", ouder2='" + getOuder2() + "'" +
            ", partnerid='" + getPartnerid() + "'" +
            ", redeneindebewoning='" + getRedeneindebewoning() + "'" +
            ", redenopschortingbijhouding='" + getRedenopschortingbijhouding() + "'" +
            ", signaleringreisdocument='" + getSignaleringreisdocument() + "'" +
            ", verblijfstitel='" + getVerblijfstitel() + "'" +
            "}";
    }
}
