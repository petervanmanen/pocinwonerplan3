package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Declaratieregel.
 */
@Entity
@Table(name = "declaratieregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Declaratieregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "toewijzingToewijzings",
            "emptyClient",
            "geeftafClientbegeleider",
            "isgebaseerdopBeperkings",
            "geleverdeprestatieLeverings",
            "isvoorDeclaratieregels",
        },
        allowSetters = true
    )
    private Beschikking isvoorBeschikking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Client betreftClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "ingedienddoorLeverancier", "soortdeclaratieDeclaratiesoort", "dientinWerknemer", "valtbinnenDeclaratieregels" },
        allowSetters = true
    )
    private Declaratie valtbinnenDeclaratie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "isopbasisvanDeclaratieregels", "levertvoorzieningLeverancier", "toewijzingBeschikking", "geleverdezorgLeverings" },
        allowSetters = true
    )
    private Toewijzing isopbasisvanToewijzing;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Declaratieregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Declaratieregel bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getCode() {
        return this.code;
    }

    public Declaratieregel code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Declaratieregel datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Declaratieregel datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public Beschikking getIsvoorBeschikking() {
        return this.isvoorBeschikking;
    }

    public void setIsvoorBeschikking(Beschikking beschikking) {
        this.isvoorBeschikking = beschikking;
    }

    public Declaratieregel isvoorBeschikking(Beschikking beschikking) {
        this.setIsvoorBeschikking(beschikking);
        return this;
    }

    public Client getBetreftClient() {
        return this.betreftClient;
    }

    public void setBetreftClient(Client client) {
        this.betreftClient = client;
    }

    public Declaratieregel betreftClient(Client client) {
        this.setBetreftClient(client);
        return this;
    }

    public Declaratie getValtbinnenDeclaratie() {
        return this.valtbinnenDeclaratie;
    }

    public void setValtbinnenDeclaratie(Declaratie declaratie) {
        this.valtbinnenDeclaratie = declaratie;
    }

    public Declaratieregel valtbinnenDeclaratie(Declaratie declaratie) {
        this.setValtbinnenDeclaratie(declaratie);
        return this;
    }

    public Toewijzing getIsopbasisvanToewijzing() {
        return this.isopbasisvanToewijzing;
    }

    public void setIsopbasisvanToewijzing(Toewijzing toewijzing) {
        this.isopbasisvanToewijzing = toewijzing;
    }

    public Declaratieregel isopbasisvanToewijzing(Toewijzing toewijzing) {
        this.setIsopbasisvanToewijzing(toewijzing);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Declaratieregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Declaratieregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Declaratieregel{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", code='" + getCode() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            "}";
    }
}
