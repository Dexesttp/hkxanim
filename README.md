# HKXAnim - HKX animation creator for Fallout 4

## What is HKX Anim ?
HKXAnim is a tool to convert FBX animations into the HKX format.

The output files are uncompressed animations, which are larger than the usual animation files.

This tool is NOT INTENDED to convert HKX animations into another format. The recommended way to do that is either to ask the original author for the FBX file or to not do it - you shouldn't have any reason to do that anyways.

## How can I use HKXAnim ?

At the moment, you'll have to use the command line or .bat files to convert a FBX file into HKX.

You will also need Java 8 or higher to run the tool installed as a command line tool.

Run the `java -jar hkxanim.jar help` command for a list of all options. Note that the first part, `java -jar hkxanim.jar` is always required before all the below options.

 - `java -jar hkxanim.jar <filename>.fbx` Creates a HKX file called `<filename>.hkx` that contains the animation of the given FBX file.

Options :
- `-o <filename>.fbx`, `--output <filename>.fbx` Set the name of the ouput file.

## Basic troubleshooting

#### I don't know how to open the command line !
Refer to [Google](http://lmgtfy.com/?q=Windows+command+line+tutorial) for that. This tool isn't user friendly yet. If you really don't want to learn that, you will need to wait for a graphical interface tool, which may come only in several months.

#### I'm getting one of the following errors while using the tool :
```
'java' is not recognized as an internal or external command,
operable program or batch file.
```
- You don't have Java installed on this computer. Please use [Google](http://lmgtfy.com/?q=How+do+I+install+Java+8+windows) to know how to do that.
- You have Java installed but "not added to your path". Please use [Google](http://lmgtfy.com/?q=add+java+to+path) for that.

```
Exception in thread "main" java.lang.UnsupportedClassVersionError: com/dexesttp/hkxpack/cli/ConsoleView : Unsupported major.minor version 52.0
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:800)
        at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
        at java.net.URLClassLoader.defineClass(URLClassLoader.java:449)
        at java.net.URLClassLoader.access$100(URLClassLoader.java:71)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:361)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:355)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(URLClassLoader.java:354)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:425)
        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:308)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:358)
        at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:482)
```
You don't have Java 8 on this computer, or you aren't using it. Please refer to Google to find why.

#### I got this weird bug while converting a file. Did I mess something up ?
Maybe, maybe not. You can either talk to the dev team on the forums you knew about this tool from, or if you're really motivated open an issue by following [the rules below](#submitting-an-issue).

#### I'm actually a llama in a suit and don't know how to use a computer.
Refer to [this useful web page](http://reddit.com/r/ooer) for how to proceed. There's currently no opened feature request to support a LUI (Llama User Interface), so if you're interested please open an issue. There's also no LUI for the GitHub issues interface, so please contact GitHub about that at the same time.

## Can I use HKXAnim as a part of my other project ?

The HKXAnim _codebase_ is licensed under the MIT license.

However, keep in mind that the HKXAnim ecosystem (especially the files given with HKXAnim, located or hinted under the `/files/` and `/src/main/resources/files/` folder here) are not MIT, and only provided here as references for studies. You can't modify and redistribute these files. (I know this is kind of stupid, but I feel like the downside of having these files MIT is too big).

Therefore, the packed version of HKXAnim (the .jar file you can download under "releases"), which contains these files, is also licensed under a custom, not GNU-compliant, license. This is to prevent any legal issues with the aforementionned files.

So, **You can't include the HKXAnim `.jar` in your project directly**. You'll have to provide a link to the relevant release here.

**THIS ESPECIALLY MEANS YOU CAN'T UPLOAD THE HKXAnim `.jar` TOOL TO BETHSOFT/NEXUS !** Let us do it if we want, or go here if we don't want.

If you want to try to make a "GNU-compilant" version of this tool based on the source code, go ahead. But I'm really not sure this would be a good idea, so don't complain if you receive anything legal-ish after that.

## Developpers information.

### Developping HKXAnim

HKXAnim is a tool that's based on another project, [HKXPack](http://github.com/dexesttp/hkxpack). You'll need the 'base' jar version of this project to build HKXAnim.

You can import the project as a standard Maven project.

### Extra files

The `/files/` and `/src/main/resources/files` folder may be incomplete or empty, however it is required for the tool to properly work.

### Submitting a Pull Request

There's no best practices" yet, so just keep in mind to submit clean and documented changes. Never change an already existing behavior of a public interface without arguing why. If you cnahe an internal method behavior, update the documenation accordingly. Try to keep the codebase clean. These kinds of things.

A pull request should usually be targeted at the `dev` branch, or at a feature branch. This isn't required, but if you don't want to do that prepare to explain why with really convincing arguments.

### Submitting an issue

An issue must have the following informations :

#### Bug report
- The tool version
- The file(s) that caused the error
- The description of the wanted result
- The description of the actual result
- The error log, or why there's no error log (e.g. the file wasn't created, the file was created but was wrong)
- An estimation about the bug's impact (minor (the file works) to important (the file doesn't work) to serious (several files don't work) to critical (all files don't work))
- Any other relevant information

#### Feature request
- A description of the feature
- A brief explaination of what the feature would be useful for
- A justification about why a feature in THIS tool and not ANOTHER tool
- An estimation of the feature's priority (minor - important - urgent)

#### Codebase issue
- A link to the incriminated part of the code
- The problem with the part of the code
- An information about the problem's severity (minor - important - serious - critical)

## Contributors

DexesTTP - Lordescobar666

## License

The codebase is MIT, but the releases and "extra" files are custom.
It's a little bit complex, so see the `LICENSE` file.