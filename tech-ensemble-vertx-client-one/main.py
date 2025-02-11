import socket

# 서버 주소와 포트
HOST = '127.0.0.1'  # 서버의 IP 주소 (로컬 서버)
PORT = 1234  # 서버의 포트 번호

# 소켓 생성
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    # 서버와 연결
    s.connect((HOST, PORT))
    print(f"서버 {HOST}:{PORT}에 연결되었습니다.")

    # 서버로 메시지 전송
    message = "one Hello."
    s.sendall(message.encode())

    # 서버로부터 응답 받기
    while True:
        data = s.recv(1024)
        print(f"서버로부터 받은 메시지: {data.decode()}")