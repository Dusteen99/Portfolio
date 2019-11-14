//
//  VideoController.swift
//  cookingApp
//
//  Dustin Adkins,  duadkins@iu.edu
//  Kimi Chi,       zhaochi@iu.edu
//  Ahmed Shahzad,  ahshahz@iu.edu
//  Brandon Partin, branpart@iu.edu

//  Created by Adkins, Dustin T on 5/1/19.
//  Copyright © 2019 Chi, Kimi. All rights reserved.
//

import UIKit
import MobileCoreServices
import AVFoundation
import AVKit

class VideoController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate{

  
   //Loads the video from Document Directory to play
   //Mauve is supposed to provide videos. Right now, it just records a video and allows you to
   //play it back immediately, as stated in the contract. The implementation would change slightly
   //otherwise
    @IBAction func recordVideo(_ sender: Any) {
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            let picker = UIImagePickerController()
            picker.delegate = self
            picker.sourceType = .camera
            picker.mediaTypes = [kUTTypeMovie as String]
            picker.allowsEditing = true
            self.present(picker, animated: true, completion: nil)
            
        } else {
            print("Camera Unavailable")
        }
    }
    
    //Function is used to save the video
    func imagePickerController(_ picker: UIImagePickerController,
                               didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        dismiss(animated: true, completion: nil)
        
        //Getting Delegate in order to get recipe name
        let myAppDelegate = UIApplication.shared.delegate as! AppDelegate
        let myDataReference = myAppDelegate.myCookingModel
        var nameOfRecipe = myDataReference.getRecipeName()
        
        let lFM = FileManager()
        
        //Getting the URL of the video
        let videoURL = info[UIImagePickerController.InfoKey.mediaURL] as! URL
        
        //Loading Video
        let videoData = NSData(contentsOf: videoURL as URL)
        
        //Getting Document Directory path, where videos will be saved
        let paths = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.documentDirectory, FileManager.SearchPathDomainMask.userDomainMask, true)[0]
        
        //Creating the document URL
        var lDocumentsDirectoryURL = try! lFM.url(for: .documentDirectory,
                                                  in: .userDomainMask,
                                                  appropriateFor: nil,
                                                  create: true)
        lDocumentsDirectoryURL.appendPathComponent("\(paths)" + "\(nameOfRecipe)" + ".mp4")
        
        //Creating path for video
        do{
            try lFM.createDirectory(at: lDocumentsDirectoryURL,
                                    withIntermediateDirectories: true,
                                    attributes: nil)
            
        } catch let err as NSError {}
        
        // Adding video name to path
        let dataPath = "\(paths)" + "\(nameOfRecipe)" + ".mp4"
        
        //Saving Video
        videoData?.write(toFile: dataPath, atomically: false)
    }
    
    //Gives the user an alert telling them if their video was saved or not using conditionals
    @objc func video(_ videoPath: String, didFinishSavingWithError error: Error?, contextInfo info: AnyObject) {
        let title = (error == nil) ? "Success" : "Error"
        let message = (error == nil) ? "Video was saved" : "Video failed to save"
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.cancel, handler: nil))
        present(alert, animated: true, completion: nil)
    }

    //Hard-coded back button
    @IBAction func goBack(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
