package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Instructieregel.
 */
@Entity
@Table(name = "instructieregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Instructieregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "instructieregelinstrument")
    private String instructieregelinstrument;

    @Column(name = "instructieregeltaakuitoefening")
    private String instructieregeltaakuitoefening;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_instructieregel__beschrijftgebiedsaanwijzing_gebiedsaanw_51",
        joinColumns = @JoinColumn(name = "instructieregel_id"),
        inverseJoinColumns = @JoinColumn(name = "beschrijftgebiedsaanwijzing_gebiedsaanwijzing_id")
    )
    @JsonIgnoreProperties(value = { "verwijstnaarLocaties", "beschrijftgebiedsaanwijzingInstructieregels" }, allowSetters = true)
    private Set<Gebiedsaanwijzing> beschrijftgebiedsaanwijzingGebiedsaanwijzings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Instructieregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstructieregelinstrument() {
        return this.instructieregelinstrument;
    }

    public Instructieregel instructieregelinstrument(String instructieregelinstrument) {
        this.setInstructieregelinstrument(instructieregelinstrument);
        return this;
    }

    public void setInstructieregelinstrument(String instructieregelinstrument) {
        this.instructieregelinstrument = instructieregelinstrument;
    }

    public String getInstructieregeltaakuitoefening() {
        return this.instructieregeltaakuitoefening;
    }

    public Instructieregel instructieregeltaakuitoefening(String instructieregeltaakuitoefening) {
        this.setInstructieregeltaakuitoefening(instructieregeltaakuitoefening);
        return this;
    }

    public void setInstructieregeltaakuitoefening(String instructieregeltaakuitoefening) {
        this.instructieregeltaakuitoefening = instructieregeltaakuitoefening;
    }

    public Set<Gebiedsaanwijzing> getBeschrijftgebiedsaanwijzingGebiedsaanwijzings() {
        return this.beschrijftgebiedsaanwijzingGebiedsaanwijzings;
    }

    public void setBeschrijftgebiedsaanwijzingGebiedsaanwijzings(Set<Gebiedsaanwijzing> gebiedsaanwijzings) {
        this.beschrijftgebiedsaanwijzingGebiedsaanwijzings = gebiedsaanwijzings;
    }

    public Instructieregel beschrijftgebiedsaanwijzingGebiedsaanwijzings(Set<Gebiedsaanwijzing> gebiedsaanwijzings) {
        this.setBeschrijftgebiedsaanwijzingGebiedsaanwijzings(gebiedsaanwijzings);
        return this;
    }

    public Instructieregel addBeschrijftgebiedsaanwijzingGebiedsaanwijzing(Gebiedsaanwijzing gebiedsaanwijzing) {
        this.beschrijftgebiedsaanwijzingGebiedsaanwijzings.add(gebiedsaanwijzing);
        return this;
    }

    public Instructieregel removeBeschrijftgebiedsaanwijzingGebiedsaanwijzing(Gebiedsaanwijzing gebiedsaanwijzing) {
        this.beschrijftgebiedsaanwijzingGebiedsaanwijzings.remove(gebiedsaanwijzing);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instructieregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Instructieregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instructieregel{" +
            "id=" + getId() +
            ", instructieregelinstrument='" + getInstructieregelinstrument() + "'" +
            ", instructieregeltaakuitoefening='" + getInstructieregeltaakuitoefening() + "'" +
            "}";
    }
}
