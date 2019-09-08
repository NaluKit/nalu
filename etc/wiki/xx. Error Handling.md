



#TBD












## Error Route
There are several validations ins Nalu, that can cause an error. For example:

* a routing loop
* an illegal route
* using an non existing selector

In such cases Nalu will show an error. To display error in Nalu,

Nalu will route the error page. The current error is stored in a error model inside the router. Using `this.router.getNaluErrorMessage()` will give access to the error object.

**Once an error is displayed, the error object should be reseted by calling `this.router.clearNaluErrorMessage()`.**

### Using Error Route to display application errors (since 1.2.1)
Starting with version **1.2.1** Nalu supports using the error route to display application error messages. To do so, Nalu offers several new methods:
* **clearApplicationErrorMessage**: clears the application error message
* **setApplicationErrorMessage**: sets the application error message. This methods accepts two parameters:
  - **errorType**: a String that indicates the type of the error (value is to set by the developer)
  - **errorMessage**: the error message that should be displayed
* **getApplicationErrorMessage**: which returns the application error message.

In cases where an application error message and an error message from Nalu exists, you can use **getErrorMessageByPriority** to get the error message with the higher priority, which will always return the Nalu error message. Otherwise this method returns the application error message.

**Once an error is displayed, the error object should be reseted by calling `this.router.clearApplicationErrorMessage()`.**
