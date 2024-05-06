package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Werkorder.
 */
@Entity
@Table(name = "werkorder")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Werkorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "documentnummer")
    private String documentnummer;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "werkordertype")
    private String werkordertype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "valtbinnenHoofdrekenings",
            "heeftSubrekenings",
            "heeftWerkorders",
            "heeftActivas",
            "heeftKostenplaats",
            "valtbinnenHoofdrekening2",
            "betreftBegrotingregels",
            "vanMutaties",
            "naarMutaties",
            "wordtgeschrevenopInkooporders",
        },
        allowSetters = true
    )
    private Hoofdrekening heeftHoofdrekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Kostenplaats heeftKostenplaats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Werkorder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Werkorder code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDocumentnummer() {
        return this.documentnummer;
    }

    public Werkorder documentnummer(String documentnummer) {
        this.setDocumentnummer(documentnummer);
        return this;
    }

    public void setDocumentnummer(String documentnummer) {
        this.documentnummer = documentnummer;
    }

    public String getNaam() {
        return this.naam;
    }

    public Werkorder naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Werkorder omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getWerkordertype() {
        return this.werkordertype;
    }

    public Werkorder werkordertype(String werkordertype) {
        this.setWerkordertype(werkordertype);
        return this;
    }

    public void setWerkordertype(String werkordertype) {
        this.werkordertype = werkordertype;
    }

    public Hoofdrekening getHeeftHoofdrekening() {
        return this.heeftHoofdrekening;
    }

    public void setHeeftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.heeftHoofdrekening = hoofdrekening;
    }

    public Werkorder heeftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.setHeeftHoofdrekening(hoofdrekening);
        return this;
    }

    public Kostenplaats getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Werkorder heeftKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Werkorder)) {
            return false;
        }
        return getId() != null && getId().equals(((Werkorder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Werkorder{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", documentnummer='" + getDocumentnummer() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", werkordertype='" + getWerkordertype() + "'" +
            "}";
    }
}
