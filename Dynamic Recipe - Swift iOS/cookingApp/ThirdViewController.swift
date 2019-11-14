//
//  ThirdViewController.swift
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

class ThirdViewController: UITableViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    //Reloads data as the view comes back
    override func viewWillAppear(_ animated: Bool) {
        self.tableView.reloadData()
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        //Getting model to get the number of Ingredients
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        var howManyRows = myDataReference.currentIngredientAmounts.count
        
        return howManyRows
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "IngredientCell", for: indexPath) as! IngredientCell
        
        //Getting model to get name and amount of ingredient
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        cell.nameOfIngredient.text = myDataReference.retreiveName()
        cell.amountOfIngredient.text = myDataReference.retreiveAmount()
        
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
