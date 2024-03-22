import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Observable} from 'rxjs';
import { Message } from '../interfaces/message.interface';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient!: Stomp.Client;

  constructor() { }

  //  Initializes the WebSocket connection and subscribes to the user's chat
  public initializeWebSocketConnection(userId: number): Observable<Message> {
    const socket = new SockJS('http://localhost:8080/wss');
    this.stompClient = Stomp.over(socket);
    return new Observable<Message>(observer => {
      this.stompClient.connect({}, () => {
        this.stompClient.subscribe(`/chat/user/${userId}`, (message: Stomp.Message) => {
          observer.next(JSON.parse(message.body));
        });
      });
    });
  }

  // Sends a message to the server
  public sendMessage(message: Message, userId: number): void {
    this.stompClient.send(`/app/chat.sendMessage/${userId}`, {}, JSON.stringify(message));
  }

}

