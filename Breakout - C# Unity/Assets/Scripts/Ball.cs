using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Ball : MonoBehaviour
{
    private static float BallSpeed = 300.0f;
    private static Vector2 BallStartPosition;
    public GameObject Paddle;

    //Awake is called before the game starts after objects are initialzed
    void Awake(){
        BallStartPosition = transform.position;
    }

    // Start is called before the first frame update
    void Start()
    {
        transform.position = BallStartPosition;
        GetComponent<Rigidbody2D>().velocity = Vector2.up * BallSpeed;
    }

    // Update is called once per frame
    void Update()
    {
        if(transform.position.y <  Paddle.transform.position.y){
            Start();
            GameController.Instance.LifeLost();
        }
    }

    //Calculates horizonal angles and speed
    private float CalculateHorizontalUpdate(Vector2 ballPosition, Vector2 paddlePosition, float paddleWidth){
        return (ballPosition.x - paddlePosition.x) / paddleWidth;
    }

    void OnCollisionEnter2D(Collision2D hit){
        var gameObject = hit.collider.gameObject;
        var label = gameObject.tag;

        if(label == "Brick"){        
            GameController.Instance.BrickCollision(gameObject);   
        }
        

        if(label == "Paddle"){

            //Get horizontal position update from helper
            float horizontalUpdate = CalculateHorizontalUpdate(transform.position,
                                            hit.transform.position, hit.collider.bounds.size.x);
                
            //Get horizonal position update from helper
            float verticalUpdate = (transform.position.y - hit.transform.position.y) / hit.collider.bounds.size.y;

            //Calculate new direction of the Ball and normalize the vector
            Vector2 direction = new Vector2(horizontalUpdate, verticalUpdate).normalized;

            //Update the velocity using the new direction
            GetComponent<Rigidbody2D>().velocity = direction * BallSpeed;
        }

    }
}

