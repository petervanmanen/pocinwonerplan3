package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Begrotingregel.
 */
@Entity
@Table(name = "begrotingregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Begrotingregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "batenlasten", length = 20)
    private String batenlasten;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "soortregel")
    private String soortregel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftProducts", "isvansoortDoelstellingsoort", "heeftHoofdstuk", "betreftBegrotingregels" },
        allowSetters = true
    )
    private Doelstelling betreftDoelstelling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftprijsPrijs",
            "betreftZaaktypes",
            "leverancierLeverancier",
            "heeftKostenplaats",
            "valtbinnenOmzetgroeps",
            "valtbinnenProductgroeps",
            "betreftWinkelvoorraaditem",
            "heeftDoelstelling",
            "isopdrachtgeverOpdrachtgever",
            "isopdrachtnemerOpdrachtnemer",
            "betreftBalieverkoops",
            "betreftDiensts",
            "betreftBegrotingregels",
        },
        allowSetters = true
    )
    private Product betreftProduct;

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
    private Kostenplaats betreftKostenplaats;

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
    private Hoofdrekening betreftHoofdrekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftDoelstellings", "binnenPeriodes", "betreftBegrotingregels" }, allowSetters = true)
    private Hoofdstuk betreftHoofdstuk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftBegrotingregels" }, allowSetters = true)
    private Begroting heeftBegroting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Begrotingregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatenlasten() {
        return this.batenlasten;
    }

    public Begrotingregel batenlasten(String batenlasten) {
        this.setBatenlasten(batenlasten);
        return this;
    }

    public void setBatenlasten(String batenlasten) {
        this.batenlasten = batenlasten;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Begrotingregel bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getSoortregel() {
        return this.soortregel;
    }

    public Begrotingregel soortregel(String soortregel) {
        this.setSoortregel(soortregel);
        return this;
    }

    public void setSoortregel(String soortregel) {
        this.soortregel = soortregel;
    }

    public Doelstelling getBetreftDoelstelling() {
        return this.betreftDoelstelling;
    }

    public void setBetreftDoelstelling(Doelstelling doelstelling) {
        this.betreftDoelstelling = doelstelling;
    }

    public Begrotingregel betreftDoelstelling(Doelstelling doelstelling) {
        this.setBetreftDoelstelling(doelstelling);
        return this;
    }

    public Product getBetreftProduct() {
        return this.betreftProduct;
    }

    public void setBetreftProduct(Product product) {
        this.betreftProduct = product;
    }

    public Begrotingregel betreftProduct(Product product) {
        this.setBetreftProduct(product);
        return this;
    }

    public Kostenplaats getBetreftKostenplaats() {
        return this.betreftKostenplaats;
    }

    public void setBetreftKostenplaats(Kostenplaats kostenplaats) {
        this.betreftKostenplaats = kostenplaats;
    }

    public Begrotingregel betreftKostenplaats(Kostenplaats kostenplaats) {
        this.setBetreftKostenplaats(kostenplaats);
        return this;
    }

    public Hoofdrekening getBetreftHoofdrekening() {
        return this.betreftHoofdrekening;
    }

    public void setBetreftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.betreftHoofdrekening = hoofdrekening;
    }

    public Begrotingregel betreftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.setBetreftHoofdrekening(hoofdrekening);
        return this;
    }

    public Hoofdstuk getBetreftHoofdstuk() {
        return this.betreftHoofdstuk;
    }

    public void setBetreftHoofdstuk(Hoofdstuk hoofdstuk) {
        this.betreftHoofdstuk = hoofdstuk;
    }

    public Begrotingregel betreftHoofdstuk(Hoofdstuk hoofdstuk) {
        this.setBetreftHoofdstuk(hoofdstuk);
        return this;
    }

    public Begroting getHeeftBegroting() {
        return this.heeftBegroting;
    }

    public void setHeeftBegroting(Begroting begroting) {
        this.heeftBegroting = begroting;
    }

    public Begrotingregel heeftBegroting(Begroting begroting) {
        this.setHeeftBegroting(begroting);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Begrotingregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Begrotingregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Begrotingregel{" +
            "id=" + getId() +
            ", batenlasten='" + getBatenlasten() + "'" +
            ", bedrag=" + getBedrag() +
            ", soortregel='" + getSoortregel() + "'" +
            "}";
    }
}
