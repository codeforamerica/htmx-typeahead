package org.codeforamerica.htmxtypeahead;

public class Provider {
  private String id;
  private String name;
  private String email;
  private String phone;
  private String address;

  // Getters and Setters
  public String getId() {
    return id;
  }

  public String getName() { return name; }

  public String getEmail() {
    return email;
  }

  public String getPhone() { return phone; }

  public String getAddress() { return address; }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        '}';
  }
}
