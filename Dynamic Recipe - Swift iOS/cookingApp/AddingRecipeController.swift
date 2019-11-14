//
//  AddingRecipeController.swift
//  cookingApp
//Dustin Adkins,  duadkins@iu.edu
// Kimi Chi,       zhaochi@iu.edu
// Ahmed Shahzad,  ahshahz@iu.edu
// Brandon Partin, branpart@iu.edu
//  Created by Chi, Kimi on 4/23/19.
//  Copyright © 2019 Chi, Kimi. All rights reserved.
//

import UIKit

class AddingRecipeController: UIViewController {

    //Getting reference to model
    var appDelegate: AppDelegate?
    var myCookingModel: CookingModel?
    
    @IBOutlet weak var amtFieldFour: UITextField!
    @IBOutlet weak var amtFieldThree: UITextField!
    @IBOutlet weak var amtFieldTwo: UITextField!
    @IBOutlet weak var amtFieldOne: UITextField!
    @IBOutlet weak var ingredFieldFour: UITextField!
    @IBOutlet weak var ingredFieldThree: UITextField!
    @IBOutlet weak var ingredFieldTwo: UITextField!
    @IBOutlet weak var ingredFieldOne: UITextField!
    @IBOutlet weak var descField: UITextField!
    @IBOutlet weak var nameField: UITextField!
    
    
    
    @IBAction func cancelButton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    //Huge if let statement to handle all 10 of the text fields on the screen
    @IBAction func confirmAdd(_ sender: Any) {
        
        //Seeing if model is available to add a recipe
        if let appDelegate = UIApplication.shared.delegate as? AppDelegate{
            let myCooking = appDelegate.myCookingModel
        
            if let name = nameField.text {
                if let desc = descField.text {
                    if let ingred1 = ingredFieldOne.text {
                        if let ingred2 = ingredFieldTwo.text {
                            if let ingred3 = ingredFieldThree.text {
                                if let ingred4 = ingredFieldFour.text {
                                    if let amt1 = amtFieldOne.text {
                                        if let amt2 = amtFieldTwo.text {
                                            if let amt3 = amtFieldThree.text {
                                                if let amt4 = amtFieldFour.text {
                                                    
                                                    myCooking.addNewRecipe(name: name, desc: desc, in1: ingred1, amt1: amt1, in2: ingred2, amt2: amt2, in3: ingred3, amt3: amt3, in4: ingred4, amt4: amt4)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
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
