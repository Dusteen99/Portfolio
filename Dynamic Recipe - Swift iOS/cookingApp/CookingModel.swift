//
//  CookingModel.swift
//  RecipeNow
//
// Dustin Adkins,  duadkins@iu.edu
// Kimi Chi,       zhaochi@iu.edu
// Ahmed Shahzad,  ahshahz@iu.edu
// Brandon Partin, branpart@iu.edu
// 4/21
//  Copyright © 2019 Chi, Kimi. All rights reserved.
//
import Foundation

class CookingModel: Codable {
    
    
    var allRecipes: [[String]] = []
    var recipeCount = 0;
    var matchCount = 0;
    var recipeInfo: [String] = ["", "",  "",  "",  "",  "",  "", "", "",  ""]
    
    //Returns array containing all information about that Recipe
    func getRecipeInfo() -> [String]{
        return recipeInfo
    }
    
    //Returns recipe name
    func getRecipeName() -> String{
        var out = allRecipes[recipeCount]
        var output = out[0]
        recipeCount += 1;
        
        if (recipeCount >= allRecipes.count) {
            recipeCount = 0;
        }
        return output
    }
    
    //Used to show the recipe in the Recipe Details view when called
    //Loops to find the recipe, returns that information
    func showRecipe(name: String){
        var flag = true
        var counter = 0
        
        while(flag){
            var out = allRecipes[counter]
            if(name == out[0]){
                let curRec = allRecipes[counter]
                flag = false
                recipeInfo = [curRec[0], curRec[1], curRec[2], curRec[3], curRec[4],curRec[5], curRec[6], curRec[7], curRec[8], curRec[9]]
            }
            counter += 1
        }
    }
    
    
    var matchingRecipe : [String] = []
    var currentIngredientNames:[String] = []
    var currentIngredientAmounts: [String] = []
    var currentCountIng = 0;
    var currentCountAmt = 0;
    var availRecipes : [String] = []
    
    //Function for finding the matching recipe by name
    func findRecipeAll(name: String) -> [String]{
        var matching = name
        
        for recipe in allRecipes {
            var recipeMatchName = getRecipeName()
            if recipe.contains(matching) {
                if matchingRecipe.contains(recipeMatchName){
                     recipeMatchName = getRecipeName()
                    }
                self.matchingRecipe.append(recipeMatchName)
                recipeMatchName = ""
                }
            }
        return matchingRecipe
    }
    
    func getMatchingRecipes(){
        availRecipes = []
        for recipe in allRecipes{
            //Cycle through ingredings, see if they match. If they dont, move to next, if they do, go to array
            var ingred1 = recipe[2]
            var ingred2 = recipe[4]
            var ingred3 = recipe[6]
            var ingred4 = recipe[8]
            
            var first = false
            var second = false
            var third = false
            var fourth = false
            
            //One loop for each ingredient
            for ing in currentIngredientNames{
                if(ingred1 == ""){
                    first = true
                }
                if(ingred1 == ing){
                    first = true
                }
            }
            
            for ing in currentIngredientNames{
                if(ingred2 == ""){
                    second = true
                }
                if(ingred2 == ing){
                    second = true
                }
            }
            
            for ing in currentIngredientNames{
                if(ingred3 == ""){
                    third = true
                }
                if(ingred3 == ing){
                    third = true
                }
            }
            
            for ing in currentIngredientNames{
                if(ingred4 == ""){
                    fourth = true
                }
                if(ingred4 == ing){
                    fourth = true
                }
            }
            
            if (first && second && third && fourth){
                availRecipes.append(recipe[0])
            }
            
        }
    }
    
    //This function is used in the table to display the matching recipes
    var availCount = 0
    func showAvailRecipes() -> String{
        var avail = availRecipes[availCount]
        availCount += 1
        if (availCount >= availRecipes.count) {
            availCount = 0;
        }
        return avail
    }
    
    //Adds a new ingredient to the array, updates the table
    func addNewIngredient(name: String, amount: String) {
        currentIngredientNames.append(name)
        currentIngredientAmounts.append(amount)
        
        NotificationCenter.default.post(name: Notification.Name(rawValue: "updateIngredients"), object: nil)
    }
    
    
    //Returns name of the Ingredient for the table
    func retreiveName() -> String {
        let out = currentIngredientNames[currentCountIng]
        currentCountIng += 1;
        if (currentCountIng >= currentIngredientNames.count) {
            currentCountIng = 0;
        }
        return out
        
    }
    
    //Returns amount of the ingredient for the table
    func retreiveAmount() -> String  {
        let out = currentIngredientAmounts[currentCountAmt]
        currentCountAmt += 1;
        if (currentCountAmt >= currentIngredientNames.count) {
            currentCountAmt = 0;
        }
        return out
    }
    
    //Adds a new recipe to the array
    func addNewRecipe(name: String, desc: String, in1: String, amt1: String,in2: String, amt2: String,in3: String, amt3: String,in4: String, amt4: String){
        
        allRecipes.append([name,desc,in1,amt1,in2,amt2,in3,amt3,in4,amt4])
        NotificationCenter.default.post(name: Notification.Name(rawValue: "updateRecipes"), object: nil)
        
    }
}

