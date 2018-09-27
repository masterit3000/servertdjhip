import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Images } from './images.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Images>;

@Injectable()
export class ImagesService {

    private resourceUrl =  SERVER_API_URL + 'api/images';

    constructor(private http: HttpClient) { }

    create(images: Images): Observable<EntityResponseType> {
        const copy = this.convert(images);
        return this.http.post<Images>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(images: Images): Observable<EntityResponseType> {
        const copy = this.convert(images);
        return this.http.put<Images>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Images>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Images[]>> {
        const options = createRequestOption(req);
        return this.http.get<Images[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Images[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Images = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Images[]>): HttpResponse<Images[]> {
        const jsonResponse: Images[] = res.body;
        const body: Images[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Images.
     */
    private convertItemFromServer(images: Images): Images {
        const copy: Images = Object.assign({}, images);
        return copy;
    }

    /**
     * Convert a Images to a JSON which can be sent to the server.
     */
    private convert(images: Images): Images {
        const copy: Images = Object.assign({}, images);
        return copy;
    }
}
