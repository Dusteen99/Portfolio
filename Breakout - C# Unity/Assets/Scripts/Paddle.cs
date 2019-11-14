using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Paddle : MonoBehaviour
{
    private static float PaddleSpeed = 150.0f;
    private Rigidbody2D Rigidbody;

    void Awake(){
        Rigidbody = GetComponent<Rigidbody2D>();
    }
    void FixedUpdate(){
        //Get Horizontal Input
        // Unity's built-in GetAxisRaw function takes an axis as an input, and for
        // horizontal changes, returns -1 for left, 1 for right, and 0 for no movement

        float direction = Input.GetAxisRaw("Horizontal");

        // Using the direction, set the Paddle velocity
        Rigidbody.velocity = Vector2.right * direction * PaddleSpeed;
    }

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
