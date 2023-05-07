import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient } from '@angular/common/http';
import { delay, first, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private API = 'api/courses'

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Course[]>(this.API)
      .pipe(
        //Obtém a primeira resposta do servidor e finaliza o processo.
        first(),
        //Podemos usar o delay para testar o Spinner no carregamento da página.
        // delay(3000),
        tap(courses => console.log(courses))
      );
  }

  //Além de passar o que vamos enviar, devemos colocar como parâmetro os dados que irão no corpo
  save(record: Partial<Course>){
    if(record._id){
      return this.update(record);
    }
    return this.create(record);
  }

  loadById(id: string){
    return this.httpClient.get<Course>(`${this.API}/${id}`);
  }

  private create(record: Partial<Course>){
    return this.httpClient.post<Course>(this.API, record).pipe(first());
  }

  private update(record: Partial<Course>){
    return this.httpClient.put<Course>(`${this.API}/${record._id}`, record).pipe(first());
  }

  delete(id: string){
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }
}
