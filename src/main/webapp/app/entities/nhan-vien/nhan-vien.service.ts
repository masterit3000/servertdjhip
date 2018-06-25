import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { NhanVien } from './nhan-vien.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NhanVien>;

@Injectable()
export class NhanVienService {

    private resourceUrl =  SERVER_API_URL + 'api/nhan-viens';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(nhanVien: NhanVien): Observable<EntityResponseType> {
        const copy = this.convert(nhanVien);
        return this.http.post<NhanVien>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nhanVien: NhanVien): Observable<EntityResponseType> {
        const copy = this.convert(nhanVien);
        return this.http.put<NhanVien>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NhanVien>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NhanVien[]>> {
        const options = createRequestOption(req);
        return this.http.get<NhanVien[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NhanVien[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NhanVien = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NhanVien[]>): HttpResponse<NhanVien[]> {
        const jsonResponse: NhanVien[] = res.body;
        const body: NhanVien[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NhanVien.
     */
    private convertItemFromServer(nhanVien: NhanVien): NhanVien {
        const copy: NhanVien = Object.assign({}, nhanVien);
        copy.ngayTao = this.dateUtils
            .convertDateTimeFromServer(nhanVien.ngayTao);
        return copy;
    }

    /**
     * Convert a NhanVien to a JSON which can be sent to the server.
     */
    private convert(nhanVien: NhanVien): NhanVien {
        const copy: NhanVien = Object.assign({}, nhanVien);

        copy.ngayTao = this.dateUtils.toDate(nhanVien.ngayTao);
        return copy;
    }
}
