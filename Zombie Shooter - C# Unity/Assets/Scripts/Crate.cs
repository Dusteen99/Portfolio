using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Crate : MonoBehaviour
{
    //Tracks distance to know when to display text
    public float Distance;

    //Texts that may need to be shown
    public GameObject CommandKey;
    public GameObject Command;

    //The crate and its animator
    public GameObject ActualCrate;
    private Animator Animator;

    //Flag so we can disable text when the GameObject isn't supposed to
    //be interacted with
    private bool flag = true;

    //Audio cues to go with animation
    public AudioClip Opening;
    public AudioClip Closing;
    public AudioSource Box;

    //Pistol reference so we can update the ammo
    public GameObject Pistol;
    private Pistol PistolScript;

    //Gets our animator, inital audio source, and our pistol reference
    void Start()
    {
        Animator = ActualCrate.GetComponent<Animator>();
        Box = GetComponent<AudioSource>();
        PistolScript = Pistol.GetComponent<Pistol>();
    }

    //Used to display text when necessary
    void Update()
    {
        Distance = Player.TargetDistance;
    }

    //Only show our texts when mousing over the objects
    void OnMouseOver(){
        //The flag is to disable the text while the ammo box isn't active
        if (Distance < 2 && flag){
            CommandKey.SetActive(true);
            Command.SetActive(true);
            //When pressed, disable texts, disable them from showing again,
            //start our animation and Coroutine
            if(Input.GetButtonDown("Action")){
                CommandKey.SetActive(false);
                Command.SetActive(false);
                flag = false;
                Animator.SetTrigger("OpenCrate");
                StartCoroutine(OpenCrate());
            }
        }
        else{
            CommandKey.SetActive(false);
            Command.SetActive(false);
        }
    }

    //Give player bullets, update player UI, play sounds and animations
    IEnumerator OpenCrate(){
        PistolScript.Bullets += 7;
        PistolScript.TotalBulletsText.text = PistolScript.Bullets.ToString();
        StartCoroutine(OpenSound());
        yield return new WaitForSeconds(3f);
        ActualCrate.SetActive(false);
        StartCoroutine(MakeNewCrate());   
    }

    //Waits 30s, reactivates the ammo crate and allows text to be displayed again
    IEnumerator MakeNewCrate(){
        yield return new WaitForSeconds(30f);
        ActualCrate.SetActive(true);
        flag = true; 
    }

    //This chain of Coroutines delays sounds so they match the animation
    IEnumerator OpenSound(){
        Box.clip = Opening;
        Box.Play();
        yield return new WaitForSeconds(1.9f);
        StartCoroutine(CloseSound());
        
    }
    IEnumerator CloseSound(){
        Box.clip = Closing;
        Box.Play();
        yield return null;
    }
}
