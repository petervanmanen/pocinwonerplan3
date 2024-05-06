package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "autorisatiegroep")
    private String autorisatiegroep;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "classificatie")
    private String classificatie;

    @Column(name = "contractrevisie")
    private String contractrevisie;

    @Column(name = "datumcreatie")
    private LocalDate datumcreatie;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "groep")
    private String groep;

    @Column(name = "interncontractid")
    private String interncontractid;

    @Column(name = "interncontractrevisie")
    private String interncontractrevisie;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "voorwaarde")
    private String voorwaarde;

    @Column(name = "zoekwoorden")
    private String zoekwoorden;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatContract")
    @JsonIgnoreProperties(value = { "heeftLeverancier", "bevatContract", "heeftVoorziening" }, allowSetters = true)
    private Set<Tarief> bevatTariefs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "bevatTariefs",
            "bovenliggendContract",
            "betreftInkooporder",
            "heeftLeverancier",
            "contractantLeverancier",
            "bovenliggendContract2s",
        },
        allowSetters = true
    )
    private Contract bovenliggendContract;

    @JsonIgnoreProperties(
        value = {
            "betreftContract",
            "oorspronkelijkInkooporder",
            "hoortbijWerkbons",
            "gerelateerdInkooporders",
            "heeftInkooppakket",
            "verplichtingaanLeverancier",
            "wordtgeschrevenopHoofdrekenings",
            "oorspronkelijkInkooporder2",
            "gerelateerdInkooporder2",
            "gedektviaFactuurs",
            "heeftKostenplaats",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftContract")
    private Inkooporder betreftInkooporder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftContracts",
            "leverdeprestatieLeverings",
            "voertwerkuitconformWerkbons",
            "contractantContracts",
            "heeftInschrijvings",
            "biedtaanKandidaats",
            "heeftKwalificaties",
            "gekwalificeerdCategories",
            "leverancierProducts",
            "ingedienddoorDeclaraties",
            "levertvoorzieningToewijzings",
            "heeftTariefs",
            "uitvoerderMeldings",
            "heeftleverancierApplicaties",
            "heeftleverancierServers",
            "crediteurFactuurs",
            "verplichtingaanInkooporders",
            "gerichtaanUitnodigings",
            "geleverdviaMedewerkers",
            "gerichtaanOfferteaanvraags",
            "ingedienddoorOffertes",
        },
        allowSetters = true
    )
    private Leverancier heeftLeverancier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftContracts",
            "leverdeprestatieLeverings",
            "voertwerkuitconformWerkbons",
            "contractantContracts",
            "heeftInschrijvings",
            "biedtaanKandidaats",
            "heeftKwalificaties",
            "gekwalificeerdCategories",
            "leverancierProducts",
            "ingedienddoorDeclaraties",
            "levertvoorzieningToewijzings",
            "heeftTariefs",
            "uitvoerderMeldings",
            "heeftleverancierApplicaties",
            "heeftleverancierServers",
            "crediteurFactuurs",
            "verplichtingaanInkooporders",
            "gerichtaanUitnodigings",
            "geleverdviaMedewerkers",
            "gerichtaanOfferteaanvraags",
            "ingedienddoorOffertes",
        },
        allowSetters = true
    )
    private Leverancier contractantLeverancier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bovenliggendContract")
    @JsonIgnoreProperties(
        value = {
            "bevatTariefs",
            "bovenliggendContract",
            "betreftInkooporder",
            "heeftLeverancier",
            "contractantLeverancier",
            "bovenliggendContract2s",
        },
        allowSetters = true
    )
    private Set<Contract> bovenliggendContract2s = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutorisatiegroep() {
        return this.autorisatiegroep;
    }

    public Contract autorisatiegroep(String autorisatiegroep) {
        this.setAutorisatiegroep(autorisatiegroep);
        return this;
    }

    public void setAutorisatiegroep(String autorisatiegroep) {
        this.autorisatiegroep = autorisatiegroep;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Contract beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Contract categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getClassificatie() {
        return this.classificatie;
    }

    public Contract classificatie(String classificatie) {
        this.setClassificatie(classificatie);
        return this;
    }

    public void setClassificatie(String classificatie) {
        this.classificatie = classificatie;
    }

    public String getContractrevisie() {
        return this.contractrevisie;
    }

    public Contract contractrevisie(String contractrevisie) {
        this.setContractrevisie(contractrevisie);
        return this;
    }

    public void setContractrevisie(String contractrevisie) {
        this.contractrevisie = contractrevisie;
    }

    public LocalDate getDatumcreatie() {
        return this.datumcreatie;
    }

    public Contract datumcreatie(LocalDate datumcreatie) {
        this.setDatumcreatie(datumcreatie);
        return this;
    }

    public void setDatumcreatie(LocalDate datumcreatie) {
        this.datumcreatie = datumcreatie;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Contract datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Contract datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getGroep() {
        return this.groep;
    }

    public Contract groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public String getInterncontractid() {
        return this.interncontractid;
    }

    public Contract interncontractid(String interncontractid) {
        this.setInterncontractid(interncontractid);
        return this;
    }

    public void setInterncontractid(String interncontractid) {
        this.interncontractid = interncontractid;
    }

    public String getInterncontractrevisie() {
        return this.interncontractrevisie;
    }

    public Contract interncontractrevisie(String interncontractrevisie) {
        this.setInterncontractrevisie(interncontractrevisie);
        return this;
    }

    public void setInterncontractrevisie(String interncontractrevisie) {
        this.interncontractrevisie = interncontractrevisie;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Contract opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getStatus() {
        return this.status;
    }

    public Contract status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public Contract type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVoorwaarde() {
        return this.voorwaarde;
    }

    public Contract voorwaarde(String voorwaarde) {
        this.setVoorwaarde(voorwaarde);
        return this;
    }

    public void setVoorwaarde(String voorwaarde) {
        this.voorwaarde = voorwaarde;
    }

    public String getZoekwoorden() {
        return this.zoekwoorden;
    }

    public Contract zoekwoorden(String zoekwoorden) {
        this.setZoekwoorden(zoekwoorden);
        return this;
    }

    public void setZoekwoorden(String zoekwoorden) {
        this.zoekwoorden = zoekwoorden;
    }

    public Set<Tarief> getBevatTariefs() {
        return this.bevatTariefs;
    }

    public void setBevatTariefs(Set<Tarief> tariefs) {
        if (this.bevatTariefs != null) {
            this.bevatTariefs.forEach(i -> i.setBevatContract(null));
        }
        if (tariefs != null) {
            tariefs.forEach(i -> i.setBevatContract(this));
        }
        this.bevatTariefs = tariefs;
    }

    public Contract bevatTariefs(Set<Tarief> tariefs) {
        this.setBevatTariefs(tariefs);
        return this;
    }

    public Contract addBevatTarief(Tarief tarief) {
        this.bevatTariefs.add(tarief);
        tarief.setBevatContract(this);
        return this;
    }

    public Contract removeBevatTarief(Tarief tarief) {
        this.bevatTariefs.remove(tarief);
        tarief.setBevatContract(null);
        return this;
    }

    public Contract getBovenliggendContract() {
        return this.bovenliggendContract;
    }

    public void setBovenliggendContract(Contract contract) {
        this.bovenliggendContract = contract;
    }

    public Contract bovenliggendContract(Contract contract) {
        this.setBovenliggendContract(contract);
        return this;
    }

    public Inkooporder getBetreftInkooporder() {
        return this.betreftInkooporder;
    }

    public void setBetreftInkooporder(Inkooporder inkooporder) {
        if (this.betreftInkooporder != null) {
            this.betreftInkooporder.setBetreftContract(null);
        }
        if (inkooporder != null) {
            inkooporder.setBetreftContract(this);
        }
        this.betreftInkooporder = inkooporder;
    }

    public Contract betreftInkooporder(Inkooporder inkooporder) {
        this.setBetreftInkooporder(inkooporder);
        return this;
    }

    public Leverancier getHeeftLeverancier() {
        return this.heeftLeverancier;
    }

    public void setHeeftLeverancier(Leverancier leverancier) {
        this.heeftLeverancier = leverancier;
    }

    public Contract heeftLeverancier(Leverancier leverancier) {
        this.setHeeftLeverancier(leverancier);
        return this;
    }

    public Leverancier getContractantLeverancier() {
        return this.contractantLeverancier;
    }

    public void setContractantLeverancier(Leverancier leverancier) {
        this.contractantLeverancier = leverancier;
    }

    public Contract contractantLeverancier(Leverancier leverancier) {
        this.setContractantLeverancier(leverancier);
        return this;
    }

    public Set<Contract> getBovenliggendContract2s() {
        return this.bovenliggendContract2s;
    }

    public void setBovenliggendContract2s(Set<Contract> contracts) {
        if (this.bovenliggendContract2s != null) {
            this.bovenliggendContract2s.forEach(i -> i.setBovenliggendContract(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setBovenliggendContract(this));
        }
        this.bovenliggendContract2s = contracts;
    }

    public Contract bovenliggendContract2s(Set<Contract> contracts) {
        this.setBovenliggendContract2s(contracts);
        return this;
    }

    public Contract addBovenliggendContract2(Contract contract) {
        this.bovenliggendContract2s.add(contract);
        contract.setBovenliggendContract(this);
        return this;
    }

    public Contract removeBovenliggendContract2(Contract contract) {
        this.bovenliggendContract2s.remove(contract);
        contract.setBovenliggendContract(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contract)) {
            return false;
        }
        return getId() != null && getId().equals(((Contract) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", autorisatiegroep='" + getAutorisatiegroep() + "'" +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", classificatie='" + getClassificatie() + "'" +
            ", contractrevisie='" + getContractrevisie() + "'" +
            ", datumcreatie='" + getDatumcreatie() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", groep='" + getGroep() + "'" +
            ", interncontractid='" + getInterncontractid() + "'" +
            ", interncontractrevisie='" + getInterncontractrevisie() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", voorwaarde='" + getVoorwaarde() + "'" +
            ", zoekwoorden='" + getZoekwoorden() + "'" +
            "}";
    }
}
