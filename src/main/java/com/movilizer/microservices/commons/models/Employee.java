package com.movilizer.microservices.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

/**
 * Employee
 */
public class Employee {

  private Long id;
  private String username = null;
  private String password = null;
  private String role = null;
  private String name = null;
  private String lastname = null;
  private String documentType = null;
  private Long documentNumber;
  private String gender = null;
  private String adress = null;
  private String city = null;
  private String province = null;
  private String contry = null;
  private String email = null;
  private Long contactTelephone1;
  private Long contactTelephone2;
  private String urgentName1 = null;
  private Long urgentTelephone1;
  private String urgentName2 = null;
  private List<ExtraField> extraFields;

  public Employee id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   **/

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Employee name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get extraFields
   *
   * @return extraFields
   **/

  public List<ExtraField> getExtraFields() {
    return extraFields;
  }


  public void setExtraFields(List<ExtraField> extraFields) {
    this.extraFields = extraFields;
  }

  public Employee extraFields(List<ExtraField> extraFields) {
    this.extraFields = extraFields;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   **/


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee lastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  /**
   * Get lastname
   *
   * @return lastname
   **/


  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Employee documentType(String documentType) {
    this.documentType = documentType;
    return this;
  }

  /**
   * Get documentType
   *
   * @return documentType
   **/

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public Employee documentNumber(Long documentNumber) {
    this.documentNumber = documentNumber;
    return this;
  }

  /**
   * Get documentNumber
   *
   * @return documentNumber
   **/

  public Long getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(Long documentNumber) {
    this.documentNumber = documentNumber;
  }

  public Employee gender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   *
   * @return gender
   **/

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Employee adress(String adress) {
    this.adress = adress;
    return this;
  }

  /**
   * Get adress
   *
   * @return adress
   **/

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  public Employee city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   *
   * @return city
   **/


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Employee province(String province) {
    this.province = province;
    return this;
  }

  /**
   * Get province
   *
   * @return province
   **/


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public Employee contry(String contry) {
    this.contry = contry;
    return this;
  }

  /**
   * Get contry
   *
   * @return contry
   **/


  public String getContry() {
    return contry;
  }

  public void setContry(String contry) {
    this.contry = contry;
  }

  public Employee email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   *
   * @return email
   **/


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Employee contactTelephone1(Long contactTelephone1) {
    this.contactTelephone1 = contactTelephone1;
    return this;
  }

  /**
   * Get contactTelephone1
   *
   * @return contactTelephone1
   **/


  public Long getContactTelephone1() {
    return contactTelephone1;
  }

  public void setContactTelephone1(Long contactTelephone1) {
    this.contactTelephone1 = contactTelephone1;
  }

  public Employee contactTelephone2(Long contactTelephone2) {
    this.contactTelephone2 = contactTelephone2;
    return this;
  }

  /**
   * Get contactTelephone2
   *
   * @return contactTelephone2
   **/


  public Long getContactTelephone2() {
    return contactTelephone2;
  }

  public void setContactTelephone2(Long contactTelephone2) {
    this.contactTelephone2 = contactTelephone2;
  }

  public Employee urgentName1(String urgentName1) {
    this.urgentName1 = urgentName1;
    return this;
  }

  /**
   * Get urgentName1
   *
   * @return urgentName1
   **/


  public String getUrgentName1() {
    return urgentName1;
  }

  public void setUrgentName1(String urgentName1) {
    this.urgentName1 = urgentName1;
  }

  public Employee urgentTelephone1(Long urgentTelephone1) {
    this.urgentTelephone1 = urgentTelephone1;
    return this;
  }

  /**
   * Get urgentTelephone1
   *
   * @return urgentTelephone1
   **/


  public Long getUrgentTelephone1() {
    return urgentTelephone1;
  }

  public void setUrgentTelephone1(Long urgentTelephone1) {
    this.urgentTelephone1 = urgentTelephone1;
  }

  public Employee urgentName2(String urgentName2) {
    this.urgentName2 = urgentName2;
    return this;
  }

  /**
   * Get urgentName2
   *
   * @return urgentName2
   **/


  public String getUrgentName2() {
    return urgentName2;
  }

  public void setUrgentName2(String urgentName2) {
    this.urgentName2 = urgentName2;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id) &&
            Objects.equals(this.name, employee.name) &&
            Objects.equals(this.lastname, employee.lastname) &&
            Objects.equals(this.documentType, employee.documentType) &&
            Objects.equals(this.documentNumber, employee.documentNumber) &&
            Objects.equals(this.gender, employee.gender) &&
            Objects.equals(this.adress, employee.adress) &&
            Objects.equals(this.city, employee.city) &&
            Objects.equals(this.province, employee.province) &&
            Objects.equals(this.contry, employee.contry) &&
            Objects.equals(this.email, employee.email) &&
            Objects.equals(this.contactTelephone1, employee.contactTelephone1) &&
            Objects.equals(this.contactTelephone2, employee.contactTelephone2) &&
            Objects.equals(this.urgentName1, employee.urgentName1) &&
            Objects.equals(this.urgentTelephone1, employee.urgentTelephone1) &&
            Objects.equals(this.urgentName2, employee.urgentName2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lastname, documentType, documentNumber, gender, adress, city, province, contry, email, contactTelephone1, contactTelephone2, urgentName1, urgentTelephone1, urgentName2);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Employee {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
    sb.append("    documentNumber: ").append(toIndentedString(documentNumber)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    adress: ").append(toIndentedString(adress)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    province: ").append(toIndentedString(province)).append("\n");
    sb.append("    contry: ").append(toIndentedString(contry)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    contactTelephone1: ").append(toIndentedString(contactTelephone1)).append("\n");
    sb.append("    contactTelephone2: ").append(toIndentedString(contactTelephone2)).append("\n");
    sb.append("    urgentName1: ").append(toIndentedString(urgentName1)).append("\n");
    sb.append("    urgentTelephone1: ").append(toIndentedString(urgentTelephone1)).append("\n");
    sb.append("    urgentName2: ").append(toIndentedString(urgentName2)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
