package com.vitsed.tacocloud.data;

import com.vitsed.tacocloud.Ingredient;
import com.vitsed.tacocloud.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for(var ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreateAt(new Date());
        PreparedStatementCreator psc =
            new PreparedStatementCreatorFactory(
                "INSERT INTO Taco (name, createdAt) VALUES (?,?)",
                Types.VARCHAR, Types.TIMESTAMP
            ).newPreparedStatementCreator(Arrays.asList(
                taco.getName(),
                new Timestamp(taco.getCreateAt().getTime())
            ));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbc.update(
            "INSERT INTO Taco_Ingredients (taco, ingredient) " +
            "VALUES (?,?)",
            tacoId, ingredient.getId()
        );
    }
}
