package main

import "net/http"
import "github.com/gin-gonic/gin"

type user struct {
	ID    string `json:"id"`
	Name  string `json:"title"`
	Email string `json:"artist"`
}

// A slice of users, we don't have a DB for now.
var users = []user{
	{ID: "1", Name: "Test User", Email: "test@example.com"},
	{ID: "2", Name: "Real User", Email: "user@example.com"},
}

func getUsers(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, users)
}

func main() {
	router := gin.Default()
	router.GET("/api/users", getUsers)

	router.Run("localhost:8080")
}
