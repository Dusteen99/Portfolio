//
//  IngredientCell.swift
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

class IngredientCell: UITableViewCell {
    
    @IBOutlet weak var nameOfIngredient: UILabel!
    @IBOutlet weak var amountOfIngredient: UILabel!
    
    override func awakeFromNib(){
        super.awakeFromNib()
        
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}
