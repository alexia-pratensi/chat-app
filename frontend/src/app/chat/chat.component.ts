import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Message } from 'src/app/interfaces/message.interface';
import { User } from 'src/app/interfaces/user.interface';
import { ChatService } from 'src/app/services/chat.service';
import { MessageApiService } from 'src/app/services/message-api.service';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, AfterViewChecked {
  @ViewChild('messageContainer') private messageContainer!: ElementRef;

  messages: Message[] = [];
  newMessage: string = '';
  currentUser$!: User ;
  currentDateTime = new Date().toISOString();
  userChatId!: number;

  constructor(private chatService: ChatService,
              private userService: UserService,
              private messageApiService: MessageApiService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    // Fetching the currently logged-in user
    this.userService.getCurrentUser().pipe(take(1)).subscribe((user) => {
      this.currentUser$ = user;
      // Subscribing to URL parameter changes to get the ID of the "USER" of the chat (not the agent's ID)
      this.route.params.pipe(take(1)).subscribe(params => {
        this.userChatId = +params['userId'];
        this.loadMessageHistory(this.userChatId);
      });
      // Initializing WebSocket connection for real-time updates
      this.chatService.initializeWebSocketConnection(this.userChatId).pipe(take(1)).subscribe(message => {
        // Adding the new received message to the messages list
        this.messages.push(message);
      });
    });
  }

  // Scroll to the bottom of the chat container when a new message is added
  ngAfterViewChecked(): void{
    try {
      this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    } catch(err) { }
  }
  
  // Fetches the message history of the chat between the 2 users of the chat
  private loadMessageHistory(userId: number): void{
    this.messageApiService.loadMessageHistory(userId).pipe(take(1)).subscribe((messages) => {
      this.messages = messages;
    });
  }
  
  protected sendMessage(): void {
    // Checking that the message is not empty
    if (this.newMessage.trim() !== '') {
      const message: Message = {
        content: this.newMessage,
        sourceUser: this.currentUser$,
        createdAt: this.currentDateTime
      };
      // Sending the message via the chat service
      console.log("this.userChatId" + this.userChatId);
      this.chatService.sendMessage(message, this.userChatId);
      this.newMessage = '';
    }
  }
  
}