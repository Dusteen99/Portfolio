using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using UnityEngine.UI;

public class PickUpPistol : MonoBehaviour
{
    //Used to calculate when to display text to player
    public float Distance;

    //Two texts shown on the world gun
    public GameObject CommandKey;
    private Text CommandKeyText;
    public GameObject Command;
    private Text CommandText;
    public GameObject WorldGun;

    //Player gun and its crosshair
    public GameObject PlayerGun;
    public Image Crosshair;

    //Bullet text shown to player
    public GameObject TotalBulletsObject;
    private Text TotalBulletsText;


    
    //Grabs components needed to change text for player
    void Awake(){
        CommandKeyText = CommandKey.GetComponent<Text>();
        CommandText = Command.GetComponent<Text>();
    }

    //Checks to see if player is close enough to display text
    void Update()
    {
        Distance = Player.TargetDistance;        
    }

    /*So for this function, the logic goes like this: We check if we are 
        close enough to the gun to display the text when we are moused over. If we are, we enable our texts.
        We then know the player is close enough to pick up the gun, so if they press
        our action button, we hide our text, activate the player's gun and disabled the gun
        on the table. When the player gets too far away, the texts are disabled again 
        since they don't need to be read.
    */
    void OnMouseOver(){
        if (Distance < 2){
            CommandKeyText.text = "[E]";
            CommandText.text = "Pick Up Pistol";
            CommandKey.SetActive(true);
            Command.SetActive(true);
            if(Input.GetButtonDown("Action")){
                WorldGun.SetActive(false);
                PlayerGun.SetActive(true);
                CommandKey.SetActive(false);
                Command.SetActive(false);
                //Newly added, enables our UI for our pistol ammo and enables crosshair
                TotalBulletsObject.SetActive(true);
                PlayerGun.GetComponent<Pistol>().WeaponPanel.SetActive(true);
                Crosshair.gameObject.SetActive(true);
            }
        }
        else{
            CommandKey.SetActive(false);
            Command.SetActive(false);
        }
    }

    //Hides the text from the player when they aren't mousing over
    void OnMouseExit(){
        CommandKey.SetActive(false);
        Command.SetActive(false);
    }
}
