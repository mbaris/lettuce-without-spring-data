package io.baris.performance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genie {

    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private int grade;

    public String getId() {
        return id;
    }

    public Genie setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Genie setName(String name) {
        this.name = name;
        return this;
    }

    public int getGrade() {
        return grade;
    }

    public Genie setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genie genie = (Genie) o;
        return grade == genie.grade &&
                Objects.equals(id, genie.id) &&
                Objects.equals(name, genie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Genie{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", grade=").append(grade);
        sb.append('}');
        return sb.toString();
    }
}