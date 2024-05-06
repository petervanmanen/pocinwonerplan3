package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Ingezetene.
 */
@Entity
@Table(name = "ingezetene")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ingezetene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidingeuropeeskiesrecht")
    private Boolean aanduidingeuropeeskiesrecht;

    @Column(name = "aanduidinguitgeslotenkiesrecht")
    private Boolean aanduidinguitgeslotenkiesrecht;

    @Column(name = "datumverkrijgingverblijfstitel")
    private String datumverkrijgingverblijfstitel;

    @Column(name = "datumverliesverblijfstitel")
    private String datumverliesverblijfstitel;

    @Column(name = "indicatieblokkering")
    private String indicatieblokkering;

    @Column(name = "indicatiecurateleregister")
    private String indicatiecurateleregister;

    @Column(name = "indicatiegezagminderjarige")
    private String indicatiegezagminderjarige;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftIngezetenes" }, allowSetters = true)
    private Verblijfstitel heeftVerblijfstitel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ingezetene id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAanduidingeuropeeskiesrecht() {
        return this.aanduidingeuropeeskiesrecht;
    }

    public Ingezetene aanduidingeuropeeskiesrecht(Boolean aanduidingeuropeeskiesrecht) {
        this.setAanduidingeuropeeskiesrecht(aanduidingeuropeeskiesrecht);
        return this;
    }

    public void setAanduidingeuropeeskiesrecht(Boolean aanduidingeuropeeskiesrecht) {
        this.aanduidingeuropeeskiesrecht = aanduidingeuropeeskiesrecht;
    }

    public Boolean getAanduidinguitgeslotenkiesrecht() {
        return this.aanduidinguitgeslotenkiesrecht;
    }

    public Ingezetene aanduidinguitgeslotenkiesrecht(Boolean aanduidinguitgeslotenkiesrecht) {
        this.setAanduidinguitgeslotenkiesrecht(aanduidinguitgeslotenkiesrecht);
        return this;
    }

    public void setAanduidinguitgeslotenkiesrecht(Boolean aanduidinguitgeslotenkiesrecht) {
        this.aanduidinguitgeslotenkiesrecht = aanduidinguitgeslotenkiesrecht;
    }

    public String getDatumverkrijgingverblijfstitel() {
        return this.datumverkrijgingverblijfstitel;
    }

    public Ingezetene datumverkrijgingverblijfstitel(String datumverkrijgingverblijfstitel) {
        this.setDatumverkrijgingverblijfstitel(datumverkrijgingverblijfstitel);
        return this;
    }

    public void setDatumverkrijgingverblijfstitel(String datumverkrijgingverblijfstitel) {
        this.datumverkrijgingverblijfstitel = datumverkrijgingverblijfstitel;
    }

    public String getDatumverliesverblijfstitel() {
        return this.datumverliesverblijfstitel;
    }

    public Ingezetene datumverliesverblijfstitel(String datumverliesverblijfstitel) {
        this.setDatumverliesverblijfstitel(datumverliesverblijfstitel);
        return this;
    }

    public void setDatumverliesverblijfstitel(String datumverliesverblijfstitel) {
        this.datumverliesverblijfstitel = datumverliesverblijfstitel;
    }

    public String getIndicatieblokkering() {
        return this.indicatieblokkering;
    }

    public Ingezetene indicatieblokkering(String indicatieblokkering) {
        this.setIndicatieblokkering(indicatieblokkering);
        return this;
    }

    public void setIndicatieblokkering(String indicatieblokkering) {
        this.indicatieblokkering = indicatieblokkering;
    }

    public String getIndicatiecurateleregister() {
        return this.indicatiecurateleregister;
    }

    public Ingezetene indicatiecurateleregister(String indicatiecurateleregister) {
        this.setIndicatiecurateleregister(indicatiecurateleregister);
        return this;
    }

    public void setIndicatiecurateleregister(String indicatiecurateleregister) {
        this.indicatiecurateleregister = indicatiecurateleregister;
    }

    public String getIndicatiegezagminderjarige() {
        return this.indicatiegezagminderjarige;
    }

    public Ingezetene indicatiegezagminderjarige(String indicatiegezagminderjarige) {
        this.setIndicatiegezagminderjarige(indicatiegezagminderjarige);
        return this;
    }

    public void setIndicatiegezagminderjarige(String indicatiegezagminderjarige) {
        this.indicatiegezagminderjarige = indicatiegezagminderjarige;
    }

    public Verblijfstitel getHeeftVerblijfstitel() {
        return this.heeftVerblijfstitel;
    }

    public void setHeeftVerblijfstitel(Verblijfstitel verblijfstitel) {
        this.heeftVerblijfstitel = verblijfstitel;
    }

    public Ingezetene heeftVerblijfstitel(Verblijfstitel verblijfstitel) {
        this.setHeeftVerblijfstitel(verblijfstitel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingezetene)) {
            return false;
        }
        return getId() != null && getId().equals(((Ingezetene) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ingezetene{" +
            "id=" + getId() +
            ", aanduidingeuropeeskiesrecht='" + getAanduidingeuropeeskiesrecht() + "'" +
            ", aanduidinguitgeslotenkiesrecht='" + getAanduidinguitgeslotenkiesrecht() + "'" +
            ", datumverkrijgingverblijfstitel='" + getDatumverkrijgingverblijfstitel() + "'" +
            ", datumverliesverblijfstitel='" + getDatumverliesverblijfstitel() + "'" +
            ", indicatieblokkering='" + getIndicatieblokkering() + "'" +
            ", indicatiecurateleregister='" + getIndicatiecurateleregister() + "'" +
            ", indicatiegezagminderjarige='" + getIndicatiegezagminderjarige() + "'" +
            "}";
    }
}
