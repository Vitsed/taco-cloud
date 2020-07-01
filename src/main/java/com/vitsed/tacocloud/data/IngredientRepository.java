package com.vitsed.tacocloud.data;


import com.vitsed.tacocloud.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
