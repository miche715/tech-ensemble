import json
import socket

# 서버 주소와 포트
HOST = '127.0.0.1'  # 서버의 IP 주소 (로컬 서버)
PORT = 7000  # 서버의 포트 번호

# JSON 형식의 메시지 생성
message_data = {
    "verticleType": "three",  # 원하는 verticleType 설정
    "commandType": "a",  # 원하는 commandType 설정
    "message": "Hello, Vert.x!"
}

# JSON 문자열로 변환
json_message = json.dumps(message_data)

# 소켓 생성
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    # 서버와 연결
    s.connect((HOST, PORT))
    print(f"서버 {HOST}:{PORT}에 연결되었습니다.")

    # JSON 메시지 전송
    s.sendall(json_message.encode())

    # 서버로부터 응답 받기
    while True:
        data = s.recv(1024)
        if not data:
            break
        print(f"서버로부터 받은 메시지: {data.decode()}")