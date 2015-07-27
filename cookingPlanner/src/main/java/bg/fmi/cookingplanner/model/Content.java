package bg.fmi.cookingplanner.model;

import java.io.Serializable;

public class Content extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ContentUnit[] contentUnits;

    public Content() {
    }

    public Content(ContentUnit[] contentUnits) {
        this.contentUnits = contentUnits;
    }

    public ContentUnit[] getContentUnits() {
        return contentUnits;
    }

    public void setContentUnits(ContentUnit[] contentUnits) {
        this.contentUnits = contentUnits;
    }

    public static class ContentUnit implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private long id;
        private Ingredient ingredient;
        private double amount;
        private Measurement measurement;
        private String description;

        public Ingredient getIngredient() {
            return ingredient;
        }

        public ContentUnit() {
            this.amount = -1;
        }

        public ContentUnit(Ingredient ingredient, Measurement measurement,
                double amount, String description) {
            this.ingredient = ingredient;
            this.measurement = measurement;
            this.amount = amount;
            this.description = description;
        }

        public ContentUnit(long id, Ingredient ingredient,
                Measurement measurement, double amount, String description) {
            this.ingredient = ingredient;
            this.measurement = measurement;
            this.amount = amount;
            this.description = description;
            this.id = id;
        }

        public void setIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public Measurement getMeasurement() {
            return measurement;
        }

        public void setMeasurement(Measurement measurement) {
            this.measurement = measurement;
        }

        public String getDescription() {
            return description;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
