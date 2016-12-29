# :ant: antreminder [![Build Status](https://travis-ci.org/tolusalako/antreminder.svg?branch=master)](https://travis-ci.org/tolusalako/antreminder)
 [__Antreminder__](antreminder.csthings.net) is a free web app that notifies you when a class's status has changed. Its purpose is to simplify enrollment by reminding you whenever your class has a specific status. Currently only UCI's websoc is supported, but I do plan to expand it.

### Contributing
Tools:
* Java 8
* Gradle
* MySql Server

Change the connection configurations in `config/application.yml` and you're good to go.

```shell
# Build
gradle build

# Generate project
gradle eclipse
gradle idea

# Run
gradle bootRun
```      
Please focus on improving the current features and fixing any bugs before adding any new features.

### License
[MIT License](https://github.com/tolusalako/antreminder/blob/master/LICENSE.md)

Big thanks to [Mailgun](https://www.mailgun.com/) for proving 10K free emails per month.
Binoculars on the error page by mikicon [The Noun Project](https://thenounproject.com)
