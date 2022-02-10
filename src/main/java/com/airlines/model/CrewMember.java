package com.airlines.model;

import java.time.LocalDate;
import java.util.Objects;

public class CrewMember {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final Position position;
    private final LocalDate birthday;
    private final Citizenship citizenship;
    private final int crewId;

    private CrewMember(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.position = builder.position;
        this.birthday = builder.birthday;
        this.citizenship = builder.citizenship;
        this.crewId = builder.crewId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Position getPosition() {
        return position;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    public int getCrewId() {
        return crewId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private Position position;
        private LocalDate birthday;
        private Citizenship citizenship;
        private int crewId;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withPosition(Position position) {
            this.position = position;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withCitizenship(Citizenship citizenship) {
            this.citizenship = citizenship;
            return this;
        }

        public Builder withCrewId(int crewId) {
            this.crewId = crewId;
            return this;
        }

        public CrewMember build() {
            return new CrewMember(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrewMember)) {
            return false;
        }
        CrewMember that = (CrewMember) o;
        return getId() == that.getId() && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && getPosition() == that.getPosition() && Objects.equals(getBirthday(), that.getBirthday());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPosition(), getBirthday());
    }
}