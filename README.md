Basic cryptography demonstration code
===============================

This is a project for demonstrating basic historical cryptographic techniques, such as substitution or rotation ciphers.

This is primarily meant as a gentle introduction to cryptography, not as usable code.

In addition, it is assumed that the text is in English, or at least a language that uses the same 26 letters as English.  A different dictionary file could be used for other languages.

# Installation

You will need to have both the git, and mvn command line tools installed.  This has been tested with Java 8, git 2.19.1, and Apache maven 3.6.0.

1. cd to your working directory
2. git clone https://github.com/onelineoff/crypto.git
3. mvn clean install
4. mvn javadoc:javadoc

# Rotation Cipher
One of the simplest methods of encypting data.  It involves rotating the letters of the alphabet.  For example, the Caesar Cipher rotates by 3, so there is a one to one substitution for each letter, like this:
`Plain Text      ABCDEFGHIJKLMNOPQRSTUVWXYZ`
`Encrypted Text  DEFGHIJKLMNOPQRSTUVWXYXABC`

This was useful in ancient times, but a simple program can crack this almost instantaneously.

The secret key is the number of positions to rotate by.  There are only 25 possibilities.  Rotating by 26 is the same as not encrypting at all.

# Substitution Cipher
This is only slightly more complex than the rotation cipher.

For each letter in the plain text, a different letter is substituted.

This is the simplest possible substitution cipher.

For example, a one to one substitution might be done like this:
`Plain Text      ABCDEFGHIJKLMNOPQRSTUVWXYZ`
`Encrypted Text  CLMQBAZVINODHGFEJKPRSTYXWU`

There are many more combinations than with the rotation cipher, but this too can be easily cracked by a modern computer. There are too many combinations to solve this by brute force, but some techniques are:
* Letters appear with different frequency.  The most common are: e,a,r,i,o,t,n,s.
* The most common 80 words are likely to appear with high frequency in any text.
* Certain bigrams (two letters in sequence) appear with the greatest frequency.  The most frequent are: th, he, in, er, an.

Knowing the above facts, the decrypter program can first narrow down the possibilities by analyzing the encrypted text, and looking at the frequency of the encrypted letters, as well as the most common encrypted words, and most common encrypted bigrams.

Using the above information, the number of possibilities becomes much smaller, and those can be examined in a brute force manner.

This can be further refined by breaking up the brute force search into two steps, taking advantage of the fact that the brute force search is proportional to n factorial, where n is the number of unknown letters, and if a + b = c, then a! + b! is much, much less than c!.



# Stegnography

Stegnography is the science of hiding information where it is not plainly visible.  There are many examples of this, going back to ancient Greece, when a man had his hair shaved, a messaged tattooed on his scalp,  and waited for the hair to grow back enough to hide the message.

Some physical techniques include hidden ink, and embedding messages in knitting patterns.

More modern examples include embedding hidden messages in audio or video files.



# Encoding

Encoding is different from encryption, in that the information is not easily human readable, but there is no secret key; the message can be encoded or decoded according to well known rules.  QR codes are an example of this.  A QR code can encode several thousand characters worth of data. Information can be hidden from the average person, but can be easily viewed by anyone who decodes the image, often with an app on a phone or tablet.

Messages can also be encoded before being encrypted, to defeat some of the simplistic decryption techniques used in this code sample.

# Links

https://en.wikipedia.org/wiki/Caesar_cipher

https://en.wikipedia.org/wiki/Substitution_cipher

https://en.wikipedia.org/wiki/Steganography

https://en.wikipedia.org/wiki/QR_code

https://www.qr-code-generator.com - QR image generator.
https://zxing.org/w/decode.jspx -  QR image decoder.

# Issues
1. More comments.  
4. Additional unit tests.
5. Lots of duplicate code. Clean up, and refactor.
6. Examine Character vs char, array vs List.
8. Move code from SubstitutionDecrypter to DictionaryUtil class.
10. Some methods in StringUtil should be in DictionaryUtil.

# Caveats

This is demonstration code, not meant to be useful in any practical way.  As a consequence, there are certain techniques that are not used here, such as using IoC/Spring.  This is intentional, to make the code as  easy to understand as possible.

Also, some type of logging library, such as log4j or slf4j, would normally be used.  System.out.println() is used for simplicity.

The code is of fair, not great quality.  There should be more comments, unit tests, and general refactoring and cleanup.

The assumption that the plaintext is in English is embedded throughout the code, and would require major refactoring to remove.  It is not intended that this code is ever used for anything except demonstration purposes, so I decided not to introduce the extra complexity.