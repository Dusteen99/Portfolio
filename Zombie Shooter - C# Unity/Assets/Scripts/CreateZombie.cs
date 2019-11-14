using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CreateZombie : MonoBehaviour
{
    //Our singleton used to spawn zombie
    private ZombieFactory Factory;

    //Zombie's target
    public GameObject Player;

    //Zombies spawn every 20 seconds, Timer tracks this.
    private static float SpawnTime = 20.0f;
    public float Timer;

    //Creates our zombie factory and starts by spawning one zombie instantly
    void Start()
    {
        Factory = ZombieFactory.Instance;
        Factory.CreateZombieRandom();
        Timer = 0.0f;
    }

    //Spawns zombies every 20 seconds
    void Update()
    {
        Timer += Time.deltaTime;
        if (Timer > SpawnTime){
            Factory.CreateZombieRandom();
            Timer = 0.0f;
        }
    }
}
