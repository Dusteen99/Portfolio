using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ZombieFactory : MonoBehaviour{
    private static ZombieFactory _instance;
    public GameObject Zombie;
    public GameObject Player;
    public GameObject Terrain;

    //Awake and ZombieFactory create a singleton
    void Awake(){
        _instance = this;
    }

    public static ZombieFactory Instance{
        get{
            return _instance;
        }
    }

    //Statically define a starting position for play testing
    public Zombie CreateZombie(){
        return CreateZombie(new Vector3(0, 10, 10));
    }

    //Gives a zombie in a random location
    public Zombie CreateZombieRandom(){
        //Dynamically calculate the bounds for the ground object
        var terrainCollider = Terrain.GetComponent<Collider>();
        var scaleX = terrainCollider.bounds.size.x;
        var scaleZ = terrainCollider.bounds.size.z;

        //Initialize a system random number generator
        var random = new System.Random();

        /* A set starting Y-position such that the zombie does not fall through
        the tarrain. We can and should determine the height of the terrain at the 
        random X,Z location from the terrain and base the Y value from that
         */
        var startPositionY = 10.0f;
        //Changed these so spawning worked for me inside my bounds
        var startPositionX = (float) (random.NextDouble()) * scaleX;
        var startPositionZ = (float) (random.NextDouble()) * scaleZ;
        var startPosition = new Vector3(startPositionX, startPositionY, startPositionZ);
        return CreateZombie(startPosition);
    }

    //Helper for creating a random zombie
    private Zombie CreateZombie(Vector3 startPosition){
        //Creates the zombie at the random location from CreatZombieRandom
        var zombieGameObject = Object.Instantiate(_instance.Zombie, startPosition, 
        Quaternion.identity);

        //Gives it the zombie script and targets player
        var zombieObject = zombieGameObject.GetComponent<Zombie>();
        zombieObject.Initialize(Player);

        return zombieObject;
    }
}