import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Page} from './page.model';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';
import {createRequestOption} from '../shared/model/request-util';

@Injectable()
export class PageService {

    private resourceUrl = 'api/page';

    constructor(private http: Http) {
    }

    create(page: Page): Observable<Page> {
        const copy = this.convert(page);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(page: Page): Observable<Page> {
        const copy = this.convert(page);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Page> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(page: Page): Page {
        const copy: Page = Object.assign({}, page);
        return copy;
    }
}
