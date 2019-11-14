//
//  LogInViewController.swift
//  cookingApp
//
//  Dustin Adkins,  duadkins@iu.edu
//  Kimi Chi,       zhaochi@iu.edu
//  Ahmed Shahzad,  ahshahz@iu.edu
//  Brandon Partin, branpart@iu.edu

//  Created by Adkins, Dustin T on 4/28/19.
//  Copyright © 2019 Chi, Kimi. All rights reserved.
//

import UIKit

class LogInViewController: UIViewController {

    @IBOutlet weak var errorMessage: UILabel!
    @IBOutlet weak var passTextField: UITextField!
    @IBOutlet weak var userTextField: UITextField!
    let myDefaults = UserDefaults.standard
    
    //Checks settings to see if user is using correct information. Otherwise tells them to log in again
    @IBAction func attemptLogIn(_ sender: Any) {
        if let pass = passTextField.text {
            if let user = userTextField.text {
                if(user == myDefaults.string(forKey: "user_preference") && pass == myDefaults.string(forKey: "pass_preference")){
                    
                    let board : UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
                    let mainViewController = board.instantiateViewController(withIdentifier: "mainBoard")
                    self.present(mainViewController, animated:true, completion: nil)
                }
                else{
                    errorMessage.text = "Your user name or password was incorrect. Please try again."
                }
            }
        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        errorMessage.text = ""
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
