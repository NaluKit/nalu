# Migration v1 -> v2

To migrate an existing v1 application to v2, follow the instructions on this site.

## Moved classes
The following classes have been moved:

1. **IsContext**: `com.github.nalukit.nalu.client.application.IsContext` -> `com.github.nalukit.nalu.client.context.IsContex`

2. **RouterStateEvent**: `com.github.nalukit.nalu.client.router.event` -> `com.github.nalukit.nalu.client.event`

## New required method
* **Shell:** you need to override the `detachShell`-method with the code needed to remove the shell.

## Removed features
The following features from version 1.x have been removed:

1. **errorRoute**: See error handling doc for more information on how to migrate ([Error Handling](https://github.com/NaluKit/nalu/wiki/12.-Error-Handling))
2. **plugins**: See Multi Module Project ([Multi Module Feature](https://github.com/NaluKit/nalu/wiki/15.-Multi-Module-Project))
