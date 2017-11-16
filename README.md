# SmartRails README

1.  To use the program, run the jar file.  This will prompt the opening screen

OPENING SCENE:
 - Provides the ability to choose the number of trains to run.  At this time, only 1 is available.
 - Click "Set the Schedule" to decide the path of the selected train(s).
 
SCHEDULING SCENE:
 - Provides the ability to set the schedule for however many trains to be run.
 - First, select either "LEFT" or "RIGHT", this will set whether the train begins on the 
    left side or right side of the screen.
 - Next, select the destinations.  The first destination is where the train is first stationed.
    The second, third, and fourth will set where the train travels to.  With the provided .txt file 
    that creates the track, any combination of stations is possible.  
 - After destinations are chosen, click "Schedule".  Once all schedules are set, the final button 
    "Release the Trains." will become clickable.  Clicking this button will change to train scene.
    
TRAIN SCENE:
 - Main scene to see train move between stations.  Close after destinations have been reached.
 
Entry point is in SmartRailsWindow class.  

2. Testing and debugging: While running, the console will display threads created and messages passed
to help with debugging.

3.  Project distribution:
Alex: 
 - Created track images
 - Finished messaging system
 - Created correspondences
 - Thread creation
      
Cat:  
 - Opening scene
 - Station images
 - Schedule scene
 - Worked on messaging system
      
Third Party Content:
 - Thomas the Tank theme song plays throughout
      
4.  Known Bugs and Issues:
    Sometimes threads spawn errors during train travel.  If this happens please run again.  We haven't found
    what causes this.  
    
