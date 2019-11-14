//
//  AvailableRecipeTableViewController.swift
//  cookingApp
// Dustin Adkins,  duadkins@iu.edu
// Kimi Chi,       zhaochi@iu.edu
// Ahmed Shahzad,  ahshahz@iu.edu
// Brandon Partin, branpart@iu.edu
// 4/28
//  Created by Ahmed on 4/27/19.
//  Copyright © 2019 Shahzad, Ahmed. All rights reserved.

import UIKit

class AvailableRecipeTableViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        self.tableView.reloadData()
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //Getting the model to count the number of recipes
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        myDataReference.getMatchingRecipes()
        var howManyRows = myDataReference.availRecipes.count
        
        
        return howManyRows
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //Making cells not reusable
        let cell = tableView.dequeueReusableCell(withIdentifier: "AvailableRecipe", for: indexPath) as! MatchRecipeCell
        
        //Getting model to count number of matching recipes
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        cell.matchLabel.text = myDataReference.showAvailRecipes()
        
        return cell
    }
}
