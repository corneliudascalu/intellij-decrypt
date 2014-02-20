intellij-decrypt
================

Small utility to decrypt passwords saved by Intellij IDEA. It works for passwords you forgot but they are still stored (encrypted) by Intellij IDEA. You have to supply the master password and the path to the security.xml file.

Usage
-----
Download the intellij-decrypt.jar file and run this in the folder you've saved it (for Linux):

    java -classpath .:intellij-decrypt.jar:lib/commons-codec-1.9.jar org.corneliudascalu.intellijdecrypt.Main

