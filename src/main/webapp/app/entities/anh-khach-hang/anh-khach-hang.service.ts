import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AnhKhachHang } from './anh-khach-hang.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AnhKhachHang>;

@Injectable()
export class AnhKhachHangService {

    private resourceUrl =  SERVER_API_URL + 'api/anh-khach-hangs';

    constructor(private http: HttpClient) { }

    create(anhKhachHang: AnhKhachHang): Observable<EntityResponseType> {
        const copy = this.convert(anhKhachHang);
        return this.http.post<AnhKhachHang>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(anhKhachHang: AnhKhachHang): Observable<EntityResponseType> {
        const copy = this.convert(anhKhachHang);
        return this.http.put<AnhKhachHang>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AnhKhachHang>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AnhKhachHang[]>> {
        const options = createRequestOption(req);
        return this.http.get<AnhKhachHang[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AnhKhachHang[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AnhKhachHang = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AnhKhachHang[]>): HttpResponse<AnhKhachHang[]> {
        const jsonResponse: AnhKhachHang[] = res.body;
        const body: AnhKhachHang[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AnhKhachHang.
     */
    private convertItemFromServer(anhKhachHang: AnhKhachHang): AnhKhachHang {
        const copy: AnhKhachHang = Object.assign({}, anhKhachHang);
        return copy;
    }

    /**
     * Convert a AnhKhachHang to a JSON which can be sent to the server.
     */
    private convert(anhKhachHang: AnhKhachHang): AnhKhachHang {
        const copy: AnhKhachHang = Object.assign({}, anhKhachHang);
        return copy;
    }
}
