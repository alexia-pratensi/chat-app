import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { Message } from '../interfaces/message.interface';

@Injectable({
  providedIn: 'root'
})
export class MessageApiService {

  private baseUrl = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) { }

  // Fetches the users who sent messages to the agent
  public getMessagesFromUsersToAgent(agentId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/${agentId}`);
  }

  // Fetches the message history of the chat between the 2 users of the chat
  public loadMessageHistory(userId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.baseUrl}/history/${userId}`);
  }

}
