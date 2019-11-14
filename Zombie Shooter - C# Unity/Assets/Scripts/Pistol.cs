using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Pistol : MonoBehaviour
{
    //Used for our reload animations
    private Animator Animator;

    //Stuff used for animations
    private bool Firing = false;
    public GameObject MuzzleFlash;
    public GameObject Barrel;
    public AudioSource PistolShot;
    public GameObject Pistol1;

    //Damage done to zombies
    private readonly float Damage = 25.0f;

    //Initial total bullets and bullets in the magazine
    public int Bullets;
    public int Mag;

    //Text for the player that shows total bullets in player gun
    public GameObject TotalBulletsObject;
    public Text TotalBulletsText;
    public GameObject PlayerGun;

    //Selfaudio is our audiosource, fed clips for reloading and dryfires when needed
    public AudioSource SelfAudio;
    public AudioClip Reloading;
    public AudioClip DryFire;

    //Weapon panel that contains HUD
    public GameObject WeaponPanel;

    /*This counter is used to help me know which of the 
    images that represent bullets in the magazine to show or hide
    */
    private int bulletCounter = 1;

    /* I am very sorry for hard coding this. It is a mess, but it
    is the only way I found that could reliably manage the graphical
    portion of the UI. Each image is linked in the inspector and enabled 
    or disabled based on our bulletcounter
     */
    public GameObject Bullet1;
    public GameObject Bullet2;
    public GameObject Bullet3;
    public GameObject Bullet4;
    public GameObject Bullet5;
    public GameObject Bullet6;
    public GameObject Bullet7;

    //Allows our gun states to be mutually exclusive
    private bool ReloadFlag = false;
    

    //Allows for animations and begins the text that shows total bullets
    void Start()
    {
        Animator = Pistol1.GetComponent<Animator>();
        Bullets = 14;
        Mag = 7;
        TotalBulletsText = TotalBulletsObject.GetComponent<Text>();
        TotalBulletsText.text = Bullets.ToString();
    }

    //Checks for Firing, Dry firing, and reloading.
    void Update()
    {
        //First case: Firing while you have bullets and not reloading
         if(Input.GetButtonDown("Fire1") && Mag > 0 && !ReloadFlag){
            //Animates fire, removes one from the magazine
            Animator.SetTrigger("Fire");
            Mag -= 1;

            //This is the hard coding that I am not proud of. Every other idea I tried
            //had unreliable results. Disables images that represents bullets in order while
            //keeping track of which one we are on globally
            if(bulletCounter == 1){
                Bullet1.SetActive(false);
                bulletCounter += 1;
            }
            else if(bulletCounter == 2){
                Bullet2.SetActive(false);
                bulletCounter += 1;
            }
            else if(bulletCounter == 3){
                Bullet3.SetActive(false);
                bulletCounter += 1;
            }
            else if(bulletCounter == 4){
                Bullet4.SetActive(false);
                bulletCounter += 1;
            }
            else if(bulletCounter == 5){
                Bullet5.SetActive(false);
                bulletCounter += 1;
            }
            else if(bulletCounter == 6){
                Bullet6.SetActive(false);
                bulletCounter += 1;
            }
            else if(bulletCounter == 7){
                Bullet7.SetActive(false);
                bulletCounter += 1;
            }
            //Coroutine handles everything else about firing the gun
            StartCoroutine(FirePistol());
        }
        //Dry firing, only while not reloading
        else if(Input.GetButtonDown("Fire1") && Mag == 0 && !ReloadFlag){
            SelfAudio.clip = DryFire;
            SelfAudio.Play();
        }
        //Actual reloading. Enables flag so no other cases are possible
        //Only reloads if theres room in the magazine.
        else if(Input.GetButtonDown("Reload") && Bullets > 0 && !ReloadFlag){
            if(Mag <= 6){
                ReloadFlag = true;

                //Custom reload animation, reload sound to match
                Animator.SetTrigger("Reload");
                SelfAudio.clip = Reloading;
                SelfAudio.Play();
                
                
                //Handles re-enabling the images and updating total bullets
                //once sound and animation are done
                StartCoroutine(Reload());
            }    
        }  
    }



    IEnumerator Reload(){
        //Waits for animation to finish
        yield return new WaitForSeconds(2.9f);

        //Loads gun up to 7 bullets, taking them from the total
        //Huge if statement is the opposite of the one explained before
        while(Bullets > 0 && Mag < 7){
                    Bullets -= 1;
                    Mag += 1;
                    TotalBulletsText.text = Bullets.ToString(); 

                    if(bulletCounter == 2){
                        Bullet1.SetActive(true);
                        bulletCounter -= 1;
                    }
                    else if(bulletCounter == 3){
                        Bullet2.SetActive(true);
                        bulletCounter -= 1;
                    }
                    else if(bulletCounter == 4){
                        Bullet3.SetActive(true);
                        bulletCounter -= 1;
                    }
                    else if(bulletCounter == 5){
                        Bullet4.SetActive(true);
                        bulletCounter -= 1;
                    }
                    else if(bulletCounter == 6){
                        Bullet5.SetActive(true);
                        bulletCounter -= 1;
                    }
                    else if(bulletCounter == 7){
                        Bullet6.SetActive(true);
                        bulletCounter -= 1;
                    }
                    else if(bulletCounter == 8){
                        Bullet7.SetActive(true);
                        bulletCounter -= 1;
                    }
                }
                //Allows our other cases to happen again
                ReloadFlag = false;
    }

    //Tracks bullets to see if we hit a zombie, with animation and sound
    IEnumerator FirePistol(){
        RaycastHit objectShot;
        if(Physics.Raycast(transform.position, transform.TransformDirection(Vector3.forward), out
        objectShot)){
            objectShot.transform.SendMessage("DamageZombie", Damage, 
            SendMessageOptions.DontRequireReceiver);
        }
        Animator.SetTrigger("Fire");
        var flash = Instantiate(MuzzleFlash, Barrel.transform.position, Barrel.transform.rotation);
        PistolShot.Play();
        yield return new WaitForSeconds(1.0f);
        Destroy(flash);
        Firing = false;
    }
}
