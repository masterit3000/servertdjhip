import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CuaHang } from './cua-hang.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CuaHang>;

@Injectable()
export class CuaHangService {

    private resourceUrl =  SERVER_API_URL + 'api/cua-hangs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(cuaHang: CuaHang): Observable<EntityResponseType> {
        const copy = this.convert(cuaHang);
        return this.http.post<CuaHang>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(cuaHang: CuaHang): Observable<EntityResponseType> {
        const copy = this.convert(cuaHang);
        return this.http.put<CuaHang>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CuaHang>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CuaHang[]>> {
        const options = createRequestOption(req);
        return this.http.get<CuaHang[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CuaHang[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CuaHang = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CuaHang[]>): HttpResponse<CuaHang[]> {
        const jsonResponse: CuaHang[] = res.body;
        const body: CuaHang[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CuaHang.
     */
    private convertItemFromServer(cuaHang: CuaHang): CuaHang {
        const copy: CuaHang = Object.assign({}, cuaHang);
        copy.ngayTao = this.dateUtils
            .convertDateTimeFromServer(cuaHang.ngayTao);
        return copy;
    }

    /**
     * Convert a CuaHang to a JSON which can be sent to the server.
     */
    private convert(cuaHang: CuaHang): CuaHang {
        const copy: CuaHang = Object.assign({}, cuaHang);

        copy.ngayTao = this.dateUtils.toDate(cuaHang.ngayTao);
        return copy;
    }
}
