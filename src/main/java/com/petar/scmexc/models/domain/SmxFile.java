package com.marco.scmexc.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javassist.bytecode.ByteArray;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(schema = "public", name = "files")
public class SmxFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fileName;

    @Column(name = "download_url")
    private String downloadUrl;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "data")
    private byte[] data;

    @Column(name = "date_uploaded")
    private ZonedDateTime dateUploaded;

    @Column(name = "virus_total_link")
    private String virusTotalLink;

    @Column(name = "safe")
    private boolean safe;

    @OneToOne(mappedBy = "smxFile")
    @JsonIgnore
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ZonedDateTime getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(ZonedDateTime dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getVirusTotalLink() {
        return virusTotalLink;
    }

    public void setVirusTotalLink(String virusTotalLink) {
        this.virusTotalLink = virusTotalLink;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
