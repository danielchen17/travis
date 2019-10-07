package com.daniel.demotest;

public class PersonalInfo {
    private String username;
    private String title;

    public static class Builder {

        private PersonalInfo info;

        public Builder() {
            info = new PersonalInfo();
        }

        public Builder setUsername(String username) {
            info.username = username;
            return this;
        }

        public Builder setTitle(String title) {
            info.title = title;
            return this;
        }

        public String build() {
            return String.format("%s %s", info.title, info.username);
        }
    }
}
