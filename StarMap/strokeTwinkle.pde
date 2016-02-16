void strokeTwinkle(){
  if(strokeWeightNum > 0.08){
    strokeWeightNum -= 0.02;
    strokeWeight(strokeWeightNum);
  }else{
    strokeWeightNum -= 0.02;
    strokeWeight(0.08);
    if(strokeWeightNum <= -0.8){
      strokeWeightNum = 0.5;
    }
  }
}