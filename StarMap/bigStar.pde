class bigStar{
  int starType,starMagnitude,starMagnitudeLevel,linkStarMaxNum=0,childStarMinLong=10,childStarMaxLong=80,parentStarLong,parentStarLongNow=5,starLevelUp;//starType=aloneStar(0) or linkStar(1); starMagnitude=starLevel;
  float starWidth,starHeight,starSize,starMaxSize,sizeChange,twinkleSpeed,starLineWidth,starLineHeight,parentStarWidth,parentStarHeight;
  boolean starTypeBool=false;
  color starColor;
  
  bigStar(int type,float myWidth,float myHeight,float pWidth,float pHeight){
    starType=type;
    parentStarWidth=pWidth;
    parentStarHeight=pHeight;
    starLineWidth=pWidth;
    starLineHeight=pHeight;
    starMagnitude = ceil(random(25));
    starSize=starMaxSize;
    twinkleSpeed=random(0.1,0.5);
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
  
  void twinkleStar(){
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