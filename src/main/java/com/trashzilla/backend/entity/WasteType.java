package com.trashzilla.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "waste_types")
public class WasteType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "label")
    private String label;

    @Column(name = "class_name")
    private String className;


    private WasteType() {
    }
    public WasteType(String value, String label, String className) {
        this.value = value;
        this.label = label;
        this.className = className;
    }

    // Getters / Setters
    public Long getId() {
        return this.id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
}