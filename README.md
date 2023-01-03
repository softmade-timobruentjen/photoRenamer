# photoRenamer
A command line interpretion of https://github.com/kaiwinter/exifrename

Simple renaming command line tool - rename your images based on the exif information in the jpeg (date created).
For Example:
asfjasfljlfdsjflwejrlewjrlksjlkjsdfljagsgl.jpeg (EXIF 2022-10-01 03:00:00AM) - will get 20221001_030000.jpeg

Usage:
java -jar photoRenamer-1.0-SNAPSHOT.jar PATH_TO_THE_IMAGES_TO_BE_RENAMED

I found it very helpful if your are presenting your photos to friends via usb-stick to get them sorted properly.

Uses [metadata-extractor](https://github.com/drewnoakes/metadata-extractor) which can extract metadata from this file types:
* JPEG
* TIFF
* WebP
* PSD
* PNG
* BMP
* GIF
* ICO
* PCX
* Camera Raw
  * NEF (Nikon)
  * CR2 (Canon) 
  * ORF (Olympus) 
  * ARW (Sony)
  * RW2 (Panasonic)
  * RWL (Leica)
  * SRW (Samsung)
