//
//  RecipeCell.swift
//  cookingApp
//  cookingApp
// Dustin Adkins,  duadkins@iu.edu
// Kimi Chi,       zhaochi@iu.edu
// Ahmed Shahzad,  ahshahz@iu.edu
// Brandon Partin, branpart@iu.edu
// 4/28
//  Created by Ahmed on 4/27/19.
// Copyright © 2019 Chi, Kimi. All rights reserved.

import UIKit

class RecipeCell: UITableViewCell {

    
    @IBOutlet weak var nameOfRecipe: UILabel!
    
    //Shows details of the recipe
    @IBAction func showRecipe(_ sender: Any) {
        //Getting model to show the name of the recipe
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        
        if let name = nameOfRecipe.text {
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
