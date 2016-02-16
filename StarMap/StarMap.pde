int width=800,height=600,miniStarNum=30,bigStarNum=3000;
color backColor=#000010;
miniStar[] miniStarArray = new miniStar[miniStarNum];
bigStar[] bigStarArray = new bigStar[bigStarNum];
int bigStarArrayNum=0;
float strokeWeightNum=0.5;

void setup(){
  size(800, 600);
  frameRate(20);
  strokeWeight(strokeWeightNum);
  smooth();
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

void draw(){
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

void mouseReleased(){
  bigStarArray[bigStarArrayNum] = new bigStar(0,mouseX,mouseY,0,0);
  bigStarArrayNum++;
}