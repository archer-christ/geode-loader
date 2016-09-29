# geode-loader
Utility to load and remove dummy data to/from geode/gemfire

## How to run

Configure the pool details in the client-cache.xml file.
The regions specified in the below commands should be preexisting.
```
Available Commands  :
	1. create <1GB/1MB> <CustomerRegion>  ----- Loads 1GB/1MB size of values to the given region
	2. remove <1GB/1MB> <CustomerRegion>  ----- Removes 1GB/1MB worth of entries from the given region
	3. help                               ----- Prints help
	4. quit                               ----- Quit the program
```
```
Please enter a command to execute $>
create 1024MB /Customer
```