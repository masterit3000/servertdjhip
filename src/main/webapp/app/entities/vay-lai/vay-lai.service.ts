import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VayLai } from './vay-lai.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VayLai>;

@Injectable()
export class VayLaiService {

    private resourceUrl =  SERVER_API_URL + 'api/vay-lais';

    constructor(private http: HttpClient) { }

    create(vayLai: VayLai): Observable<EntityResponseType> {
        const copy = this.convert(vayLai);
        return this.http.post<VayLai>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vayLai: VayLai): Observable<EntityResponseType> {
        const copy = this.convert(vayLai);
        return this.http.put<VayLai>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VayLai>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VayLai[]>> {
        const options = createRequestOption(req);
        return this.http.get<VayLai[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VayLai = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VayLai[]>): HttpResponse<VayLai[]> {
        const jsonResponse: VayLai[] = res.body;
        const body: VayLai[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VayLai.
     */
    private convertItemFromServer(vayLai: VayLai): VayLai {
        const copy: VayLai = Object.assign({}, vayLai);
        return copy;
    }

    /**
     * Convert a VayLai to a JSON which can be sent to the server.
     */
    private convert(vayLai: VayLai): VayLai {
        const copy: VayLai = Object.assign({}, vayLai);
        return copy;
    }
}
