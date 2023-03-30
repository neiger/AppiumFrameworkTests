# Mobile Automation Framework for Android

## Getting started

This is an upgrade version of the code that will be compatible with Appium v2. Also works
with appium v1.

### ====================================================

#### REQUIREMENTS 
Appium server installation v1: 
- brew install node      
- npm install -g appium  
- npm install wd       
- appium &

The needed dependencies are located in the ```pom.xml``` file.
To take in consideration:
1. TestNG v7.5 still works with Java v8. A newer version requires Java v11.
2. Selenium v4.8.1 still works with Java v8. Newer version are backward compatible.
3. For more info check this link [Java Client migration from 7 to 8](https://github.com/appium/java-client/blob/master/docs/v7-to-v8-migration-guide.md)

To execute the code, this framework must be downloaded first, 
the dependencies are listed in the pom.xml maven file. 

#### CLONE OR DOWNLOAD THE CODE
$ git clone https://github.com/neiger/AndroidFramework.git
$ git pull

Then proceed as normal with the POM pattern. 
Create a Screens package then a Tests package.
Locate all the UI elements and create the methods
Finally proceed with the test creation order
Use an xml config file to manage the executions.
