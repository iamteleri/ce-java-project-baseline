import { Coordinates } from "./coordinates";
import { Street } from "./street";
import { Timezone } from "./timezone";

export class Location {

    street: Street;
    city: string;
    state: string;
    country: string;
    postcode: string;
    coordinates: Coordinates;
    timezone: Timezone;

    constructor(
        street: Street,
        city: string,
        state: string,
        country: string,
        postcode: string,
        coordinates: Coordinates,
        timezone: Timezone
    ) {
        this.street = street
        this.city = city
        this.state = state
        this.country = country
        this.postcode = postcode
        this.coordinates = coordinates
        this.timezone = timezone
    }

}
