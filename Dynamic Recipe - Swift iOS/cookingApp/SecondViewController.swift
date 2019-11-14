//
//  SecondViewController.swift
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

class SecondViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    //Reloading data as the view shows again
    override func viewWillAppear(_ animated: Bool) {
        self.tableView.reloadData()
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        //Getting model to see how many recipes there are
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        var howManyRows = myDataReference.allRecipes.count

        return howManyRows
    }
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeCell", for: indexPath) as! RecipeCell
        
        //Getting Model in order to get the name of the Recipe
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        
        cell.nameOfRecipe.text = myDataReference.getRecipeName()
        
        return cell
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

