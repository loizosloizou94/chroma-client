# Java Chroma Client

An open-source Java library for the Chroma embedding database.

## Prerequisites

### Pull the ChromaDB Docker Image
```sh
docker pull chromadb/chromadb:latest
```


### Run ChromaDB in a Docker Container
```sh
docker run -d -p 8000:8000 chromadb/chromadb:latest
```

## What is Chroma DB
ChromaDB is an open-source vector database designed for storing and retrieving vector embeddings. Its primary function is to store embeddings with associated metadata for subsequent use by LLM.

## Connection
```java
    var chromaClient = ChromaClient.builder()
                .host("http://localhost:8000")
                .build();

    chromaClient.validateConnection();
```

## Operations
All ChromaDB operations are supported
- Create, Update, Delete Collections
- Insert, Update, Delete Embeddings
- Query Embeddings
- Where-Document and Where-Metadata Filter Builder
- Etc
