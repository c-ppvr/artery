package net.ppvr.artery.datagen.recipe;


public enum ArteryRecipeCategory {
    INFUSION("infusion");

    private final String name;

    ArteryRecipeCategory(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
