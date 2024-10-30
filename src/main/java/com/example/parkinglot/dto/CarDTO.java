package com.example.parkinglot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CarDTO {
        Long id;

        @NotNull
        @Size(min = 1, max = 25)
        String model;

        @NotNull
        @Size(min = 1, max = 25)
        String make;

        @NotNull
        @Size(min = 1, max = 25)
        String color;

        @NotNull
        @Size(min = 1, max = 10)
        String registration;

        UserDTO user;

        public CarDTO(Long id, String model, String make, String color, String registration, UserDTO user) {
                this.id = id;
                this.model = model;
                this.make = make;
                this.color = color;
                this.registration = registration;
                this.user = user;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public @NotNull @Size(min = 1, max = 25) String getModel() {
                return model;
        }

        public void setModel(@NotNull @Size(min = 1, max = 25) String model) {
                this.model = model;
        }

        public @NotNull @Size(min = 1, max = 25) String getMake() {
                return make;
        }

        public void setMake(@NotNull @Size(min = 1, max = 25) String make) {
                this.make = make;
        }

        public @NotNull @Size(min = 1, max = 25) String getColor() {
                return color;
        }

        public void setColor(@NotNull @Size(min = 1, max = 25) String color) {
                this.color = color;
        }

        public @NotNull @Size(min = 1, max = 10) String getRegistration() {
                return registration;
        }

        public void setRegistration(@NotNull @Size(min = 1, max = 10) String registration) {
                this.registration = registration;
        }

        public UserDTO getUser() {
                return user;
        }

        public void setUser(UserDTO user) {
                this.user = user;
        }
}