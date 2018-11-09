I made this small program to elimate a repetitive task I had at work:
Our automated cutter/plotter saves its instructions as editable text files. Now, I wanted to add a pause between the plotting and cutting portions of the process. I was able to do this by adding an instruction to the file but there was no way to natively tell the machine to do this automatically. So I would manually have to open each file and do a find and replace on each file. I was doing this several times a day.  

I knew I could make a simple script to automate this for me, but I wanted to make something more accessible so that co-workers could use it also. So I quickly made this program in JavaFX with a user friendly GUI. The user can add or remove files to a queue and see all the files in the queue. The user can then run the script on all the files in the queue at once.

This will check if the added instruction already exists and skip the file. It will also check if there is any plotting at all or just cutting and skip the file if just cutting. It will inform the user when complete and how many files have been modified.

The regex and replacement strings are hard coded as they will never need to be changed for my purposes but the program could easily be modified to accept strings from user input.
