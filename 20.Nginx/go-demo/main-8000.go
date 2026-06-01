package main

import (
	"fmt"
	"net/http"
)

// go run main.go
func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintf(w, "Hello, World!")
	})

	fmt.Println("Server is running on http://localhost:8000")
	http.ListenAndServe(":8000", nil)
}
