ReadMe
Program: uavcontrols5
Objective: Update the unmanned aerial vehicle control simulation program 
           to include a game mode.
Date of Completion: 9/2/16


Run Instructions:
In the shell, in the file which contains the package uavcontrols5,
the images folder and the highScores.txt file, run:
			javac uavcontrols5/Main.java
			java uavcontrols5.Main
The program will create its own window and begin running.
All interfacing with the program happens through this window.
To stop the program, simply exit the window.

Notes:
At the current time, the program is unfinished. Several necessary parts of the
game were left unimplemented due to time considerations, to poor time 
management, and to lack of foresight. While the hostile planes are near fully 
implemented, replete with methods for providing challenge to the player, there
is no method that generates hostile planes. During the process of changing the 
player so that it would be more responsive to the user in accelerating and 
controlling, its movement was removed and wasn't replaced. A fullscreen mode
for the game was flirted with, but key presses were not registering. The idea of 
adding "graphical states" which were originally intended to somewhat replicate
the game-state concept occurred late in the coding process and so was 
implemented somewhat superficially, instead of becoming a more integral
and useful idea. 
In truth, the lack of pre-planning on this section of the assignment is really 
what extended it beyond any reasonable length - I would spend long, long hours 
working on classes or functions that I later realized would be worse than 
useless and would end up removing entirely. Every time I started working on an
unimportant feature, I would realize that I would need to rewrite large swathes 
of code to make it fit, and I would start in on that. You can see some of that 
in the concept of the variable cursor design, which I must have spent two weeks
trying to get to work, and the fullscreen mode, which is still unfinished.


System Design:

Inheritance Diagram:

GameUAVCursors

GameUAVGraphicalButton
    GameUAVConfirmButton
    GameUAVControlsButton
    GameUAVControlsPauseButton
    GameUAVDenyButton
    GameUAVEnterQuitDialogButton
    GameUAVQuitButton
    GameUAVResumeButton
    GameUAVReturnButton
    GameUAVScoreButton
    GameUAVStartButton

GameUAVGraphicalState
    GameUAVActiveGameState
    GameUAVControlsMenuState
    GameUAVExitDialogState
    GameUAVGameOverState
    GameUAVMainMenuState
    GameUAVPauseState
    GameUAVQuitDialogState
    GameUAVScoresMenuState

GameUAVLaser    

GameUAVMouseManager

GameUAVPlaneTypes

GameUAVScore

GameUAVScreens

GameUAVWalls

Main

UAVCanvas

UAVColors

UAVMap

UAVModes

UAVModeSelector

UAVPlane
    GameUAVPlane
        GameUAVHostileFighterPlane
        GameUAVHostileStrikerPlane
        GameUAVPlayer
    SimUAVPlane
        SimUAVDeliveryPlane
        SimUAVToyPlane

SimUAVButton
    SimUAVAnimateButton
    SimUAVClockwiseTurnButton
    SimUAVColorButton
    SimUAVDecreaseAltitudeButton
    SimUAVDecreaseAngularSpeedButton
    SimUAVDecreaseSpeedButton
    SimUAVFillButton
    SimUAVFinalizeButton
    SimUAVFinalizeCreatedPlaneButton
    SimUAVGridButton
    SimUAVIncreaseAltitudeButton
    SimUAVIncreaseAngularSpeedButton
    SimUAVIncreaseSpeedButton
    SimUAVRepairPlaneButton
    SimUAVScaleButton
    SimUAVWiddershinsTurnButton

SimUAVInfoPane

SimUAVMenu

SimUAVPlaneCreatorWindow

SimUAVPlaneTypes

SimUAVTool
    SimUAVModIDTool
    SimUAVNumericalTool
        SimUAVModAltTool
        SimUAVModAngSpeedTool
        SimUAVModDirTool
        SimUAVModSpeedTool
    SimUAVTextualTool
        SimUAVModColorTool
    
SimUAVToolBar



Aggregation Diagram:
What exists and where everything is located is mode-dependent

Aggregation Diagram (Game):
Main owns the map;
The map owns the player, the walls, the array of planes, the array of lasers, 
    the canvas, and the mouse manager;
The canvas owns the graphical states;
The graphical states own the graphical buttons;


Main
    UAVMap
        GameUAVMouseManager
        GameUAVPlayer
        GameUAVPlane [array which contains both GameUAVHostileStriker and GameUAVHostileFighter]
        GameUAVLaser [array thereof]
        UAVCanvas
            GameUAVActiveGameState
            GameUAVControlsMenuState
                GameUAVReturnButton
            GameUAVExitDialogState
                GameUAVConfirmButton
                GameUAVDenyButton
            GameUAVGameOverState
                GameUAVReturnButton
            GameUAVMainMenuState
                GameUAVStartButton
                GameUAVControlsButton
                GameUAVScoreButton
            GameUAVPauseState
                GameUAVResumeButton
                GameUAVQuitButton
                GameUAVControlsPauseButton
            GameUAVQuitDialogState
                GameUAVConfirmButton
                GameUAVDenyButton
            GameUAVScoresMenuState
                GameUAVReturnButton




Aggregation Diagram (Simulation):
Main owns the map, the button pane and the menu;
The map owns the array of planes and the canvas;
The tool bar owns the buttons and initializes tools;
The menu owns nothing, but it initializes the Info Pane and the Tools, which are
             separate from main's hierarchy because they create their own frames


Main
    UAVMap
        SimUAVPlane [array which contains instances of SimUAVToyPlane 
                     and of SimUAVDeliveryPlane]
        UAVCanvas
    SimUAVToolBar
        SimUAVAnimateButton
        SimUAVClockwiseTurnButton
        SimUAVColorButton
        SimUAVDecreaseAltitudeButton
        SimUAVDecreaseAngularSpeedButton
        SimUAVDecreaseSpeedButton
        SimUAVFillButton
        SimUAVFinalizeButton
        SimUAVFinalizeCreatedPlaneButton
        SimUAVGridButton
        SimUAVIncreaseAltitudeButton
        SimUAVIncreaseAngularSpeedButton
        SimUAVIncreaseSpeedButton
        SimUAVRepairPlaneButton
        SimUAVScaleButton
        SimUAVWiddershinsTurnButton
    SimUAVMenu


SimUAVInfoPane
SimUAVModAltTool
SimUAVModAngSpeedTool
SimUAVModColorTool
SimUAVModDirTool
SimUAVModIDTool
SimUAVModSpeedTool




Accreditation 

(many of my problems were solved through google searches and this
list contains the sites that I consulted in working on this project.
Some sites on this list actually have no direct influence on the final 
program, because the sections of code they helped write were removed)
https://docs.oracle.com/javase/8/docs/api/javax/swing/JCheckBox.html
https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
https://blog.udemy.com/for-each-loop-java/
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/JSpinner.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/SpinnerNumberModel.html
http://docs.oracle.com/javase/7/docs/api/java/awt/GridLayout.html
http://stackoverflow.com/questions/12589494/align-text-in-jlabel-to-the-right
http://stackoverflow.com/questions/4286759/how-to-show-hide-jpanels-in-a-jframe
http://docs.oracle.com/javase/tutorial/uiswing/components/jcomponent.html#layout
https://docs.oracle.com/javase/8/docs/api/java/awt/GridLayout.html
https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridLayoutDemoProject/src/layout/GridLayoutDemo.java
https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
http://docs.oracle.com/javase/7/docs/api/java/lang/Math.html
http://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/DialogDemo.java
https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
http://stackoverflow.com/questions/8214958/the-getsource-and-getactioncommand
http://stackoverflow.com/questions/18735762/using-boxlayout-as-vertical-flowlayout-to-hold-jpanel
https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ComboBoxDemo2Project/src/components/ComboBoxDemo2.java
https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormatterFactoryDemoProject/src/components/FormatterFactoryDemo.java
https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html
http://docs.oracle.com/javase/7/docs/api/javax/swing/JFrame.html
https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
http://stackoverflow.com/questions/2744851/exporting-non-public-type-through-public-api
https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html
http://stackoverflow.com/questions/3930210/java-int-to-string-integer-tostringi-vs-new-integeri-tostring
http://stackoverflow.com/questions/13510641/add-controls-vertically-instead-of-horizontally-using-flow-layout
http://stackoverflow.com/questions/1954674/can-i-make-swing-jbuttons-have-smaller-margins
http://stackoverflow.com/questions/2559527/non-static-variable-cannot-be-referenced-from-a-static-context
http://docs.oracle.com/javase/7/docs/api/java/lang/Math.html
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
http://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
http://stackoverflow.com/questions/260666/can-an-abstract-class-have-a-constructor
https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Dimension.html
http://docs.oracle.com/javase/7/docs/api/java/awt/Shape.html
https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
https://docs.oracle.com/javase/7/docs/api/java/awt/geom/AffineTransform.html#scale(double,%20double)
http://stackoverflow.com/questions/8560810/aligning-jmenu-on-the-right-corner-of-jmenubar-in-java-swing
https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
https://docs.oracle.com/javase/7/docs/api/java/awt/BasicStroke.html#BasicStroke(float,%20int,%20int)
https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
http://stackoverflow.com/questions/12018321/combine-two-graphics-objects-in-java
http://www.java2s.com/Code/Java/Swing-JFC/Comboboxcombinesabuttonoreditablefieldandadropdownlist.htm
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=ComboBoxModel
https://docs.oracle.com/javase/7/docs/api/java/awt/event/ComponentListener.html
https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html
http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=create+a+list+of+available+values+inside+an+enum+type+Java
https://docs.oracle.com/javase/7/docs/api/javax/swing/DefaultComboBoxModel.html
http://stackoverflow.com/questions/36701/struct-like-objects-in-java
http://www.java2s.com/Code/Java/2D-Graphics-GUI/Drawcanvaswithcolorandtext.htm
http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawwithLine2DDoubleandEllipse2DDouble.htm
https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html
http://stackoverflow.com/questions/22084373/exponential-operator-in-java
https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#readAllLines-java.nio.file.Path-java.nio.charset.Charset-
https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html
http://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=g.draw%20triange
http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html#drawChars(char[],%20int,%20int,%20int,%20int)
https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html#setComposite(java.awt.Composite)
https://docs.oracle.com/javase/7/docs/api/java/awt/GridBagLayout.html
http://stackoverflow.com/questions/13395114/how-to-initialize-liststring-object-in-java
http://docs.oracle.com/javase/tutorial/uiswing/components/button.html#looks
https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#itemapi
https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html
https://docs.oracle.com/javase/tutorial/uiswing/components/toolbar.html
https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
https://docs.oracle.com/javase/tutorial/uiswing/events/propertychangelistener.html
http://www.w3schools.com/colors/colors_picker.asp
https://docs.oracle.com/javase/7/docs/api/javax/swing/ImageIcon.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/ImageIcon.html#ImageIcon(java.lang.String)
https://www.cs.umd.edu/~clin/MoreJava/Intro/expr-mod.html
http://stackoverflow.com/questions/12063599/get-propertychangelistener-notification-on-static-variables-class-variables
http://stackoverflow.com/questions/3449826/how-do-i-find-the-inverse-tangent-of-a-line
http://stackoverflow.com/questions/9851688/how-to-align-left-or-right-inside-gridbaglayout-cell
http://stackoverflow.com/questions/7522022/how-to-delete-stuff-printed-to-console-by-system-out-println
http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
http://stackoverflow.com/questions/2839508/java2d-increase-the-line-width
http://stackoverflow.com/questions/2839508/java2d-increase-the-line-width
http://stackoverflow.com/questions/9710497/shortest-way-to-cast-a-2d-int-array-into-2d-double
http://stackoverflow.com/questions/11479356/why-does-gridbaglayout-anchor-not-work-for-multiple-buttons
https://blog.idrsolutions.com/2015/03/java-8-consumer-supplier-explained-in-5-minutes/
http://alvinalexander.com/blog/post/jfc-swing/swing-faq-list-fonts-current-platform
http://alvinalexander.com/blog/post/jfc-swing/swing-faq-list-fonts-current-platform
http://stackoverflow.com/questions/6098137/java-swing-aligning-radiobuttons
http://stackoverflow.com/questions/5512891/java-base-to-power-of-n-problem-in-java
http://www.tutorialspoint.com/java/lang/math_toradians.htm
http://math.hws.edu/javanotes/c3/s6.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html#getVisibleRect()
http://stackoverflow.com/questions/13583521/join-two-arrays-in-java
https://docs.oracle.com/javase/8/docs/api/javax/swing/JRadioButton.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/JTabbedPane.html#setMnemonicAt(int,%20int)
https://docs.oracle.com/javase/8/docs/api/javax/swing/JToolBar.html
https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyListener.html
https://docs.oracle.com/javase/7/docs/api/java/util/ListIterator.html
http://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbers
http://docs.oracle.com/javase/6/docs/api/java/lang/Math.html#ceil%28double%29
https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html
https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#atan(double)
https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#atan2(double,%20double)
https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#atan(double)
https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#atan(double)
https://en.wikipedia.org/wiki/Matrix_multiplication#Matrix_product_.28two_matrices.29
https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
http://www.oracle.com/technetwork/java/tbg-media-137102.html#Play
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=mouseinfo%20java
https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseListener.html
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=non-static%20method%20cannot%20be%20referenced%20from%20a%20static%20context
http://stackoverflow.com/questions/3153337/get-current-working-directory-in-java
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=pause%20button
https://docs.oracle.com/javase/7/docs/api/java/awt/Point.html
https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Point2D.html
https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Point2D.Double.html
https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Point2D.Double.html
http://docs.oracle.com/javase/8/docs/api/java/awt/PointerInfo.html#getLocation%28%29
https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html#translate(int,%20int)
https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
https://docs.oracle.com/javase/7/docs/api/java/util/Random.html
https://docs.oracle.com/javase/tutorial/essential/io/file.html
https://docs.oracle.com/javase/tutorial/essential/io/file.html#common
https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html
https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Rectangle2D.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/SpinnerNumberModel.html
https://docs.oracle.com/javase/7/docs/api/javax/swing/SpinnerNumberModel.html
http://stackoverflow.com/questions/1043872/are-there-any-built-in-methods-in-java-to-increase-font-size
http://stackoverflow.com/questions/14284754/java-center-text-in-rectangle
https://docs.oracle.com/javase/7/docs/api/java/awt/font/TextAttribute.html#KERNING
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
https://docs.oracle.com/javase/tutorial/2d/advanced/transforming.html
https://docs.oracle.com/javase/tutorial/java/concepts/interface.html
http://stackoverflow.com/questions/19025868/what-is-the-meaning-r-carriage-return-in-java-can-any-one-give-a-small-example
https://docs.oracle.com/javase/7/docs/api/java/awt/GraphicsConfiguration.html
https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html
https://docs.oracle.com/javase/tutorial/essential/environment/env.html
https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getProperties--
https://docs.oracle.com/javase/tutorial/extra/fullscreen/exclusivemode.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Window.html
https://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Frame.html#ICONIFIED
https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html#maps
https://docs.oracle.com/javase/8/docs/api/javax/swing/JComponent.html#getInputMap--
https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html#actionmap
https://docs.oracle.com/javase/tutorial/uiswing/misc/action.html#actionapi
https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html#VK_ESCAPE
https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter/25005082#25005082
https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#approach9
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html#Color(float,%20float,%20float,%20float)
http://stackoverflow.com/questions/4274606/how-to-change-cursor-icon-in-java
https://docs.oracle.com/javase/7/docs/api/java/awt/Cursor.html
http://stackoverflow.com/questions/1009607/how-can-i-edit-a-jpg-image-through-java
https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
https://docs.oracle.com/javase/tutorial/essential/exceptions/try.html
https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html
http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html#BufferedImage(java.awt.image.ColorModel,%20java.awt.image.WritableRaster,%20boolean,%20java.util.Hashtable)
https://docs.oracle.com/javase/7/docs/api/java/awt/image/AffineTransformOp.html#TYPE_BICUBIC
https://docs.oracle.com/javase/7/docs/api/java/awt/geom/AffineTransform.html#rotate(double,%20double,%20double)
https://docs.oracle.com/javase/7/docs/api/java/io/File.html#File(java.lang.String)
https://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html#drawImage(java.awt.image.BufferedImage,%20java.awt.image.BufferedImageOp,%20int,%20int)
https://docs.oracle.com/javase/tutorial/2d/images/drawonimage.html
https://docs.oracle.com/javase/7/docs/api/java/awt/Toolkit.html#createCustomCursor(java.awt.Image,%20java.awt.Point,%20java.lang.String)
http://stackoverflow.com/questions/6575578/convert-a-graphics2d-to-an-image-or-bufferedimage
https://docs.gimp.org/en/gimp-tutorial-quickie-separate.html
http://docs.gimp.org/2.8/en/gimp-images-out.html#file-png-save
http://stackoverflow.com/questions/16423503/java-graphics2d-transparent-background
http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#cos(double)
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/cos
http://stackoverflow.com/questions/12223133/key-bindings-and-holding-down-keys
https://docs.oracle.com/javase/7/docs/api/javax/swing/KeyStroke.html#getKeyStroke(java.lang.Character,%20int)
http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
http://stackoverflow.com/questions/11178908/full-screen-window-wont-get-keyboard-input-using-keylistener-or-keyboardfocusma
https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
http://stackoverflow.com/questions/4297857/an-enclosing-instance-that-contains-my-reference-is-required
http://stackoverflow.com/questions/7486012/static-classes-in-java
https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html#getInputMap(int)
http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
https://docs.oracle.com/javase/8/docs/api/javax/swing/KeyStroke.html#getKeyStroke-java.lang.String-
http://stackoverflow.com/questions/17984912/java-key-bindings-not-working
https://en.wikipedia.org/wiki/Space_bar
https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html#value
https://docs.oracle.com/javase/7/docs/api/javax/swing/JFrame.html#getGlassPane()
http://stackoverflow.com/questions/2106367/listen-to-jframe-resize-events-as-the-user-drags-their-mouse
https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseWheelEvent.html