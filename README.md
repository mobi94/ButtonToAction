## The app

This app has only 1 button. Clicking on this button performs one of the actions below. The performed
action is an enabled action that satisfies a few conditions (valid days, cool down) and has the
highest priority. The configuration of the actions is retrieved from:
https://s3-us-west-2.amazonaws.com/androidexam/butto_to_action_config.json

## The actions

The app supports the following actions:

1. **“Animation” action:** Animate the button to perform a 360 rotation.
2. **“Toast message” action:** Show a Toast message saying “Action is Toast!”
3. **“Call” action:** Open a “choose contact” screen. Choosing a contact calls that contact.
4. **“Notification” action:** Show a notification with the text “Action is Notification!” Clicking
   on that notification performs the same as the “Call action” (Open a “choose contact” screen.
   Choosing a contact calls that contact)
   _* Keep in mind that in the future, the app should be able to support actions that are more
   complex than the actions above._

## Choosing an action

When the user clicks on the button, one action should be chosen and performed.

## Config values that apply to all actions

The way an action is chosen is according to the configuration values retrieved from the
configuration json above.

The configuration values:

* **enabled (boolean)**
    * a disabled action can never be chosen
* **priority (int)**
    * if action X has a higher priority than action Y, action X will be selected
    * If two actions have the same priority, choose one at random.
* **valid days (days array)**
    * valid days to choose the action
    * for example, we can set the “Animation action” action to be choosable only on  
      Monday-Thursday
* **cool down period (long)**
    * cool down period between choosing an action that was already chosen
    * for example, if an action has a 3 day (1000\*60\*60\*24\*3) cool down period, it can’t  
      be chosen for 3 days after it was shown to the user
    * this should persist across app runs

## Filters that apply to specific actions

In addition to the configuration values above (which apply to all actions), the “Toast message
action” could only be chosen if there is an internet connection. Meaning, the “Toast message action”
will only be chosen if it satisfies all of the conditions from the configuration json AND there is
an internet connection.
