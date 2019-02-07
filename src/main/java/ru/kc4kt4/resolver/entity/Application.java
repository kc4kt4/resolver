package ru.kc4kt4.resolver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The type Application.
 */
@Entity
@Table(name = "application")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "default_gen",
        sequenceName = "application_gen",
        allocationSize = 1)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @Column(name = "application_id")
    private Long applicationId;
}
