
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alaa.Ezzeldin
 */

public class MemoryLocation {
    
    private int ID;
    private int size;
    private String color;
    private int StartTime;
    private int EndTime;
    private String type; 
    
    public int getID(){
    return ID;
    }
    public int getsize(){
    return size;
    }
    public int getStartTime(){
    return StartTime;
    }
    public String getColor(){
    return color;
    }
   
  
    public int getEndTime(){
    return this.StartTime+this.size;
    }
    
    public void setID(int id){
    this.ID=id;
    }
    public void setSize(int size){
    this.size=size;
    }
    public void setStartTime(int n){
    this.StartTime= n;
    }
    public void setEndTime(){
    this.EndTime= this.StartTime+this.size;
    }

        
    public void setColor(String color){
    this.color =color;
    }

    public String getType(){
    return type;
    }
    
    public void setType(String type) {
        this.type=type;
    }

    public String getDescripton(){
        String s;
        if(this.type== "Hole"){
            s="size="+this.size +"             " + "Starting Address "+this.StartTime;
        }
        else
            s= "P"+this.ID+"       "+ this.size;
                    
        return s;
    }
    
    
    public ArrayList<MemoryLocation> update (ArrayList <MemoryLocation>holes, int MemorySize)
    {
        ArrayList <MemoryLocation> Updated = new ArrayList<>();
        MemoryLocation temp1 = new MemoryLocation();
        MemoryLocation temp = new MemoryLocation();
        MemoryLocation templastused = new MemoryLocation();
        
        
        if(holes.get(0).getStartTime()>0){
            temp1.setSize(holes.get(0).getStartTime());
            temp1.setStartTime(0);
            temp1.setType("Used");
            Updated.add(temp1);
        }
        
        for(int i=0; i<holes.size();i++){holes.get(i).setEndTime();}
        
        if( holes.size()>1){
             
        for(int i=0; i<holes.size()-1;i++){
            if(holes.get(i).getEndTime()==holes.get(i+1).getStartTime()){
                if(holes.get(i).getType()== "Hole" && holes.get(i+1).getType()== "Hole" ){
                    temp.setSize(holes.get(i+1).getEndTime()+holes.get(i).getStartTime());
                    temp.setStartTime(holes.get(i).getStartTime());
                    temp.setType("Hole");
                    temp.setEndTime();
                    holes.remove(i);
                    holes.set(i, temp);
                }
            }
        }
        if(holes.get(holes.size()-2).getEndTime()==holes.get(holes.size()-1).getStartTime()){
            if("Hole".equals(holes.get(holes.size()-2).getType()) && "Hole".equals(holes.get(holes.size()-1).getType())){
                temp.setSize(holes.get(holes.size()-1).getEndTime()+holes.get(holes.size()-1).getStartTime());
                temp.setStartTime(holes.get(holes.size()-2).getStartTime());
                temp.setType("Hole");
                holes.remove(holes.size()-1);
                holes.set(holes.size()-1, temp);
            }
        }
        
            for(int i=0; i<holes.size()-1;i++){

                if (holes.get(i).getEndTime()!=holes.get(i+1).getStartTime()){
                  MemoryLocation temp2 = new MemoryLocation();
                  Updated.add(holes.get(i));

                  temp2.setSize(holes.get(i+1).getStartTime()-holes.get(i).getEndTime());
                  temp2.setStartTime(holes.get(i).getEndTime());
                  temp2.setType("Used");
                  temp2.setEndTime();
                  Updated.add(temp2);
                }
            }
        
        }

        if(holes.get(holes.size()-1).getEndTime() < MemorySize){
          Updated.add(holes.get(holes.size()-1));
          templastused.setSize(MemorySize-holes.get(holes.size()-1).getEndTime());
          templastused.setStartTime(holes.get(holes.size()-1).getEndTime());
          templastused.setType("Used");
          Updated.add(templastused);
        }
        else{Updated.add(holes.get(holes.size()-1));}
        
        for(int i=0; i< Updated.size();i++){
            Updated.get(i).setEndTime();
        }

        return Updated;
    }
      
    public ArrayList <MemoryLocation> BestFit (MemoryLocation Process,ArrayList<MemoryLocation> Updated )
    {
        int Min=0;
        for(int i=0; i<Updated.size();i++){
            if(Updated.get(i).getType()=="Hole"&& Updated.get(i).getsize()>=Process.getsize()){
                Min =i;
                break;
            }
         }
        
        for(int i=0; i<Updated.size();i++){
            if("Hole".equals(Updated.get(i).getType())){
                if(Updated.get(i).getsize()== Process.getsize()){
                    Updated.set(i, Process);
                    Min=i;
                    break;
                }
                if(Updated.get(i).getsize()> Process.getsize()){
                    if(Updated.get(i).getsize() < Updated.get(Min).getsize()){
                        Min=i;
                    }
                }
            }
        }
        
       if(Updated.get(Min).getsize() > Process.getsize()){
            Updated.get(Min).setSize(Updated.get(Min).getsize()-Process.getsize());
            Updated.add(Min, Process);
       }  
        
       for(int i=0; i< Updated.size();i++){
           Updated.get(i).setEndTime();
       }
       
       return Updated;
    }
    
        public ArrayList <MemoryLocation> FirstFit (MemoryLocation Process,ArrayList<MemoryLocation> Updated )
    {
        for(int i=0; i<Updated.size();i++){
            if("Hole".equals(Updated.get(i).getType())){
                if(Updated.get(i).getsize()>Process.getsize()){
                    Updated.get(i).setSize(Updated.get(i).getsize()-Process.getsize());
                    Updated.add(i, Process);
                    break;
                }
                if(Updated.get(i).getsize()==Process.getsize()){
                    Updated.set(i, Process);
                    break;
                }
                
            }
        }
        
        for(int i=0; i< Updated.size();i++){
            Updated.get(i).setEndTime();
        }
        
        return Updated;
    }
        
    public ArrayList<MemoryLocation> Deallocate (ArrayList <MemoryLocation>holes, MemoryLocation DeLoc)
    {
        ArrayList <MemoryLocation> Updated = new ArrayList<>();
        MemoryLocation temp= new MemoryLocation();
        
        for(int i=0; i<holes.size();i++){
            if(holes.get(i)==DeLoc){
                 holes.get(i).setType("Hole");
            }
        }
       

        for(int i=0; i< holes.size();i++){
            Updated.add(holes.get(i));
        }

        for (int i=0; i<Updated.size()-1;i++){
            if(Updated.get(i).getType()=="Hole" && Updated.get(i+1).getType()=="Hole"){
                Updated.get(i+1).setSize(Updated.get(i+1).getsize()+Updated.get(i).getsize());
                Updated.get(i+1).setStartTime(Updated.get(i).getsize());
                Updated.get(i+1).setEndTime();
                
                Updated.remove(i);
            }
        }
        for(int i =1; i <Updated.size();i++){
           if(Updated.get(i).getType()=="Hole" && Updated.get(i-1).getType()=="Hole"){
                Updated.get(i).setSize(Updated.get(i-1).getsize()+Updated.get(i).getsize());
                Updated.get(i).setStartTime(Updated.get(i-1).getsize());
                Updated.get(i).setEndTime();
                
                Updated.remove(i-1);
           }
        }
           
        return Updated;
    }
}

