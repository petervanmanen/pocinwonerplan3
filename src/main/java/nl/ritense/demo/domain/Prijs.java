package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Prijs.
 */
@Entity
@Table(name = "prijs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prijs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @ManyToOne(optional = false)
    @NotNull
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
    private Product heeftprijsProduct;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tegenprijsPrijs")
    @JsonIgnoreProperties(value = { "tegenprijsPrijs", "betreftProduct" }, allowSetters = true)
    private Set<Balieverkoop> tegenprijsBalieverkoops = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prijs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Prijs bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Prijs datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Prijs datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public Product getHeeftprijsProduct() {
        return this.heeftprijsProduct;
    }

    public void setHeeftprijsProduct(Product product) {
        this.heeftprijsProduct = product;
    }

    public Prijs heeftprijsProduct(Product product) {
        this.setHeeftprijsProduct(product);
        return this;
    }

    public Set<Balieverkoop> getTegenprijsBalieverkoops() {
        return this.tegenprijsBalieverkoops;
    }

    public void setTegenprijsBalieverkoops(Set<Balieverkoop> balieverkoops) {
        if (this.tegenprijsBalieverkoops != null) {
            this.tegenprijsBalieverkoops.forEach(i -> i.setTegenprijsPrijs(null));
        }
        if (balieverkoops != null) {
            balieverkoops.forEach(i -> i.setTegenprijsPrijs(this));
        }
        this.tegenprijsBalieverkoops = balieverkoops;
    }

    public Prijs tegenprijsBalieverkoops(Set<Balieverkoop> balieverkoops) {
        this.setTegenprijsBalieverkoops(balieverkoops);
        return this;
    }

    public Prijs addTegenprijsBalieverkoop(Balieverkoop balieverkoop) {
        this.tegenprijsBalieverkoops.add(balieverkoop);
        balieverkoop.setTegenprijsPrijs(this);
        return this;
    }

    public Prijs removeTegenprijsBalieverkoop(Balieverkoop balieverkoop) {
        this.tegenprijsBalieverkoops.remove(balieverkoop);
        balieverkoop.setTegenprijsPrijs(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prijs)) {
            return false;
        }
        return getId() != null && getId().equals(((Prijs) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prijs{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            "}";
    }
}
