intellij-decrypt
================

Small utility to decrypt passwords saved by Intellij IDEA. It works for passwords you forgot but they are still stored (encrypted) by Intellij IDEA. You have to supply the master password and the path to the security.xml file.

Usage
-----
Download the intellij-decrypt.jar file and run this in the folder you've saved it (for Linux):

    java -classpath .:intellij-decrypt.jar:lib/commons-codec-1.9.jar org.corneliudascalu.intellijdecrypt.Main


License
-----
Copyright 2014 Corneliu Dascalu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
