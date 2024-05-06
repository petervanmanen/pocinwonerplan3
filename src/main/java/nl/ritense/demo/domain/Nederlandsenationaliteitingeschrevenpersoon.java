package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Nederlandsenationaliteitingeschrevenpersoon.
 */
@Entity
@Table(name = "nederlandsenationaliteitingeschrevenpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nederlandsenationaliteitingeschrevenpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidingbijzondernederlanderschap")
    private String aanduidingbijzondernederlanderschap;

    @Column(name = "nationaliteit")
    private String nationaliteit;

    @Column(name = "redenverkrijgingnederlandsenationaliteit")
    private String redenverkrijgingnederlandsenationaliteit;

    @Column(name = "redenverliesnederlandsenationaliteit")
    private String redenverliesnederlandsenationaliteit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nederlandsenationaliteitingeschrevenpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidingbijzondernederlanderschap() {
        return this.aanduidingbijzondernederlanderschap;
    }

    public Nederlandsenationaliteitingeschrevenpersoon aanduidingbijzondernederlanderschap(String aanduidingbijzondernederlanderschap) {
        this.setAanduidingbijzondernederlanderschap(aanduidingbijzondernederlanderschap);
        return this;
    }

    public void setAanduidingbijzondernederlanderschap(String aanduidingbijzondernederlanderschap) {
        this.aanduidingbijzondernederlanderschap = aanduidingbijzondernederlanderschap;
    }

    public String getNationaliteit() {
        return this.nationaliteit;
    }

    public Nederlandsenationaliteitingeschrevenpersoon nationaliteit(String nationaliteit) {
        this.setNationaliteit(nationaliteit);
        return this;
    }

    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public String getRedenverkrijgingnederlandsenationaliteit() {
        return this.redenverkrijgingnederlandsenationaliteit;
    }

    public Nederlandsenationaliteitingeschrevenpersoon redenverkrijgingnederlandsenationaliteit(
        String redenverkrijgingnederlandsenationaliteit
    ) {
        this.setRedenverkrijgingnederlandsenationaliteit(redenverkrijgingnederlandsenationaliteit);
        return this;
    }

    public void setRedenverkrijgingnederlandsenationaliteit(String redenverkrijgingnederlandsenationaliteit) {
        this.redenverkrijgingnederlandsenationaliteit = redenverkrijgingnederlandsenationaliteit;
    }

    public String getRedenverliesnederlandsenationaliteit() {
        return this.redenverliesnederlandsenationaliteit;
    }

    public Nederlandsenationaliteitingeschrevenpersoon redenverliesnederlandsenationaliteit(String redenverliesnederlandsenationaliteit) {
        this.setRedenverliesnederlandsenationaliteit(redenverliesnederlandsenationaliteit);
        return this;
    }

    public void setRedenverliesnederlandsenationaliteit(String redenverliesnederlandsenationaliteit) {
        this.redenverliesnederlandsenationaliteit = redenverliesnederlandsenationaliteit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nederlandsenationaliteitingeschrevenpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Nederlandsenationaliteitingeschrevenpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nederlandsenationaliteitingeschrevenpersoon{" +
            "id=" + getId() +
            ", aanduidingbijzondernederlanderschap='" + getAanduidingbijzondernederlanderschap() + "'" +
            ", nationaliteit='" + getNationaliteit() + "'" +
            ", redenverkrijgingnederlandsenationaliteit='" + getRedenverkrijgingnederlandsenationaliteit() + "'" +
            ", redenverliesnederlandsenationaliteit='" + getRedenverliesnederlandsenationaliteit() + "'" +
            "}";
    }
}
