import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BatHo } from './bat-ho.model';
import { createRequestOption } from '../../shared';
import { LichSuDongTien } from '../lich-su-dong-tien';
import { TRANGTHAIHOPDONG } from '../hop-dong';
export type EntityResponseType = HttpResponse<BatHo>;

@Injectable()
export class BatHoService {

    private resourceUrl = SERVER_API_URL + 'api/bat-hos';
    private quanLyVonUrl = SERVER_API_URL + 'api/quan-ly-von';
    private daoHoUrl = SERVER_API_URL + 'api/dao-bat-hos';
    private resourceUrlBatHoByCuaHang = SERVER_API_URL + 'api/bat-hos-by-cua-hang';
    // private resourceUrl =  SERVER_API_URL + 'api/bat-hos';
    private dongTien = 'dongtien';
    private resourceUrlTimBatHo = SERVER_API_URL + 'api/tim-bat-hos-by-ten-cmnd';
    private resourceUrlBatHoByHopDong = SERVER_API_URL + 'api/tim-bat-hos';
    private baocaoUrl = SERVER_API_URL + 'api/bao-cao-bat-hos';
    private baocaoNVUrl = SERVER_API_URL + 'api/bao-cao-bat-hos-nhanvien';
    private findbytrangthaiUrl = SERVER_API_URL + 'api/find-by-trangthai-bat-hos';
    private findbytrangthaihopdongUrl = SERVER_API_URL + 'api/find-by-trangthai-hopdong-bat-hos';
    constructor(private http: HttpClient) { }

    create(batHo: BatHo): Observable<EntityResponseType> {
        const copy = this.convert(batHo);
        return this.http.post<BatHo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }
    daoHo(batHo: BatHo,id: number): Observable<EntityResponseType> {
        const copy = this.convert(batHo);
        return this.http.post<BatHo>(`${this.daoHoUrl}/${id}`, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(batHo: BatHo): Observable<EntityResponseType> {
        const copy = this.convert(batHo);
        return this.http.put<BatHo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BatHo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BatHo[]>> {
        const options = createRequestOption(req);
        return this.http.get<BatHo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    quanLyVon(): Observable<HttpResponse<string>> {
        return this.http.get<string>(`${this.quanLyVonUrl}`, { observe: 'response' });
    }
    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BatHo = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<BatHo[]>): HttpResponse<BatHo[]> {
        const jsonResponse: BatHo[] = res.body;
        const body: BatHo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to BatHo.
     */
    private convertItemFromServer(batHo: BatHo): BatHo {
        const copy: BatHo = Object.assign({}, batHo);
        return copy;
    }

    /**
     * Convert a BatHo to a JSON which can be sent to the server.
     */
    private convert(batHo: BatHo): BatHo {
        const copy: BatHo = Object.assign({}, batHo);
        return copy;
    }

    findBatHoByTenOrCMND(query: any): Observable<HttpResponse<BatHo[]>> {
        // const options = createRequestOption(req);
        return this.http
            .get<BatHo[]>(`${this.resourceUrlTimBatHo}/${query}`, {
                observe: 'response'
            })
            .map((res: HttpResponse<BatHo[]>) =>
                this.convertArrayResponse(res)
            );
    }

    // Tùng add
    findByCuaHangId(id: number): Observable<HttpResponse<BatHo[]>> {
        return this.http.get<BatHo[]>(`${this.resourceUrlBatHoByCuaHang}/${id}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }

    findByHopDong(id: number): Observable<HttpResponse<BatHo[]>> {
        return this.http.get<BatHo[]>(`${this.resourceUrlBatHoByHopDong}/${id}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }
    baoCaoNV(start: Date, end: Date, id:number): Observable<HttpResponse<BatHo[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<BatHo[]>(`${this.baocaoNVUrl}/${startd}/${endd}/${id}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }
    baoCao(start: Date, end: Date): Observable<HttpResponse<BatHo[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<BatHo[]>(`${this.baocaoUrl}/${startd}/${endd}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }
    findByTrangThai(start: Date, end: Date, trangthai: TRANGTHAIHOPDONG): Observable<HttpResponse<BatHo[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<BatHo[]>(`${this.findbytrangthaiUrl}/${startd}/${endd}/${trangthai}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }
    findByTrangThaiHopDong( trangthai: TRANGTHAIHOPDONG): Observable<HttpResponse<BatHo[]>> {

        return this.http.get<BatHo[]>(`${this.findbytrangthaihopdongUrl}${trangthai}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }
    findByTrangThaiNV(start: Date, end: Date, trangthai: TRANGTHAIHOPDONG, id:number): Observable<HttpResponse<BatHo[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<BatHo[]>(`${this.findbytrangthaiUrl}/${startd}/${endd}/${trangthai}/${id}`, {observe: 'response' })
            .map((res: HttpResponse<BatHo[]>) => this.convertArrayResponse(res));
    }
    private convertDateToString(d: Date): String {

        let m = d.getMonth() + 1;
        let mm = m < 10 ? '0' + m : m;
        let day = d.getDate();
        let sday = day < 10 ? '0' + day : day;

        return d.getFullYear() + ' ' + mm + ' ' + sday;

    }

}
