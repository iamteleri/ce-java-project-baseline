export class Filter {
    name: string;
    lastname: string;
    email: string;
    nationality: string;

    constructor(name: string, email: string, lastname: string, nationality: string) {
        this.name = name;
        this.email = email;
        this.lastname = lastname;
        this.nationality = nationality;
    }
}
