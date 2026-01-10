package main

func main() {
	server := NewServer("127.0.0.1", 8888) //两个包都属于半包, 不用import
	server.Start()
}
