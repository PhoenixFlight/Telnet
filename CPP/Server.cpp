#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

main() 
{
	socket(AF_INET, SOCK_STREAM, 0)
}
