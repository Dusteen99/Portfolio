//
//  AppDelegate.swift
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

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    //Creating a model
    var window: UIWindow?
    var myCookingModel : CookingModel = CookingModel()

    //Loads local data
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        let manager = FileManager.default
        
        do{
            let documentURL = try manager.url(for:.documentDirectory,
                                              in: .userDomainMask, appropriateFor: nil, create: false)
            var cookingFile = documentURL.appendingPathComponent("MyRecipeFolder", isDirectory: true)
            cookingFile = cookingFile.appendingPathComponent("theCookingFile.txt")
            let cookingData = try Data(contentsOf: cookingFile)
            self.myCookingModel = try PropertyListDecoder().decode(CookingModel.self, from: cookingData)
        }
        catch{
            print("Something went wrong!")
        }
        return true
    }

    //App saves our local data (Ingredients and All Recipes) when app enters this state
    func applicationWillResignActive(_ application: UIApplication) {
        
        //encoding the model instance
        let myPropertyList = PropertyListEncoder()

        do {
            let encoded = try myPropertyList.encode(myCookingModel)
            
            let lFM = FileManager()
            
            //Creating the document URL
            let lDocumentsDirectoryURL = try! lFM.url(for: .documentDirectory,
                                                      in: .userDomainMask,
                                                      appropriateFor: nil,
                                                      create: true)
            
            //Creating folder path, calling MyRecipeFolder
            let myFolderNameWithPath = lDocumentsDirectoryURL.appendingPathComponent("MyRecipeFolder", isDirectory: true)
            
            do{
                try lFM.createDirectory(at: myFolderNameWithPath,
                                        withIntermediateDirectories: true,
                                        attributes: nil)
                
            } catch let err as NSError {
                print( "**** DANGER \(err) detected when creating directory ***** \n")
            }
            
            //Creating the file
            do {
                try encoded.write(to: myFolderNameWithPath.appendingPathComponent("theCookingFile.txt"))
            } catch let err as NSError {
                print( "**** DANGER \(err) detected when creating file ***** \n")
            }
        }
        catch {
            print("Something went wrong!")
        }
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

