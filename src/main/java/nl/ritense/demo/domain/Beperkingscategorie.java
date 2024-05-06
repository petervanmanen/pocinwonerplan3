package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Beperkingscategorie.
 */
@Entity
@Table(name = "beperkingscategorie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beperkingscategorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 8)
    @Column(name = "code", length = 8)
    private String code;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "iseenBeperkingscategorie")
    @JsonIgnoreProperties(value = { "emptyBeperkingscores", "iseenBeperkingscategorie", "isgebaseerdopBeschikking" }, allowSetters = true)
    private Set<Beperking> iseenBeperkings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beperkingscategorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Beperkingscategorie code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWet() {
        return this.wet;
    }

    public Beperkingscategorie wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Beperking> getIseenBeperkings() {
        return this.iseenBeperkings;
    }

    public void setIseenBeperkings(Set<Beperking> beperkings) {
        if (this.iseenBeperkings != null) {
            this.iseenBeperkings.forEach(i -> i.setIseenBeperkingscategorie(null));
        }
        if (beperkings != null) {
            beperkings.forEach(i -> i.setIseenBeperkingscategorie(this));
        }
        this.iseenBeperkings = beperkings;
    }

    public Beperkingscategorie iseenBeperkings(Set<Beperking> beperkings) {
        this.setIseenBeperkings(beperkings);
        return this;
    }

    public Beperkingscategorie addIseenBeperking(Beperking beperking) {
        this.iseenBeperkings.add(beperking);
        beperking.setIseenBeperkingscategorie(this);
        return this;
    }

    public Beperkingscategorie removeIseenBeperking(Beperking beperking) {
        this.iseenBeperkings.remove(beperking);
        beperking.setIseenBeperkingscategorie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beperkingscategorie)) {
            return false;
        }
        return getId() != null && getId().equals(((Beperkingscategorie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beperkingscategorie{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
