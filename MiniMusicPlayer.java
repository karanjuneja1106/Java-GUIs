import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
public class MiniMusicPlayer{
static JFrame f= new JFrame("Music Player");
static MyDrawPanel m;
public static void main(String args[]){
MiniMusicPlayer mini=new MiniMusicPlayer();
mini.go();
}
public void setUpGui(){
m=new MyDrawPanel();
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.setContentPane(m);
f.setBounds(30,30,300,300);
f.setVisible(true);
}
public void go(){
setUpGui();
try{
Sequencer sequencer=MidiSystem.getSequencer();
sequencer.open();
sequencer.addControllerEventListener(m,new int[] {127});
Sequence seq=new Sequence(Sequence.PPQ,4);
Track track=seq.createTrack();
int r=0;
for(int i=0;i<60;i+=4){
r=(int)((Math.random()*50)+1);
track.add(makeEvent(144,1,r,111,i));
track.add(makeEvent(176,1,127,0,i));
track.add(makeEvent(128,1,r,111,i+2));
sequencer.setSequence(seq);
sequencer.start();
sequencer.setTempoInBPM(120);
}
}
catch(Exception ex){ex.printStackTrace();}
}
public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
MidiEvent event=null;
try{
ShortMessage a=new ShortMessage();
a.setMessage(comd,chan,one,two);
event=new MidiEvent(a,tick);
}
catch(Exception ex){}
return event;
}
class MyDrawPanel extends JPanel implements ControllerEventListener{
boolean msg=false;
public void controlChange(ShortMessage event){
msg=true;
repaint();
}
public void paintComponent(Graphics g){
if(msg){
int r=(int)(Math.random()*255);
int gr=(int)(Math.random()*255);
int b=(int)(Math.random()*255);
g.setColor(new Color(r,gr,b));
int w=(int)((Math.random()*120)+10);
int h=(int)((Math.random()*120)+10);
int x=(int)((Math.random()*40)+10);
int y=(int)((Math.random()*40)+10);
g.fillRect(x,y,h,w);
msg=false;
}
}
}
}