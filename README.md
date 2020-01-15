Basic cryptography demonstration code
===============================

This is a project for demonstrating basic historical cryptographic techniques, such as substitution or rotation ciphers.

This is primarily meant as a gentle introduction to cryptography, not as usable code.

# Rotation Cipher
One of the simplest methods of encypting data.  It involves rotating the letters of the alphabet.  For example, the Caeser Cipher rotates by 3, so there is a one to one substituation for each letter, like this:
Plain Text      ABCDEFGHIJKLMNOPQRSTUVWXYZ
Encrypted Text  XYZABCDEFGHIJKLMNOPQRSTUVW

This was useful in ancient times, but a simple program can crack this almost instantaneously.

The secret key is the number of positions to rotate by.  There are only 25 possibilities.  Rotating by 26 is the same as not encrypting at all.

# Substitution Cipher
This is only slightly more complex than the rotation cipher.
For each letter in the plain text, a different letter is substituted.
For example, a one to one substitution might be done like this:
Plain Text      ABCDEFGHIJKLMNOPQRSTUVWXYZ
Encrypted Text  CLMQBAZVINODHGFEJKPRSTYXWU

There are many more combinations than with the rotation cipher, but this too can be easily cracked by a modern computer.

# Stegnography

Stegnography is the science of hiding information where it is not plainly visible.  Although not stegnography per se, QR codes are a well-defined example of this.  A QR code can encode several thousand characters worth of data.
