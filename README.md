## calculator3 project

Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git://github.com/dominiklanger/calculator3.git calculator3

2. Change directory into your clone:

        $ cd calculator3

3. Spin up the virtual machine
		
		$ vagrant up dev
		
4. SSH into the virtual machine

		$ vagrant ssh dev
		
5. Launch SBT (when you do it the first time, sbt will download some stuff - just be patient for some minutes):

        $ sbt

6. Compile everything and run all tests:

        > test

7. Start the application:

        > re-start

8. On your physical machine, browse to [http://10.100.199.200:8080](http://10.100.199.200:8080/sum/2?addend=10)

9. Stop the application:

        > re-stop
