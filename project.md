
# Wiki Podcasts

The project is about providing podcasts of Wikipedia/Wikivoyage articles (and their text version) for travellers.

The articles will be brought depending on the location of the traveller (or his future location).

The articles will be available offline once fetched.

## Technically

Technically it is an opportunity to work with Android libraries. Instead of packaging everything in the app, there will be an Android library providing the core functionalities. This document explains the different blocks of the library.

### Providing location based on sensors

based on android.location facilities

### Providing location based on name (geocoding)

using android, osm or rome2rio geonames facilities

### Fetching Wikipedia articles based on location

using mediawiki API to provide articles

### Fetching Wikipedia articles based on title

using mediawiki API to provide articles

### Reading out loud the article

using Android TTS
