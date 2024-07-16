package recipescraping.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import recipescraping.enums.FoodCategory;
import recipescraping.pojo.RecipeGetterSetter;

public class RecipeDetailsDBUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/Recipescraping";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createRecipe(RecipeGetterSetter recipe, String tableName) { 
        String sql = "INSERT INTO "+tableName+" (recipeId, recipeUrl, recipeName, ingredients, tags, preparationMethod, preparationTime, cookingTime, noOfServings, cuisineCategory, recipeCategory, foodCategory, recipeDescription) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, recipe.getRecipeId());
            pstmt.setString(2, recipe.getRecipeUrl());
            pstmt.setString(3, recipe.getRecipeName());
            pstmt.setString(4, String.join(",", recipe.getIngredients()));
            pstmt.setString(5, String.join(",", recipe.getTags()));
            pstmt.setString(6, String.join(",", recipe.getPreparationMethod()));
            pstmt.setString(7, recipe.getPreparationTime());
            pstmt.setString(8, recipe.getCookingTime());
            pstmt.setInt(9, recipe.getNoOfServings());
            pstmt.setString(10, recipe.getCuisineCategory());
            pstmt.setString(11, recipe.getRecipeCategory());
            //pstmt.setString(12, recipe.getFoodCategory().name());
            pstmt.setString(12, "");
            pstmt.setString(13, recipe.getRecipeDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
