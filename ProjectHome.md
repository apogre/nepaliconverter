# नेपाल एस्पेरान्तो संघको Conversions Tools #

Aŭtoro: Jacob Nordfalk,
Nepala Esperanto-Asocio

Dankon al/Thanks to: Heidel Press, Dilibazaar, Kathmandu:
  * tipar-konvertaj tabeloj / font conversion tables
  * presi / pressing नेपाली-एस्पेरान्तो शब्दकोश - Nepali-Esperanto-vortaro

# अन्तराष्त्रिय भाषल एस्पेरान्तो सिकौ  ...  संसार भरी साथी बनाऔ #
http://www.esperanto.org.np/



This is a collection of tools for converting Nepali documents between different representations.

Currently there exists several special fonts for Nepali text, like Preeti and Kantipur. Documents using these fonts are difficult to handle and cannot be f.ex. spell checked.

This converter tool can convert these to text to standard Unicode representation.


Converter and Deconverter are softwares used to convert Non-Unicode fonts to Unicode fonts and vice-versa respectively. The Font Converter is used to convert Non-Unicode (Preeti, Himali, Jaga Himal, Kanchan, Kantipur) fonts to Unicode fonts (Kalimati, Kanjirowa, Madan, and Samanata) and the DeConverter is used to convert Unicode fonts to Non-Unicode font.

For ready-to-run binaries of the old Font Converter and Deconverter and other resources see Madan Puraskar Pustakalaya's home page: http://madanpuraskar.org/


# Talk #
```
Dear All,
This is an invitation to you all to the upcoming FOSS Ka Kura (FKK) which is being organized by Madan Puraskar Pustakalya (MPP). Please find the details of the programme below:


Topic: "Formatted Nepali Text Conversion"

Venue: Yala Maya Kendra, Lalitpur, PatanDhoka
Date: Friday January 9, 2009
Time: 4 P.M - 5 P.M

Presentation of a new Open Source tool for i.a. converting mixed Nepali documents using Nepali fonts/from to Unicode.

The converter is in several ways revolutionary in comparision to what exists right now:

VERSATILE:
- Supports formatted (OpenOffice) documents natively, and HTML, RTF through plugins.
- All formatting (font size, underline, italic,...) is kept intact.
- Not limited to text, but also spreadsheets, presentations etc can be converted.
- Mixed text with Nepali and non-devanagari (English) text can be converted (i.e. documents using mixed fonts Preeti, Arial, Kantipur, Courier, etc are converted correctly, with all formatting intact)

USER FRIENDLY AND EASY:

- much more user friendly than existsing tools. F.eks. user does not need to know about or specify text encodings
- clipboard conversion
- conversion tables are kept in user friendly spreadsheets (one for each font), which an (educated) user can maintain even with no programming skills.


Jacob Nordfalk is a Danish programmer and assistant professor in CS, is living in Nepal, Kathmandu and have i.a. written 3 books about Java programming (http://javabog.dk).

The project is 80% complete and available from http://code.google.com/p/nepaliconverter/ . It will be a big step forward for Digital Nepal... if volunteers are found to take over and finish the project before until April 2009, when Jacob moves permanently to live in Denmark.

The talk will present how the (Java) converter is working. Knowing some Java is fine, but there are other activities (transliteration schemes and font conversion tables and quality assurance/testing, use case analysis etc) where just general computer knowledge is required to contribute to the project.

Regards,
Bal Krishna Bal
Madan Puraskar Pustakalaya
Lalitpur, PatanDhoka
Nepal
```


# Help, or we are closing! #

The project is currently maintained by me, Jacob Nordfalk, Nepala Esperanto-Asocio (NESPA), as Madan Puraskar Pustakalaya doesent have the resources.


Ive done 80% of the work and the converter i revolutionary in comparision to what exists right now:
  1. formatted Supports OpenOffice documents natively, and HTML, RTF (perhaps Ill even allow Word documents:-) through plugins
  1. mixed Preeti, Kantipur, Courier, mixed formats etc are converted correctly, with all formatting intact
  1. conversion tables are kept in user friendly spreadsheets (one for each font)
  1. clipboard conversion
  1. much more user friendly than existsing tools. F.eks. user does not need to know about or specify text encodings
  1. a lot of other things


All in all a big step forward for Digital Nepal !!!

But I need a Nepali co-developer!

You'll have to know some Java and be ready to do (or delegate) the spreadsheet work for the font conversion tables
(see http://code.google.com/p/nepaliconverter/source/browse/trunk/ConversionTools4/res/preeti_unicode.ods for an example).

Perhaps some company could assign a developer to this and get great publicity when its finished and they can publish it in their own name (must remain Open Source, of course).
I suppose 2 months on half time work for a developer is needed.

There is also a small presentation at http://nepaliconverter.googlecode.com/svn/trunk/ConversionTools4/documents/presentation.odp (you need http://OpenOffice.org installed)

Please contact me at jacob.nordfalk@gmail.com if you can help.


## Try the beta ##

The beta is only showing a fraction of the functionalities, but will give you an idea og the next generation converter in action.

Just download from http://code.google.com/p/nepaliconverter/downloads/list , unzip the file and run 'run.bat' (just double click on it)

You need to have Java installed (http://java.com).

If you dont have a document to test with, try this one:  http://nepaliconverter.googlecode.com/svn/trunk/ConversionTools4/documents/esperanto-introduction.odt

Jacob