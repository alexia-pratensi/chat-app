import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: any;

  constructor() { }

  public initializeWebSocketConnection(userId: number): Observable<any> {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    return new Observable<any>(observer => {
      this.stompClient.connect({}, () => {
        this.stompClient.subscribe(`/chat/user/${userId}`, (message: any) => {
          observer.next(JSON.parse(message.body));
        });
      });
    });
  }

  public sendMessage(message: any, userId: number): void {
    this.stompClient.send(`/app/chat.sendMessage/${userId}`, {}, JSON.stringify(message));
  }

  public addUser(message: any, userId: number): void {
    this.stompClient.send(`/app/chat.addUser/${userId}`, {}, JSON.stringify(message));
  }
}

