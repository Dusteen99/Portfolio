using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameController : MonoBehaviour
{
    public GameObject AllBricks;
    public static int BrickCount;
    private static int LevelIndex = 0;
    private static string [] Levels = {"Level1", "Level2", "Level3", "Level4"};
    private static GameController _instance;
    private int Score = 0;
    private HUD HUD;
    private int ExtraLifeCount = 2;
    private int ExtraLifeCountMax = 5;


    public static GameController Instance{
        get{
            return _instance;
        }
    }

    void Awake(){
        HUD = HUD.Instance;
        if (_instance == null){
            _instance = this;
            DontDestroyOnLoad(this.gameObject);
        }
        else{
            Destroy(this);
        }
    }

    public void LifeLost(){
        ExtraLifeCount -= 1;
        if(ExtraLifeCount < 0){
            Application.Quit();
        }
    }

    public void BrickCollision(GameObject brick){
        var scoreChange = brick.GetComponent<Brick>().OnHit();
        if(scoreChange > 0){
            BrickCount -= 1;
            Score += scoreChange;
        }
    }
    
    
    // Start is called before the first frame update
    void OnEnable(){
        SceneManager.sceneLoaded += OnLevelFinishedLoading;
    }

    //Start is called before the first frame update
    void OnLevelFinishedLoading(Scene scene, LoadSceneMode mode){
        AllBricks = GameObject.FindGameObjectWithTag("AllBricks");

        BrickCount = 0;
        foreach (Transform brickRow in AllBricks.transform){
            foreach(Transform brick in brickRow.transform){
                brick.GetComponent<Brick>().Initialize(brick.gameObject, LevelIndex + 1);
                BrickCount += 1;
            }
        }
    }

    // Update is called once per frame
    void Update()
    {
        if(BrickCount == 0){
            LevelIndex += 1;
            SceneManager.LoadScene(Levels[LevelIndex % Levels.Length]);
            if(ExtraLifeCount < ExtraLifeCountMax){
                ExtraLifeCount += 1;
            }
        }
        HUD.Render(Score, LevelIndex, ExtraLifeCount);
    }
}
