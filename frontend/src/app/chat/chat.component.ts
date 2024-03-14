import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Message } from 'src/app/interfaces/message.interface';
import { User } from 'src/app/interfaces/user.interface';
import { ChatService } from 'src/app/services/chat.service';
import { MessageApiService } from 'src/app/services/message-api.service';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';

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
    this.userService.getCurrentUser().subscribe((user) => {
      this.currentUser$ = user;
      this.route.params.subscribe(params => {
        this.userChatId = +params['userId'];
        this.loadMessageHistory(this.userChatId);
      });
      this.chatService.initializeWebSocketConnection( this.userChatId).subscribe(message => {
        this.messages.push(message);
      });
    });
  }

  ngAfterViewChecked(): void{
    try {
      this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    } catch(err) { }
  }
  
  private loadMessageHistory(userId: number): void{
    this.messageApiService.loadMessageHistory(userId).subscribe((messages) => {
      this.messages = messages;
    });
  }
  
  protected sendMessage(): void {
    if (this.newMessage.trim() !== '') {
      const message: Message = {
        content: this.newMessage,
        sourceUser: this.currentUser$,
        createdAt: this.currentDateTime
      };
      console.log("this.userChatId" + this.userChatId);
      this.chatService.sendMessage(message, this.userChatId);
      this.newMessage = '';
    }
  }
  
}