//
//  FirstViewController.swift
//  RecipeNow
//
//  Dustin Adkins,  duadkins@iu.edu
//  Kimi Chi,       zhaochi@iu.edu
//  Ahmed Shahzad,  ahshahz@iu.edu
//  Brandon Partin, branpart@iu.edu
//  4/21
//  Copyright © 2019 Chi, Kimi. All rights reserved.
//

import UIKit
import AVFoundation
import AVKit

class FirstViewController: UIViewController{

    @IBOutlet weak var ingredients: UILabel!
    @IBOutlet weak var desc: UILabel!
    @IBOutlet weak var nameOfRecipe: UILabel!
    var count = 0

    //Hard-coded back button to prevent errors
    @IBAction func backButton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    //Used to refresh the view
    @IBAction func loadAgain(_ sender: Any) {
        self.viewDidLoad()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Getting Delegate and model in order to obtain Recipe information
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        
        var stringArray = myDataReference.getRecipeInfo()
        
        //This fills the text fields needed to show the ingredients
        nameOfRecipe.text = stringArray[0]
        desc.text = stringArray[1]
        if (count > 0){
            
            //Conditions to make the ingredients field cleaner.
            var ingred = "Ingredients contained in the recipe: "
                
            if (stringArray[2] != ""){
                ingred += stringArray[3] + " of " + stringArray[2]
            }

            if (stringArray[4] != "" && stringArray[6] == ""){
                ingred += " and " + stringArray[5] + " of " + stringArray[4]
            }
            else if (stringArray[4] != ""){
                ingred += ", " + stringArray[5] + " of " + stringArray[4]
            }
            
            if (stringArray[6] != "" && stringArray[8] == ""){
                ingred += ", and " + stringArray[7] + " of " + stringArray[6]
            }
            else if (stringArray[6] != ""){
                ingred += ", " + stringArray[7] + " of " + stringArray[6]
            }
            
            if (stringArray[8] != ""){
                ingred += ", and " + stringArray[9] + " of " + stringArray[8]
            }
            ingredients.text = ingred
        }
        else{
            ingredients.text = ""
            desc.text = ""
            nameOfRecipe.text = ""
            count += 1
        }
    }
    
    //These should minimize the keyboard
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func minKeyBoardReturn(_ textField: UITextField) -> Bool{
        textField.resignFirstResponder()
        return true
    }
}

