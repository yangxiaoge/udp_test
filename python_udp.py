import socket
import sys, time
def send():
    cs = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    cs.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    cs.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
    network = '<broadcast>'
    while 1:
        print('+1...')
        cs.sendto(('This is a test').encode("utf-8"), (network, 10086))
        time.sleep(2)

send()
