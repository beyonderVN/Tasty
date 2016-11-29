package com.vnwarriors.tastyclarify.model;

/**
 * Created by Long on 11/16/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostModel implements Serializable{

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("tipImageRatio")
    @Expose
    private Double tipImageRatio;
    @SerializedName("tipTime")
    @Expose
    private String tipTime;
    @SerializedName("tipHot")
    @Expose
    private Boolean tipHot;
    @SerializedName("tipOven")
    @Expose
    private Boolean tipOven;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("tipName")
    @Expose
    private String tipName;
    @SerializedName("tipDescription")
    @Expose
    private String tipDescription;
    @SerializedName("tipIngredients")
    @Expose
    private String tipIngredients;
    @SerializedName("tipZzz")
    @Expose
    private String tipZzz;
    @SerializedName("tipImage")
    @Expose
    private TipImage tipImage;
    @SerializedName("tipPortion")
    @Expose
    private String tipPortion;
    @SerializedName("tipPublished")
    @Expose
    private Integer tipPublished;
    @SerializedName("tipDifficulty")
    @Expose
    private Integer tipDifficulty;
    @SerializedName("tipSeasons")
    @Expose
    private String tipSeasons;
    @SerializedName("tipCategories")
    @Expose
    private String tipCategories;
    @SerializedName("tipSender")
    @Expose
    private String tipSender;
    @SerializedName("tipDairy")
    @Expose
    private Boolean tipDairy;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("tipPersons")
    @Expose
    private Integer tipPersons;

    @SerializedName("tipUserId")
    @Expose
    private String tipUserId;
    public String getTipUserId() {
        return tipUserId;
    }

    @SerializedName("tipComments")
    @Expose
    private List<Comment> tipComments;

    public void setTipUserId(String tipUserId) {
        this.tipUserId = tipUserId;
    }

    public List<Comment> getTipComments() {
        return tipComments;
    }

    public void setTipComments(List<Comment> tipComments) {
        this.tipComments = tipComments;
    }

    public PostModel() {
    }

    public PostModel(String tipName) {
        this.tipName = tipName;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return The tipImageRatio
     */
    public Double getTipImageRatio() {
        return tipImageRatio;
    }

    /**
     * @param tipImageRatio The tipImageRatio
     */
    public void setTipImageRatio(Double tipImageRatio) {
        this.tipImageRatio = tipImageRatio;
    }

    /**
     * @return The tipTime
     */
    public String getTipTime() {
        return tipTime;
    }

    /**
     * @param tipTime The tipTime
     */
    public void setTipTime(String tipTime) {
        this.tipTime = tipTime;
    }

    /**
     * @return The tipHot
     */
    public Boolean getTipHot() {
        return tipHot;
    }

    /**
     * @param tipHot The tipHot
     */
    public void setTipHot(Boolean tipHot) {
        this.tipHot = tipHot;
    }

    /**
     * @return The tipOven
     */
    public Boolean getTipOven() {
        return tipOven;
    }

    /**
     * @param tipOven The tipOven
     */
    public void setTipOven(Boolean tipOven) {
        this.tipOven = tipOven;
    }

    /**
     * @return The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return The tipName
     */
    public String getTipName() {
        return tipName;
    }

    /**
     * @param tipName The tipName
     */
    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    /**
     * @return The tipDescription
     */
    public String getTipDescription() {
        return tipDescription;
    }

    /**
     * @param tipDescription The tipDescription
     */
    public void setTipDescription(String tipDescription) {
        this.tipDescription = tipDescription;
    }

    /**
     * @return The tipIngredients
     */
    public String getTipIngredients() {
        return tipIngredients;
    }

    /**
     * @param tipIngredients The tipIngredients
     */
    public void setTipIngredients(String tipIngredients) {
        this.tipIngredients = tipIngredients;
    }

    /**
     * @return The tipZzz
     */
    public String getTipZzz() {
        return tipZzz;
    }

    /**
     * @param tipZzz The tipZzz
     */
    public void setTipZzz(String tipZzz) {
        this.tipZzz = tipZzz;
    }

    /**
     * @return The tipImage
     */
    public TipImage getTipImage() {
        return tipImage;
    }

    /**
     * @param tipImage The tipImage
     */
    public void setTipImage(TipImage tipImage) {
        this.tipImage = tipImage;
    }

    /**
     * @return The tipPortion
     */
    public String getTipPortion() {
        return tipPortion;
    }

    /**
     * @param tipPortion The tipPortion
     */
    public void setTipPortion(String tipPortion) {
        this.tipPortion = tipPortion;
    }

    /**
     * @return The tipPublished
     */
    public Integer getTipPublished() {
        return tipPublished;
    }

    /**
     * @param tipPublished The tipPublished
     */
    public void setTipPublished(Integer tipPublished) {
        this.tipPublished = tipPublished;
    }

    /**
     * @return The tipDifficulty
     */
    public Integer getTipDifficulty() {
        return tipDifficulty;
    }

    /**
     * @param tipDifficulty The tipDifficulty
     */
    public void setTipDifficulty(Integer tipDifficulty) {
        this.tipDifficulty = tipDifficulty;
    }

    /**
     * @return The tipSeasons
     */
    public String getTipSeasons() {
        return tipSeasons;
    }

    /**
     * @param tipSeasons The tipSeasons
     */
    public void setTipSeasons(String tipSeasons) {
        this.tipSeasons = tipSeasons;
    }

    /**
     * @return The tipCategories
     */
    public String getTipCategories() {
        return tipCategories;
    }

    /**
     * @param tipCategories The tipCategories
     */
    public void setTipCategories(String tipCategories) {
        this.tipCategories = tipCategories;
    }

    /**
     * @return The tipSender
     */
    public String getTipSender() {
        return tipSender;
    }

    /**
     * @param tipSender The tipSender
     */
    public void setTipSender(String tipSender) {
        this.tipSender = tipSender;
    }

    /**
     * @return The tipDairy
     */
    public Boolean getTipDairy() {
        return tipDairy;
    }

    /**
     * @param tipDairy The tipDairy
     */
    public void setTipDairy(Boolean tipDairy) {
        this.tipDairy = tipDairy;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The tipPersons
     */
    public Integer getTipPersons() {
        return tipPersons;
    }

    /**
     * @param tipPersons The tipPersons
     */
    public void setTipPersons(Integer tipPersons) {
        this.tipPersons = tipPersons;
    }

}

