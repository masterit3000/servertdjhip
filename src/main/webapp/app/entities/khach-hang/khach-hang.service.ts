import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { KhachHang } from './khach-hang.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<KhachHang>;

@Injectable()
export class KhachHangService {

    private resourceUrl =  SERVER_API_URL + 'api/khach-hangs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(khachHang: KhachHang): Observable<EntityResponseType> {
        const copy = this.convert(khachHang);
        return this.http.post<KhachHang>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(khachHang: KhachHang): Observable<EntityResponseType> {
        const copy = this.convert(khachHang);
        return this.http.put<KhachHang>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<KhachHang>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<KhachHang[]>> {
        const options = createRequestOption(req);
        return this.http.get<KhachHang[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<KhachHang[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: KhachHang = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<KhachHang[]>): HttpResponse<KhachHang[]> {
        const jsonResponse: KhachHang[] = res.body;
        const body: KhachHang[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to KhachHang.
     */
    private convertItemFromServer(khachHang: KhachHang): KhachHang {
        const copy: KhachHang = Object.assign({}, khachHang);
        copy.ngayTao = this.dateUtils
            .convertDateTimeFromServer(khachHang.ngayTao);
        return copy;
    }

    /**
     * Convert a KhachHang to a JSON which can be sent to the server.
     */
    private convert(khachHang: KhachHang): KhachHang {
        const copy: KhachHang = Object.assign({}, khachHang);

        copy.ngayTao = this.dateUtils.toDate(khachHang.ngayTao);
        return copy;
    }
}
