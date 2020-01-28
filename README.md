Basic cryptography demonstration code
===============================

This is a project for demonstrating basic historical cryptographic techniques, such as substitution or rotation ciphers.

This is primarily meant as a gentle introduction to cryptography, not as usable code.

# Rotation Cipher
One of the simplest methods of encypting data.  It involves rotating the letters of the alphabet.  For example, the Caeser Cipher rotates by 3, so there is a one to one substituation for each letter, like this:
`Plain Text      ABCDEFGHIJKLMNOPQRSTUVWXYZ`
`Encrypted Text  DEFGHIJKLMNOPQRSTUVWXYXABC`

This was useful in ancient times, but a simple program can crack this almost instantaneously.

The secret key is the number of positions to rotate by.  There are only 25 possibilities.  Rotating by 26 is the same as not encrypting at all.

# Substitution Cipher
This is only slightly more complex than the rotation cipher.
For each letter in the plain text, a different letter is substituted.
For example, a one to one substitution might be done like this:
`Plain Text      ABCDEFGHIJKLMNOPQRSTUVWXYZ`
`Encrypted Text  CLMQBAZVINODHGFEJKPRSTYXWU`

There are many more combinations than with the rotation cipher, but this too can be easily cracked by a modern computer. There are too many combinations to solve this by brute force, but some techniques are:
* Letters appear with different frequency.  The most common are: e,a,r,i,o,t,n,s.
* The most common 80 words are likely to appear with high frequency in any text.
*  Certain bigrams (two letters in sequence) appear with the greatest frequency.  The most frequent are: th, he, in, er, an.

Knowing the above facts, the decrypter program can first narrow down the possibilities by analyzing the encrypted text, and looking at the frequency of the encrypted letters, as well as the most common encrypted words, and most common encrypted bigrams.

Using the above information, the number of possibilities becomes much smaller, and those can be examined in a brute force manner.

# Stegnography

Stegnography is the science of hiding information where it is not plainly visible.  Although not stegnography per se, QR codes are a well-defined example of this.  A QR code can encode several thousand characters worth of data. Information can be hidden from the average person, but can be easily viewed by anyone who decodes the image, often with an app on a phone or tablet.

# Issues
2. More comments.  Test javadoc.
3. Add pom.xml. 
3. Evaluate whether other third party libraries, such as Apache Commons, should be used to replace some of this code.
4. Additional unit tests.
5. Implement decrypter for substitution cipher.
6. Lots of duplicate code. Clean up.
9. Test Eclipse install from scratch from maven file.
10. Some methods in StringUtil should be in DictionaryUtil
11. Test mvn clean install.
12. Test mvn javadoc.

# Caveats

This is demonstration code, not meant to be useful in any practical way.  As a consequence, there are certain techniques that are not used here, such as using IoC/Spring.  This is intentional, to make the code as  easy to understand as possible.

Also, some type of logging library, such as log4j or slf4j, would normally be used.  System.out.println() is used for simplicity.

