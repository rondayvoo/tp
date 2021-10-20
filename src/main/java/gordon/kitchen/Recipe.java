package gordon.kitchen;

import gordon.exception.GordonException;
import gordon.util.Difficulty;
import gordon.util.Tag;

import java.util.ArrayList;

public class Recipe {
    protected String name;
    protected Difficulty difficulty = Difficulty.None;
    protected int preparationTime = -1;
    protected int cookingTime = -1;
    protected ArrayList<String> ingredients;
    protected ArrayList<String> steps;
    protected float totalPrice = -1;
    protected int calories = -1;
    protected ArrayList<String> recipeTags;

    public Recipe(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        recipeTags = new ArrayList<>();
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public boolean containsIngredients(ArrayList<String> search) {
        ArrayList<String> ingredientsToLowercase = new ArrayList<>(ingredients);
        ingredientsToLowercase.replaceAll(String::toLowerCase);
        search.replaceAll(String::toLowerCase);
        return ingredientsToLowercase.containsAll(search);
    }

    public void addStep(String step) {
        steps.add(step);
    }

    public void addStep(String step, int index) throws GordonException {
        try {
            steps.add(index, step);
        } catch (IndexOutOfBoundsException e) {
            throw new GordonException(GordonException.INDEX_OOB);
        } catch (IllegalArgumentException e) {
            throw new GordonException(GordonException.INDEX_INVALID);
        }
    }

    public void removeStep(int index) throws GordonException {
        try {
            steps.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new GordonException(GordonException.INDEX_OOB);
        } catch (IllegalArgumentException e) {
            throw new GordonException(GordonException.INDEX_INVALID);
        }
    }

    /////////////////////////// TAGGING FUNCTIONALITIES ///////////////////////////
    public void addTagToRecipe(Tag tag, String recipeName) {
        try {
            // Checking if tag has been linked to recipe before
            if (doesRecipeTagExists(tag.getTagName())) {
                throw new GordonException(GordonException.DUPLICATE_TAG_NAME);
            }
            recipeTags.add(tag.getTagName());
            System.out.println("Successfully tagged " + recipeName + " under " + tag.getTagName());
        } catch (GordonException e) {
            System.out.println("GordonException: " + e.getMessage());
        }
    }

    public void deleteTagFromRecipe(Tag tag) {
        recipeTags.remove(tag.getTagName());
    }

    public boolean doesRecipeTagExists(String tagName) {
        for (String recipeTag : recipeTags) {
            if (recipeTag.toLowerCase().trim().equals(tagName.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public boolean containsTags(ArrayList<String> search) {
        ArrayList<String> tagsToLowercase = new ArrayList<>(recipeTags);
        tagsToLowercase.replaceAll(String::trim);
        tagsToLowercase.replaceAll(String::toLowerCase);
        search.replaceAll(String::trim);
        search.replaceAll(String::toLowerCase);
        return tagsToLowercase.containsAll(search);
    }

    /////////////////////////// GET/SET FUNCTIONALITIES ///////////////////////////
    public String getName() {
        return name.trim();
    }

    public void setTimes(int prepTime, int cookTime) {
        preparationTime = prepTime;
        cookingTime = cookTime;
    }

    public int getTotalTime() {
        return preparationTime + cookingTime;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder(name + System.lineSeparator());

        outputString.append("Calories:").append(calories).append("kcal").append(System.lineSeparator());

        if (difficulty != Difficulty.None) {
            outputString.append("Difficulty: ")
                    .append(System.lineSeparator())
                    .append(difficulty)
                    .append(System.lineSeparator());
        }

        if (preparationTime > -1) {
            outputString.append("Preparation time: ")
                    .append(System.lineSeparator())
                    .append(preparationTime)
                    .append(System.lineSeparator());
        }

        if (cookingTime > -1) {
            outputString.append("Cooking time: ")
                    .append(System.lineSeparator())
                    .append(cookingTime)
                    .append(System.lineSeparator());
        }

        if (totalPrice > -1) {
            outputString.append("Total price of ingredients: ")
                    .append(System.lineSeparator())
                    .append("$")
                    .append(String.format("%.2f", totalPrice))
                    .append(System.lineSeparator());
        }

        outputString.append("Ingredients needed: ").append(System.lineSeparator());
        for (int i = 0; i < ingredients.size(); i++) {
            outputString.append(i + 1).append(". ");
            outputString.append(ingredients.get(i));
            outputString.append(System.lineSeparator());
        }

        outputString.append("Method: ").append(System.lineSeparator());
        for (int j = 0; j < steps.size(); j++) {
            outputString.append(j + 1).append(". ");
            outputString.append(steps.get(j));
            outputString.append(System.lineSeparator());
        }

        outputString.append("Tags: ").append(System.lineSeparator());
        if (recipeTags.size() > 0) {
            for (int k = 0; k < recipeTags.size(); k++) {
                outputString.append(k + 1).append(". ");
                outputString.append(recipeTags.get(k));
                outputString.append(System.lineSeparator());
            }
        }

        return outputString.toString();
    }
}
