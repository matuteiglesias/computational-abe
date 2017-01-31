# computational-abe

## Instructions for installation - Pre requisites to run the Computational-ABE:

###  -MySQL database installed in the system with network connection available through 127.0.0.1:3306.:
  
  *Download mysql server and install in local system.
  
  *Configure my.cnf to open management port in 3306 (default port for mysql). There should be an uncommented line saying "port = 3306" .
  
  *Access the mysql CLI to create database simulations and user simulations with password simulations. Create database simulations. grant all privileges on simulations.* to 'simulations'@' ' identified by 'simulations'


###  -Eclipse IDE installed.

Importing the project to Eclipse:
  -In Eclipse, open a new workspace and create the computational-abe repository from git-hub
	-Click in Window->Show View->Other, write down git in the search box and select Git Repositories.
	-Once the view is opened, click on "Clone a Git Repository"  to add a new remote repository  and paste the following in URI text box: https://github.com/matuteiglesias/computational-abe/. Click next and select only margarita branch.
	-Once the repository is created, on the Project Explorer right click and select Import->Import and write down "git" in the search text box. Select "Project from Git", then "Existing local repository", select the computational-abe, click Next and then Finish.
		
### Creating the database schema from schema.sql.:
 In the project root there's a file called schema.sql that should be imported to the MySQL database. The easiest way is to open the mysql cli with the user simulations, database simulations and paste the content of the file.

Once all the steps are complete, you should be able to run the project from Eclipse Run button. The input for the simulations is stored in SimConfig.csv and the results will be stored in the simulations MySQL database.


## Agent Based Economics Model Simulator

### What is an ABM?

Agent Based Models (ABMs) for an economic system take advantage of the huge increase in computational power we've seen recent years, and emerges as a response to many flaws of mainstream or neoclassical models.
ABMs rely on letting a multiplicity of agents (representing households, firms, or banks, for example) to interact with each other and make decisions according to certain rules, hence letting the macro evolution of the system emerge from micro fundations.
These models are interesting when studying complex systems, network dynamics and micro-macro interactions in the economy.

### Why use java?
ABMs are especially suitable for object oriented paradigms as agents can be instantiations of different classes and their methods describe the scope of their actions.

### What is this project about?
In this case, we build up an implementation of an ABM where Keynesian (demand led) processes as well as Schumpeterian-like technological improvement fuel output growth.
The simulation will generate the agents, and let the system evolve, while computing a set of variables of interest (total output, growth, unemployment, inflation, productivity, etc etc). The output is a .csv file with the simulation results, plus a .txt file with a summary of the parameters you chose.
For detailed description, and a review of this simulation in action, you can check this [document](https://www.overleaf.com/read/hxfqmrtktwhp).

### The data_analysis.nb file...
Is a Mathematica notebook, you can use it to process the .csv out of the simulations very directly, and reproduce the plots shown in the description document above.

##### For general useful information links and resources on computational agnet based models, check out [Leigh Tesfatsion website](http://www2.econ.iastate.edu/tesfatsi/ace.htm)

Get in touch at: matuteiglesias at gmail.com
