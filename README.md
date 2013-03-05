spring-security-gwt-template
============================

[Spring MVC](http://www.springsource.org/spring-framework)
and
[Spring Security](http://www.springsource.org/spring-security)
for the middleware, and
[Google Web Toolkit](http://code.google.com/p/google-web-toolkit/)
for the front-end.

Requires JRE 1.6+ compatible servlet container for running built code

CI Build via BuildHive:
[spring-security-gwt-template](https://buildhive.cloudbees.com/job/jzerbe/job/spring-security-gwt-template/)

Development
-------------
1. [Download](http://www.eclipse.org/downloads/) and install the Eclipse IDE.
If you have not done so before, I suggest the
_Eclipse IDE for Java Developers_ package.
2. [Download and install](https://developers.google.com/eclipse/docs/download)
the Google Plugin for Eclipse.
[More detailed instructions](https://developers.google.com/eclipse/docs/install-eclipse-4.2)
for installation on Eclipse 4.2.
3. Right-Click OR CMD-Click on the build.xml file and hit
__Run As -> Ant Build ...__.
4. Select the `clean` and `eclipse-dev` Ant build targets, so that the
target execution order looks like `clean, eclipse-dev`.
Next, launch with the `GWT_Debug.launch` config for debugging server and
front-end code. __OR.__ Use the `clean` and (default) `gwt-dev` Ant targets to
allow Ant to handle the entire launch, but you won't be able to debug
server-side code.
5. Click _Launch Default Browser_ or select a launch URL.
You may need to fiddle with the devmode permissions
[when on Windows](http://vraidsys.com/2012/10/gwt-devmode-on-windows-requires-localhost-permissions/).
6. Proceed with the plugin setup, restart browser, and enjoy coding!


FAQ
-------------
__Q__: What does
`GWT module 'com.vraidsys.MainEntryPoint' may need to be (re)compiled`
mean?

__A__: As currently configured, Spring auto-redirects all requests from a user that is not
authenticated to `/login.html`. In GWT devmode, on-the-fly compilation of Java to JavaScript
is served up to the browser based on the `?gwt.codesvr=` URL parameter. Thus,
when Spring auto-redirects, this parameter is lost and the browser does not know where to pull
its instructions from.

The two part process when working in devmode:

1. log in when prompted, which will set the appropriate auth cookies
2. click the devmode console "launch browser" button again
