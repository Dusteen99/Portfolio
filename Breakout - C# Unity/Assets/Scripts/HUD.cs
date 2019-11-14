using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System;

public class HUD : MonoBehaviour
{
    private static HUD _instance;
    private int score = 0;
    public Text ScoreText;
    public Text LevelText;
    public static HUD Instance{
        get{
            return _instance;
        }
    }

    void Awake(){
        if (_instance == null){
            _instance = this;
            DontDestroyOnLoad(this.gameObject);
        }
        else{
            Destroy(this);
        }
    }

    public void Render(int Score, int Level, int extraLifeCount){
        score = Score;
        ScoreText.text = string.Format("Score: {0}", score);
        LevelText.text = string.Format("Level: {0}", Level + 1);

        Transform lives = transform.Find("Lives");
        int i;
        for(i = 0; i < extraLifeCount; i++){
            lives.GetChild(i).gameObject.SetActive(true);
        }
        for(int j = 1; j < lives.childCount; j++){
            lives.GetChild(i).gameObject.SetActive(false);
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
