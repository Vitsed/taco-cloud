package com.vitsed.tacocloud.data;


import com.vitsed.tacocloud.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> finAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
