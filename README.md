# Textboard App

A simple Spring Boot application for 
creating, listing, and searching text entries; 
built to explore **Azure** cloud deployment 
and **GitHub Actions** CI/CD.

---

## Features

- Create, retrieve, and search text entries
- REST API for text management
- Multi-database support with H2 for testing and MS SQL for production
- GitHub Actions CI/CD pipeline with deployment to Azure

---

## Technology Stack

- **Spring** for the backend API (Boot, Web, Data)
- **Maven** for dependency management and .jar build
- **Docker** for replicable container image build
- **GitHub Actions** for CI/CD automation
- **Azure** for cloud deployment
- **Gitflow** based branching strategy
- **SemVer** versioning scheme
- **H2** in-memory database for unit testing
- **MS SQL** for production database

---

## Currently Deployed in Azure Container Apps:

- **main branch**: [textboard.ilkersahin.dev](https://textboard.ilkersahin.dev)  

- **develop branch**: [textboard.develop.ilkersahin.dev](https://textboard.develop.ilkersahin.dev)  

---

## API Documentation

### Entry Management API

#### Create Entry
- **Endpoint**: `/api/v1/entry`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "title": "String",
        "text": "String",
        "author": "String",
        "creationDate": "Instant"
    }
    ```
- **Response**:
    - **Status Code**: `201 Created`
    - **Body**:
      ```json
      {
          "id": "Long",
          "title": "String",
          "text": "String",
          "author": "String",
          "creationDate": "Instant"
      }
      ```

#### Get All Entries
- **Endpoint**: `/api/v1/entry`
- **Method**: `GET`
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**: Array of Entry objects.

#### Search Entries by Title
- **Endpoint**: `/api/v1/entry/search/title`
- **Method**: `GET`
- **Query Parameters**: `title`
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**: Array of Entry objects matching the title.

#### Search Entries by Title (Contains)
- **Endpoint**: `/api/v1/entry/search/title/contains`
- **Method**: `GET`
- **Query Parameters**: `title`
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**: Array of Entry objects containing the title.

#### Search Entries by Author
- **Endpoint**: `/api/v1/entry/search/author`
- **Method**: `GET`
- **Query Parameters**: `author`
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**: Array of Entry objects by author.

#### Search Entries by Author (Contains)
- **Endpoint**: `/api/v1/entry/search/author/contains`
- **Method**: `GET`
- **Query Parameters**: `author`
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**: Array of Entry objects containing the author.
