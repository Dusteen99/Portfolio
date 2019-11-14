using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PickUpBox : MonoBehaviour
{
   public GameObject Box;
   public GameObject HoldBox;
   private BoxCollider BoxCollider;
   private Rigidbody Rigidbody;

    //Grabs out collider and rigidbody
   void Awake(){
       BoxCollider = GetComponent<BoxCollider>();
       Rigidbody = GetComponent<Rigidbody>();
   } 

   //Just disables gravity if the player is close enough and clicks
   public void OnMouseDown(){
       BoxCollider.enabled = false;
       Rigidbody.useGravity = false;
       transform.position = HoldBox.transform.position;
       transform.parent = HoldBox.transform;
   }

    //Re-enables gravity for the box
   public void OnMouseUp(){
       transform.parent = null;
       BoxCollider.enabled = true;
       Rigidbody.useGravity = true;
   }
}
