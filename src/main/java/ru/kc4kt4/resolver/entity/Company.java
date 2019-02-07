package ru.kc4kt4.resolver.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The type Company.
 */
@Entity
@Table(name = "company")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@PrimaryKeyJoinColumn(name = "application_id")
public class Company extends Application {

    @Column(name = "director_name")
    private String directorName;

    @Column(name = "director_surname")
    private String directorSurname;

    @Column(name = "company_name")
    private String companyName;
}
