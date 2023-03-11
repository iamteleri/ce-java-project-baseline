import { Pageable } from "./pageable";
import { User } from "./user";

export class UserPage {
    content: User[];
    totalElements: number;
    pageable: Pageable;
    totalPages: number;

    constructor(content: User[], totalElements: number, pageable: Pageable, totalPages: number) {
        this.content = content
        this.totalElements = totalElements
        this.pageable = pageable;
        this.totalPages = totalPages;
    }
}
