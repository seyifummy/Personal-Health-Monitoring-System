/*
 * Copyright (c) 2014 Personal-Health-Monitoring-System
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cse3310.phms.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.cse3310.phms.ui.utils.UserSingleton;

@Table(name = "Info")
public class Info extends Model{

    @Column private String firstName;
    @Column private String lastName;
    @Column private String email;
    @Column private String phone;
    @Column protected User user; // used as a foreign key

    /**
     * Instantiates a new Info.
     */
    public Info() {
        super();
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    /**
     * Instantiates a new Info.
     *
     * @param firstName the first name
     * @param lastName the last name
     */
    public Info(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        user = UserSingleton.INSTANCE.getCurrentUser();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public Info setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public Info setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Gets full name.
     *
     * @return the first and last name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public Info setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets phone.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone number
     */
    public Info setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String toString() {
        return "Info{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
