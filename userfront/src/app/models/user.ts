import { Dob } from "./dob";
import { Location } from "./location";
import { Login } from "./login";
import { Name } from "./name";
import { Picture } from "./picture";
import { Registered } from "./registered";

export class User {
    id: string;
    gender: string;
    name: Name;
    location: Location;
    login: Login;
    email: string;
    dob: Dob;
    registered: Registered;
    phone: string;
    cell: string;
    picture: Picture;
    nat: string;

    constructor(
        id: string,
        gender: string,
        name: Name,
        location: Location,
        email: string,
        dob: Dob,
        registered: Registered,
        phone: string,
        cell: string,
        picture: Picture,
        nat: string,
        login: Login
    ) {
        this.id = id
        this.gender = gender
        this.name = name
        this.location = location
        this.email = email
        this.dob = dob
        this.registered = registered
        this.phone = phone
        this.cell = cell
        this.picture = picture
        this.nat = nat
        this.login = login;
    }


}
