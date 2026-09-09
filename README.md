# IPL Management System (DBMS & JavaFX)

A comprehensive desktop management platform for the Indian Premier League (IPL) built with Java, JavaFX, FXML, and a MySQL relational database. The application provides dynamic interfaces for admins to schedule matches, enter match results, manage teams and venues, and publish posts, while giving fan users access to live team updates, fixture feeds, and match statistics.

---

## Key Features

* **Authentication & Role Access**: Secure user signup/login workflow (`LoginController`, `SignupController`, `UserDAO`) distinguishing admin management capabilities from fan views.
* **Team & Venue Management**: Complete CRUD operations for franchise teams and tournament grounds/stadiums (`ManageTeamsController`, `ManageVenuesController`).
* **Match Scheduling & Results**: Tools for scheduling upcoming fixtures and posting verified match outcomes/scores (`ScheduleMatchController`, `EnterResultController`).
* **Interactive Fan Feed**: Dedicated dashboard for following favorite teams, browsing match schedules, and reading announcement posts (`FeedController`, `FollowTeamsController`).
* **Data Access Object (DAO) Layer**: Clean architectural separation using dedicated DAO classes (`MatchDAO`, `TeamDAO`, `VenueDAO`, `UserDAO`) for structured database persistence and query execution.

---

## Project Structure

```text
IPL_Project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/      # FXML UI Controllers (Admin Dashboard, Feeds, Scheduling, etc.)
│   │   │   ├── dao/             # Data Access Objects (MatchDAO, TeamDAO, UserDAO, VenueDAO)
│   │   │   ├── model/           # Entity Models (Match, MatchResult, Team, User, Venue, Post)
│   │   │   ├── util/            # Utilities (DBConnection.java for JDBC relational mapping)
│   │   │   └── org/example/     # Application entry point (Main.java)
│   │   └── resources/
│   │       └── view/            # JavaFX FXML view templates
│   └── test/                    # Unit tests
└── pom.xml                      # Maven dependency configuration

## How to Run

### Prerequisites

Make sure the following are installed:

- Java JDK
- IntelliJ IDEA or another Java IDE
- Maven
- MySQL
