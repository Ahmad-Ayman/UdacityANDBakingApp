package com.freelance.ahmed.walima;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 3/16/2018.
 */

public class Recipes {
    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredients> ingredients;

    @SerializedName("steps")
    private List<Steps> steps;

    public Recipes(String recipename) {
        this.name = recipename;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public static class Ingredients  implements Parcelable {
        @SerializedName("quantity")
        private double quantity;
        @SerializedName("measure")
        private String measure;
        @SerializedName("ingredient")
        private String ingred;

        public Ingredients(int quantity, String measure, String ingred) {
            this.quantity = quantity;
            this.measure = measure;
            this.ingred = ingred;
        }

        protected Ingredients(Parcel in) {
            quantity = in.readDouble();
            measure = in.readString();
            ingred = in.readString();
        }

        public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
            @Override
            public Ingredients createFromParcel(Parcel in) {
                return new Ingredients(in);
            }

            @Override
            public Ingredients[] newArray(int size) {
                return new Ingredients[size];
            }
        };

        public double getQuantity() {
            return quantity;
        }

        public String getIngred() {
            return ingred;
        }

        public String getMeasure() {
            return measure;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(quantity);
            parcel.writeString(measure);
            parcel.writeString(ingred);
        }
    }

    public static class Steps implements Parcelable{
        @SerializedName("shortDescription")
        private String shortDesc;
        @SerializedName("description")
        private String desc;
        @SerializedName("videoURL")
        private String videourl;
        @SerializedName("thumbnailURL")
        private String thumb;

        public Steps(String shortDesc, String desc, String videourl,String thumb) {
            this.shortDesc = shortDesc;
            this.desc = desc;
            this.videourl = videourl;
            this.thumb=thumb;
        }

        protected Steps(Parcel in) {
            shortDesc = in.readString();
            desc = in.readString();
            videourl = in.readString();
            thumb = in.readString();
        }

        public static final Creator<Steps> CREATOR = new Creator<Steps>() {
            @Override
            public Steps createFromParcel(Parcel in) {
                return new Steps(in);
            }

            @Override
            public Steps[] newArray(int size) {
                return new Steps[size];
            }
        };

        public String getDesc() {
            return desc;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public String getVideourl() {
            return videourl;
        }

        public String getThumb() {
            return thumb;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(shortDesc);
            parcel.writeString(desc);
            parcel.writeString(videourl);
            parcel.writeString(thumb);
        }
    }
}

