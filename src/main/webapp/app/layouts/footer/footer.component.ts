import {Component} from '@angular/core';
import {VERSION} from '../../app.constants';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html',
    styleUrls: [
        'footer.scss'
    ]
})
export class FooterComponent {

    version: String;
    today: number = Date.now();

    constructor() {
        this.version = VERSION ? 'v' + VERSION : '';
    }
}
