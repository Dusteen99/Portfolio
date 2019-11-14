//
//  MatchRecipeCell.swift
//  cookingApp
//  cookingApp
// Dustin Adkins,  duadkins@iu.edu
// Kimi Chi,       zhaochi@iu.edu
// Ahmed Shahzad,  ahshahz@iu.edu
// Brandon Partin, branpart@iu.edu
// 4/28
//  Created by Ahmed on 4/27/19.
//  Copyright © 2019 Shahzad, Ahmed. All rights reserved.


import UIKit

class MatchRecipeCell: UITableViewCell {

    @IBOutlet weak var matchLabel: UILabel!
    
    //Shows the details of the recipe
    @IBAction func showRecipe(_ sender: Any) {
        //Getting model to get name of recipe and ingredients
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        myDataReference.getMatchingRecipes()
        if let name = matchLabel.text {
            let newRecipe = myDataReference.showRecipe(name: name) 
        }
    }
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}
