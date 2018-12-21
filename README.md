# TirNaNog
TirNaNog Home Automation Project

TirNaNog is a Distributed Responsibility Home Automation project that aims to do 'simple' home automation.
The simplicity lies in the simplicity of the modules; a module is responsible for its own thing, and knowing TirNaNog.
What this means is that any module is a gateway to your TirNaNog environment, and that it isn't responsible for anything other than that and its own function.
For example, a tea making robot knows how to make tea and knows about TirNaNog. The lightswitch module knows ho to turn lights on and off, and about TirNaNog. So through the tea making robot you can access the lightswitch module which can control the lights, but the lightswitch module can also make tea through the tea making robot. Simple, isn't it? Each module is responsible for one thing and will tell the network of TirNaNog devices what it is capable of and allows them access to its functionality.
A screen device could have no other functionality than to be a screen and have a custom page set up that takes controls and sensor readings from other TirNaNog devices to create a control panel. But if the screen fails, everything can still be accessed through another TirNaNog device.
