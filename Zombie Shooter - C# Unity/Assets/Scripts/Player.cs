using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEditor;
using UnityEngine.SceneManagement;

public class Player : MonoBehaviour
{
    //Manages distance, has health and damage
    public static float TargetDistance;
    public float Distance;
    private float Health = 100.0f;
    public AudioSource hurt;
    
    //This is used to limit how often zombies can do damage
    private float HurtTime;

    void Start(){
        HurtTime = 0.0f;
    }

    //Keeps track of distance
    void Update()
    {
        RaycastHit objectHit;
        if (Physics.Raycast(transform.position, transform.TransformDirection(Vector3.forward), out objectHit)){
            Distance = objectHit.distance;
            TargetDistance = Distance;
        }
        
    }

    //If no damage has been done within last 1.2 seconds, proceed to damage and play sound.
    //If you die, show game over
    public void DecreaseHealth(float healthLoss, float timeDamaged){
        if(timeDamaged - HurtTime > 1.2){
            Health -= healthLoss;
            hurt.Play();
            if(Health <= 0){
                SceneManager.LoadScene(1);
            }
            HurtTime = timeDamaged;
        }
    }
}
