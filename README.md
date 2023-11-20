
# CarWash - Semi RESTful/Thymeleaf site.

<br />
<div align="center">
  <a href="https://github.com/Borovaneca/CarWash">
    <img src="src/main/resources/static/images/logo.png" alt="Logo" width="80" height="80">
  </a>
    <h2>CarWash</h2>
  <p>
<!--     <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a> -->
    <a href="https://github.com/Borovaneca/CarWash/issues">Report Bug</a>
    ·
    <a href="https://github.com/Borovaneca/CarWash/issues">Request Feature</a>
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


## Getting Started

Please make sure you follow the steps, steo by step!

### Installation

_In order to run CarWash you need to:_

1. <a href="https://github.com/Borovaneca/CarWash/archive/refs/heads/master.zip">DOWNLOAD</a> the repo.
2. Set up environment variable `${MYYSQL_PORT}` 
   ```yaml
   spring.datasource.url: jdbc:mysql://localhost:${MYSQL_PORT}/carwash?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC
   ```
3. Do the same for those variables too:
   ```yaml
   admin.username: ${ADMIN_USERNAME}
   admin.password: ${ADMIN_PASSWORD}
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>
