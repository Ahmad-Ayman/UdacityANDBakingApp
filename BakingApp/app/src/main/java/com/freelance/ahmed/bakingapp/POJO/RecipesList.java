package com.freelance.ahmed.bakingapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 3/14/2018.
 */

public class RecipesList {
    private List<Recipes> recipes;

    public RecipesList() {
        this.recipes = new ArrayList<>();
    }

    public List<Recipes> getResult() {
        return recipes;
    }

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

        public class Ingredients {
            @SerializedName("quantity")
            private int quantity;
            @SerializedName("measure")
            private String measure;
            @SerializedName("ingredient")
            private String ingred;

            public Ingredients(int quantity, String measure, String ingred) {
                this.quantity = quantity;
                this.measure = measure;
                this.ingred = ingred;
            }

            public int getQuantity() {
                return quantity;
            }

            public String getIngred() {
                return ingred;
            }

            public String getMeasure() {
                return measure;
            }
        }

        public class Steps {
            @SerializedName("shortDescription")
            private String shortDesc;
            @SerializedName("description")
            private String desc;
            @SerializedName("videoURL")
            private String videourl;

            public Steps(String shortDesc, String desc, String videourl) {
                this.shortDesc = shortDesc;
                this.desc = desc;
                this.videourl = videourl;
            }

            public String getDesc() {
                return desc;
            }

            public String getShortDesc() {
                return shortDesc;
            }

            public String getVideourl() {
                return videourl;
            }
        }
    }

}
