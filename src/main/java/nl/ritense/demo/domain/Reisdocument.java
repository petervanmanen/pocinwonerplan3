package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Reisdocument.
 */
@Entity
@Table(name = "reisdocument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reisdocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidinginhoudingvermissing")
    private String aanduidinginhoudingvermissing;

    @Column(name = "autoriteitvanafgifte")
    private String autoriteitvanafgifte;

    @Column(name = "datumeindegeldigheiddocument")
    private String datumeindegeldigheiddocument;

    @Column(name = "datumingangdocument")
    private String datumingangdocument;

    @Column(name = "datuminhoudingofvermissing")
    private String datuminhoudingofvermissing;

    @Column(name = "datumuitgifte")
    private String datumuitgifte;

    @Column(name = "reisdocumentnummer")
    private String reisdocumentnummer;

    @Column(name = "soort")
    private String soort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reisdocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidinginhoudingvermissing() {
        return this.aanduidinginhoudingvermissing;
    }

    public Reisdocument aanduidinginhoudingvermissing(String aanduidinginhoudingvermissing) {
        this.setAanduidinginhoudingvermissing(aanduidinginhoudingvermissing);
        return this;
    }

    public void setAanduidinginhoudingvermissing(String aanduidinginhoudingvermissing) {
        this.aanduidinginhoudingvermissing = aanduidinginhoudingvermissing;
    }

    public String getAutoriteitvanafgifte() {
        return this.autoriteitvanafgifte;
    }

    public Reisdocument autoriteitvanafgifte(String autoriteitvanafgifte) {
        this.setAutoriteitvanafgifte(autoriteitvanafgifte);
        return this;
    }

    public void setAutoriteitvanafgifte(String autoriteitvanafgifte) {
        this.autoriteitvanafgifte = autoriteitvanafgifte;
    }

    public String getDatumeindegeldigheiddocument() {
        return this.datumeindegeldigheiddocument;
    }

    public Reisdocument datumeindegeldigheiddocument(String datumeindegeldigheiddocument) {
        this.setDatumeindegeldigheiddocument(datumeindegeldigheiddocument);
        return this;
    }

    public void setDatumeindegeldigheiddocument(String datumeindegeldigheiddocument) {
        this.datumeindegeldigheiddocument = datumeindegeldigheiddocument;
    }

    public String getDatumingangdocument() {
        return this.datumingangdocument;
    }

    public Reisdocument datumingangdocument(String datumingangdocument) {
        this.setDatumingangdocument(datumingangdocument);
        return this;
    }

    public void setDatumingangdocument(String datumingangdocument) {
        this.datumingangdocument = datumingangdocument;
    }

    public String getDatuminhoudingofvermissing() {
        return this.datuminhoudingofvermissing;
    }

    public Reisdocument datuminhoudingofvermissing(String datuminhoudingofvermissing) {
        this.setDatuminhoudingofvermissing(datuminhoudingofvermissing);
        return this;
    }

    public void setDatuminhoudingofvermissing(String datuminhoudingofvermissing) {
        this.datuminhoudingofvermissing = datuminhoudingofvermissing;
    }

    public String getDatumuitgifte() {
        return this.datumuitgifte;
    }

    public Reisdocument datumuitgifte(String datumuitgifte) {
        this.setDatumuitgifte(datumuitgifte);
        return this;
    }

    public void setDatumuitgifte(String datumuitgifte) {
        this.datumuitgifte = datumuitgifte;
    }

    public String getReisdocumentnummer() {
        return this.reisdocumentnummer;
    }

    public Reisdocument reisdocumentnummer(String reisdocumentnummer) {
        this.setReisdocumentnummer(reisdocumentnummer);
        return this;
    }

    public void setReisdocumentnummer(String reisdocumentnummer) {
        this.reisdocumentnummer = reisdocumentnummer;
    }

    public String getSoort() {
        return this.soort;
    }

    public Reisdocument soort(String soort) {
        this.setSoort(soort);
        return this;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reisdocument)) {
            return false;
        }
        return getId() != null && getId().equals(((Reisdocument) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reisdocument{" +
            "id=" + getId() +
            ", aanduidinginhoudingvermissing='" + getAanduidinginhoudingvermissing() + "'" +
            ", autoriteitvanafgifte='" + getAutoriteitvanafgifte() + "'" +
            ", datumeindegeldigheiddocument='" + getDatumeindegeldigheiddocument() + "'" +
            ", datumingangdocument='" + getDatumingangdocument() + "'" +
            ", datuminhoudingofvermissing='" + getDatuminhoudingofvermissing() + "'" +
            ", datumuitgifte='" + getDatumuitgifte() + "'" +
            ", reisdocumentnummer='" + getReisdocumentnummer() + "'" +
            ", soort='" + getSoort() + "'" +
            "}";
    }
}
