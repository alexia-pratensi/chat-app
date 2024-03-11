import { User } from "./user.interface";

export interface Message {
    id?: number;
    content: string
    sourceUser: User;
    targetUser?: User;
    createdAt: string;
}