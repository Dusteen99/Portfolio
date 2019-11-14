//
//  ThirdViewController.swift
//  RecipeNow
//
// Dustin Adkins,  duadkins@iu.edu
// Kimi Chi,       zhaochi@iu.edu
// Ahmed Shahzad,  ahshahz@iu.edu
// Brandon Partin, branpart@iu.edu
// 4/21
//  Copyright © 2019 Chi, Kimi. All rights reserved.
//

import UIKit

class AddIngredientController: UIViewController {
    
    //Getting reference to our Model
    var appDelegate: AppDelegate?
    var myCookingModel: CookingModel?
    
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var amountTextField: UITextField!
    
    //Hard-coded back button
    @IBAction func cancelButton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    //Adds the information from the two text fields to array in the model, dismisses when done
    @IBAction func confirmAdd(_ sender: Any) {
        if let appDelegate = UIApplication.shared.delegate as? AppDelegate{
            let myCooking = appDelegate.myCookingModel

            if let nameText = nameTextField.text {
                if let amountText = amountTextField.text {
                    myCooking.addNewIngredient(name: nameText, amount: amountText)
                }
            }
        }
            self.dismiss(animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
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
