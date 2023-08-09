package com.quangho.designpattern.saga.demo.sim_card_data_package.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity(name = "DataPackage")
@Table(name = "data_package")
@Data
@NoArgsConstructor
public class DataPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "package_name", unique = true)
    @Length(max = 50)
    protected String packageName;

    @Column(name = "package_description")
    @Length(max = 300)
    protected String packageDesc;

    @Column(name = "package_price")
    protected float packagePrice;

    @Column(name = "package_duration")
    protected int packageDuration;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;

    public DataPackage(@Length(max = 50) String packageName, @Length(max = 300) String packageDesc, float packagePrice, int packageDuration) {
        this.packageName = packageName;
        this.packageDesc = packageDesc;
        this.packagePrice = packagePrice;
        this.packageDuration = packageDuration;
    }
}
