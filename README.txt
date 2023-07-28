README 

We were able to implement mosaicking correctly, support a script command, and expose it through the GUI.



How we implemented mosaicking in harmony with the design we were given...


The first thing we did was write a Mosaic class that implements the already written ICommand interface. This Mosaic class represents the command for mosaicking and is compatitible with all the previous uses of any other ICommand in the controller and view because the Mosaic class is an ICommand as well. We made this Mosaic class have one field (int seeds) to represent the number of seeds that will be randomly generated across the image. 


It also only has one method, "public void execute(ImageModel model, String name, String destination)" which all ICommands have and is what the controller calls to execute any command on an image. We noticed that in their other commands, this execute method calls a helper function in the image class that actually does the work of applying the change to each pixel across the entire image, so we decided to write a method in the image class called "mosaic".

This "mosaic" method in the image class required 2 private helper methods, private HashMap<IPixel, Point> generateSeeds(int numSeeds)  and private IPixel getNearestSeed(int curRow, int curCol, HashMap<IPixel, Point> seedMap). The first one takes in a desired number of seeds and generates a HashMap where the key is the IPixel that we are using as the seed, and the value is a Point, representing where exactly it is in the image (x,y). The second method takes in the current row, col that we are on and the hash map of all the seeds we generated, and returns an IPixel representing the seed that is closest to our current position. 

Now for the actual implementation of the "mosaic" method that uses these two helpers. 

The mosaic method first creates an empty 2d Pixel array with the same height and width of this image. (This is going to be the resulting image pixels)

Then it creates a hash map of seeds and their respective locations using generateSeeds (we call this hash map seedMap). 

Then it loops through every pixel in the image in a double for loop, and updates each pixel's RGB values to match those of it's nearest seed (using getNearestSeed mentioned above.

Once we have this new 2d Pixel array, we create a new image of same width and height but with this new pixel 2d array and return that as the result image. 


In the view, we simply added a Button corresponding to Mosaic and set its event listener to "mosaic".

In the ImageController, we added support for the command "mosaic" which works like the following. 

Mosaic 10 oldImageName newImageName

This command would apply a mosaic with 10 seeds to the image named "oldImageName" and the resulting image will be called "newImageName"


We also added a case for mosaic in the GUIController that runs a new Mosaic command on the image, and we passed in view.getMosaicSeeds() which is a helper method that we wrote in the view to make a popup come up for the user to enter in the desired number of seeds, almost identical to the way this group implemented their brighten command. 

