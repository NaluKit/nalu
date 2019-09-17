# Migration v1 -> v2

To migrate an existing v1 application to v2, follow the instructions on this site.

## Moved classes
The following classes have been moved:

1. **IsContext**: `com.github.nalukit.nalu.client.application.IsContext` -> `com.github.nalukit.nalu.client.context.IsContex`

2. **RouterStateEvent**: `com.github.nalukit.nalu.client.router.event` -> `com.github.nalukit.nalu.client.event`

## Removed features
The following from version 1.x have been removed: 

1. **errorRoute**: See error handling doc for more informations on how to migratge (ToDo: here add link)
