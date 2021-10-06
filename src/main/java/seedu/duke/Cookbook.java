package seedu.duke;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Cookbook {
    protected ArrayList<Recipe> recipes;

    public Cookbook() {
        recipes = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < recipes.size(); i++) {
            output.append(i + 1).append(". ");
            output.append(recipes.get(i).getName());
            output.append(System.lineSeparator());
        }
        return output.toString();
    }

    public void listRecipe() {
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }
    }

    public void addRecipe(Recipe r) throws GordonException {
        try {
            r.setId(recipes.size() + 1);
            recipes.add(r);
        } catch (IndexOutOfBoundsException e) {
            throw new GordonException(GordonException.INDEX_OOB);
        } catch (IllegalArgumentException e) {
            throw new GordonException(GordonException.INDEX_INVALID);
        }
    }

    public void removeRecipe(String input) throws GordonException {
        try {
            String inputIndex = input.contains(" ") ? input.substring(input.indexOf(" ") + 1) : " ";
            if (inputIndex.isEmpty() || inputIndex.equals(" ")) {
                throw new GordonException(GordonException.INVALID_RECIPE);
            }
            int index = Integer.parseInt(inputIndex);
            recipes.remove(index - 1);
            System.out.println("OK! The recipe has been deleted from your cookbook.");
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new GordonException(GordonException.INVALID_RECIPE);
        }
    }

    public void checkRecipe(int index) throws GordonException {
        try {
            System.out.println(recipes.get(index).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new GordonException(GordonException.INDEX_OOB);
        } catch (IllegalArgumentException e) {
            throw new GordonException(GordonException.INDEX_INVALID);
        }
    }

    public void checkRecipe(String name) throws GordonException {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                System.out.println(recipe);
                return;
            }
        }

        throw new GordonException(GordonException.NO_RESULT_FOUND);
    }

    public void sortByID() {
        Comparator<Recipe> compareByID = Comparator
                .comparing(Recipe::getID);
        recipes = recipes.stream()
                .sorted(compareByID)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void sortByPrice() {
        Comparator<Recipe> compareByPrice = Comparator.comparing(Recipe::getTotalPrice)
                .thenComparing(Recipe::getID);
        recipes = recipes.stream()
                .sorted(compareByPrice)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void sortByCalories() {
        Comparator<Recipe> compareByCalories = Comparator.comparing(Recipe::getCalories)
                .thenComparing(Recipe::getID);
        recipes = recipes.stream()
                .sorted(compareByCalories)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Recipe> filterByTag(String tag) {
        Comparator<Recipe> compareByID = Comparator
                .comparing(Recipe::getID);
        return recipes.stream()
                .filter(r -> r.getTags().contains(tag.toLowerCase()))
                .sorted(compareByID)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
