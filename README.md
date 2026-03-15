# New2Comics

This application allows new comic book readers to gain information on what comic books they might be interested in. The area of focus is a consumer information site, as users are presented with information such as series overviews, genres, publishers, key characters, and beginner-friendly recommendations. The application supports informed purchasing decisions by allowing users to view titles, explore popularity and themes, and understand entry points into different comic universes.

PROTOTYPE SPECIFICATIONS
- Entities with full CRUD: Volume, User
- Relationships with full CRUD: preferredCharacter, preferredGenre, preferredPublisher, preferredTeam
- Other entities have partial CRUD (full CRUD not yet needed)
- User authentication not yet implemented: CRUD operations that are conditional on user logins currently have a default user_id of 0
- User login page does not work, it is a placeholder for when we learn to implement authentication
- Profile page: clicking checkboxes then clicking the Submit button adds the users preferences, which are then used in searching on the Browse page (this is a temporary solution to populating user preferences)
- Browse page: can search by genre, publisher, volume (aka comic series), team. Results will be sorted by user preferences. See VolumeRepository.java for the SQL query
- Home page: clicking a genre or universe (aka publisher) button leads to the Browse page with the genre name being entered into the search bar
- Home page: recommendations list is ordered according to user preferences, popularity list is ordered according to popularity (number of likes)
- Data in data.sql is currently dummy data
