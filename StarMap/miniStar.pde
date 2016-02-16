class miniStar{
  float starWidth, starHeight, starSize, starMaxSize, sizeChange, twinkleSpeed;
  
  miniStar(){
    starWidth=random(width);
    starHeight=random(height);
    starMaxSize=random(0.5,2);
    starSize=starMaxSize;
    twinkleSpeed=random(0.03,0.1);
  }
  void twinkleStar(){
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