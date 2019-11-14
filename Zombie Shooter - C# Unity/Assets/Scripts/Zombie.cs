using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class Zombie : MonoBehaviour
{
    //Player object for inspector along with animator
    public GameObject PlayerGameObject;
    private Player PlayerObject;
    private Animator Animator;

    //Attacking flag for zombie animation
    private bool Attacking = false;

    //Zombie starting health
    private float Health = 100.0f;

    //I use this to know when a zombie is dead. They were attacking while on the ground before.
    private bool DeadFlag = false;

    //Used for the player class, limits how much damage a zombie can do to a player
    //to once per 1.2 seconds
    private float Timer;

    //Grabs animator
    void Awake(){
        Animator = GetComponent<Animator>();
    }


    //Damages zombie, starts kill animation if 0 health
    public void DamageZombie(float damage){
        Health -= damage;
        if(Health <= 0.0f){
            Animator.SetTrigger("Death");
            StartCoroutine(ZombieDeath());
        }
    }

    //Stop zombie from attacking, destroys it after 25 seconds.
    IEnumerator ZombieDeath(){
        DeadFlag = true;
        yield return new WaitForSeconds(25.0f);
        Destroy(gameObject);
    }

    //Moves zombie towards player, attacks if in range and not dead
    void Update()
    {
        Timer += Time.deltaTime;
        if(gameObject != null && !DeadFlag){
            RaycastHit objectHit;
            if(Physics.Raycast(transform.position, transform.TransformDirection(Vector3.forward), out objectHit)){
                var distance = objectHit.distance;
                if(objectHit.transform.tag == "Player" && distance < 1.0 ){
                    StartCoroutine(Attack());
                }
            }
            
            var target = PlayerGameObject.transform.position;
            target.y = transform.position.y + (transform.lossyScale.y / 2);

            transform.LookAt(target);
        }
    }

    //Attack coroutine, which starts animation and tries to damage player
    //if 1.2 seconds has passed between last attack
    IEnumerator Attack(){
        Attacking = true;
        Animator.SetTrigger("Attack");
        PlayerObject.DecreaseHealth(10.0f, Timer);
        yield return new WaitForSeconds(1.2f);
        Attacking = false;
    }

    //I needed to put this here instead as the game object was not 
    //initialized yet
    private void Start(){
        PlayerObject = PlayerGameObject.GetComponent<Player>();
        Timer = 0;
    }

    //Takes zombie from ZombieFactory
    public void Initialize(GameObject player){
       PlayerGameObject = player;
    }
}
