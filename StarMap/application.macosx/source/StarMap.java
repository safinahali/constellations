import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class StarMap extends PApplet {

int width=800,height=600,miniStarNum=30,bigStarNum=3000;
int backColor=0xff000010;
miniStar[] miniStarArray = new miniStar[miniStarNum];
bigStar[] bigStarArray = new bigStar[bigStarNum];
int bigStarArrayNum=0;
float strokeWeightNum=0.5f;

public void setup(){
  
  frameRate(20);
  strokeWeight(strokeWeightNum);
  
  rectMode(CENTER);
  ellipseMode(CENTER);
  background(backColor);
  //make star
  for(int num=0;num<miniStarNum;num++){
    miniStarArray[num] = new miniStar();
  }
  for(int num=0;num<bigStarArrayNum;num++){
    bigStarArray[num] = new bigStar(0,random(width),random(height),0,0);
  }
}

public void draw(){
  fill(backColor,50);
  noStroke();
  rect(width/2,height/2,width,height);
  //twinkle miniStar
  for(int num=0;num<miniStarNum;num++){
    miniStarArray[num].twinkleStar();
  }
  //twinkle bigSta
  for(int num=0;num<bigStarArrayNum;num++){
    bigStarArray[num].twinkleStar();
  }
  //strokeTwinkle();
}

public void mouseReleased(){
  bigStarArray[bigStarArrayNum] = new bigStar(0,mouseX,mouseY,0,0);
  bigStarArrayNum++;
}
class bigStar{
  int starType,starMagnitude,starMagnitudeLevel,linkStarMaxNum=0,childStarMinLong=10,childStarMaxLong=80,parentStarLong,parentStarLongNow=5,starLevelUp;//starType=aloneStar(0) or linkStar(1); starMagnitude=starLevel;
  float starWidth,starHeight,starSize,starMaxSize,sizeChange,twinkleSpeed,starLineWidth,starLineHeight,parentStarWidth,parentStarHeight;
  boolean starTypeBool=false;
  int starColor;
  
  bigStar(int type,float myWidth,float myHeight,float pWidth,float pHeight){
    starType=type;
    parentStarWidth=pWidth;
    parentStarHeight=pHeight;
    starLineWidth=pWidth;
    starLineHeight=pHeight;
    starMagnitude = ceil(random(25));
    starSize=starMaxSize;
    twinkleSpeed=random(0.1f,0.5f);
    if(type==0){
      starWidth=myWidth;
      starHeight=myHeight;
      starMaxSize=random(6,7);
      linkStarMaxNum=2;
      starColor=color(255,255,120);
      starMagnitudeLevel=2;
    }else{
      if(round(random(1))==1){
        starWidth=random(parentStarWidth+childStarMinLong,parentStarWidth+childStarMaxLong);
      }else{
        starWidth=random(parentStarWidth-childStarMaxLong,parentStarWidth-childStarMinLong);
      }
      if(round(random(1))==1){
        starHeight=random(parentStarHeight+childStarMinLong,parentStarHeight+childStarMaxLong);
      }else{
        starHeight=random(parentStarHeight-childStarMaxLong,parentStarHeight-childStarMinLong);
      }
      if(starMagnitude==1){
        starMaxSize=random(8,9);
        linkStarMaxNum=3;
        starColor=color(255,120,120);
        starMagnitudeLevel=1;
      }else if(starMagnitude==2 || starMagnitude==3){
        starMaxSize=random(6,7);
        linkStarMaxNum=2;
        starColor=color(255,255,120);
        starMagnitudeLevel=2;
      }else if(starMagnitude==4 || starMagnitude==5 || starMagnitude==6 || starMagnitude==7 || starMagnitude==8){
        starMaxSize=random(5,6);
        linkStarMaxNum=1;
        starColor=color(150,255,150);
        starMagnitudeLevel=3;
      }else{
        starMaxSize=random(3,4);
        starColor=color(200,200,255);
        starMagnitudeLevel=4;
      }
      parentStarLong=round(dist(starWidth,starHeight,parentStarWidth,parentStarHeight));
    }
  }
  
  public void twinkleStar(){
    if(parentStarLongNow >= parentStarLong){
      starTypeBool=true;
      parentStarLongNow=0;
    }
    if(starType==0 || starTypeBool){
      fill(starColor);
      noStroke();
      ellipse(starWidth, starHeight, starSize, starSize);
      if(starSize >= starMaxSize){
        sizeChange = twinkleSpeed*-1;
      }else if(starSize <= 2){
        sizeChange = twinkleSpeed;
      }
      starSize += sizeChange;
      
      if(ceil(random(100))==1 && bigStarArrayNum < bigStarNum && linkStarMaxNum > 0){
        bigStarArray[bigStarArrayNum] = new bigStar(1,0,0,starWidth,starHeight);
        bigStarArrayNum++;
        linkStarMaxNum--;
      }
      if(starType==1){
        //stroke(150,180,255);
        stroke(255,130);
        strokeWeight(strokeWeightNum);
        line(parentStarWidth,parentStarHeight,starWidth,starHeight);
        stroke(255,100);
        float starLineWidth=parentStarWidth+((starWidth-parentStarWidth)*parentStarLongNow/parentStarLong);
        float starLineHeight=parentStarHeight+((starHeight-parentStarHeight)*parentStarLongNow/parentStarLong);
        line(parentStarWidth,parentStarHeight,starLineWidth,starLineHeight);
        noStroke();
        fill(starColor,200);
        ellipse(starLineWidth,starLineHeight,2,2);
        parentStarLongNow+=2;
      }
      //sinka
      if((starMagnitudeLevel!=1 && ceil(random(1000))==1) || starLevelUp > 0){
        if(starLevelUp < 0){
          starLevelUp=50;
        }else{
          starLevelUp -= 5;
        }
        noFill();
        stroke(255);
        strokeWeight(1);
        ellipse(starWidth,starHeight,starLevelUp,starLevelUp);
        if(starLevelUp==5){
          if(starMagnitudeLevel==4){
            starMaxSize=random(5,6);
            starColor=color(150,255,150);
          }else if(starMagnitudeLevel==3){
            starMaxSize=random(6,7);
            starColor=color(255,255,120);
          }else if(starMagnitudeLevel==2){
            starMaxSize=random(8,9);
            starColor=color(255,120,120);
          }
          linkStarMaxNum++;
          starMagnitudeLevel--;
        }
      }
    }else{
      stroke(255,100);
      strokeWeight(strokeWeightNum);
      line(parentStarWidth,parentStarHeight,starLineWidth,starLineHeight);
      noStroke();
      ellipse(starLineWidth,starLineHeight,2,2);
      parentStarLongNow+=3;
      starLineWidth=parentStarWidth+((starWidth-parentStarWidth)*parentStarLongNow/parentStarLong);
      starLineHeight=parentStarHeight+((starHeight-parentStarHeight)*parentStarLongNow/parentStarLong);
    }
  }
}
class miniStar{
  float starWidth, starHeight, starSize, starMaxSize, sizeChange, twinkleSpeed;
  
  miniStar(){
    starWidth=random(width);
    starHeight=random(height);
    starMaxSize=random(0.5f,2);
    starSize=starMaxSize;
    twinkleSpeed=random(0.03f,0.1f);
  }
  public void twinkleStar(){
    fill(255);
    noStroke();
    ellipse(starWidth, starHeight, starSize, starSize);
    if(starSize >= starMaxSize){
      sizeChange = twinkleSpeed*-1;
    }else if(starSize <= 0){
      sizeChange = twinkleSpeed;
    }
    starSize += sizeChange;
  }
}
public void strokeTwinkle(){
  if(strokeWeightNum > 0.08f){
    strokeWeightNum -= 0.02f;
    strokeWeight(strokeWeightNum);
  }else{
    strokeWeightNum -= 0.02f;
    strokeWeight(0.08f);
    if(strokeWeightNum <= -0.8f){
      strokeWeightNum = 0.5f;
    }
  }
}
  public void settings() {  size(800, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "StarMap" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
